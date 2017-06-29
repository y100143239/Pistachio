package com.pistachio.base.util;

import com.pistachio.base.jdbc.DataRow;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 描述:  数据类型转换
 */
public class ConvertHelper
{
    private static Logger logger = Logger.getLogger(ConvertHelper.class);

    /**
     * 把字串转化为整数,若转化失败，则返回0
     *
     * @param str 字串
     * @return
     */
    public static int strToInt(String str)
    {
        if ( str == null )
        {
            return 0;
        }

        try
        {
            return Integer.parseInt(str);
        }
        catch (Exception ex)
        {
            //logger.debug(str+"转换成int类型失败，请检查数据来源", ex);
            return 0;
        }

    }

    /**
     * 把字串转化为长整型数,若转化失败，则返回0
     *
     * @param str 要转化为长整型的字串
     * @return
     */
    public static long strToLong(String str)
    {
        if ( str == null )
        {
            return 0;
        }

        try
        {
            return Long.parseLong(str);
        }
        catch (Exception ex)
        {
            // logger.debug(str + "转换成long类型失败，请检查数据来源", ex);
            return 0;
        }

    }

    /**
     * 把字串转化为Float型数据,若转化失败，则返回0
     *
     * @param str 要转化为Float的字串
     * @return
     */
    public static float strToFloat(String str)
    {
        if ( str == null )
        {
            return 0;
        }
        try
        {
            return Float.parseFloat(str);
        }
        catch (Exception ex)
        {
            //logger.debug(str + "转换成float类型失败，请检查数据来源", ex);
            return 0;
        }

    }

    /**
     * 把字串转化为Double型数据，若转化失败，则返回0
     * @param str 要转化为Double的字串
     * @return
     */
    public static double strToDouble(String str)
    {
        if ( str == null )
        {
            return 0;
        }
        try
        {
            return Double.parseDouble(str);
        }
        catch (Exception ex)
        {
            //logger.debug(str + "转换成double类型失败，请检查数据来源", ex);
            return 0;
        }

    }

    /**
     * 描述：字符转为一个元素的Object数组
     * 作者：李建
     * 时间：Sep 1, 2011 11:35:48 AM
     * @param str
     * @return
     */
    public static Object[] strToArry(String str)
    {
        if ( str == null )
        {
            return null;
        }
        else
        {
            return new Object[] { str };
        }
    }

