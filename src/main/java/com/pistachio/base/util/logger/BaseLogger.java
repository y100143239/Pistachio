package com.pistachio.base.util.logger;

import com.pistachio.base.config.Configuration;
import com.pistachio.base.util.FileHelper;
import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述:
 */
public abstract class BaseLogger
{

    private static Logger       logger      = Logger.getLogger(BaseLogger.class);

    //日志级别定义
    private static int          ALL_LEVEL   = 0;

    private static int          DEBUG_LEVEL = 1;

    private static int          INFO_LEVEL  = 2;

    private static int          WARN_LEVEL  = 3;

    private static int          ERROR_LEVEL = 4;

    private static int          FATAL_LEVEL = 5;

    private static int          OFF_LEVEL   = 6;

    //*****************************************************

    //日志记录级别(缺省为ALL)
    private int                 logLevel    = 0;

    //需要写入的日志消息
    private LinkedBlockingQueue msgQueue    = null;

    //日志格式化对象
    private SimpleDateFormat    dateFormat  = null;

    //日志写入线程
    private LoggerThread        logThread   = null;

    /**
     * 构造函数，启动日志写入线程
     */
    public BaseLogger(String logLevel, String logFilePath, String logFileName)
    {
        logLevel = (StringHelper.isEmpty(logLevel)) ? "ALL" : logLevel;
        setLogLevel(logLevel);

        msgQueue = new LinkedBlockingQueue();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logThread = new LoggerThread(logFilePath, logFileName);
        logThread.start();
    }

    /**
     * 日志运行级别
     *
     * @param logLevel 日志级别
     */
    public void setLogLevel(String logLevel)
    {
        String strTemp = logLevel;

        if ( strTemp.equalsIgnoreCase("ALL") )
        {
            this.logLevel = ALL_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("DEBUG") )
        {
            this.logLevel = DEBUG_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("INFO") )
        {
            this.logLevel = INFO_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("WARN") )
        {
            this.logLevel = WARN_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("ERROR") )
        {
            this.logLevel = ERROR_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("FATAL") )
        {
            this.logLevel = FATAL_LEVEL;
        }
        else if ( strTemp.equalsIgnoreCase("OFF") )
        {
            this.logLevel = OFF_LEVEL;
        }
        else
        {
            this.logLevel = ALL_LEVEL;
        }
    }

    /**
     * 格式化日志字串
     *
     * @return
     */
    private String getLongTimeStr()
    {
        return dateFormat.format(new Date());
    }

    /**
     * 把日志消息加入记录队列
     *
     * @param msg
     */
    private void addLogMessage(String msg)
    {
        try
        {
            msgQueue.add(msg);
        }
        catch (Exception ex)
        {
            logger.error("", ex);
        }
    }

    /**
     * 记录FATAL级别日志
     *
     * @param msg
     */
    public void fatal(String msg)
    {
        if ( logLevel <= FATAL_LEVEL )
        {
            String msgTemp = getLongTimeStr();
            msgTemp += " [FATAL] ";
            msgTemp += msg;
            msgTemp += "\r\n";
            addLogMessage(msgTemp);
        }
    }

    /**
     * 记录ERROR级别日志
     *
     * @param msg
     */
    public void error(String msg)
    {
        if ( logLevel <= ERROR_LEVEL )
        {
            String msgTemp = getLongTimeStr();
            msgTemp += " [ERROR] ";
            msgTemp += msg;
            msgTemp += "\r\n";
            addLogMessage(msgTemp);
        }
    }

    /**
     * 记录WARN级别日志
     *
     * @param msg
     */
    public void warn(String msg)
    {
        if ( logLevel <= WARN_LEVEL )
        {
            String msgTemp = getLongTimeStr();
            msgTemp += " [WARN] ";
            msgTemp += msg;
            msgTemp += "\r\n";
            addLogMessage(msgTemp);
        }
    }

    /**
     * 记录INFO级别日志
     *
     * @param msg
     */
    public void info(String msg)
    {
        if ( logLevel <= INFO_LEVEL )
        {
            String msgTemp = getLongTimeStr();
            msgTemp += " [INFO] ";
            msgTemp += msg;
            msgTemp += "\r\n";
            addLogMessage(msgTemp);
        }
    }

