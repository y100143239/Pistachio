package com.pistachio.base.common;

import com.pistachio.base.util.FileHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Read and cache all the hint correspond to the errorCode in the jar and the path of class,
 * and provide the methods that transfer the errorCode to message
 * note: The priority of the  configuration in the path of class is higher than it in jar.
 * i.e. if there exists same configuration in the jar and the class path , it's subject to the
 * class path.
 */
public class ShowMsgCache
{
    
    private static Logger               logger         = Logger.getLogger(ShowMsgCache.class);
    
    /**
     * the message source file information(Chinese) in classpath saved in the memory.
     */
    private static Map<Integer, String> fileMsgCache   = new HashMap<Integer, String>();
    
    /**
     * the message source file information(English) in classpath saved in the memory.
     */
    private static Map<Integer, String> fileEnMsgCache = new HashMap<Integer, String>();
    
    /**
     * the message source file information(Chinese) in jar that saved in memory
     */
    private static Map<Integer, String> jarMsgCache    = new HashMap<Integer, String>();
    
    /**
     * the message source file information(English) in jar that saved in memory
     */
    private static Map<Integer, String> jarEnMsgCache  = new HashMap<Integer, String>();
    
    static
    {
        //read configuration file in Chinsese
        loadMsgCache(Constants.SHOW_MSG_FILE, Constants.LOCALE_CH);
        //read the configuration file in English
        loadMsgCache(Constants.SHOW_EN_MSG_FILE, Constants.LOCALE_EN);
    }

