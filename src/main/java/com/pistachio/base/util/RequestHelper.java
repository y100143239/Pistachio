package com.pistachio.base.util;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * 描述:	 Request工具类
 */
public final class RequestHelper
{
    private static Logger logger = Logger.getLogger(RequestHelper.class);

    /**
     * 从表单中提取字符串
     *
     * @param request   HttpServletRequest对象
     * @param fieldName 表单字段名称
     * @return 字符串，如果为NULL返回空字符
     */
    public static String getString(HttpServletRequest request, String fieldName)
    {
        return getString(request, fieldName, "");
    }

    /**
     * 从表单中提取字符串
     *
     * @param request      HttpServletRequest对象
     * @param fieldName    表单字段名称
     * @param defaultValue 缺省值
     * @return
     */
    public static String getString(HttpServletRequest request, String fieldName, String defaultValue)
    {
        return getString(request, fieldName, defaultValue, null);
    }

    /**
     * 从表单中提取字符串，指定编码
     *
     * @param request      HttpServletRequest对象
     * @param fieldName    表单字段名称
     * @param defaultValue 缺省值
     * @param encoding 解码所需编码 如：gbk、utf-8
     * @return
     */
    public static String getString(HttpServletRequest request, String fieldName, String defaultValue, String encoding)
    {
        String value = request.getParameter(fieldName);
        if ( value != null && value.length() > 0 )
        {
            if ( encoding != null && encoding.length() > 0 )
            {
                try
                {
                    value = URLDecoder.decode(value, encoding);
                }
                catch (UnsupportedEncodingException e)
                {
                    throw new RuntimeException(e);
                }
            }
            value = StringHelper.encodeHtml(value);
        }
        return value == null ? defaultValue : value;
    }

    /**
     * 从表单是提取数值，如果不存在，返回0
     *
     * @param request request
     * @param fieldName 表单名称
     * @return 数值，如果不存在，返回0
     */
    public static int getInt(HttpServletRequest request, String fieldName)
    {
        String value = getString(request, fieldName);
        if ( StringHelper.isEmpty(value) )
        {
            return 0;
        }
        try
        {
            return new Integer(value).intValue();
        }
        catch (Exception ex)
        {
            logger.debug(fieldName + "对应的值[" + value + "]不是数字，返回0", ex);
            return 0;
        }
    }

