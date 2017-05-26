package com.pistachio.timerengine;

import com.pistachio.base.util.StringHelper;
import com.pistachio.timerengine.config.TaskConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class TaskManager
{
    private static Logger logger = Logger.getLogger(TaskManager.class);
    private static HashMap taskEntryMap = new HashMap();

    /**
     * 启动所有任务
     */
    public static void start()
    {
        List taskList = TaskConfig.getTaskList();
        Iterator iter = taskList.iterator();
        while (iter.hasNext())
        {
            HashMap taskPropertyMap = (HashMap) iter.next();
            String taskType = StringHelper.n2s((String) taskPropertyMap.get("task-type"));
            AbstractTaskBuilder taskBuilder = TaskBuilderFactory.getInstance().getTaskBuilder(taskType);
            if (taskBuilder != null)
            {
                taskBuilder.setTaskProperty(taskPropertyMap);
                TaskEntry taskEntry = taskBuilder.builderTask();
                if (taskEntry != null)
                {
                    taskEntryMap.put(taskEntry.getId(), taskEntry);
                    taskEntry.start();
                }
            }
        }
    }

    /**
     * 停止所有任务
     */
    public static void stop()
    {
        if (taskEntryMap != null)
        {
            for (Iterator iter = taskEntryMap.keySet().iterator(); iter.hasNext();)
            {
                String key = (String) iter.next();
                TaskEntry taskEntry = (TaskEntry) taskEntryMap.get(key);
                taskEntry.stop();
            }
        }
    }
}