    /**
     * 记录DEBUG级别日志
     *
     * @param msg
     */
    public void debug(String msg)
    {
        if ( logLevel <= DEBUG_LEVEL )
        {
            String msgTemp = getLongTimeStr();
            msgTemp += " [DEBUG] ";
            msgTemp += msg;
            msgTemp += "\r\n";
            addLogMessage(msgTemp);
        }
    }

    boolean IsFatalEnabled()
    {
        return (logLevel <= FATAL_LEVEL);
    }

    boolean IsErrorEnabled()
    {
        return (logLevel <= ERROR_LEVEL);
    }

    boolean IsWarnEnabled()
    {
        return (logLevel <= WARN_LEVEL);
    }

    boolean IsInfoEnabled()
    {
        return (logLevel <= INFO_LEVEL);
    }

    boolean IsDebugEnabled()
    {
        return (logLevel <= DEBUG_LEVEL);
    }

    /**
     * 获取日志队列长度
     *
     * @return
     */
    int getQueueLength()
    {
        return msgQueue.size();
    }

    //**********************************************************************************

    /**
     * 日志写入线程
     */
    class LoggerThread extends Thread
    {

        private int              writeLength = 4000;

        //日志格式化对象
        private SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyyMMdd");

        //文件操作对象
        FileWriter               writer      = null;

        //日志文件路径
        private String           logFilePath = "";

        //日志名
        private String           logFileName = "";

        //当前操作文件路径
        private String           curFilePath = "";

        public LoggerThread(String filePath, String fileName)
        {
            //创建目录

            logFilePath = filePath;
            logFileName = fileName;

            writeLength = Configuration.getInt("system.writeLength", 4000);

            FileHelper.createDirectory(filePath);
        }

        /**
         * 线程运行方法
         */
        public void run()
        {
            while (true)
            {
                try
                {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < msgQueue.size() && i < writeLength; i++)
                    {
                        builder.append((String) msgQueue.take());
                    }
                    openLogFile();
                    writeLog(builder.toString());
                    flushLogFile();
                }
                catch (Exception ex)
                {
                    logger.error("", ex);
                }
            }
        }

        /**
         * 格式化日志字串
         *
         * @return
         */
        private String getShortTimeStr()
        {
            return dateFormat.format(new Date());
        }

        /**
         * 打开新的日志文件
         *
         * @param
         */
        private void openLogFile()
        {
            boolean bNeedOpen = false;

            String filePath = "";
            if ( !StringHelper.isEmpty(logFileName) )
            {
                filePath = logFilePath + "/" + logFileName + getShortTimeStr() + ".log";
            }
            else
            {
                filePath = logFilePath + "/" + getShortTimeStr() + ".log";
            }
            filePath = FileHelper.normalize(filePath);

            if ( writer == null ) //文件没有打开,需要重新打开
            {
                bNeedOpen = true;
            }
            else if ( !filePath.equalsIgnoreCase(curFilePath) ) //需要写入的日志文件和当前日志文件名不一样，需要重新打开(一天一个日志文件)
            {
                bNeedOpen = true;
            }

            if ( bNeedOpen ) //重现打开文件
            {
                if ( writer != null )
                {
                    closeLogFile();
                    writer = null;
                }

                try
                {
                    writer = new FileWriter(filePath, true);
                    curFilePath = filePath;
                }
                catch (Exception ex)
                {
                    logger.error("", ex);
                }
            }
        }

        /**
         * 写日志入文件
         */
        private void writeLog(String msg)
        {
            try
            {
                writer.write(msg);
            }
            catch (Exception ex)
            {
                logger.error("", ex);
            }
        }

        /**
         * 刷新日志文件，把内容写入文件
         */
        private void flushLogFile()
        {
            try
            {
                if ( writer != null )
                {
                    writer.flush();
                }
            }
            catch (Exception ex)
            {
                logger.error("", ex);
            }
        }

        /**
         * 关闭当前已经打开的日志
         */
        private void closeLogFile()
        {
            try
            {
                if ( writer != null )
                {
                    writer.close();
                }
            }
            catch (Exception ex)
            {
                logger.error("", ex);
            }
        }
    }

}
