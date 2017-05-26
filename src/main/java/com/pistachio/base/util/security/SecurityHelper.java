package com.pistachio.base.util.security;

import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;

/**
 * 描述:  加解密封装类
 */
public class SecurityHelper
{

	/**
	 * 常量 8
	 */
	private final static int FINAL_EIGHT = 8;

	/**
	 * 常量 24
	 */
	private final static int FINAL_TWENTYFOUR = 24;

	/**
	 * SecurityHelper 日志
	 */
	private static Logger logger = Logger.getLogger(SecurityHelper.class);

	/**
	 * 密钥
	 */
	private static String key = "B49A86FA425D439dB510A234A3E25A3E";

	/**
	 * 根据MD5摘要算法，生成相应的32位的MD5摘要字串
	 *
	 * @param inbuf 字串参数
	 * @return
	 */
	public static String getMD5of32Str(String inbuf)
	{
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(inbuf);
	}

	/**
	 * 根据MD5摘要算法，生成相应的16位的MD5摘要字串(取32位中间的8-24位)
	 *
	 * @param inbuf 字串参数
	 * @return
	 */
	public static String getMD5of16Str(String inbuf)
	{
		MD5 md5 = new MD5();
		String str = md5.getMD5ofStr(inbuf);
		if (!StringHelper.isEmpty(str))
		{
			str = str.substring(FINAL_EIGHT, FINAL_TWENTYFOUR);
		}
		return str;
	}

	/**
	 *
	 * 描述：根据SHA-512 摘要算法，生成摘要字串
	 * @param inbuf
	 * @return
	 */
	public static String getSHA512Str(String inbuf)
	{
		SHA512 sha512 = new SHA512();
		return sha512.getSHA512ofStr(inbuf);
	}

	/**
	 * 采用DES算法对字串进行加密
	 *
	 * @param originStr 需要加密的字串
	 * @return
	 */
	public static String encode(String originStr)
	{
		return encode(originStr, key);
	}

	/**
	 * 采用DES算法对字串进行加密
	 *
	 * @param originStr 需要加密的字串
	 * @param key       加密的密匙
	 * @return
	 */
	public static String encode(String originStr, String key)
	{
		try
		{
			KDES kDes = new KDES();
			kDes.setKey(key);
			return kDes.encode(originStr);
		}
		catch (Exception ex)
		{
			logger.error("加密出错", ex);
		}
		return "";
	}

	/**
	 * 采用DES算法对字串进行解密
	 *
	 * @param originStr 需要解密的字串
	 * @return
	 */
	public static String decode(String originStr)
	{
		return decode(originStr, key);
	}

	/**
	 * 采用DES算法对字串进行解密
	 *
	 * @param originStr 需要解密的字串
	 * @param key       解密的密匙
	 * @return
	 */
	public static String decode(String originStr, String key)
	{
		try
		{
			KDES kDes = new KDES();
			kDes.setKey(key);
			return kDes.decode(originStr, key);
		}
		catch (Exception ex)
		{
			logger.error("解密出错", ex);
		}
		return "";
	}

}
