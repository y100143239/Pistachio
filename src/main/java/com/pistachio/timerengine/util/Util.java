package com.pistachio.timerengine.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述:
 */
public class Util
{

    public static String dateToLongStr(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static boolean isDateBeforeCurrentDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar now=Calendar.getInstance();
        return cal.before(now);
    }

    public static Date dateAddSpaceMilliSecond(Date date, int milliSecond)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MILLISECOND, milliSecond);
        return cal.getTime();
    }

    public static int getDayFromMilliSecond(int milliSecond)
    {
        return (milliSecond) / (24 * 60 * 60 * 1000);
    }

    public static int getDaySpareFromMilliSecond(int milliSecond)
    {
        return (milliSecond) % (24 * 60 * 60 * 1000);
    }

    public static int getHourFromMilliSecond(int milliSecond)
    {
        return (milliSecond) / (60 * 60 * 1000);
    }

    public static int getHourSpareFromMilliSecond(int milliSecond)
    {
        return (milliSecond) % (60 * 60 * 1000);
    }

    public static int getMinuteFromMilliSecond(int milliSecond)
    {
        return (milliSecond) / (60 * 1000);
    }

    public static int getMinuteSpareFromMilliSecond(int milliSecond)
    {
        return (milliSecond) % (60 * 1000);
    }

    public static int getSecondFromMilliSecond(int milliSecond)
    {
        return (milliSecond) / (1000);
    }

    public static int getSecondSpareFromMilliSecond(int milliSecond)
    {
        return (milliSecond) % (1000);
    }

    /**
     * 根据当前的classpath设置， 显示出包含指定类的类文件所在 位置的绝对路径
     */
    public static String which(String className)
    {
        if (!className.startsWith("/"))
        {
            className = "/" + className;
        }
        className = className.replace('.', '/');
        className = className + ".class";

        java.net.URL classUrl = Util.class.getResource(className);

        return classUrl.getPath();
    }

    public static String getMSecondDescribe(int spareMSecond)
    {
        int n = 0;
        StringBuffer buffer = new StringBuffer();

        n = Util.getDayFromMilliSecond(spareMSecond);
        if (n > 0)
        {
            buffer.append("" + n + "天");
        }
        spareMSecond = Util.getDaySpareFromMilliSecond(spareMSecond);
        if (spareMSecond == 0)
        {
            return buffer.toString();
        }

        n = Util.getHourFromMilliSecond(spareMSecond);
        if (n > 0)
        {
            buffer.append("" + n + "小时");
        }
        spareMSecond = Util.getHourSpareFromMilliSecond(spareMSecond);
        if (spareMSecond == 0)
        {
            return buffer.toString();
        }

        n = Util.getMinuteFromMilliSecond(spareMSecond);
        if (n > 0)
        {
            buffer.append("" + n + "分");
        }
        spareMSecond = Util.getMinuteSpareFromMilliSecond(spareMSecond);
        if (spareMSecond == 0)
        {
            return buffer.toString();
        }

        n = Util.getSecondFromMilliSecond(spareMSecond);
        if (n > 0)
        {
            buffer.append("" + n + "秒");
        }
        spareMSecond = Util.getSecondSpareFromMilliSecond(spareMSecond);
        if (spareMSecond == 0)
        {
            return buffer.toString();
        }

        buffer.append("" + spareMSecond + "毫秒");
        return buffer.toString();
    }
}