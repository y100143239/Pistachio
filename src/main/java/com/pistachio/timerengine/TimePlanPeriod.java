package com.pistachio.timerengine;


import com.pistachio.timerengine.util.Util;

import java.util.Date;

/**
 * "周期性间隔"的时间计划方案类
 */
public class TimePlanPeriod extends AbstractTimePlan
{
    /**
     * 间隔时间,单位毫秒
     */
    private int spaceTime;

    public Date nextDate()
    {
        //把当前的计划时间保存在中间变量中
        Date returnDate = this.currentDate;

        //若得到的时间比当前的时间还早，则继续获得下一个启动时间
        while (Util.isDateBeforeCurrentDate(returnDate))
        {
            returnDate = Util.dateAddSpaceMilliSecond(returnDate, spaceTime);
        }

        //计算下次启动的时间
        currentDate = Util.dateAddSpaceMilliSecond(returnDate, spaceTime);

        return returnDate;
    }


    public String getTimePlanString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("第一次运行于:");
        buffer.append(Util.dateToLongStr(startDate));
        buffer.append(",");
        buffer.append("再每隔 ");
        buffer.append(getDescribe());
        buffer.append(" 运行一次");
        return buffer.toString();
    }

    private String getDescribe()
    {
        return Util.getMSecondDescribe(spaceTime);
    }


    public int getSpaceTime()
    {
        return spaceTime;
    }


    public void setSpaceTime(int MilliSecond)
    {
        spaceTime = MilliSecond;
    }


}
