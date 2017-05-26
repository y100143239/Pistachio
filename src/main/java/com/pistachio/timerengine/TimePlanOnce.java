package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;

import java.util.Date;

/**
 * 一次性任务时间计划
 */
public class TimePlanOnce extends AbstractTimePlan
{

    public Date nextDate()
    {
        //把当前的计划时间保存在中间变量中
        Date returnDate = this.currentDate;

        //算出下一个计划时间。由于只执行一次，没有下一次的时间，
        //所以设为null
        currentDate = null;

        return returnDate;
    }

    public String getTimePlanString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("只运行一次，运行的时间为:");
        buffer.append(Util.dateToLongStr(startDate));

        return buffer.toString();
    }
}
