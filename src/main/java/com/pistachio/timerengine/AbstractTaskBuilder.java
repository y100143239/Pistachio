package com.pistachio.timerengine;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public abstract class AbstractTaskBuilder implements TaskBuilder
{
    private static Logger logger = Logger.getLogger(AbstractTaskBuilder.class);

    //保存每一个任务属性的Map，此处用Map保存，主要是为了以后扩充的需要
    //可以针对不同的任务增加相应的属性设置
    HashMap propMap = new HashMap();

    public AbstractTaskBuilder()
    {
        super();
    }

    protected Task taskObjectFromClassName(String className)
    {
        Task task = null;
        try
        {

            Object object = Class.forName(className).newInstance();
            if (object instanceof Task)
            {
                task = (Task) object;
            }
        }
        catch (InstantiationException ex)
        {
            logger.error("",ex);
        }
        catch (IllegalAccessException ex)
        {
            logger.error("",ex);
        }
        catch (ClassNotFoundException ex)
        {
            logger.error("",ex);
        }

        return task;
    }

    protected Date generateStartDate(Date startDate)
    {
        //若启动时间为空，则产生一个启动时间,在当前的时间上加5秒
        if (startDate == null)
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 5);
            startDate = cal.getTime();
        }
        return startDate;
    }


    public abstract TaskEntry builderTask();

    public void setTaskProperty(HashMap taskPropMap)
    {
        Iterator iter = taskPropMap.keySet().iterator();
        while (iter.hasNext())
        {
            Object key = iter.next();
            propMap.put(key, taskPropMap.get(key));
        }
    }

    public String getString(String name)
    {
        String strRet = (String) propMap.get(name);
        return (strRet == null) ? "" : strRet;
    }

    public int getInt(String name)
    {
        int intRet = 0;
        String intStr = (String) propMap.get(name);
        try
        {
            intRet = Integer.parseInt(intStr);
        }
        catch (NumberFormatException ex)
        {
        }

        return intRet;
    }

    public void showConfigErrMsg(String taskId, String msg)
    {
        logger.info("[ID=" + taskId + "]的任务配置有问题，" + msg);
    }

}
