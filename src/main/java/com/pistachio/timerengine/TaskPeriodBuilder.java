package com.pistachio.timerengine;


import com.pistachio.timerengine.util.TimerGenerate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskPeriodBuilder extends AbstractTaskBuilder
{

    public TaskPeriodBuilder()
    {
        super();
    }

    public TaskEntry builderTask()
    {

        //或得需要的属性
        String taskId = getString("id");
        String taskName = getString("task-name");
        String description = getString("description");
        String startDateStr = getString("startdate");
        String taskClass = getString("task-class");
        String intervalStr = getString("task-interval");

        //若没有任务名称，则返回空
        if (taskName.length() == 0)
        {
            showConfigErrMsg(taskId, "task-name必须设定");
            return null;
        }

        //若task-interval不是整数，且不大于0，则返回空
        int interval = 0;
        try
        {
            interval = Integer.parseInt(intervalStr);
        }
        catch (NumberFormatException ex1)
        {
        }
        if (interval <= 0)
        {
            showConfigErrMsg(taskId, "task-interval必须设定，且必须设为大于0的整数");
            return null;
        }


        Task task = taskObjectFromClassName(taskClass);
        //若对象不能建立，则返回空
        if (task == null)
        {
            showConfigErrMsg(taskId, "task-class的设置存在问题，不能建立相应的对象");
            return null;
        }


        Date startDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            startDate = dateFormat.parse(startDateStr);
        }
        catch (ParseException ex)
        {
        }

        TimePlanPeriod timePlan = new TimePlanPeriod();
        timePlan.setStartDate(generateStartDate(startDate));
        timePlan.setSpaceTime(interval);

        //产生任务对象
        TaskEntry taskEntry = new TaskEntry();
        taskEntry.setId(taskId);
        taskEntry.setDescription(description);
        taskEntry.setName(taskName);
        taskEntry.setTimer(TimerGenerate.getInstance().getTimer());
        taskEntry.setTimePlan(timePlan);
        taskEntry.setTimerTask(new TimerTaskImpl(taskEntry, task));

        return taskEntry;
    }


}
