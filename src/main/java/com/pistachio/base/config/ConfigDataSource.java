package com.pistachio.base.config;

import com.pistachio.base.domain.Config;

import java.util.List;

/**
 *
 */
public interface ConfigDataSource
{
	
	public List<Config> getAllSysConfig();
}
