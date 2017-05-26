package com.pistachio.timerengine;


import com.pistachio.timerengine.util.TimerGenerate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;


public class TaskSelectWeekBuilder extends AbstractTaskBuilder
{

    public TaskSelectWeekBuilder()
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
            showConfigErrMsg(taskId, "task-interval必须设定，且至少必须指定一天");
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

        TimePlanSelectWeek timePlan = new TimePlanSelectWeek();
        timePlan.setStartDate(generateStartDate(startDate));

        //若没有选择星期中的任何一天，则返回空
        if (setSelectWeek(timePlan, intervalStr) == 0)
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

    public int setSelectWeek(TimePlanSelectWeek timePlan, String intervalStr)
    {
        StringTokenizer token = new StringTokenizer(intervalStr, ",");
        //记录有几天被选中
        int i = 0;
        while (token.hasMoreElements())
        {
            String str = token.nextToken();
            if ("0".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(0, true);
                i++;
            }
            else if ("1".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(1, true);
                i++;
            }
            else if ("2".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(2, true);
                i++;
            }
            else if ("3".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(3, true);
                i++;
            }
            else if ("4".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(4, true);
                i++;
            }
            else if ("5".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(5, true);
                i++;
            }
            else if ("6".equalsIgnoreCase(str))
            {
                timePlan.setSelectWeek(6, true);
                i++;
            }
        }

        return i;
    }

}
