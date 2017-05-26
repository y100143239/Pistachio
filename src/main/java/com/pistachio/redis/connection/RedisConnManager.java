package com.pistachio.redis.connection;

import com.pistachio.base.util.ConvertHelper;
import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Transaction;

import java.util.HashMap;

/**
 * 描述:	 数据库连接管理
 */

public final class RedisConnManager
{

	private static Logger logger = Logger.getLogger(RedisConnManager.class);

	private static RedisConfigure configure = RedisConfigure.getInstance();

	/**
	 * 根据datasource.xml文件中配置的数据源ID，得到对应的数据库连接池中的连接
	 *
	 * @param id 数据源ID
	 * @return conn
	 */
	public static Jedis getConnection(String id)
	{
		JedisPool dataSource = configure.getDataSource(id);
		if(dataSource != null)
		{
			Jedis conn = dataSource.getResource();
			return conn;
		}
		return null;
	}

	/**
	 * 获得缺省的数据源连接池的数据库连接
	 *
	 * @return conn
	 */
	public static Jedis getConnection()
	{
		JedisPool dataSource = configure.getDataSource();
		if(dataSource != null)
		{
			Jedis conn = dataSource.getResource();
			return conn;
		}
		return null;
	}

	/**
	 * 开始数据库事务
	 * @param conn
	 */
	public static Transaction begin(Jedis conn)
	{
		if (conn != null && conn.isConnected())
		{
			return conn.multi();
		}
		return null;
	}

	/**
	 * 提交数据库事务
	 * @param conn
	 */
	public static void commit(Jedis conn, Transaction trans)
	{
		if (conn != null && conn.isConnected())
		{
			trans.exec();
		}
	}

	/**
	 * 回滚数据库事务
	 * @param conn
	 */
	public static void rollback(Jedis conn, Transaction trans)
	{
		if (conn != null && conn.isConnected())
		{
			trans.discard();
		}
	}

	/**
	 * 返回连接到连接池
	 */
	public static void returnJedis(Jedis conn, String id, boolean isSuccess)
	{
		JedisPool dataSource = configure.getDataSource(id);
		if(dataSource != null)
		{
			if (isSuccess)
			{
				dataSource.returnResource(conn);
			}
			else
			{
				dataSource.returnBrokenResource(conn);
			}
		}
	}

	/**
	 * 关闭数据库连接(不使用连接池的时候在finally可以调用)
	 * @param conn
	 */
	public static void close(Jedis conn)
	{
		if (conn != null && conn.isConnected())
		{
			conn.close();
		}
	}

	/**
	 *
	 * 描述：获得数据库单连接(不使用连接池)
	 * @param id
	 * @return
	 */
	public static Jedis getSingleConn(String id)
	{
		HashMap xmlMap = configure.getDbConnXmlMap(id);
		if(xmlMap != null)
		{
			String url = (String) xmlMap.get("url");
			String[] temp = StringHelper.split(url, ":");
			String host = "";
			int port = 0;
			if (temp != null && temp.length == 2)
			{
				host = temp[0];
				port = ConvertHelper.strToInt(temp[1]);
			}
			String password = (String) xmlMap.get("password");
			String timeout = (String) xmlMap.get("timeout");
			JedisShardInfo shareInfo = new JedisShardInfo(host, port, ConvertHelper.strToInt(timeout));
			shareInfo.setPassword(password);
			Jedis conn = new Jedis(shareInfo);
			conn.connect();
			return conn;
		}
		return null;
	}

}
