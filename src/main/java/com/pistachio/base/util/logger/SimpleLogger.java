package com.pistachio.base.util.logger;

import com.pistachio.base.config.Configuration;
import com.pistachio.base.util.StringHelper;

/**
 *
 * @描述: 异步简单日志功能
 */
public class SimpleLogger extends BaseLogger
{
    private static SimpleLogger simpleLogger = null;

    private SimpleLogger(String logLevel, String logFilePath, String logFileName)
    {
        super(logLevel, logFilePath, logFileName);
    }

    public synchronized static SimpleLogger getInstance()
    {
        if ( simpleLogger == null )
        {
            //日志级别
            String logLevel = Configuration.getString("logger.simpleLogLevel");
            logLevel = (StringHelper.isEmpty(logLevel)) ? "ALL" : logLevel;

            //日志保存路径
            String filePath = Configuration.getString("logger.simpleFilePath");
            filePath = (StringHelper.isEmpty(filePath)) ? "./logs/" : filePath;

            //日志文件名
            String logFileName = Configuration.getString("logger.simpleLogFileName");
            logFileName = (StringHelper.isEmpty(logFileName)) ? "simple" : logFileName;

            simpleLogger = new SimpleLogger(logLevel, filePath, logFileName);
        }
        return simpleLogger;
    }

}
