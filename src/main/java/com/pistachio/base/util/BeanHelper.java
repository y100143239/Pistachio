package com.pistachio.base.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

/**
 * 描述:	 javabean工具类，bean与map间的互转
 */
public class BeanHelper
{
    /**
     * BeanHelper 日志
     */
    private static Logger logger = Logger.getLogger(BeanHelper.class);

    /**
     * 把一个bean中的属性转化到map中
     *
     * @param bean       bean对象
     * @param properties 存放bean中属性的map对象
     */
    public static void beanToMap(Object bean, Map properties)
    {
        if (bean == null || properties == null)
        {
            return;
        }

        try
        {
            Map map = BeanUtils.describe(bean);
            map.remove("class");
            for(Iterator iter=map.keySet().iterator();iter.hasNext();)
            {
                String key = (String)iter.next();
                Object value = map.get(key);
                properties.put(key,value);
            }
        }
        catch (Exception ex)
        {
            logger.error("读取bean属性出错", ex);
        }
    }

    /**
     * 把一个map中的所有属性值设置到bean中
     *
     * @param properties 含有属性的map对象
     * @param bean       需要被设置属性的对象
     */
    public static void mapToBean(Map properties, Object bean)
    {
        if (properties == null || bean == null)
        {
            return;
        }
        try
        {
            for (Iterator iter = properties.keySet().iterator(); iter.hasNext();)
            {
                String key = (String) iter.next();
                Object value = properties.get(key);
                BeanUtils.setProperty(bean, key, value);
            }
        }
        catch (Exception ex)
        {
            logger.error("设置bean属性出错", ex);
        }
    }
}
