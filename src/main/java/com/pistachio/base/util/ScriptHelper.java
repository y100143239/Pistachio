package com.pistachio.base.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述:	 Script工具类
 */
public class ScriptHelper
{
    /**
     * ScriptHelper 日志
     */
    private static Logger logger = Logger.getLogger(ScriptHelper.class);


    /**
     * 通过javascript,提示信息
     *
     * @param response response
     * @param message  需要显示的信息
     */
    public static void alert(HttpServletResponse response, String message)
    {
        StringBuffer bf = new StringBuffer();

        bf.append("<script language=\"javascript\">");
        bf.append("alert(\"" + message + "\");");
        bf.append("</script>");

        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }


    /**
     * 通过javascript,提示信息，并跳转到目标页面
     *
     * @param response response
     * @param message  提示信息
     * @param redirect 跳转页面。特殊: back：后退  close:关闭窗口
     */
    public static void alert(HttpServletResponse response, String message, String redirect)
    {
        StringBuffer bf = new StringBuffer();

        bf.append("<script language=\"javascript\">");
        bf.append("alert(\"" + message + "\");");

        if (redirect.equals("back"))
        {
            bf.append("window.history.back();");

        }
        else if (redirect.equalsIgnoreCase("close"))
        {
            String outStr = "top.window.close();";
            bf.append(outStr);
        }
        else
        {
            String outStr = "window.location.href=\"" + redirect + "\";";
            bf.append(outStr);
        }

        bf.append("</script>");

        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 描述：使用boxy.alert提示
     * @param response
     * @param message
     */
    public static void bAlert(HttpServletResponse response, String message, String redirect)
    {
        bAlert(response, message, redirect, "/script/jquery/jquery-1.4.2.min.js", "/script/jquery/jquery.boxy.js", "/css/boxy.css");
    }

    /**
     * 描述：使用boxy.alert提示
     * @param response
     * @param message
     */
    public static void bAlert(HttpServletResponse response, String message, String redirect, String jqueryPath, String boxyPath, String boxyCss)
    {
        response.setHeader("Content-Type", "text/html");
        response.setCharacterEncoding("GBK");
        StringBuffer bf = new StringBuffer();

        bf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">");
        bf.append("<script type=\"text/javascript\" language=\"javascript\" src=\""+jqueryPath+"\"></script>");
        bf.append("<script type=\"text/javascript\" language=\"javascript\" src=\""+boxyPath+"\" charset=\"GBK\"></script>");
        bf.append("<link href=\""+boxyCss+"\" rel=\"stylesheet\" type=\"text/css\" />");
        bf.append("<script language=\"javascript\">");
        bf.append("$(document).ready(function() {");
        bf.append("Boxy.alert(\""+message+"\",null,{title:\"提示信息\",afterHide:function(){");
        if (redirect.equals("back"))
        {
            bf.append("window.history.back();");

        }
        else if (redirect.equalsIgnoreCase("close"))
        {
            String outStr = "top.window.close();";
            bf.append(outStr);
        }
        else
        {
            String outStr = "window.location.href=\"" + redirect + "\";";
            bf.append(outStr);
        }
        bf.append("}});");
        bf.append("});");
        bf.append("</script>");
        bf.append("</html>");

        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 通过javascript,跳转页面
     *
     * @param response response
     * @param redirect 跳转页面。特殊: back：后退  close:关闭窗口
     */
    public static void redirect(HttpServletResponse response, String redirect)
    {
        StringBuffer bf = new StringBuffer();

        bf.append("<script language=\"javascript\">");
        if (redirect.equals("back"))
        {
            bf.append("window.history.back();");

        }
        else if (redirect.equalsIgnoreCase("close"))
        {
            String outStr = "top.window.close();";
            bf.append(outStr);
        }
        else
        {
            String outStr = "window.location.href=\"" + redirect + "\";";
            bf.append(outStr);
        }

        bf.append("</script>");

        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }


    /**
     * 通过javascript，刷新当前窗口的父窗口
     *
     * @param response response
     * @param close 是否关闭当前的窗口
     */
    public static void refreshParentWin(HttpServletResponse response, boolean close)
    {
        StringBuffer bf = new StringBuffer();

        bf.append("<script language=\"javascript\">");
        bf.append("if(window.parent)");
        bf.append("{");
        bf.append("  window.parent.location.reload()");
        if (close)
        {
            bf.append("  window.close();");
        }
        bf.append("}");
        bf.append("</script>");
        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 通过javascript,刷新打开当前窗口的窗口，并关闭本窗口
     *
     * @param response response
     * @param close 是否关闭当前的窗口
     */
    public static void refreshOpenerWin(HttpServletResponse response, boolean close)
    {
        StringBuffer bf = new StringBuffer();

        bf.append("<script language=\"javascript\">");
        bf.append(" if(window.parent){");
        bf.append("  window.parent.location.reload()");
        if (close)
        {
            bf.append("  window.close();");
        }
        bf.append("}");
        bf.append("</script>");
        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }

    /**
     * 通过javascript,调用页面中的脚本
     *
     * @param response response
     * @param script  需要执行的脚本
     */
    public static void eval(HttpServletResponse response, String script)
    {
        StringBuffer bf = new StringBuffer();
        bf.append("<script language=\"javascript\">");
        bf.append(script);
        bf.append("</script>");
        try
        {
            response.getWriter().write(bf.toString());
        }
        catch (Exception ex)
        {
            logger.error("将内容写入response出错", ex);
        }
    }
}
