package com.pistachio.redis.connection;

import com.pistachio.base.config.Configuration;
import com.pistachio.base.util.ConvertHelper;
import com.pistachio.base.util.FileHelper;
import com.pistachio.base.util.PropHelper;
import com.pistachio.base.util.StringHelper;
import com.pistachio.base.util.security.AES;
import com.pistachio.base.util.security.SecurityHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * abtain the database configuration information of the datasource.xml
 */
public final class RedisConfigure
{
	private static HashMap dataSourceMap = new HashMap();
	
	private static HashMap dbConnXmlMap = new HashMap();
	
	private static String _default = "";
	
	private static Logger logger = Logger.getLogger(RedisConfigure.class);
	
	private static RedisConfigure instance = new RedisConfigure();
	
	/**
	 * The database configuration file
	 */
	private static final String CONFIG_FILE_NAME = "redis_datasource.xml";
	
	/**
	 * AES encrypt required data file
	 */
	private static String KEY_FILE_NAME = "database.dat";
	
	private static String ENCRYPT_KEY = Configuration.getString("encryption.key");
	
	/**
	 * Whether or not encrypt keyword
	 */
	private static final String IS_ENCRYPT_KEYWORD = "encrypt:";
	
	static
	{
		loadConfig();
	}
	
	private RedisConfigure()
	{
	}
	
	/**
	 * get instance
	 */
	public static RedisConfigure getInstance()
	{
		return instance;
	}
	
	/**
	 * destroy datasource
	 */
	public static void destroyDataSource()
	{
		try
		{
			for (Iterator iter = dataSourceMap.keySet().iterator(); iter.hasNext();)
			{
				String key = (String) iter.next();
				JedisPool dataSource = (JedisPool) dataSourceMap.get(key);
				dataSource.destroy();
			}
		}
		catch (Exception ex)
		{
		}
	}
	
	private static void loadConfig()
	{
		File file = PropHelper.guessPropFile(RedisConfigure.class, CONFIG_FILE_NAME);
		if (file != null && file.exists() && file.isFile())
		{
			InputStream is = null;
			try
			{
				is = new FileInputStream(file);
				SAXReader reader = new SAXReader();
				Document document = reader.read(is);
				if (document == null)
					return;
				//get root element
				Element rootElement = document.getRootElement();
				_default = rootElement.attributeValue("default", "");
				List funcElementList = rootElement.elements("datasource");
				boolean isEncrypt = false;
				for (Iterator iter = funcElementList.iterator(); iter.hasNext();)
				{
					Element dsElement = (Element) iter.next();
					String id = dsElement.attributeValue("id", "");
					if (StringHelper.isEmpty(id))
						continue;
					HashMap propMap = new HashMap();
					List propElementList = dsElement.elements("property");
					String encrypt = "";
					Element pwdElm = null;
					Element encryptElm = null;
					for (Iterator iter2 = propElementList.iterator(); iter2.hasNext();)
					{
						Element propElement = (Element) iter2.next();
						String name = propElement.attributeValue("name");
						String value = propElement.getTextTrim();
						if (name != null && name.equals("password"))
						{
							pwdElm = propElement;
						}
						else if (name != null && name.equals("encrypt"))
						{
							encryptElm = propElement;
						}
						propMap.put(name, value);
					}
					if (encryptElm != null)
					{
						String pwd = pwdElm.getText();
						//password isn't encrypted
						if (!pwd.startsWith(IS_ENCRYPT_KEYWORD))
						{
							//encrypt mode : AES or DES
							encrypt = encryptElm.getTextTrim();
							if (StringHelper.isNotEmpty(encrypt))
							{
								if (encrypt.toUpperCase().equalsIgnoreCase("AES"))
								{
									//AES
									String key = getAesKey();
									AES aes = new AES(key);
									pwd = IS_ENCRYPT_KEYWORD + aes.encrypt(pwd);
									pwdElm.setText(pwd);
									isEncrypt = true;
								}
								else if (encrypt.toUpperCase().equalsIgnoreCase("DES"))
								{
									//DES
									pwd = IS_ENCRYPT_KEYWORD + SecurityHelper.encode(pwd, ENCRYPT_KEY);
									pwdElm.setText(pwd);
									isEncrypt = true;
								}
							}
						}
						else
						{
							pwd = pwd.substring(IS_ENCRYPT_KEYWORD.length());
							//Encrypt mode : AES or DES
							encrypt = encryptElm.getTextTrim();
							if (encrypt.equalsIgnoreCase("AES"))
							{
								//AES
								String key = getAesKey();
								AES aes = new AES(key);
								pwd = aes.decrypt(pwd);
								propMap.put("password", pwd);
							}
							else
							{
								//DES
								pwd = SecurityHelper.decode(pwd, ENCRYPT_KEY);
								propMap.put("password", pwd);
							}
						}
					}
					if (!dbConnXmlMap.containsKey(id))
					{
						HashMap xmlMap = new HashMap();
						xmlMap.putAll(propMap);
						dbConnXmlMap.put(id, xmlMap);
					}
					JedisPool dataSource = buildDataSource(propMap);
					if (dataSource != null)
					{
						dataSourceMap.put(id, dataSource);
					}
				}
				//Rewrite the configuration file from the plaintext encrypted to the ciphertext
				if (isEncrypt)
				{
					FileHelper.WriteToXMLFile(file.getAbsolutePath(), document, "GBK");
				}
			}
			catch (Exception ex)
			{
				logger.error("", ex);
			}
			finally
			{
				if (is != null)
				{
					try
					{
						is.close();
						is = null;
					}
					catch (IOException ex)
					{
						logger.error("", ex);
					}
				}
			}
		}
	}
	
