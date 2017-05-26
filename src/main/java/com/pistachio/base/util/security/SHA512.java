package com.pistachio.base.util.security;

import org.apache.log4j.Logger;

/**
 * 描述: SHA-512 摘要算法实现类
 */
public class SHA512
{
	private static Logger logger = Logger.getLogger(SHA512.class);

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private String getSHA512(byte[] source)
	{
		try
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
			md.update(source);
			byte tmp[] = md.digest(); // SHA-512 的计算结果是一个长度64的字节数组
			char str[] = new char[128]; // 以字节可转换成两位16进制数，所以64个字节需要128位
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 64; i++)
			{
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			return new String(str); // 换后的结果转换为字符串

		}
		catch (Exception e)
		{
			logger.error("获取sha出错，返回null", e);
			return null;
		}
	}

	/**
	 * getMD5ofStr是类MD5最主要的公共方法，入口参数是你想要进行MD5变换的字符串
	 * 返回的是变换完的结果，这个结果是从公共成员digestHexStr取得的．
	 */
	public String getSHA512ofStr(String inbuf)
	{
		return getSHA512(inbuf.getBytes());
	}

}
