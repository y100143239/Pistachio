package com.pistachio.redis.client;

import com.pistachio.base.util.StringHelper;
import com.pistachio.redis.connection.RedisConnManager;
import com.pistachio.redis.util.JsonHelper;
import com.pistachio.redis.util.SerializeHelper;
import org.apache.log4j.Logger;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 *
 * 描述: redis的连接客户端
 */
public class JedisClient
{

	private static Logger logger = Logger.getLogger(JedisClient.class);

	private String id = "";

	public JedisClient()
	{
	}

	public JedisClient(String id)
	{
		this.id = id;
	}

	/**
	 *
	 * 描述：获取redis连接
	 * @return
	 */
	public Jedis getJedis()
	{
		if(StringHelper.isNotEmpty(this.id))
		{
			return RedisConnManager.getConnection(this.id);
		}
		return RedisConnManager.getConnection();
	}

	/**
	 *
	 * 描述：缓存数据
	 * @param key
	 * @param value
	 */
	public boolean set(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.set(key, value);
				return result.equalsIgnoreCase("OK");
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：缓存数据
	 * @param key
	 * @param value
	 */
	public boolean set(String key,byte[] value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.set(key.getBytes(), value);
				return result.equalsIgnoreCase("OK");
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：缓存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key,Map<String,String> value)
	{
		String jsonStr = JsonHelper.getJSONString(value);
		return set(key, jsonStr);
	}

	/**
	 *
	 * 描述：缓存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key,List<Map<String, String>> value)
	{
		String jsonStr = JsonHelper.getJSONString(value);
		return set(key, jsonStr);
	}

	/**
	 *
	 * 描述：缓存数据，obj必须是支持序列化的对象
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean set(String key ,Object obj)
	{
		return set(key, SerializeHelper.serialize(obj));
	}

	/**
	 *
	 * 描述：设置缓存数据，并设定缓存的时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public boolean set(String key,String value,int seconds)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.setex(key, seconds, value);
				return result.equalsIgnoreCase("OK");
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：设置缓存数据，并设定缓存的时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public boolean set(String key,byte[] value,int seconds)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.setex(key.getBytes(), seconds, value);
				return result.equalsIgnoreCase("OK");
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：设置缓存数据，并设定缓存的时间
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public boolean set(String key,Map<String, String> value,int seconds)
	{
		String jsonStr = JsonHelper.getJSONString(value);
		return set(key, jsonStr, seconds);
	}

	/**
	 *
	 * 描述：设置缓存数据，并设定缓存的时间
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public boolean set(String key,List<Map<String, String>> value,int seconds)
	{
		String jsonStr = JsonHelper.getJSONString(value);
		return set(key, jsonStr, seconds);
	}

	/**
	 *
	 * 描述：缓存数据，并设定缓存的时间，obj必须是支持序列化的对象
	 * 作者：d2015-9-8 下午09:35:18
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean set(String key ,Object obj,int seconds)
	{
		return set(key, SerializeHelper.serialize(obj),seconds);
	}

	/**
	 *
	 * 描述：获取缓存值
	 * @param key
	 * @return
	 */
	public String getString(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.get(key);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：获取缓存值
	 * @param key
	 * @return
	 */
	public byte[] getBytes(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.get(key.getBytes());
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：获取缓存的值
	 * @param key
	 * @return
	 */
	public Map<String,String>getMap(String key)
	{
		String value = getString(key);
		if(StringHelper.isNotEmpty(value))
		{
			Object o = JsonHelper.getObjectByJSON(value);
			if(o != null && o instanceof Map)
			{
				return (Map<String,String>)o;
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：获取缓存的值
	 * @param key
	 * @return
	 */
	public List<Map<String,String>>getList(String key)
	{
		String value = getString(key);
		if(StringHelper.isNotEmpty(value))
		{
			Object o = JsonHelper.getObjectByJSON(value);
			if(o != null && o instanceof List)
			{
				return (List<Map<String,String>>)o;
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：获取缓存的序列化的对象
	 * @param key
	 * @return
	 */
	public Object getObject(String key)
	{
		byte[] value = getBytes(key);
		if(value != null && value.length > 0)
		{
			return SerializeHelper.unserialize(value);
		}
		return null;
	}

	/**
	 *
	 * 描述：设置数据的失效时间
	 * @param key
	 * @param seconds
	 */
	public void expire(String key,int seconds)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.expire(key, seconds);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：设置数据的失效时间
	 * @param key
	 * @param date
	 */
	public void expireAt(String key,Date date)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.expireAt(key, date.getTime());
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：获取剩余的失效时间
	 * @param key
	 * @return
	 */
	public long getLastExpireTime(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.ttl(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return Long.MAX_VALUE;
	}

	/**
	 *
	 * 描述：强制数据失效
	 * @param key
	 */
	public void persist(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.persist(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：重命名key
	 * @param key
	 * @param key1
	 * @return
	 */
	public boolean rename(String key,String key1)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.rename(key, key1);
				return "OK".equalsIgnoreCase(result);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：是否包含指定的key
	 * @param key
	 * @return
	 */
	public boolean exists(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.exists(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：删除key
	 * @param key
	 */
	public void delete(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.del(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：数据库大小
	 * @return
	 */
	public long dbsize()
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.dbSize();
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：获取服务器的信息和统计。
	 * @return
	 */
	public String info()
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.info();
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：增长一个整形的值，返回增长后的值，失败返回Long.MAX_VALUE
	 * @param key
	 * @param integer
	 */
	public long incrby(String key,long integer)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.incrBy(key, integer);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return Long.MAX_VALUE;
	}

	/**
	 *
	 * 描述：减小一个整形的值,返回减小后的值，失败返回Long.MIN_VALUE
	 * @param key
	 * @param integer
	 */
	public long decrby(String key,long integer)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.decrBy(key, integer);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return Long.MIN_VALUE;
	}

	/**
	 *
	 * 描述：扩展缓存的字符串
	 * @param key
	 * @param value
	 */
	public void append(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.append(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：删除所有数据库中的所有key
	 */
	public boolean flushall()
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.flushAll();
				return "OK".equalsIgnoreCase(result);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：删除当前选择数据库中的所有key。
	 * @param db
	 * @return
	 */
	public boolean flushDB(int db)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.select(db);
				String result = jedis.flushDB();
				return "OK".equalsIgnoreCase(result);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：redis内置的设置Map对象方法
	 * @param key
	 * @param value
	 */
	public boolean hset(String key,Map<String ,String> value)
	{
		this.hdelete(key);
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.hmset(key, value);
				return "OK".equalsIgnoreCase(result);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：redis内置的获取Map对象方法
	 * @param key
	 * @return
	 */
	public Map<String,String> hget(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.hgetAll(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：redis内置的删除Map对象的方法
	 * @param key
	 */
	public void hdelete(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				Set<String> keys = jedis.hkeys(key);
				if(keys != null && keys.size() > 0)
				{
					for (Iterator iterator = keys.iterator(); iterator.hasNext();)
					{
						String k = (String) iterator.next();
						jedis.hdel(key, k);
					}
				}
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：设置数据
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public void hset(String key,String field,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.hset(key, field, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：删除数据
	 * @param key
	 * @param field
	 */
	public void hdelete(String key,String field)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.hdel(key, field);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
	}

	/**
	 *
	 * 描述：判断字段是否存在
	 * @param key
	 * @param field
	 */
	public boolean hexists(String key,String field)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.hexists(key, field);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：加入数据到队列头部
	 * @param key
	 * @param value
	 * @return
	 */
	public long lpush(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.lpush(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：加入数据到队列尾部
	 * @param key
	 * @param value
	 * @return
	 */
	public long rpush(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.rpush(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：在指定元素之前插入数据
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 */
	public long linsert(String key,String pivot,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.linsert(key, LIST_POSITION.BEFORE, pivot, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：设置队列中的数据
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean lset(String key,long index,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.lset(key, index, value);
				return "OK".equalsIgnoreCase(result);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：删除队列中的指定元素
	 * @param key
	 * @param value
	 * @param count
	 */
	public long lrem(String key,String value,long count)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				jedis.lrem(key, count, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：获取子队列
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key,long start, long end)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.lrange(key, start, end);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：从list 的头部删除元素，并返回删除元素
	 * @param key
	 */
	public String lpop(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.lpop(key);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：从list 的尾部删除元素，并返回删除元素
	 * @param key
	 * @return
	 */
	public String rpop(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.rpop(key);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：返回名称为key 的list 中index 位置的元素
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key,long index)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.lindex(key, index);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：返回key 对应list 的长度
	 * @param key
	 * @return
	 */
	public long llen(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.llen(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：从第一个list 的尾部移除元素并添加到第二个list 的头部,
	 * 最后返回被移除的元素值，整个操作是原子的.如果第一个list 是空或者不存在返回nil
	 * @param key
	 * @param key1
	 * @return
	 */
	public String rpoplpush(String key,String key1)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.rpoplpush(key, key1);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：向名称为key 的set 中添加元素
	 * @param key
	 * @param value
	 * @return
	 */
	public long sadd(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.sadd(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：删除名称为key 的set 中的元素member
	 * @param key
	 * @param value
	 * @return
	 */
	public long srem(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.srem(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：随机返回并删除名称为key 的set 中一个元素
	 * @param key
	 * @return
	 */
	public String spop(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				String result = jedis.spop(key);
				if(StringHelper.isEmpty(result) || "nil".equalsIgnoreCase(result))
				{
					result = "";
				}
				return result;
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return "";
	}

	/**
	 *
	 * 描述：返回所有给定key 与第一个key 的差集
	 * @param key
	 * @param key1
	 * @return
	 */
	public Set<String>sdiff(String key,String key1)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.sdiff(key,key1);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回所有给定key 的交集
	 * @param key
	 * @param key1
	 * @return
	 */
	public Set<String>sinter(String key,String key1)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.sinter(key,key1);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回所有给定key 的并集
	 * @param key
	 * @param key1
	 * @return
	 */
	public Set<String>sunion(String key,String key1)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.sunion(key,key1);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回名称为key 的set 的元素个数
	 * @param key
	 * @param key
	 * @return
	 */
	public long scard(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.scard(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：测试member 是否是名称为key 的set 的元素
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.sismember(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return false;
	}

	/**
	 *
	 * 描述：向名称为key 的zset 中添加元素value，score 用于排序。如果该元素已经存在，则根据score 更新该元素的顺序
	 * @param key
	 * @param value
	 * @return
	 */
	public long zadd(String key,String value,double score)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zadd(key, score, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：删除名称为key 的zset 中的元素member
	 * @param key
	 * @param value
	 * @return
	 */
	public long zrem(String key ,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrem(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：返回名称为key 的zset 中value 元素的排名(按score 从小到大排序)即下标
	 * @param key
	 * @param value
	 * @return
	 */
	public long zrank(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrank(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：返回名称为key 的zset 中member 元素的排名(按score 从大到小排序)即下标
	 * @param key
	 * @param value
	 * @return
	 */
	public long zrevrank(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrevrank(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：返回名称为key 的zset（按score 从小到大排序）中的index 从start 到end 的所有元素
	 * @param key
	 * @return
	 */
	public Set<String> zrange(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrange(key, 0, -1);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回名称为key 的zset（按score 从大到小排序）中的index 从start 到end 的所有元素
	 * @param key
	 * @return
	 */
	public Set<String> zrevrange(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrevrange(key, 0, -1);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回集合中score 在给定区间的元素
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String>zrangebyscore(String key,double min,double max)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zrangeByScore(key, min, max);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return null;
	}

	/**
	 *
	 * 描述：返回集合中score 在给定区间的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zcount(String key,double min,double max)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zcount(key, min, max);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：返回集合中元素个数
	 * @param key
	 * @return
	 */
	public long zcard(String key)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zcard(key);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：返回给定元素对应的score
	 * @param key
	 * @param value
	 * @return
	 */
	public double zscore(String key,String value)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zscore(key, value);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：删除集合中排名在给定区间的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public long zremrangebyrank(String key,long start, long end)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zremrangeByRank(key, start, end);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}

	/**
	 *
	 * 描述：删除集合中score 在给定区间的元素
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public long zremrangebyscore(String key,float min,float max)
	{
		Jedis jedis = null;
		boolean success = true;
		try
		{
			jedis = this.getJedis();
			if(jedis != null)
			{
				return jedis.zremrangeByScore(key, min, max);
			}
		}
		catch (JedisException e)
		{
			success = false;
			logger.error("",e);
		}
		finally
		{
			if (jedis != null)
			{
				RedisConnManager.returnJedis(jedis, this.id, success);
			}
		}
		return -1;
	}
}
