package com.pistachio.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:	 cookie工具类
 */
public final class CookieHelper
{
    /**
     * 根据cookie名称，得到对应的cookie值
     *
     * @param request    HttpServletRequest对象
     * @param cookieName cookie的名称
     * @return 对应cookie的值,若不存在，则返回null
     */

    public static Cookie getCookieByName(HttpServletRequest request, String cookieName)
    {
        Cookie cookies[] = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equalsIgnoreCase(cookieName))
                {
                    return (Cookie) cookies[i].clone();
                }
            }
        }
        return null;
    }


    /**
     * 根据cookie名称，得到cookie的值
     *
     * @param request    HttpServletRequest对象
     * @param cookieName Cookie的名称
     * @return cookie的值，若不存在，则返回空字串
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName)
    {
        String value = "";

        Cookie cookie = getCookieByName(request, cookieName);
        if (cookie != null)
        {
            value = cookie.getValue();
        }

        return value;
    }


    /**
     * 删除cookie
     *
     * @param request    HttpServletRequest对象
     * @param response   HttpServletResponse对象
     * @param cookieName cookie名称
     * @return
     */
    public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName)
    {
        Cookie cookie = getCookieByName(request, cookieName);
        if (cookie == null)
        {
            return false;
        }
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return true;
    }


    /**
     * 描述：设置 Cookie
     * 作者：
     * 时间：Oct 29, 2008 2:32:40 PM
     * @param request request
     * @param response response
     * @param cookieName cookie名
     * @param value cookie值
     * @param maxAge 保存最大时长
     * @return
     */
    public static boolean setCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String value, int maxAge)
    {
        if (StringHelper.isEmpty(cookieName))
        {
            return false;
        }

        Cookie cookie = getCookieByName(request, cookieName);
        if (cookie == null)
        {
            createCookie(request, response, cookieName, value, maxAge);
        }
        else
        {
            cookie.setValue(value);
            response.addCookie(cookie);
        }

        return true;
    }


    /**
     * 描述：设置 Cookie
     * 作者：
     * 时间：Oct 29, 2008 2:37:48 PM
     * @param request request
     * @param response response
     * @param cookieName cookie名
     * @param value cookie值
     * @return
     */
    public static boolean setCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String value)
    {
        if (StringHelper.isEmpty(cookieName))
        {
            return false;
        }

        Cookie cookie = getCookieByName(request, cookieName);
        if (cookie == null)
        {
            createCookie(request, response, cookieName, value);
        }
        else
        {
            cookie.setValue(value);
            response.addCookie(cookie);
        }

        return true;
    }

    /**
     * 描述：设置 Cookie 保存最大时长
     * 作者：
     * 时间：Oct 29, 2008 2:38:42 PM
     * @param request request
     * @param response response
     * @param cookieName cookie名
     * @param maxAge 保存最大时长
     */
    public static void setCookieMaxAge(HttpServletRequest request, HttpServletResponse response,
                                       String cookieName, int maxAge)
    {
        if (StringHelper.isEmpty(cookieName))
        {
            return;
        }

        Cookie cookie = getCookieByName(request, cookieName);
        if (cookie != null)
        {
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }
    }

    /**
     * 描述：创建有生命周期的 Cookie
     * 作者：
     * 时间：Oct 29, 2008 2:39:41 PM
     * @param request request
     * @param response response
     * @param cookieName cookie名
     * @param value cookie值
     * @param maxAge 保存最大时长
     * @return
     */
    public static boolean createCookie(HttpServletRequest request, HttpServletResponse response,
                                       String cookieName, String value, int maxAge)
    {
        Cookie cookie = new Cookie(cookieName, value);
        if (maxAge > 0)
        {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        response.addCookie(cookie);

        return true;
    }

    /**
     * 描述：创建无生命周期的 Cookie
     * 作者：
     * 时间：Oct 29, 2008 2:40:26 PM
     * @param request request
     * @param response response
     * @param cookieName cookie名
     * @param value cookie值
     * @return
     */
    public static boolean createCookie(HttpServletRequest request, HttpServletResponse response,
                                       String cookieName, String value)
    {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        response.addCookie(cookie);

        return true;
    }

    /**
     * 设置cookie值
     *
     * @param response response
     * @param cookieName cookie名
     * @param value cookie值
     * @param maxAge 保存最大时长
     * @param path cookie路径
     * @param domain domain
     * @return
     */
    public static boolean setCookie(HttpServletResponse response, String cookieName, String value, int maxAge, String path, String domain)
    {
        if (StringHelper.isEmpty(cookieName))
        {
            return false;
        }

        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        if (!StringHelper.isEmpty(domain))
        {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);

        return true;
    }
}

