package com.pistachio.base.util;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * @����: �����Ѿ�Ǩ�Ƶ�FileHelper��
 * @��Ȩ: Copyright (c) 2015 
 * @��˾: Thinkive 
 * @����: yuezz
 * @��������: 2015��7��10�� ����6:14:31
 * @deprecated
 */
public class PropHelper
{
	private static Logger logger = Logger.getLogger(PropHelper.class);
	
	/**
	*guessPropFile: �����Ѿ�Ǩ�Ƶ�FileHelper�У���Ҫ��ʹ��
	*@param cls:��ҪѰ�ҵ������ļ�������ͬ�İ��е��������
	*@param propFile:ҪѰ�ҵ������ļ���
	*@deprecated
	*/
	public static File guessPropFile(Class<?> cls, String propFile)
	{
		return FileHelper.guessPropFile(cls, propFile);
	}
}
