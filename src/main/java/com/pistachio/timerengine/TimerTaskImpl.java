package com.pistachio.timerengine;


public class TimerTaskImpl extends AbstractTimerTask
{
    //需要执行的任务
    private Task task = null;

    public TimerTaskImpl(TaskEntry taskEntry, Task task)
    {
        super(taskEntry);
        this.task = task;
    }

    public AbstractTimerTask getCloneObject()
    {
        return new TimerTaskImpl(taskEntry, task);
    }


    public void execute()
    {
        task.execute();
    }

    public void setTask(Task task)
    {
        this.task = task;
    }
}
