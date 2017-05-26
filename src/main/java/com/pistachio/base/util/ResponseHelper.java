package com.pistachio.base.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述:	 Response工具类
 */
public class ResponseHelper
{
    private static Logger logger = Logger.getLogger(ResponseHelper.class);
    /**
     * 把内容打印到HttpServletResponse中
     *
     * @param response HttpServletResponse对象
     * @param content  需要输出的内容
     */
    public static void print(HttpServletResponse response, String content)
    {
        try
        {
            response.getWriter().print(content);
        }
        catch (IOException ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 把内容打印到HttpServletResponse中
     *
     * @param response HttpServletResponse对象
     * @param content  需要输出的内容
     */
    public static void print(HttpServletResponse response, Object content)
    {
        try
        {
            response.getWriter().print(content);
        }
        catch (IOException ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 把内容打印到HttpServletResponse中
     *
     * @param response HttpServletResponse对象
     * @param content  需要输出的内容
     * @param contentType  http输出类型内容
     */
    public static void print(HttpServletResponse response, Object content, String contentType)
    {
        try
        {
            response.setContentType(contentType);
            response.getWriter().print(content);
        }
        catch (IOException ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 把内容打印到HttpServletResponse中
     *
     * @param response HttpServletResponse对象
     * @param content  需要输出的内容
     */
    public static void println(HttpServletResponse response, Object content)
    {
        try
        {
            response.getWriter().print(content);
        }
        catch (IOException ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 把内容打印到HttpServletResponse中
     *
     * @param response HttpServletResponse对象
     * @param content  需要输出的内容
     */
    public static void println(HttpServletResponse response, String content)
    {
        try
        {
            response.getWriter().print(content);
        }
        catch (IOException ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }
}
