package com.pistachio.base.dao.impl;

import com.pistachio.base.dao.BaseDao;
import com.pistachio.base.dao.ConfigDao;
import com.pistachio.base.domain.Config;
import com.pistachio.base.domain.Right_Url;
import com.pistachio.base.jdbc.DBPage;
import com.pistachio.base.jdbc.DataRow;
import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigDaoImpl extends BaseDao implements ConfigDao
{

	private static Logger logger = Logger.getLogger(ConfigDaoImpl.class);
	
	private static String GET_ALL = "select * from T_SYS_CONFIG order by name";
	
	
	private static String GET_CONFIG_BY_ID = "select * from T_SYS_CONFIG where id = ?";
	
	
	private static String GET_CONFIG_BY_NAME = "select * from T_SYS_CONFIG where name = ?";
	
	
	private static String GET_RIGHT_URL = "select * from T_RIGHT_URL";
	
	
	
	public void addConfig(Config config)
	{
		DataRow row = new DataRow();
		row.putAll(config.toMap());
		getJdbcTemplate().insert("T_SYS_CONFIG", row);
	}
	
	
	public void updateConfig(Config config)
	{
		DataRow row = new DataRow();
		row.putAll(config.toMap());
		getJdbcTemplate().update("T_SYS_CONFIG", row, "id", new Integer(config.getId()));
	}
	
	
	public void deleteConfig(int id)
	{
		getJdbcTemplate().delete("T_SYS_CONFIG", "id", new Integer(id));
	}
	
	
	public Config findConfigById(int id)
	{
		DataRow row = getJdbcTemplate().queryMap(GET_CONFIG_BY_ID, new Object[] { new Integer(id) });
		if (row != null)
		{
			Config config = new Config();
			config.fromMap(row);
			return config;
		}
		return null;
	}
	
	
	public Config findConfigByName(String name)
	{
		try
		{
			DataRow row = getJdbcTemplate().queryMap(GET_CONFIG_BY_NAME, new Object[] { name });
			if (row != null)
			{
				Config config = new Config();
				config.fromMap(row);
				return config;
			}
		}
		catch (Exception ex)
		{
			logger.error("an error occured when find "+name+" related file, return null", ex);
		}
		return null;
	}
	
	
	public List getAllSysConfig()
	{
		List configList = getJdbcTemplate().query(GET_ALL);
		ArrayList newConfigList = new ArrayList();
		for (Iterator iter = configList.iterator(); iter.hasNext();)
		{
			Config config = new Config();
			DataRow row = (DataRow) iter.next();
			config.fromMap(row);
			newConfigList.add(config);
		}
		return newConfigList;
	}
	
	
	public DBPage getPageData(int curPage, int numPerPage, String keyword)
	{
		
		DBPage page = null;
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from T_SYS_CONFIG where 1=1 ");
		ArrayList argList = new ArrayList();
		if (!StringHelper.isEmpty(keyword))
		{
			sqlBuffer.append(" and (name like ? or caption like ?) ");
			argList.add("%" + keyword + "%");
			argList.add("%" + keyword + "%");
		}
		sqlBuffer.append(" order by name");
		page = getJdbcTemplate().queryPage(sqlBuffer.toString(), argList.toArray(), curPage, numPerPage);
		
		if (page != null)
		{
			List dataList = page.getData();
			ArrayList newDataList = new ArrayList();
			for (Iterator iter = dataList.iterator(); iter.hasNext();)
			{
				Config config = new Config();
				DataRow row = (DataRow) iter.next();
				config.fromMap(row);
				newDataList.add(config);
			}
			page.setData(newDataList);
		}
		
		return page;
	}
	
	
	public List loadRight()
	{
		List list = this.getJdbcTemplate().query(GET_RIGHT_URL);
		ArrayList dateList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Right_Url rightUrl = new Right_Url();
			DataRow row = (DataRow) iter.next();
			rightUrl.fromMap(row);
			dateList.add(rightUrl);
		}
		return dateList;
	}
}
