package com.pistachio.base.dao.factory;

import com.pistachio.base.service.exception.ServiceException;
import org.apache.log4j.Logger;

public class DAOFactory
{
    private static Logger logger = Logger.getLogger(DAOFactory.class);

    /**
     *return data access object
     *
     * @param interfaceClass
     * @return
     */
    public static Object getDao(Class interfaceClass)
    {

        String serviceClassName = interfaceClass.getName();
        int idx = serviceClassName.lastIndexOf(".");
        String packageName = "";
        String shortName = "";
        if (idx == -1)
        {
			packageName = "";
            shortName = serviceClassName;
        }
        else
        {
            packageName = serviceClassName.substring(0, idx + 1);// add a dot at the end
            shortName = serviceClassName.substring(idx + 1);
        }

        String serviceImplClassName = packageName + "impl." + shortName + "Impl";
        try
        {
            Class clazz = Class.forName(serviceImplClassName);
            return clazz.newInstance();
        }
        catch (ClassNotFoundException ne)
        {
            logger.error("not found the class bytecode", ne);
            throw new ServiceException("not found the correspond dao implementation [" + serviceImplClassName + "]", ne);
        }
        catch (Exception ex)
        {
            logger.error("get dao instance failed", ex);
            throw new ServiceException("creating dao instance failed[" + serviceImplClassName + "]", ex);
        }
    }
}