    /**
     * 对于一个字符串数组，把字符串数组中的每一个字串转换为整数。
     * 返回一个转换后的整型数组，对于每一个字串若转换失败，则对
     * 应的整型值就为0
     *
     * @param strArray 要转化的数组
     * @return
     */
    public static int[] strArrayToIntArray(String[] strArray)
    {
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++)
        {
            intArray[i] = strToInt(strArray[i]);
        }
        return intArray;
    }

    /**
     * 描述：数组转换为字符串
     
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param arg0 数组
     * @return
     */
    public static String arrToString(Object[] arg0)
    {
        if ( arg0 == null )
        {
            return "";
        }
        return arrToString(arg0, ",");
    }

    /**
     * 描述：数据转换为字符串
     * 作者：
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param arg0 数组
     * @param arg1 取数组个数
     * @return
     */
    public static String arrToString(Object[] arg0, int arg1)
    {
        if ( arg0 == null )
        {
            return "";
        }
        return arrToString(arg0, ",", arg1);
    }

    /**
     * 描述：数据转换为字符串
     * 作者：
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param arg0 数组
     * @param arg1 间隔符号
     * @return
     */
    public static String arrToString(Object[] arg0, String arg1)
    {
        return arrToString(arg0, arg1, 0);
    }

    /**
     * 描述：数据转换为字符串
     * 作者：
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param arg0 数组
     * @param arg1 间隔符号
     * @param arg2 取数组个数
     * @return
     */
    public static String arrToString(Object[] arg0, String arg1, int arg2)
    {
        if ( arg0 == null || arg0.length == 0 )
        {
            return "";
        }
        else
        {
            StringBuffer sb = new StringBuffer();
            int length = arg0.length;
            if ( arg2 != 0 )
            {
                length = arg2;
            }
            for (int i = 0; i < length; i++)
            {
                if ( arg1 == null )
                    arg1 = "";
                sb.append(arg0[i]).append(arg1);
            }
            sb.delete(sb.lastIndexOf(arg1), sb.length());
            return sb.toString();
        }
    }

    /**
     * 描述：List转换为字符串
     * 作者：
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param list List数据
     * @return
     */
    public static String listToString(List list)
    {
        return listToString(list, ",");
    }

    /**
     * 描述：List转换为字符串
     * 作者：
     * 时间：Mar 18, 2010 2:40:43 PM
     * @param list List数据
     * @param separation 间隔符
     * @return
     */
    public static String listToString(List list, String separation)
    {
        return arrToString(listToStringArray(list), separation);
    }

    /**
     * 描述：字串数据元素包装
     * 作者：
     * 时间：May 23, 2010 7:12:52 PM
     * @param sArr 字串数据
     * @param pre 前缀
     * @param aft 后缀
     * @return
     */
    public static String[] strArrDoPack(String[] sArr, String pre, String aft)
    {
        return strArrDoPack(sArr, pre, aft, 1, 0);
    }

    /**
     * 描述：字串数据元素包装
     * 作者：
     * 时间：May 23, 2010 7:12:52 PM
     * @param sArr 字串数据
     * @param pre 前缀
     * @param aft 后缀
     * @param num 生成个数
     * @return
     */
    public static String[] strArrDoPack(String[] sArr, String pre, String aft, int num)
    {
        return strArrDoPack(sArr, pre, aft, num, 0);
    }

    /**
     * 描述：字串数据元素包装
     * 作者：
     * 时间：May 23, 2010 7:10:27 PM
     * @param sArr 字串数据
     * @param pre 前缀
     * @param aft 后缀
     * @param num 生成个数
     * @param step 数字值1：加，-1：减，0：不变
     * @return
     */
    public static String[] strArrDoPack(String[] sArr, String pre, String aft, int num, int step)
    {
        String[] arr = null;
        if ( sArr != null )
        {
            boolean isAdd = false;
            if ( step > 0 )
            {
                isAdd = true;
            }

            if ( num < 0 )
            {
                num = 1;
            }

            arr = new String[sArr.length * num];
            int icount = 0;
            for (int i = 0; i < num; i++)
            {
                for (int j = 0; j < sArr.length; j++)
                {
                    if ( StringHelper.isNotEmpty(pre) )
                    {
                        arr[icount] = pre + sArr[j];
                    }
                    if ( StringHelper.isNotEmpty(aft) )
                    {
                        arr[icount] += aft;
                    }
                    icount++;
                }

                boolean b = false;
                if ( step != 0 )
                {
                    pre = stepNumInStr(pre, isAdd);
                    b = true;
                }
                if ( !b )
                {
                    if ( step != 0 )
                    {
                        aft = stepNumInStr(aft, isAdd);
                    }
                }
            }

            /*
             arr = new String[sArr.length];
             for(int i=0;i<sArr.length;i++)
             {
             boolean b = false;
             arr[i] = sArr[i];
             if(StringHelper.isNotEmpty(pre))
             {

             String stemp = pre+arr[i];
             if(step!=0)
             {
             pre = stepNumInStr(pre,isAdd);
             b = true;
             }
             arr[i]=stemp;
             }
             if(StringHelper.isNotEmpty(aft))
             {
             String stemp = arr[i]+aft;
             if(!b)
             {
             if(step!=0)
             {
             aft = stepNumInStr(stemp,isAdd);
             }
             }
             arr[i]=arr[i]+aft;
             }
             }*/
        }
        return arr;
    }

    /**
     * 描述：生成字符串
     * 作者：
     * 时间：Mar 18, 2010 2:48:39 PM
     * @param arg0 字符串元素
     * @param arg1 生成个数
     * @return
     */
    public static String createStr(String arg0, int arg1)
    {
        if ( arg0 == null )
        {
            return "";
        }
        return createStr(arg0, arg1, ",");
    }

    /**
     * 描述：生成字符串
     * 作者：
     * 时间：Mar 18, 2010 2:48:39 PM
     * @param arg0 字符串元素
     * @param arg1 生成个数
     * @param arg2 间隔符号
     * @return
     */
    public static String createStr(String arg0, int arg1, String arg2)
    {
        if ( arg0 == null )
        {
            return "";
        }
        else
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arg1; i++)
            {
                if ( arg2 == null )
                    arg2 = "";
                sb.append(arg0).append(arg2);
            }
            if ( sb.length() > 0 )
            {
                sb.delete(sb.lastIndexOf(arg2), sb.length());
            }

            return sb.toString();
        }
    }

    /**
     * 描述：生成字符串数据
     * 作者：
     * 时间：Mar 18, 2010 2:48:39 PM
     * @param arg0 字符串元素
     * @param arg1 生成个数
     * @return
     */
    public static String[] createStrArr(String arg0, int arg1)
    {
        if ( arg0 == null )
        {
            return null;
        }
        else
        {
            String[] arr = new String[arg1];
            for (int i = 0; i < arg1; i++)
            {
                arr[i] = arg0;
            }

            return arr;
        }
    }

    /**
     * 描述：行列转换及格式化日期（没有行标题,默认转换后列名）
     * 		转换后列名默认为 "line"+i;
     * 作者：
     * 时间：May 12, 2010 8:21:02 PM
     * @param dataList query查出的List数据
     * @param lineNum 转换后列的个数
     * @param rowLieldsName 转换后行字段名
     * @param dateField 只取日期的字段名
     * @return
     */
    public static List lineToRow(List dataList, int lineNum, String[] rowLieldsName, String dateField)
    {
        return lineToRow(dataList, lineNum, rowLieldsName, null, dateField);
    }

    /**
     * 描述：行列转换及格式化日期（没有行标题）
     * 作者：
     * 时间：May 12, 2010 8:21:02 PM
     * @param dataList query查出的List数据
     * @param lineFieldsName 转换后列字段名
     * @param rowLieldsName 转换后行字段名
     * @param dateField 只取日期的字段名
     * @return
     */
    public static List lineToRow(List dataList, String[] lineFieldsName, String[] rowLieldsName, String dateField)
    {
        return lineToRow(dataList, lineFieldsName, rowLieldsName, null, dateField);
    }

    /**
     * 描述：行列转换及格式化日期（默认转换后列名）
     * 		转换后列名默认为 "line"+i;
     *
     * 时间：May 12, 2010 8:21:02 PM
     * @param dataList query查出的List数据
     * @param lineNum 转换后列的个数
     * @param rowLieldsName 转换后行字段名
     * @param rowTitle 转换后行标题
     * @param dateField 只取日期的字段名
     * @return
     */
    public static List lineToRow(List dataList, int lineNum, String[] rowLieldsName, String[] rowTitle, String dateField)
    {
        String[] lineFieldsName = new String[lineNum];
        for (int i = 0; i < lineNum; i++)
        {
            lineFieldsName[i] = "line" + i;
        }
        return lineToRow(dataList, lineFieldsName, rowLieldsName, rowTitle, dateField);
    }

    /**
     * 描述：
     * 作者：
     * 时间：May 13, 2010 11:35:03 AM
     * @param dataList query查出的List数据
     * @param lineFieldsName 转换后列字段名
     * @param rowLieldsName 转换后行字段名
     * @param rowTitle 转换后行标题
     * @param dateField 只取日期的字段名
     * @return
     */
    public static List lineToRow(List dataList, String[] lineFieldsName, String[] rowLieldsName, String[] rowTitle,
                                 String dateField)
    {
        List datas = new ArrayList();
        boolean b = false;
        int itemp = 0;
        if ( rowTitle != null && rowTitle.length > 0 )
        {
            b = true;
        }
        else
        {
            itemp = 1;
        }
        for (int i = 0; i < rowLieldsName.length; i++)
        {
            Map map = new HashMap();
            for (int j = 0; j < lineFieldsName.length; j++)
            {
                if ( j == 0 && b )
                {
                    map.put(lineFieldsName[j], rowTitle[i]);
                    continue;
                }

                if ( dataList != null && dataList.size() > 0 )
                {
                    if ( j > dataList.size() - itemp )
                    {
                        map.put(lineFieldsName[j], null);
                        continue;
                    }
                    DataRow dr = (DataRow) dataList.get(j - 1 + itemp);

                    Object obj = dr.get(rowLieldsName[i]);

                    if ( obj != null )
                    {
                        if ( obj instanceof String )
                        {
                            String stemp = (String) obj;

                            //其它日期处理
                            if ( StringHelper.isNotEmpty(dateField) && dateField.equals(rowLieldsName[i]) )
                            {
                                stemp = stemp.substring(0, 10);
                            }

                            map.put(lineFieldsName[j], stemp);
                        }
                        else
                        {
                            map.put(lineFieldsName[j], obj);
                        }
                    }
                    else
                    {
                        map.put(lineFieldsName[j], null);
                    }
                }

            }
            datas.add(map);
        }

        return datas;
    }

    /*public static List lineToRow(List dataList, String[] rowLieldsName,String[] rowTitle, String[] quarters, String quartersFields)
     {
     return lineToRow(dataList, "line", rowLieldsName, rowTitle, quarters, quartersFields, quartersFields);
     }*/

    /**
     * 描述：
     * 作者：
     * 时间：May 13, 2010 11:35:03 AM
     * @param dataList query查出的List数据
     * @param lineFieldsPreName 转换后列字段名前缀
     * @param rowLieldsName 转换后行字段名
     * @param rowTitle 转换后行标题
     * @param quarters	截止日期
     * @param quartersFields 截止日期字段名
     * @return
     */
    public static List lineToRow(List dataList, String lineFieldsPreName, String[] rowLieldsName, String[] rowTitle,
                                 String[] quarters, String quartersFields)
    {
        return lineToRow(dataList, lineFieldsPreName, rowLieldsName, rowTitle, quarters, quartersFields, quartersFields);
    }

    /**
     * 描述：
     * 作者：
     * 时间：May 13, 2010 11:35:03 AM
     * @param dataList query查出的List数据
     * @param lineFieldsPreName 转换后列字段名前缀
     * @param rowLieldsName 转换后行字段名
     * @param rowTitle 转换后行标题
     * @param quarters	截止日期
     * @param quartersFields 截止日期字段名
     * @param dateField 只取日期的字段名
     * @return
     */
    public static List lineToRow(List dataList, String lineFieldsPreName, String[] rowLieldsName, String[] rowTitle,
                                 String[] quarters, String quartersFields, String dateField)
    {
        List datas = new ArrayList();
        boolean b = false;
        int itemp = 0;
        if ( rowTitle != null && rowTitle.length > 0 )
        {
            b = true;
        }
        else
        {
            itemp = 1;
        }
        for (int i = 0; i < rowLieldsName.length; i++)
        {
            Map map = new HashMap();
            for (int j = 0; j < quarters.length + 1 - itemp; j++)
            {
                if ( j == 0 && b )
                {
                    map.put(lineFieldsPreName + j, rowTitle[i]);
                    continue;
                }

                if ( dataList != null && dataList.size() > 0 )
                {
                    boolean bDuty = false;
                    for (Iterator it = dataList.iterator(); it.hasNext();)
                    {

                        DataRow dataRow = (DataRow) it.next();
                        if ( quarters[j - 1 - itemp].equals(dataRow.get(quartersFields)) )
                        {
                            Object obj = dataRow.get(rowLieldsName[i]);
                            if ( obj instanceof String )
                            {
                                String stemp = (String) obj;

                                //其它日期处理
                                if ( StringHelper.isNotEmpty(dateField) && dateField.equals(rowLieldsName[i]) )
                                {
                                    stemp = stemp.substring(0, 10);
                                }

                                map.put(lineFieldsPreName + j, stemp);
                            }
                            else
                            {
                                map.put(lineFieldsPreName + j, obj);
                            }

                            //							map.put(lineFieldsPreName+j, dataRow.get(rowLieldsName[i]));
                            bDuty = true;
                            break;
                        }
                        else
                        {
                            if ( quartersFields.equals(rowLieldsName[i]) )
                            {
                                String stemp = quarters[j - 1 - itemp];
                                map.put(lineFieldsPreName + j, (stemp.length() > 10) ? stemp.substring(0, 10) : stemp);
                                bDuty = true;
                                break;
                            }
                        }
                    }
                    if ( !bDuty )
                    {
                        map.put(lineFieldsPreName + j, null);
                    }
                }

            }
            datas.add(map);
        }

        return datas;
    }

    /**
     * 描述：只保留字符串的英文字母和“_”号
     * 作者：
     * 时间：May 21, 2010 5:51:54 PM
     * @param str
     * @return
     */
    public static String replaceAllSign(String str)
    {
        if ( str != null && str.length() > 0 )
        {
            str = str.replaceAll("[^a-zA-Z_]", "");
        }
        return str;
    }

    /**
     * 描述：获取list中的某一列
     * 作者：
     * 时间：May 23, 2010 11:54:39 AM
     * @param list List
     * @param lineName 列名（或字段名）
     * @return
     */
    public static String[] getOneLineFromList(List list, String lineName)
    {
        return getOneLineFromList(list, lineName, -1);
    }

    /**
     * 描述：获取list中的某一列
     * 作者：
     * 时间：May 23, 2010 11:54:39 AM
     * @param list List
     * @param lineName 列名（或字段名）
     * @param num 个数
     * @return
     */
    public static String[] getOneLineFromList(List list, String lineName, int num)
    {
        if ( list == null )
        {
            return null;
        }

        String[] arr = new String[list.size()];
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext();)
        {
            DataRow data = (DataRow) it.next();
            arr[i] = data.getString(lineName);

            i++;
            if ( i == num )
            {
                break;
            }
        }
        return arr;
    }

    /**
     * 描述：字串中的数字值加1
     * 作者：
     * 时间：May 23, 2010 7:13:11 PM
     * @param str 字串
     * @param isAdd 数字值true：加，false：减
     * @return
     */
    public static String stepNumInStr(String str, boolean isAdd)
    {
        String sNum = str.replaceAll("[^0-9]", ",").trim();
        if ( sNum == null || sNum.length() == 0 )
        {
            return str;
        }
        String[] sNumArr = sNum.split(",");

        for (int i = 0; i < sNumArr.length; i++)
        {
            if ( sNumArr[i] != null && sNumArr[i].length() > 0 )
            {
                int itemp = Integer.parseInt(sNumArr[i]);
                if ( isAdd )
                {
                    itemp += 1;
                }
                else
                {
                    itemp -= 1;
                }
                str = str.replaceFirst(sNumArr[i], String.valueOf(itemp));
                break;
            }
        }

        return str;
    }

    /**
     * 描述：list 转换为 String[]
     * 作者：
     * 时间：May 26, 2010 4:27:29 PM
     * @param list
     * @return
     */
    public static String[] listToStringArray(List list)
    {
        if ( list == null || list.size() == 0 )
        {
            return null;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

}
