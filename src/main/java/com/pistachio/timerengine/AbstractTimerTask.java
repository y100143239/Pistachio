package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.TimerTask;


public abstract class AbstractTimerTask extends TimerTask
{
    private static Logger logger = Logger.getLogger(AbstractTimerTask.class);
    //任务记录
    public TaskEntry taskEntry;


    public AbstractTimerTask(TaskEntry taskEntry)
    {
        this.taskEntry = taskEntry;
    }

    public void run()
    {
        //执行具体的任务
        logger.info(Util.dateToLongStr(new Date()) + " 开始执行" + taskEntry.getTaskInfo());
        execute();
        //执行完后，启动下一次任务
        taskEntry.start();
    }

    /**
     * 生成一个新的实例相当于克隆自身;原因在于：同一任务对象不能两次加入到Timer,
     * 在TaskEntry类将看到它的使用方法
     */
    public abstract AbstractTimerTask getCloneObject();

    /**
     * 任务执行的主体，子类必须实现它
     */
    public abstract void execute();
}
