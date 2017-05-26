package com.pistachio.base.dao;

import com.pistachio.base.domain.Config;
import com.pistachio.base.jdbc.DBPage;

import java.util.List;

public interface ConfigDao
{
	
	public void addConfig(Config config);
	
	
	public void updateConfig(Config config);
	
	
	public void deleteConfig(int id);
	
	
	public Config findConfigById(int id);
	
	
	public Config findConfigByName(String name);
	
	
	public List getAllSysConfig();
	
	
	public DBPage getPageData(int curPage, int numPerPage, String keyword);
	
	
	public List loadRight();
}
