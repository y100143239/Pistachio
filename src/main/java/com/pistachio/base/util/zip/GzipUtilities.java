package com.pistachio.base.util.zip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * 描述:
 */
public class GzipUtilities
{
    /**
     * 描述：
     * 作者：
     * @param request request
     * @return
     */
    public static boolean isGzipSupported(HttpServletRequest request)
    {
        String encodings = request.getHeader("Accept-Encoding");
        return (encodings != null) && (encodings.indexOf("gzip") != -1);
    }

    /**
     * 描述：
     * 作者：
     * @param request request
     * @return
     */
    public static boolean isGzipDisabled(HttpServletRequest request)
    {
        String flag = request.getParameter("disabledGzip");
        return (flag != null) && (!flag.equalsIgnoreCase("false"));
    }

    /**
     * 描述：
     * 作者：
     * @param response response
     * @return
     * @throws IOException
     */
    public static PrintWriter getGzipWriter(HttpServletResponse response) throws IOException
    {
        return new PrintWriter(new GZIPOutputStream(response.getOutputStream()));
    }

}
