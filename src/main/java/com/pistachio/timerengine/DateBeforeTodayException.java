package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;

import java.util.Date;

public class DateBeforeTodayException extends NullPointerException
{

    private Date date;

    public DateBeforeTodayException(Date date)
    {
        this.date = date;
    }

    public String toString()
    {
        return "计划时间(" + Util.dateToLongStr(date) + ")早于当前时间";
    }
}
