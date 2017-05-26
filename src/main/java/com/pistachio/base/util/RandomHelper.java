package com.pistachio.base.util;

import java.util.Random;

/**
 * 描述: 随机数工具类
 */
public class RandomHelper {
	/**
	 * RANDOM 基数
	 */
	private final static int RANDOM_BASE = 10;

	/**
	 * 产生指定长度的数字值随机数
	 *
	 * @param length
	 *            需要产生的长度
	 * @return
	 */
	public static String getRandomStr(int length) {
		Random random = new Random();
		String randStr = "";
		for (int i = 0; i < length; i++) {
			String randItem = String.valueOf(random.nextInt(RANDOM_BASE));
			randStr += randItem;
		}
		return randStr;
	}

	/**
	 * 描述：手机验证码生成带字符 作者： 时间：Oct 29, 2008 3:40:07 PM
	 *
	 * @param len
	 *            生成手机验证码长度
	 * @return
	 */
	public String identifyCodeBuilder(int len) {
		// char[]
		// identifyStr={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I',
		// 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] identifyStr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		// 生成随机类
		// Random random = new Random();
		int min = 0;
		int maxnum = identifyStr.length;
		String codeStr = "";
		for (int i = 0; i < len; i++) {
			int num = (int) ((maxnum - min) * Math.random() + min);
			codeStr += identifyStr[num];
		}
		return codeStr;
	}

	/**
	 * 从 UUID改到当前这个地方
	 * @return
	 */
	public static String getUUIDString() {
		String result = UUID.randomUUID().toString();
		result = result.replaceAll("-", "");
		return result;
	}

}
