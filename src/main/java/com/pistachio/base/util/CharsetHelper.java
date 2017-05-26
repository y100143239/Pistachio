package com.pistachio.base.util;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * 描述:	 字符集工具类
 */
public class CharsetHelper
{
    /**
     * CharsetHelper 日志
     */
    private static Logger logger = Logger.getLogger(CharsetHelper.class);

    /**
     * 描述：
     * 作者：
     * @param buffer 要编码的字节
     * @param charset 编码类型
     * @return
     */
    public static String decode(ByteBuffer buffer, String charset)
    {
        String result = "";
        try
        {
            Charset temp = Charset.forName(charset);
            CharBuffer charBuffer = temp.decode(buffer);
            result = charBuffer.toString();
        }
        catch (Exception ex)
        {
            logger.error("解码出错", ex);
        }
        return result;
    }

    /**
     * 描述：编码字串
     * 作者：
     * @param content 要编码的字符串
     * @param charset 编码类型
     * @return
     */
    public static ByteBuffer encode(String content, String charset)
    {
        CharsetEncoder encoder = null;
        ByteBuffer buffer = null;
        try
        {
            encoder = (Charset.forName(charset)).newEncoder();
            buffer = encoder.encode(CharBuffer.wrap(content.toCharArray()));
        }
        catch (Exception ex)
        {
            logger.error("编码出错", ex);
        }
        return buffer;
    }
}
