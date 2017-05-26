package com.pistachio.base.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 描述:
 */
public class IniReader
{

	private static Logger logger = Logger.getLogger(IniReader.class);

	protected HashMap sections = new HashMap();

	/**
	 * 重试次数
	 */
	private int RETRY_TIME = 10;

	private transient String currentSection;

	private transient Properties current;

	/**
	 * 描述： 构造方法
	 * @param filename ini文件路径
	 */
	public IniReader(String filename)
	{
		int count = 0;
		while (count < RETRY_TIME)
		{
			try
			{
				init(filename);
			}
			catch (IOException e)
			{
				count++;
				if (count >= RETRY_TIME)
				{
					logger.error("读取[" + filename + "]出现错误，错误次数达到" + RETRY_TIME + "次，请检查。", e);
				}
				continue;
			}
			break;
		}
	}

	private void init(String filename) throws IOException
	{

		FileReader reader = null;
		BufferedReader bufReader = null;
		try
		{
			reader = new FileReader(filename);
			bufReader = new BufferedReader(reader);
			String line;
			while ((line = bufReader.readLine()) != null)
			{
				parseLine(line);
			}
		}
		finally
		{
			try
			{
				if (bufReader != null)
				{
					bufReader.close();
				}
				if (reader != null)
				{
					reader.close();
				}
			}
			catch (Exception ex)
			{
				logger.error("关闭文件reader出错", ex);
			}
		}

	}

	protected void parseLine(String line)
	{
		line = line.trim();
		if (line.startsWith("[") && line.endsWith("]")) //处理section
		{
			currentSection = line.substring(1, line.length() - 1);
			currentSection = currentSection.trim();
			current = new Properties();
			sections.put(currentSection, current);
		}
		else if (line.indexOf('=') >= 0) //处理值
		{
			int i = line.indexOf('=');
			String name = line.substring(0, i);
			String value = line.substring(i + 1);
			current.setProperty(name, value);
		}
	}

	/**
	 * 描述：从配置文件中读取数据值
	 * @param section 项目名。比如[LOG]
	 * @param name 键值。比如INITSZDATE
	 * @return
	 */
	public String getValue(String section, String name)
	{
		Properties p = (Properties) sections.get(section);
		if (p == null)
		{
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}

	/**
	 * 描述：获取所有的项目
	 * @deprecated
	 * @return
	 */
	public HashMap getSections()
	{
		return sections;
	}

}
