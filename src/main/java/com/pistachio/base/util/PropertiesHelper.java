package com.pistachio.base.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 描述: 属性文件工具类
 * 创建时间: 14:43:08
 */
public final class PropertiesHelper
{

    /**
     * PropertiesHelper 日志
     */
    private static Logger logger = Logger.getLogger(PropertiesHelper.class);

    /**
     * 读入属性文件，返回属性对象
     *
     * @param filepath
     *            属性文件路径
     * @return 成功则返回属性对象，失败则返回null
     */
    public static Properties loadProperties(String filepath)
    {
        try
        {
            Properties props = new Properties();
            props.load(new FileInputStream(new File(filepath)));
            return props;
        }
        catch (Exception ex)
        {
            logger.error("读取文件出错", ex);
        }
        return null;
    }

    /**
     * 读入属性文件，返回属性对象
     *
     * @param iStream
     *            输入流
     * @return 成功则返回属性对象，失败则返回null
     */
    public static Properties loadProperties(InputStream iStream)
    {
        try
        {
            Properties props = new Properties();
            props.load(iStream);
            return props;
        }
        catch (Exception ex)
        {
            logger.error("读取输入流数据出错", ex);
        }
        return null;
    }

    /**
     * 提取布尔值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @return 属性值
     */
    public static boolean getBoolean(String property, Properties properties)
    {
        return Boolean.valueOf(properties.getProperty(property)).booleanValue();
    }

    /**
     * 提取布尔值，如果值不存在，返回默认值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @param defaultValue
     *            默认值
     * @return 属性值
     */
    public static boolean getBoolean(String property, Properties properties, boolean defaultValue)
    {
        String propValue = properties.getProperty(property);
        return (propValue == null) ? defaultValue : Boolean.valueOf(propValue).booleanValue();
    }

    /**
     * 提取数值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @return 数值，如果不存在，返回0
     */
    public static int getInt(String property, Properties properties)
    {
        return getInt(property, properties, 0);
    }

    /**
     * 提取数值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @param defaultValue
     *            默认值
     * @return 数值
     */
    public static int getInt(String property, Properties properties, int defaultValue)
    {
        String propValue = properties.getProperty(property);
        return (propValue == null) ? defaultValue : Integer.parseInt(propValue);
    }

    /**
     * 提取long值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @return long值
     */
    public static long getLong(String property, Properties properties)
    {
        return getLong(property, properties, 0);
    }

    /**
     * 提取long值
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @param defaultValue
     *            默认值
     * @return long值
     */
    public static long getLong(String property, Properties properties, long defaultValue)
    {
        String propValue = properties.getProperty(property);
        return (propValue == null) ? defaultValue : Long.parseLong(propValue);
    }

    /**
     * 提取字符串
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @return 字符串
     */
    public static String getString(String property, Properties properties)
    {
        return getString(property, properties, "");
    }

    /**
     * 提取字符串
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @param defaultValue
     *            默认值
     * @return 字符串
     */
    public static String getString(String property, Properties properties, String defaultValue)
    {
        String propValue = properties.getProperty(property);
        return (propValue == null) ? defaultValue : propValue;
    }

    /**
     * 提取Integer对象
     *
     * @param property
     *            属性名称
     * @param properties
     *            属性对象
     * @return Integer对象
     */
    public static Integer getInteger(String property, Properties properties)
    {
        String propValue = properties.getProperty(property);
        return (propValue == null) ? null : Integer.valueOf(propValue);
    }

    /**
     * 把Properties对象中的所有属性存储存到Map对象中
     *
     * @param properties
     *            属性对象
     * @return 转化后的Map对象
     */
    public static Map toMap(Properties properties)
    {
        Map map = new HashMap();
        //Enumeration enumer = properties.elements();
        Enumeration enumer = properties.propertyNames();
        while (enumer.hasMoreElements())
        {
            String propName = (String) enumer.nextElement();
            String propValue = (String) properties.getProperty(propName);
            map.put(propName, propValue);
        }
        return map;
    }

    /**
     *
     * 描述：保存properties信息
     *
     * @param props
     * @param path
     */
    public static void savePropertiesFile(Properties props, String path)
    {
        FileOutputStream fileOut = null;
        try
        {
            fileOut = new FileOutputStream(path);
            props.store(fileOut, null);
        }
        catch (Exception ex)
        {
            logger.error("保存文件出错", ex);
        }
        finally
        {
            if ( fileOut != null )
            {
                try
                {
                    fileOut.close();
                    fileOut = null;
                }
                catch (Exception e)
                {
                    logger.error("关闭文件出错", e);
                }
            }
        }
    }

}