    /**
     * 描述：从表单是提取数值，如果不存在，返回0
     * 作者：
     * @param request request
     * @param fieldName 表单名称
     * @param defaultValue 缺省值
     * @return 数值，如果不存在，返回缺省值
     */
    public static int getInt(HttpServletRequest request, String fieldName, int defaultValue)
    {
        int value = getInt(request, fieldName);
        if ( value == 0 )
        {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 描述：从表单是提取数值，如果不存在，返回0
     * 作者：
     * @param request request
     * @param fieldName 表单名称
     * @return 数值，如果不存在，返回0
     */
    public static long getLong(HttpServletRequest request, String fieldName)
    {
        String value = getString(request, fieldName);
        if ( StringHelper.isEmpty(value) )
        {
            return 0;
        }
        try
        {
            return new Long(value).longValue();
        }
        catch (Exception ex)
        {
            logger.debug(fieldName + "对应的值[" + value + "]不是数字，返回0", ex);
            return 0;
        }
    }

    /**
     * 描述：从表单是提取数值，如果不存在，返回0
     * 作者：
     * @param request request
     * @param fieldName 表单名称
     * @param defaultValue 缺省值
     * @return 数值，如果不存在，返回缺省值
     */
    public static long getLong(HttpServletRequest request, String fieldName, long defaultValue)
    {
        long value = getLong(request, fieldName);
        if ( value == 0 )
        {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 描述：从表单是提取数值，如果不存在，返回0
     * 作者：
     * @param request request
     * @param fieldName 表单名称
     * @return 数值，如果不存在，返回0
     */
    public static double getDouble(HttpServletRequest request, String fieldName)
    {
        String value = request.getParameter(fieldName);
        if ( StringHelper.isEmpty(value) )
        {
            return 0;
        }
        try
        {
            return new Double(value).doubleValue();
        }
        catch (Exception ex)
        {
            logger.debug(fieldName + "对应的值[" + value + "]不是数字，返回0", ex);
            return 0;
        }
    }

    /**
     * 描述：从表单是提取数值，如果不存在，返回0
     * 作者：
     * @param request request
     * @param fieldName 表单名称
     * @param defaultValue 缺省值
     * @return 数值，如果不存在，返回缺省值
     */
    public static double getDouble(HttpServletRequest request, String fieldName, double defaultValue)
    {
        double value = getDouble(request, fieldName);
        if ( value == 0 )
        {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @return
     */
    public static String getStrAttribute(HttpServletRequest request, String attributeName)
    {
        String value = (String) request.getAttribute(attributeName);
        return value == null ? "" : value;
    }

    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @return
     */
    public static Object getAttribute(HttpServletRequest request, String attributeName)
    {
        Object value = request.getAttribute(attributeName);
        return value == null ? null : value;
    }

    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @param defaultValue  缺省值
     * @return
     */
    public static String getStrAttribute(HttpServletRequest request, String attributeName, String defaultValue)
    {
        String value = (String) request.getAttribute(attributeName);
        return value == null ? defaultValue : value;
    }

    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @return
     */
    public static int getIntAttribute(HttpServletRequest request, String attributeName)
    {
        String value = getStrAttribute(request, attributeName);
        if ( StringHelper.isEmpty(value) )
        {
            return 0;
        }
        try
        {
            return new Integer(value).intValue();
        }
        catch (Exception ex)
        {
            logger.debug(attributeName + "对应的属性[" + value + "]不是数字，返回0", ex);
            return 0;
        }
    }

    /**
     * 从HttpServletRequest中提取属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @param defaultValue  缺省值
     * @return
     */
    public static int getIntAttribute(HttpServletRequest request, String attributeName, int defaultValue)
    {
        int value = getIntAttribute(request, attributeName);
        if ( value == 0 )
        {
            value = defaultValue;
        }
        return value;
    }

    /**
     * 向HttpServletRequest中设置属性值
     *
     * @param request       HttpServletRequest对象
     * @param attributeName 属性名称
     * @param value         属性值
     */
    public static void setIntAttribute(HttpServletRequest request, String attributeName, int value)
    {
        request.setAttribute(attributeName, new Integer(value));
    }

    /**
     * 向HttpServletRequest中设置属性值
     *
     * @param request         HttpServletRequest对象
     * @param attributeName   属性名称
     * @param value           属性值
     */
    public static void setAttribute(HttpServletRequest request, String attributeName, Object value)
    {
        request.setAttribute(attributeName, value);
    }

    /**
     * 向HttpServletRequest中设置属性值
     *
     * @param request         HttpServletRequest对象
     * @param attributeName   属性名称
     * @param value           属性值
     */
    public static void setStrAttribute(HttpServletRequest request, String attributeName, String value)
    {
        request.setAttribute(attributeName, value);
    }

    /**
     * 描述：
     * 作者：
     * @param request request
     * @param fieldName 字段名称
     * @return 字符串数组
     */
    public static String[] getStringArray(HttpServletRequest request, String fieldName)
    {
        return request.getParameterValues(fieldName);
    }

    /**
     * 描述：提取数字数组
     * 作者：
     * @param request request
     * @param fieldName 字段名
     * @return
     */
    public static int[] getIntArray(HttpServletRequest request, String fieldName)
    {
        String[] array = RequestHelper.getStringArray(request, fieldName);
        if ( array == null || array.length == 0 )
        {
            return null;
        }
        int[] value = new int[array.length];
        for (int i = 0; i < array.length; i++)
        {
            value[i] = Integer.parseInt(array[i]);
        }
        return value;
    }

    /**
     * 转发请求.
     *
     * @param request  HTTP请求.
     * @param response HTTP响应.
     * @param url      需转发到的URL.
     */
    public static void dispatchRequest(HttpServletRequest request, HttpServletResponse response, String url)
            throws IOException, ServletException
    {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
        rd = null;
    }

    /**
     * 描述：判断是否是提交回来
     * 作者：
     * @param request request
     * @return
     */
    public static boolean isPostBack(HttpServletRequest request)
    {
        String method = request.getMethod();
        if ( "POST".equalsIgnoreCase(method) )
        {
            return true;
        }
        return false;
    }

    /**
     * 描述：删除request中的所有attribute值
     * 作者：
     * 时间：Oct 29, 2008 4:16:23 PM
     * @param request request
     */
    public static void removeAllAttribute(HttpServletRequest request)
    {
        Enumeration enumeration = request.getAttributeNames();
        while (enumeration.hasMoreElements())
        {
            String name = (String) enumeration.nextElement();
            request.removeAttribute(name);
        }
    }

}
