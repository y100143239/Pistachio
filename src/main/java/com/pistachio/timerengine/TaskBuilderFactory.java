package com.pistachio.timerengine;

public class TaskBuilderFactory
{

    private static TaskBuilderFactory instance = new TaskBuilderFactory();

    private TaskBuilderFactory()
    {
        super();
    }

    public static TaskBuilderFactory getInstance()
    {
        return instance;
    }

    public AbstractTaskBuilder getTaskBuilder(String taskType)
    {
        if ("1".equalsIgnoreCase(taskType))
        {
            return new TaskOnceBuilder();
        }
        else if ("2".equalsIgnoreCase(taskType))
        {
            return new TaskPeriodBuilder();
        }
        else if ("3".equalsIgnoreCase(taskType))
        {
            return new TaskSelectWeekBuilder();
        }
        else if ("4".equalsIgnoreCase(taskType))
        {
            return new TaskSelectHourMinuteBuilder();
        }

        return null;
    }
}
