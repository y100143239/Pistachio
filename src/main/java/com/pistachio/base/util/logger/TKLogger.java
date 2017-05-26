package com.pistachio.base.util.logger;

/**
 *
 * @描述:
 */
public class TKLogger
{
    private static SimpleLogger simpleLogger = SimpleLogger.getInstance();

    private TKLogger()
    {

    }

    /**
     * 记录FATAL级别日志
     *
     * @param msg
     */
    public static void fatal(String msg)
    {
        simpleLogger.fatal(msg);
    }

    /**
     * 记录ERROR级别日志
     *
     * @param msg
     */
    public static void error(String msg)
    {
        simpleLogger.error(msg);
    }

    /**
     * 记录WARN级别日志
     *
     * @param msg
     */
    public static void warn(String msg)
    {
        simpleLogger.warn(msg);
    }

    /**
     * 记录INFO级别日志
     *
     * @param msg
     */
    public static void info(String msg)
    {
        simpleLogger.info(msg);
    }

    /**
     * 记录DEBUG级别日志
     *
     * @param msg
     */
    public static void debug(String msg)
    {
        simpleLogger.debug(msg);
    }
}
