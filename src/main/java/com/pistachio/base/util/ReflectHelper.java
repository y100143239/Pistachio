package com.pistachio.base.util;

import org.apache.log4j.Logger;

/**
 * 描述:	 反射工具类
 */
public class ReflectHelper
{
    /**
     * ReflectHelper 日志
     */
    private static Logger logger = Logger.getLogger(ReflectHelper.class);

    /**
     * 提指定的类载入以系统中
     *
     * @param name 类名称
     * @return 类对象
     * @throws ClassNotFoundException
     */
    public static Class classForName(String name) throws ClassNotFoundException
    {
        try
        {
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }

        catch (ClassNotFoundException e)
        {
            logger.error("类["+name+"]加载出错", e);
        }
        catch (SecurityException e)
        {
            logger.error("类["+name+"]加载出错", e);
        }
        return Class.forName(name);
    }

    /**
     * 根据名称生成指定的对象
     *
     * @param name 类名称
     * @return 具体的对象,若发生异常，则返回null
     */
    public static Object objectForName(String name)
    {
        try
        {
            return Class.forName(name).newInstance();
        }
        catch (Exception ex)
        {
            logger.error("获取对象实例出错",ex);
        }
        return null;
    }
}
