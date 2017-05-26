package com.pistachio.base.util;

import org.apache.log4j.Logger;

@Deprecated
public class ThreadHelper
{
	private static Logger logger = Logger.getLogger(ThreadHelper.class);



	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException ex)
		{
			logger.error("", ex);
		}
	}
}
