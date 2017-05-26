package com.pistachio.base.ip;


import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * ip Utils class
 */
public class Utils
{
	private static Logger logger = Logger.getLogger(Utils.class);
    /** */
    /**
     * get ip in byte array type from string type
     * 
     * @param ip string type ip
     *
     * @return  byte array type ip
     */
    public static byte[] getIpByteArrayFromString(String ip)
    {
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try
        {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        }
        catch (Exception e)
        {
            logger.error("ip["+ip+"]format not correctȷ", e);
        }
        return ret;
    }
    
    /**
     * encode the original string, if failed, return the original string
     * 
     * @param s original string
     * @param srcEncoding  source encoding way
     * @param destEncoding destination encoding way
     * @return string encoded, return original string if encoding failed
     */
    public static String getString(String s, String srcEncoding, String destEncoding)
    {
        try
        {
            return new String(s.getBytes(srcEncoding), destEncoding);
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error("Encoding format isn't correct, return original string", e);
            return s;
        }
    }
    
    /**
     * convert the byte array into string according to a certain encoding way
     * 
     * @param b byte array
     * @param encoding encoding way
     * @return if encoding is not supported, return a string in default encoding way
     */
    public static String getString(byte[] b, String encoding)
    {
        try
        {
            return new String(b, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error("Encoding format isn't correct, return default encoding string", e);
            return new String(b);
        }
    }
    
    /**
     * convert byte array into string according to a certain encoding way
     * 
     * @param b byte array
     * @param offset the start position of the string to convert
     * @param len the length to convert
     * @param encoding encoding way
     * @return if encoding isn't supported, return string in default encoding way
     */
    public static String getString(byte[] b, int offset, int len, String encoding)
    {
        try
        {
            return new String(b, offset, len, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error("Encoding format isn't correct, return string in default encoding way", e);
            return new String(b, offset, len);
        }
    }
    
    /** */
    /**
     * @param ip  ip in byte array type
     * @return ip in string type
     */
    public static String getIpStringFromBytes(byte[] ip)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }
    
}
