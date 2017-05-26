
package com.pistachio.timerengine;

import com.pistachio.timerengine.util.TimerGenerate;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class TaskSelectHourMinuteBuilder extends AbstractTaskBuilder
{

    private static Logger logger = Logger.getLogger(TaskSelectHourMinuteBuilder.class);

    public TaskSelectHourMinuteBuilder()
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

        //若没有task-interval，则返回
        if (intervalStr.length() == 0)
        {
            showConfigErrMsg(taskId, "task-interval必须设定，且至少必须指定一个时间");
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

        TimePlanSelectHourMinute timePlan = new TimePlanSelectHourMinute();
        timePlan.setStartDate(generateStartDate(startDate));

        //若没有一天中的任何一个时间点，则返回空
        if (setSelectHourMinute(timePlan, intervalStr) == 0)
        {
            showConfigErrMsg(taskId, "task-interval的格式设定不正确");
            timePlan = null;
            return null;
        }

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

    public int setSelectHourMinute(TimePlanSelectHourMinute timePlan, String intervalStr)
    {
        StringTokenizer token = new StringTokenizer(intervalStr, ",");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        //记录设定了几个时间点
        int i = 0;
        while (token.hasMoreElements())
        {
            try
            {

                Date date = dateFormat.parse(token.nextToken());
                timePlan.addSatrtHourMinute(date);
                i++;
            }
            catch (ParseException ex)
            {
                logger.error("",ex);
            }
        }
        return i;
    }

}
