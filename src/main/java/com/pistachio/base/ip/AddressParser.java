package com.pistachio.base.ip;

import com.pistachio.base.ip.IPSeeker.IPLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将纯真IP的地址IpLocation解析为包含国家|省份|城市信息的规范的Location( 未处理情况INNA,局域网)
 *
 * @author 黄泽海
 */
public class AddressParser
{
    private static final String UNKNOWN = "未知";

    /**
     * @param ipLocation
     * @return
     */
    public static Location location(IPLocation ipLocation)
    {
        if ( ipLocation == null )
        {
            return null;
        }
        if ( ipLocation.getCountry() == null || ipLocation.getCountry().isEmpty() )
        {
            return new Location(UNKNOWN, UNKNOWN, UNKNOWN);
        }
        // 主要通过ipLocation的country属性获取省份和城市信息，area属性作为补充
        // 将概率高的写在多选语句前面，以提高匹配效率
        Pattern inlandPattern = Pattern
                .compile("^([^省]+省|广西|西藏|新疆|宁夏|内蒙古|香港|澳门|[^市]+市)([^市]+市|[^州]+州|[^县]+县|[^盟]+盟|[^旗]+旗|[^区]+区|.*)?([^市]+市|[^县]+县|[^旗]+旗|.*)?(.*)$");
        Matcher inlandMatcher = inlandPattern.matcher(ipLocation.getCountry());

        Pattern schoolPattern = Pattern.compile("^([^学]+大学|[^院]+学院)(.*)$");
        Matcher schoolMatcher = schoolPattern.matcher(ipLocation.getCountry());

        Pattern chinaPattern = Pattern.compile("^中国.*$");
        Matcher chinaMatcher = chinaPattern.matcher(ipLocation.getCountry());
        // 优先处理国内城市，因为国内城市概率远远高于国外
        if ( inlandMatcher.matches() )
        {
            String province = inlandMatcher.group(1);
            String districtCity = inlandMatcher.group(2);// 地级市
            String countryCity = inlandMatcher.group(3);// 县级市
            String city = districtCity;
            // 如果存在县级市县，则将城市名取为县级市县
            if ( countryCity != null && Pattern.matches("^([^市]+市|[^县]+县)$", countryCity) )
            {
                city = countryCity;
            }
            return new Location(province, city);
        }
        else if ( schoolMatcher.matches() )
        {
            String school = schoolMatcher.group(1);
            // 大陆大学、台湾大学在在ipLocation的country属性出现，而国外的大学是在area属性中出现
            // 将根据大学地理表判断大学所在的城市，否则国家取中国，忽略省份城市信息
            if ( SchoolLocation.yellowPages.containsKey(school) )
            {
                return SchoolLocation.yellowPages.get(school);
            }
            else
            {
                return new Location("中国", null, null);
            }
        }
        else if ( chinaMatcher.matches() )
        {
            // 匹配中国石油等
            return new Location("中国", null, null);
        }
        else
        {
            // 香港特别行政区、澳门特别行政区、外国、大范围地区（亚太地区等）
            // 仅仅保留国家或者地区信息
            String country = ipLocation.getCountry();
            String province = null;
            String city = null;
            // 如果有些州|省份没有州|省关键字，并且它太长，那么，它将被忽略掉
            Pattern abroadPattern = Pattern.compile("^([^州]+州|[^省]+省)(.*)$");
            Matcher abroadMatcher = abroadPattern.matcher(ipLocation.getArea());
            if ( ipLocation.getArea() != null && abroadMatcher.matches() )
            {
                province = abroadMatcher.group(1);
            }
            else if ( ipLocation.getArea() != null && ipLocation.getArea().length() >= 2
                    && ipLocation.getArea().length() <= 4 )
            {
                // 如果没有关键词，但词的长度在[2-4]之间，那么它几乎就是州或者省的名字。如:美国 纽约
                province = ipLocation.getArea();
            }
            Location location = new Location(country, province, city);
            return location;
        }
    }

}
