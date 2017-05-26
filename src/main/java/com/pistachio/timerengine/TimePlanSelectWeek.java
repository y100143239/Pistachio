package com.pistachio.timerengine;

import com.pistachio.timerengine.util.Util;

import java.util.Calendar;
import java.util.Date;

/**
 * 选择一周的某几天,让这几天在同一时间点运行任务, 一周内必须选择一天
 */
public class TimePlanSelectWeek extends AbstractTimePlan
{

    /**
     * 取得一个日历实例
     */
    private static Calendar c = Calendar.getInstance();

    /**
     * 间隔时间,单位毫秒
     */
    private static int spaceMilliSecond = 24 * 60 * 60 * 1000;

    /**
     * 0为星期日 ,1为星期一
     */
    private boolean[] selectWeek = new boolean[7];

    public Date nextDate()
    {
        Date returnDate = null;
        if (!isSelectWeek(currentDate))
        {
            //如果这一天不是所选周中的一天
            currentDate = getNextDate(currentDate);
        }

        returnDate = currentDate;
        currentDate = getNextDate(currentDate);

        return returnDate;
    }

    //算出下一个计划时间。没有下一个就设为null
    private Date getNextDate(Date date)
    {
        Date tempDate = date;
        Date returnDate = null;
        for (int i = 0; i < 7; i++)
        {
            tempDate = Util.dateAddSpaceMilliSecond(tempDate, spaceMilliSecond);
            if (isSelectWeek(tempDate))
            {
                returnDate = tempDate;
                break;
            }
        }
        return returnDate;
    }

    /**
     * 设置某星期是否被选, 0为星期日 ,1为星期一....6为星期六
     *
     * @param week
     * @param bSelect
     */
    public void setSelectWeek(int week, boolean bSelect)
    {
        selectWeek[week] = bSelect;
    }

    /**
     * 判断某星期是否被选
     */
    public boolean isSelectWeek(int i)
    {
        return selectWeek[i];
    }

    /**
     * 判断某天所属星期几是否被选
     */
    public boolean isSelectWeek(Date date)
    {
        if (date == null)
        {
            return false;
        }
        c.setTime(date);
        //Calendar.DAY_OF_WEEK:星期日=1,星期六=7 c.get(Calendar.DAY_OF_WEEK)
        return isSelectWeek(c.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public String getTimePlanString()
    {
        StringBuffer sb = new StringBuffer("");
        if (selectWeek[1])
        {
            sb.append("周一,");
        }
        if (selectWeek[2])
        {
            sb.append("周二,");
        }
        if (selectWeek[3])
        {
            sb.append("周三,");
        }
        if (selectWeek[4])
        {
            sb.append("周四,");
        }
        if (selectWeek[5])
        {
            sb.append("周五,");
        }
        if (selectWeek[6])
        {
            sb.append("周六,");
        }
        if (selectWeek[0])
        {
            sb.append("周日,");
        }
        return "每周的" + sb.toString() + "运行";
    }
}
