package com.pistachio.timerengine.config;

import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class TaskConfig
{
    //单子属性对象
    private static TaskConfig instance = new TaskConfig();

    //保存每一个任务的配置文件
    private static ArrayList taskList = new ArrayList();

    private static Logger logger = Logger.getLogger(TaskConfig.class);
    private static String CONFIG_FILE_NAME = "tasks.xml";

    static
    {
        loadConfig();
    }

    /**
     * 读入配置文件
     *
     * @param
     */
    private static void loadConfig()
    {
        try
        {
            InputStream is = TaskConfig.class.getResourceAsStream("/" + CONFIG_FILE_NAME);
            SAXReader builder = new SAXReader();

            Document doc = builder.read(is);
            Element tasksElement = doc.getRootElement();
            List taskElementList = tasksElement.elements("task");

            Iterator taskElementIter = taskElementList.iterator();
            while (taskElementIter.hasNext())
            {
                Element taskElement = (Element) taskElementIter.next();
                HashMap taskPropertyMap = new HashMap();

                //获得任务的ID
                String taskId = StringHelper.n2s(taskElement.attributeValue("id"));
                //若ID为空，则跳过该任务配置
                if (StringHelper.isEmpty(taskId))
                {
                    continue;
                }
                taskPropertyMap.put("id", taskId);

                //获得所有的属性
                List taskPropertyList = taskElement.elements();
                Iterator taskPropertyElementIter = taskPropertyList.iterator();
                while (taskPropertyElementIter.hasNext())
                {
                    Element propertyElement = (Element) taskPropertyElementIter.next();
                    String name = propertyElement.getName();
                    String value = StringHelper.n2s(propertyElement.getTextTrim());
                    taskPropertyMap.put(name, value);
                }
                taskList.add(taskPropertyMap);
            }
        }
        catch (DocumentException ex)
        {
            logger.error("", ex);
        }
    }


    public static List getTaskList()
    {
        return taskList;
    }

    public static TaskConfig getInstance()
    {
        return instance;
    }
}