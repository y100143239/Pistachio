package com.pistachio.base.service.impl;

import com.pistachio.base.dao.ConfigDao;
import com.pistachio.base.domain.Config;
import com.pistachio.base.jdbc.DBPage;
import com.pistachio.base.service.BaseService;
import com.pistachio.base.service.ConfigService;

import java.util.List;

/**
 * 描述:
 * 版权:	 Copyright (c) 2005
 * 公司:	 思迪科技
 * 作者:	 易庆锋
 * 版本:	 1.0
 * 创建日期: 2006-11-2
 * 创建时间: 17:09:14
 */
public class ConfigServiceImpl extends BaseService implements ConfigService
{



	/**
	 * 添加新的配置信息
	 *
	 * @param config
	 * @return
	 */
	public void addConfig(Config config)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		String id = getSeqValue("T_SYS_CONFIG");
		config.setId(Integer.parseInt(id));
		configDao.addConfig(config);
	}



	/**
	 *
	 * 读取权限URL
	 * @return
	 */
	public List loadRight()
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		return configDao.loadRight();
	}



	/**
	 * 更新配置信息
	 *
	 * @param config
	 * @return
	 */
	public void updateConfig(Config config)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		configDao.updateConfig(config);
	}



	/**
	 * 删除配置信息
	 *
	 * @param id
	 */
	public void deleteConfig(int id)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		configDao.deleteConfig(id);
	}



	/**
	 * 根据name，得到某一个配置的详细信息
	 *
	 * @param id 配置的ID值
	 * @return
	 */
	public Config findConfigByName(String name)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		return configDao.findConfigByName(name);
	}



	/**
	 * 根据ID，得到某一个配置的详细信息
	 *
	 * @param id 配置的ID值
	 * @return
	 */
	public Config findConfigById(int id)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		return configDao.findConfigById(id);
	}



	/**
	 * 获得所有的配置信息，返回列表中的每一项都是一个config
	 *
	 * @return
	 */
	public List getAllSysConfig()
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		return configDao.getAllSysConfig();
	}



	/**
	 * 获得分页数据
	 *
	 * @return
	 */
	public DBPage getPageData(int curPage, int numPerPage, String keyword)
	{
		ConfigDao configDao = (ConfigDao) getDao(ConfigDao.class);
		return configDao.getPageData(curPage, numPerPage, keyword);
	}
}
