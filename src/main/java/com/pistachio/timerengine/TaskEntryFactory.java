package com.pistachio.timerengine;

import java.util.HashMap;
import java.util.Timer;

public class TaskEntryFactory
{
    private static final HashMap tasks = new HashMap();

    private static final Timer timer = new Timer();

    public static TaskEntry getInstance(String id, String name)
    {
        if (tasks.containsKey(id))
        {
            return (TaskEntry) tasks.get(id);
        }
        else
        {
            TaskEntry entry = new TaskEntry();
            entry.setId(id);
            entry.setName(name);
            entry.setTimer(timer);
            //entry.setTimerTask(new MyTestTask(entry));
            tasks.put(new Long(id), entry);
            return entry;
        }
    }
}
