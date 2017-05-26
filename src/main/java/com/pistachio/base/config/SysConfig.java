package com.pistachio.base.config;

import com.pistachio.base.domain.Config;
import com.pistachio.base.jdbc.DataRow;
import com.pistachio.base.service.ConfigService;
import com.pistachio.base.service.ServiceLocator;
import com.pistachio.base.util.ReflectHelper;
import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * the class is used to save the system configuration informations.
 */
public class SysConfig
{
	
	private static SysConfig instance = new SysConfig();
	
	private static DataRow itemMap = new DataRow();
	
	public static ArrayList rightUrl = new ArrayList();
	
	private static Logger logger = Logger.getLogger(SysConfig.class);
	
	static
	{
		loadConfig();
	}
	
	private SysConfig()
	{
	}
	
	public static SysConfig getInstance()
	{
		return instance;
	}
	
	/**
	 *   access authorization URL
	 *
	 */
	public static void loadRight()
	{
		ConfigService service = (ConfigService) ServiceLocator.getService(ConfigService.class);
		rightUrl = (ArrayList) service.loadRight();
	}
	
	/**
	 * read the configuration file from database in
	 */
	public static void loadConfig()
	{
		DataRow data = new DataRow();
		List configList = null;
		/**
		 * Considering that many projects can't operate JDBC directly, add a implementation class of datasource here
		 */
		String configDataImpl = Configuration.getString("system.configDataImpl");
		if (StringHelper.isNotEmpty(configDataImpl))
		{
			Object object = ReflectHelper.objectForName(configDataImpl);
			if (object instanceof ConfigDataSource)
			{
				configList = ((ConfigDataSource) object).getAllSysConfig();
			}
		}
		else
		{
			ConfigService service = (ConfigService) ServiceLocator.getService(ConfigService.class);
			configList = service.getAllSysConfig();
		}
		if (configList != null)
		{
			for (Iterator iter = configList.iterator(); iter.hasNext();)
			{
				Config item = (Config) iter.next();
				String name = item.getName();
				String value = item.getValue();
				data.set(name, value);
			}
		}
		DataRow tempMap = itemMap;
		itemMap = data;
		tempMap.clear();
	}
	
	/**
	 * set the configuration information
	 *
	 * @param name  the name of the key
	 * @param value the specific value
	 */
	public static void set(String name, String value)
	{
		itemMap.set(name, value);
	}
	
	/**
	 * set configuration information
	 *
	 * @param name
	 * @param value
	 */
	public static void set(String name, int value)
	{
		itemMap.set(name, value);
	}
	
	/**
	 * get string configuration information
	 *
	 * @param name
	 * @return return "" if not exist
	 */
	public static String getString(String name)
	{
		return itemMap.getString(name);
	}
	
	/**
	 * return int configuration information
	 *
	 * @param name
	 * @return  return 0 is not exists or failed to convert
	 */
	public static int getInt(String name)
	{
		return itemMap.getInt(name);
	}
	
	/**
	 * return the line number of ervery page , default value is 20
	 *
	 * @return
	 */
	public static int getRowOfPage()
	{
		int rowOfPage = SysConfig.getInt("system.rowOfPage");
		return rowOfPage == 0 ? 20 : rowOfPage;
	}
	
	/**
	 * get the max size of upload file, the default value is 5M
	 *
	 * @return
	 */
	public static int getMaxUploadSize()
	{
		int maxUploadSize = SysConfig.getInt("system.maxUploadSize");
		return maxUploadSize == 0 ? 5242880 : maxUploadSize;
	}
}
