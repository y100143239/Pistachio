package com.pistachio.base.service;

import com.pistachio.base.domain.Config;
import com.pistachio.base.jdbc.DBPage;

import java.util.List;

public interface ConfigService
{

	/**
	 * 添加新的配置信息
	 *
	 * @param config
	 * @return
	 */
	public void addConfig(Config config);



	/**
	 *
	 * 读取权限URL
	 */
	public List loadRight();



	/**
	 * 更新配置信息
	 *
	 * @param config
	 * @return
	 */
	public void updateConfig(Config config);



	/**
	 * 删除配置信息
	 *
	 * @param id
	 */
	public void deleteConfig(int id);



	/**
	 * 根据ID，得到某一个配置的详细信息
	 *
	 * @param id 配置的ID值
	 * @return
	 */
	public Config findConfigById(int id);


	public Config findConfigByName(String name);



	/**
	 * 获得所有的配置信息，返回列表中的每一项都是一个config对象
	 *
	 * @return
	 */
	public List getAllSysConfig();



	/**
	 * 获得分页数据
	 *
	 * @return
	 */
	public DBPage getPageData(int curPage, int numPerPage, String keyword);

}
