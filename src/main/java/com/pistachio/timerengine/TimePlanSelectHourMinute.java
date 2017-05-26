package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * 选择一天的某些时间点运行，必须设定一个时间点
 */
public class TimePlanSelectHourMinute extends AbstractTimePlan
{

    /**
     * 包含了每天启动的每个时间点的排序列表
     */
    private ArrayList timeList = new ArrayList();

    /**
     * 表示当前时间处在列表的位置
     */
    private int curPos = 0;

    public Date nextDate()
    {
        Date returnDate = null;
        //如果当前时间的HH:mm不是用户设定的
        if (!isSelectHourMinute(currentDate))
        {
            currentDate = getNextDate();
        }

        //若得到的时间比当前的时间还早，则继续获得下一个启动时间
        while (Util.isDateBeforeCurrentDate(currentDate))
        {
            currentDate = getNextDate();
        }

        returnDate = currentDate;
        currentDate = getNextDate();

        return returnDate;
    }

    //算出下一个计划时间
    private Date getNextDate()
    {
        int timeListLength = timeList.size();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        //到了下一天，则时间上要加上24小时,且计数器回到时间点的起点
        if (curPos >= timeListLength)
        {
            cal.add(Calendar.HOUR_OF_DAY, 24);
            curPos = 0;
        }
        Date date = (Date) timeList.get(curPos++);
        cal.set(Calendar.HOUR_OF_DAY, date.getHours());
        cal.set(Calendar.MINUTE, date.getMinutes());
        cal.set(Calendar.SECOND, date.getSeconds());
        return cal.getTime();
    }

    public void addSatrtHourMinute(Date date)
    {
        int timeListLength = timeList.size();
        int i = 0;
        for (i = 0; i < timeListLength; i++)
        {
            Date tDate = (Date) timeList.get(i);
            if (tDate.getTime() > date.getTime())
            {
                break;
            }
        }
        timeList.add(i, date);
    }

    private boolean isSelectHourMinute(Date date)
    {
        Iterator iter = timeList.iterator();
        while (iter.hasNext())
        {
            Date tDate = (Date) iter.next();
            if ((tDate.getHours() == date.getHours()) &&
                    (tDate.getMinutes() == date.getMinutes()))
            {
                return true;
            }
        }
        return false;
    }

    public String getTimePlanString()
    {
        return "";
    }
}
