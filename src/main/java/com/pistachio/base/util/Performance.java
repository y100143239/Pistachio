package com.pistachio.base.util;

/**
 * 描述:	 间隔的时间
 */
public class Performance
{
    /**
     * 开始时间
     */
    private long startTime = 0;

    /**
     * 描述： 构造方法
     */
    public Performance()
    {
        startTime = System.currentTimeMillis();
    }

    /**
     * 返回间隔的时间(毫秒)
     *
     * @return
     */
    public long getTime()
    {
        return System.currentTimeMillis() - startTime;
    }
}
