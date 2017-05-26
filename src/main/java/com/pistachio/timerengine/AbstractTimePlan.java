
package com.pistachio.timerengine;

import java.util.Date;

public abstract class AbstractTimePlan implements TimePlan
{

    //记录计划的第一时间点，除设置新的起始时间，否则不再改变
    protected Date startDate;

    //当前计划的时间点，每次计划替换时被更新，
    protected Date currentDate;

    public boolean haveNext()
    {
        return (currentDate != null);
    }


    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date date)
    {
        startDate = date;
        //在赋给startDate值时，同时也赋给currentDate
        currentDate = date;
    }
}
