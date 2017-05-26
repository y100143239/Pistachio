package com.pistachio.timerengine.util;

import java.util.Timer;

/**
 *
 */
public class TimerGenerate
{
    private static TimerGenerate instance = new TimerGenerate();

    public static TimerGenerate getInstance()
    {
        return instance;
    }

    public Timer getTimer()
    {
        return new Timer();
    }

    private TimerGenerate()
    {
        super();
    }

}
