package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Timer;

public class TaskEntry
{

    private static Logger logger = Logger.getLogger(TaskEntry.class);

    //定义两个表示任务记录状态常量
    public static final int TASK_START = 0;

    public static final int TASK_STOP = 1;

    /*
     * 任务ID号，唯一
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务状态（启动/停止）
     */
    private int state = TASK_STOP;

    /**
     * JAVA计时器
     */
    private Timer timer;

    /**
     * 时间计划的类型
     */
    private TimePlan timePlan;

    /**
     * 任务的描述
     */
    private String description;

    /**
     * 任务类的种子对象,由这个对象来不断克隆
     */
    private AbstractTimerTask timerTask;

    /**
     * 运行计划的当前任务
     */
    private AbstractTimerTask runTimerTask;

    String getTaskInfo()
    {
        return "任务[ID=" + id + "," + "名称=" + name + "]";
    }

    /**
     * start()-->run()-->start() 形成一个循环回路。本方法负责起动本类代表的任务
     */
    public void start()
    {
        if (timePlan.haveNext())
        {
            //得到任务计划时间
            Date date = timePlan.nextDate();

            //得到的当前任务计划时间比当前时间早，计划时间存在错误，不能正常启动
            if (Util.isDateBeforeCurrentDate(date))
            {
                logger.info(getTaskInfo() + "计划时间为：" + Util.dateToLongStr(date) + ",比当前时间早，不能正常启动！");
            }

            //得到任务(克隆得到的任务对象)
            runTimerTask = timerTask.getCloneObject();

            timer.schedule(runTimerTask, date); //加入计划队列

            //打印将要运行的计划任务的信息
            date = new Date(runTimerTask.scheduledExecutionTime());

            logger.info(Util.dateToLongStr(date) + " 将运行" + getTaskInfo());
        }
        else
        {
            state = TASK_STOP;
            logger.info(getTaskInfo() + " 结束");
        }
    }

    /**
     * 停止执行任务
     */
    public void stop()
    {
        if (runTimerTask != null)
        {
            //打印信息
            Date date = new Date(runTimerTask.scheduledExecutionTime());
            logger.info("计划于:" + Util.dateToLongStr(date) + "运行的" + getTaskInfo() + "被终止");

            //终止本任务, 调用Timer.cancel()是终止Timer的所有任务。
            runTimerTask.cancel();
        }
        else
        {
            logger.info(getTaskInfo() + "未进入执行计划");
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Timer getTimer()
    {
        return timer;
    }

    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }

    public AbstractTimerTask getTimerTask()
    {
        return timerTask;
    }

    public void setTimerTask(AbstractTimerTask timerTask)
    {
        this.timerTask = timerTask;
    }

    public TimePlan getTimePlan()
    {
        return timePlan;
    }

    public void setTimePlan(TimePlan timePlan)
    {
        this.timePlan = timePlan;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