	/**
	 * Create Redis datasource
	 * commons-pool2-2.0.jar needed
	 * @param propMap
	 * @return
	 */
	private static JedisPool buildDataSource(HashMap propMap)
	{
		String url = (String) propMap.get("url");
		String[] temp = StringHelper.split(url, ":");
		String host = "";
		int port = 0;
		if(temp != null && temp.length == 2)
		{
			host = temp[0];
			port = ConvertHelper.strToInt(temp[1]);
		}
		String password = (String) propMap.get("password");
		String maxTotal = (String)propMap.get("maxTotal");
		String minIdle = (String)propMap.get("minIdle");
		String maxIdle = (String)propMap.get("maxIdle");
		String maxWait = (String)propMap.get("maxWait");
		String timeout = (String)propMap.get("timeout");
		String testOnBorrow = (String)propMap.get("testOnBorrow");
		String testOnReturn = (String)propMap.get("testOnReturn");
		String testWhileIdle = (String)propMap.get("testWhileIdle");
		String numTestsPerEvictionRun = (String)propMap.get("numTestsPerEvictionRun");
		String timeBetweenEvictionRunsMillis = (String)propMap.get("timeBetweenEvictionRunsMillis");
		String blockWhenExhausted = (String)propMap.get("blockWhenExhausted");
		propMap.clear();
		try
		{
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(ConvertHelper.strToInt(maxTotal));
			config.setMaxIdle(ConvertHelper.strToInt(maxIdle));
			config.setMinIdle(ConvertHelper.strToInt(minIdle));
			config.setMaxWaitMillis(ConvertHelper.strToLong(maxWait));
			config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
			config.setTestOnReturn(Boolean.valueOf(testOnReturn));
			config.setTestWhileIdle(Boolean.valueOf(testWhileIdle));
			config.setNumTestsPerEvictionRun(ConvertHelper.strToInt(numTestsPerEvictionRun));
			config.setTimeBetweenEvictionRunsMillis(ConvertHelper.strToInt(timeBetweenEvictionRunsMillis));
			config.setBlockWhenExhausted(Boolean.valueOf(blockWhenExhausted));
			JedisPool pooled = new JedisPool(config, host, port, ConvertHelper.strToInt(timeout),password);
			return pooled;
		}
		catch (Exception ex)
		{
			logger.error("", ex);
		}
		return null;
	}
	
	/**
	 * Get the default datasource
	 *
	 * @return
	 */
	public JedisPool getDataSource()
	{
		//Return the relative datasource directly, if there's only 1 datasource
		if (dataSourceMap.size() == 1)
		{
			Object[] dataSourceArray = dataSourceMap.values().toArray();
			return (JedisPool) dataSourceArray[0];
		}
		//Find the default datasouce if there exists multiple datasources.
		if (StringHelper.isEmpty(_default))
			return null;
		return getDataSource(_default);
	}
	
	/**
	 * Get the corresponding datasource object according to the datasource ID that configured in the file redis_datasource.xml
	 *
	 * @param id
	 * @return
	 */
	public JedisPool getDataSource(String id)
	{
		if (StringHelper.isBlank(id))
		{
			id = _default;
		}
		return (JedisPool) dataSourceMap.get(id);
	}
	
	/**
	 * Whethere datasourceid exists
	 * @param id
	 * @return
	 */
	public static boolean isExistDataSource(String id)
	{
		if (StringHelper.isBlank(id))
		{
			id = _default;
		}
		return dataSourceMap.containsKey(id);
	}
	
	/**
	 *Get the corresponding datasource object according to the datasource ID that configured in the file redis_datasource.xml
	 * @param id
	 * @return
	 */
	public HashMap getDbConnXmlMap(String id)
	{
		if (StringHelper.isBlank(id))
		{
			id = _default;
		}
		if (StringHelper.isBlank(id))
		{
			return null;
		}
		else
		{
			return (HashMap) dbConnXmlMap.get(id);
		}
	}
	
	/**
	 * @method getAeskey()
	 */
	public static String getAesKey() throws Exception
	{
		String key = "";
		File keyFile = PropHelper.guessPropFile(RedisConfigure.class, KEY_FILE_NAME);
		if (keyFile != null && keyFile.exists())
		{
			String fileKey = "B49A86FA425D439dB510A234A3E25A3E";
			byte[] data = FileHelper.readFileToByteArray(keyFile.getAbsolutePath());
			AES aes = new AES(fileKey);
			key = new String(aes.decrypt(data, fileKey.getBytes()));
		}
		return key;
	}
	
}