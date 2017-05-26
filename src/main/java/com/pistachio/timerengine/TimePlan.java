package com.pistachio.timerengine;

import java.util.Date;


public interface TimePlan
{
    /**
     * 判断还有没有下一个计划时间
     *
     * @return
     */
    public boolean haveNext();

    /**
     * 得到下一个计划时间
     *
     * @return
     */
    public Date nextDate();

    /**
     * 得到开始时间
     *
     * @return
     */

    public Date getStartDate();

    /**
     * 设定开始时间
     *
     * @param date
     */

    public void setStartDate(Date date);

    /**
     * 显示运行计划方案的文字说明
     *
     * @return
     */
    public String getTimePlanString();
}