    /**
     *
     * @param msgFile the name of the configuration file
     */
    private static void loadMsgCache(String msgFile, String locale)
    {
        //read the message source file in jar
        ClassLoader loader = ShowMsgCache.class.getClassLoader();
        Enumeration<URL> urls = null;
        try
        {
            urls = loader.getResources(msgFile);//traverse all jar and classpath,query showMsg.txt configuration file
        }
        catch (IOException e)
        {
            logger.warn("read[" + Constants.SHOW_MSG_FILE + "] configuration file error", e);
        }
        if ( urls != null )
        {
            while (urls.hasMoreElements())
            {
                URL url = urls.nextElement();
                if ( "jar".equals(url.getProtocol()) )//read the message source file in jar
                {
                    Map<Integer, String> resultMap = readMsgByUrl(url, false);
                    if ( resultMap != null )
                    {
                        if ( Constants.LOCALE_EN.equalsIgnoreCase(locale) )
                        {
                            jarEnMsgCache.putAll(resultMap);
                        }
                        else
                        {
                            jarMsgCache.putAll(resultMap);
                        }
                    }

                }
                else if ( "file".equals(url.getProtocol()) )//read message resource file in local classpath
                {
                    Map<Integer, String> resultMap = readMsgByUrl(url, true);
                    if ( resultMap != null )
                    {
                        if ( Constants.LOCALE_EN.equalsIgnoreCase(locale) )
                        {
                            fileEnMsgCache = resultMap;
                        }
                        else
                        {
                            fileMsgCache = resultMap;
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @desc read message resource file from jar
     * @param url
     * @param isUseFilesLabel whether use files label,the referenced message source files are not allowed to
     *                        use files tag, to prevent a circular reference
     * @return
     */
    private static Map<Integer, String> readMsgByUrl(URL url, boolean isUseFilesLabel)
    {
        List<String> dataList = FileHelper.readLines(url);
        return getMsgByList(url.getFile(), dataList, isUseFilesLabel);
    }

    /**
     *
     * @desc read the message resource file in classpath
     * @param fileName
     * @param isUseFilesLabel whether use files label , the referenece message resource files are not allowed to
     *                        use files tag,to prevent circular reference
     * @return file dosen't exist or fail to read ,return null
     */
    private static Map<Integer, String> readMsgByFile(String fileName, boolean isUseFilesLabel)
    {
        ClassLoader loader = ShowMsgCache.class.getClassLoader();
        Enumeration<URL> urls = null;
        try
        {
            urls = loader.getResources(fileName);//traverse all the jar and classpath to qurey the specified configuration file
        }
        catch (IOException e)
        {
            logger.warn("read[" + fileName + "]configuration file error", e);
            return null;
        }
        if ( urls != null )
        {
            Map<Integer, String> resultMap = new HashMap<Integer, String>();
            while (urls.hasMoreElements())
            {
                URL url = urls.nextElement();
                if ( "file".equals(url.getProtocol()) )//only read the message resource file in local classpath
                {
                    Map<Integer, String> temp = readMsgByUrl(url, isUseFilesLabel);
                    if ( temp != null )
                    {
                        resultMap.putAll(temp);
                    }
                }
            }
            return resultMap;
        }
        else
        {
            logger.warn("not find[" + fileName + "]configuration file. Return null");
            return null;
        }
    }

    /**
     *
     * @param dataList the message set need to be dealt with
     * @param isUseFilesLabel whether to use files label, the referenced message resource file is not allowed to use files tag,
     *                        to prevent circular reference.
     * @return
     */
    private static Map<Integer, String> getMsgByList(String fileName, List<String> dataList, boolean isUseFilesLabel)
    {
        Map<Integer, String> resultMap = new HashMap<Integer, String>();
        for (String line : dataList)
        {
            //remove all full-width whitespace, remove leading and trailing whitespace. If any whitespace is needed in message, use &nbsp;
            //line = line.replaceAll("[  ]", "").trim();
            line = line.trim();
            if ( StringUtils.isBlank(line) )
            {
                continue;
            }
            //# and // symbol are comments
            if ( line.startsWith("#") || line.startsWith("//") )
            {
                continue;
            }

            //The first = symbol at the leading position of the string, don't use split to break, in case of there's = symbol in message
            int pos = line.indexOf("=");
            if ( pos <= 0 || pos == line.length() - 1 )//no =(-1), or = at the beginning(0), or at the end (line.length() - 1)
            {
                logger.warn("in [" + fileName + "], [" + line + "] is not stardard error message configuration, skip this line. ");
                continue;
            }
            String key = line.substring(0, pos);
            String value = line.substring(pos + 1);
            if ( StringUtils.isEmpty(value) )
            {
                logger.warn("in [" + fileName + "], [" + line + "] is not stardard error message configuration, skip this line. ");
                continue;
            }

            //files is a variable here, it presents to reference another message file, multiple use | to seperate
            if ( isUseFilesLabel && Constants.SHOW_MSG_OTHER_KEY.equalsIgnoreCase(key) )
            {
                //the priorities of other files in classpath lower that it
                String[] files = StringUtils.split(value, ',');
                for (int i = 0; i < files.length; i++)
                {
                    Map<Integer, String> otherMsg = readMsgByFile(files[i], false);
                    if ( otherMsg != null )
                    {
                        resultMap.putAll(otherMsg);
                    }
                }
                continue;
            }

            int errorCode = 0;
            try
            {
                errorCode = Integer.parseInt(key);
            }
            catch (NumberFormatException e)
            {
                logger.warn("[" + fileName + "] configuratio item [" + line + "]key is not the type int, skip this line. ");
                continue;
            }

            value = value.replaceAll("&nbsp;", " ");
            resultMap.put(errorCode, value);
        }
        return resultMap;
    }

    /**
     * get the prompt message according to the error No(will use parameter to replace placeholders in information)
     * priority order: showMsg.properties in classpath > other files in classpath(multiple files in acoordance with the configuration order)
     * > showMsg.properties in jar
     * @param errorCode
     * @param params the parameter in prompt message
     * @return prompt message, return null if not found
     */
    public static String getShowMsg(int errorCode, String[] params)
    {
        return getShowMsg(Constants.LOCALE_CH, errorCode, params);
    }

    /**
     * @desc get the prompt message according to the error No(will use parameter to replace placeholders in information)
     * priority order: showMsg.properties in classpath > other files in classpath(multiple files in acoordance with the configuration order)
     * > showMsg.properties in jar
     * @param locale : configuratio file language environment CH: read configuration file data in Chinese   EN: read configuration file data in English
     * @param errorCode
     * @param params the parameter in prompt message
     * @return prompt message, return null if not found
     */
    public static String getShowMsg(String locale, int errorCode, String[] params)
    {
        String msg = getShowMsg(locale, errorCode);
        if ( StringUtils.isNotBlank(msg) && params != null && params.length > 0 )
        {
            for (int i = 0; i < params.length; i++)
            {
                msg = StringUtils.replace(msg, "${" + i + "}", params[i]);
            }
        }
        return msg;
    }

    /**
     **
     * @desc get the prompt message according to the error No(will use parameter to replace placeholders in information)
     * priority order: showMsg.properties in classpath > other files in classpath(multiple files in acoordance with the configuration order)
     * > showMsg.properties in jar
     * @param errorCode the No. of error
     * @return prompt message, return "" if not found
     */
    public static String getShowMsg(int errorCode)
    {
        return getShowMsg(Constants.LOCALE_CH, errorCode);
    }

    /**
     **
     * @desc get the prompt message according to the error No(will use parameter to replace placeholders in information)
     * priority order: showMsg.properties in classpath > other files in classpath(multiple files in acoordance with the configuration order)
     * > showMsg.properties in jar
     * @param errorCode the No. of error
     * @param locale : configuratio file language environment CH: read configuration file data in Chinese   EN: read configuration file data in English
     * @return return ""  if not found
     */
    public static String getShowMsg(String locale, int errorCode)
    {
        if ( Constants.LOCALE_EN.equalsIgnoreCase(locale) )
        {
            if ( fileEnMsgCache.containsKey(errorCode) )
            {
                return fileEnMsgCache.get(errorCode);
            }
            if ( jarEnMsgCache.containsKey(errorCode) )
            {
                return jarEnMsgCache.get(errorCode);
            }
            else
            {
                return "";
            }
        }
        else
        {
            if ( fileMsgCache.containsKey(errorCode) )
            {
                return fileMsgCache.get(errorCode);
            }
            if ( jarMsgCache.containsKey(errorCode) )
            {
                return jarMsgCache.get(errorCode);
            }
            else
            {
                return "";
            }
        }
    }
}
