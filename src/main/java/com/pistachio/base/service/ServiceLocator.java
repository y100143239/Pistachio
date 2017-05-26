package com.pistachio.base.service;

import com.pistachio.base.service.exception.ServiceException;
import org.apache.log4j.Logger;


public class ServiceLocator
{
    private static Logger logger = Logger.getLogger(ServiceLocator.class);

    /**
     * 返回服务对象
     *
     * @param interfaceClass
     * @return
     */
    public static Object getService(Class interfaceClass)
    {

        String serviceClassName = interfaceClass.getName();
        int idx = serviceClassName.lastIndexOf(".");
        String packageName = "";
        String shortName = "";
        if (idx == -1)
        {
            shortName = serviceClassName;
        }
        else
        {
            packageName = serviceClassName.substring(0, idx);
            shortName = serviceClassName.substring(idx + 1);
        }


        String serviceImplClassName = packageName + ".impl." + shortName + "Impl";
        Class clazz = null;
        try
        {
            clazz = Class.forName(serviceImplClassName);
            return clazz.newInstance();
        }
        catch (ClassNotFoundException ne)
        {
            logger.error("没有找到相应的service实现类[" + serviceImplClassName + "]", ne);
            throw new ServiceException("没有找到相应的service实现类[" + serviceImplClassName + "]", ne);
        }
        catch (Exception ex)
        {
            logger.error("创建service实现类失败[" + serviceImplClassName + "]", ex);
            throw new ServiceException("创建service实现类失败[" + serviceImplClassName + "]", ex);
        }
    }
}
