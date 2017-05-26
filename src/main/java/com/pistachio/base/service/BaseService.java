/*
 * Copyright (c) 2016 Your Corporation. All Rights Reserved.
 */

package com.pistachio.base.service;

import com.pistachio.base.dao.factory.DAOFactory;
import com.pistachio.base.jdbc.JdbcTemplate;
import com.pistachio.base.jdbc.connection.ConnManager;
import com.pistachio.base.jdbc.session.Session;
import com.pistachio.base.jdbc.session.SessionFactory;

import java.sql.Connection;

public class BaseService
{
	/**
	 * 获得相应的DAO对象
	 *
	 * @param interfaceClass
	 * @return
	 */
	public Object getDao(Class interfaceClass)
	{
		return DAOFactory.getDao(interfaceClass);
	}

	/**
	 * 根据datasource.xml文件中配置的缺省数据源，得到对应的连接对象
	 *
	 * @param id 数据源ID
	 * @return
	 */
	public Connection getConnection()
	{
		return ConnManager.getConnection();
	}

	/**
	 * 根据datasource.xml文件中配置的数据源ID，得到对应的连接对象
	 *
	 * @param id 数据源ID
	 * @return
	 */
	public Connection getConnection(String id)
	{
		return ConnManager.getConnection(id);
	}


	/**
	 * 根据datasource.xml文件中配置的缺省数据源，得到对应的会话对象
	 *
	 * @return
	 */
	public Session getSession()
	{
		return SessionFactory.getSession();
	}

	/**
	 * 返回根据datasource.xml文件中配置数据源ID，得到的会话对象
	 *
	 * @param id 数据源ID
	 * @return
	 */
	public Session getSession(String id)
	{
		return SessionFactory.getSession(id);
	}

	/**
	 * 返回根据datasource.xml文件中配置的数据源ID，构造的数据操作对象
	 *
	 * @param id 数据源的ID
	 */
	public JdbcTemplate getJdbcTemplate(String id)
	{
		return new JdbcTemplate(id);
	}


	/**
	 * 根据Sequence的名称，得到sequence的值
	 *
	 * @param seqName sequence的名称，对应到T_SEQUENCE表中的某一项的名称
	 * @return
	 */
	public String getSeqValue(String seqName)
	{
		return SequenceGenerator.getInstance().getNextSequence(seqName);
	}


	/**
	 * 根据Sequence的名称，得到sequence的值
	 *
	 * @param seqName sequence的名称，对应到T_SEQUENCE表中的某一项的名称
	 * @return
	 */
	public String getSeqValue(String id, String seqName)
	{
		return SequenceGenerator.getInstance().getNextSequence(id, seqName);
	}
}