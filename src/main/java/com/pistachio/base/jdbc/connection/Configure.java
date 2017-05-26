package com.pistachio.base.jdbc.connection;

import com.mchange.v2.c3p0.DataSources;
import com.pistachio.base.config.Configuration;
import com.pistachio.base.util.FileHelper;
import com.pistachio.base.util.StringHelper;
import com.pistachio.base.util.XMLHelper;
import com.pistachio.base.util.security.AES;
import com.pistachio.base.util.security.SecurityHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @desc get the database configuration information in configuration.xml
 */
public final class Configure {

	private static HashMap dataSourceMap = new HashMap();

	private static HashMap dbConnXmlMap = new HashMap();

	private static String _default = "";

	private static Logger logger = Logger.getLogger(Configure.class);

	private static Configure instance = new Configure();

	/**
	 * database configuration file
	 */
	private static final String CONFIG_FILE_NAME = "datasource.xml";

	/**
	 * AES encrypt the needed data
	 */
	private static String KEY_FILE_NAME = "database.dat";

	private static String ENCRYPT_KEY = Configuration
			.getString("encryption.key");

	/**
	 * whether to encrypt keyword
	 */
	private static final String IS_ENCRYPT_KEYWORD = "encrypt:";

	static {
		loadConfig();
	}

	private Configure() {
	}

	/**
	 * @desc get instance
	 * 
	 * @return
	 */
	public static Configure getInstance() {
		return instance;
	}

	/**
	 * @desc destroy datasource
	 */
	public static void destroyDataSource() {
		try {
			for (Iterator iter = dataSourceMap.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				DataSource dataSource = (DataSource) dataSourceMap.get(key);
				DataSources.destroy(dataSource);
			}
		} catch (Exception ex) {
			logger.error("An error occurred when destroying datasource!", ex);
		}
	}

	private static void loadConfig() {
		try {

			Document document = XMLHelper.getDocument(Configure.class,
					CONFIG_FILE_NAME);
			if (document == null)
				return;

			// get the root element
			Element rootElement = document.getRootElement();
			_default = rootElement.attributeValue("default", "");

			List funcElementList = rootElement.elements("datasource");
			boolean isEncrypt = false;
			for (Iterator iter = funcElementList.iterator(); iter.hasNext();) {
				Element dsElement = (Element) iter.next();
				String id = dsElement.attributeValue("id", "");
				if (StringHelper.isEmpty(id))
					continue;

				HashMap propMap = new HashMap();
				List propElementList = dsElement.elements("property");

				String encrypt = "";
				Element pwdElm = null;
				Element encryptElm = null;
				for (Iterator iter2 = propElementList.iterator(); iter2
						.hasNext();) {
					Element propElement = (Element) iter2.next();
					String name = propElement.attributeValue("name");
					String value = propElement.getTextTrim();
					if (name != null && name.equals("password")) {
						pwdElm = propElement;
					} else if (name != null && name.equals("encrypt")) {
						encryptElm = propElement;
					}
					propMap.put(name, value);
				}

				if (encryptElm != null) {
					String pwd = pwdElm.getText();
					// password not encrypted
					if (!pwd.startsWith(IS_ENCRYPT_KEYWORD)) {
						// encrypt type: AES or DES
						encrypt = encryptElm.getTextTrim();

						if (StringHelper.isNotEmpty(encrypt)) {
							if (encrypt.toUpperCase().equalsIgnoreCase("AES")) {
								// AES
								String key = getAesKey();

								AES aes = new AES(key);
								pwd = IS_ENCRYPT_KEYWORD + aes.encrypt(pwd);
								pwdElm.setText(pwd);
								isEncrypt = true;
							} else if (encrypt.toUpperCase().equalsIgnoreCase(
									"DES")) {
								// DES
								pwd = IS_ENCRYPT_KEYWORD
										+ SecurityHelper.encode(pwd,
												ENCRYPT_KEY);
								pwdElm.setText(pwd);
								isEncrypt = true;
							}
						}

					} else {
						pwd = pwd.substring(IS_ENCRYPT_KEYWORD.length());

						// encrypt type : AES or DES
						encrypt = encryptElm.getTextTrim();

						if (encrypt.equalsIgnoreCase("AES")) {
							// AES
							String key = getAesKey();

							AES aes = new AES(key);
							pwd = aes.decrypt(pwd);
							propMap.put("password", pwd);
						} else {
							// DES
							pwd = SecurityHelper.decode(pwd, ENCRYPT_KEY);
							propMap.put("password", pwd);
						}
					}
				}
				if (!dbConnXmlMap.containsKey(id)) {
					HashMap xmlMap = new HashMap();
					xmlMap.putAll(propMap);
					dbConnXmlMap.put(id, xmlMap);
				}
				DataSource dataSource = buildDataSource(propMap);
				if (dataSource != null) {
					dataSourceMap.put(id, dataSource);
				}
			}
			// rewrite the configuration file when encrypt the plaintext to ciphertext
			if (isEncrypt) {
				File file = FileHelper.guessPropFile(Configure.class,
						CONFIG_FILE_NAME);
				if (file != null && file.exists()) {
					FileHelper.WriteToXMLFile(file.getAbsolutePath(), document,
							"GBK");
				}
			}
		} catch (Exception ex) {
			logger.error("An error occurred when loading configuration file", ex);
		}

	}

	/**
	 * create c3p0 datasource
	 * 
	 * @param propMap
	 * @return
	 */
	private static DataSource buildDataSource(HashMap propMap) {
		String driverName = (String) propMap.get("driver-name");
		String url = (String) propMap.get("url");
		String user = (String) propMap.get("user");
		String password = (String) propMap.get("password");

		propMap.remove("driver-name");
		propMap.remove("url");
		propMap.remove("user");
		propMap.remove("passowrd");

		try {
			Class.forName(driverName);
			DataSource unpooled = DataSources.unpooledDataSource(url, user,
					password);
			DataSource pooled = DataSources.pooledDataSource(unpooled, propMap);
			//test whether the datasource can connect
			connectToDB(pooled);
			return pooled;
		} catch (Exception ex) {
			logger.error("An error occurred when creating datasource", ex);
		}
		return null;
	}

	private static void connectToDB(DataSource dataSource) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (Exception ex) {
			logger.error("An error occurred when getting data from datasource", ex);
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * get default datasource
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		// return corresponding datasource directly if there's only one datasource
		if (dataSourceMap.size() == 1) {
			Object[] dataSourceArray = dataSourceMap.values().toArray();
			return (DataSource) dataSourceArray[0];
		}

		// query the default datasource if there're multiple datasources
		if (StringHelper.isEmpty(_default))
			return null;

		return getDataSource(_default);
	}

	/**
	 * get corresponding datasource object according to the datasource id configured in datasource.xml
	 * 
	 * @param id
	 * @return
	 */
	public DataSource getDataSource(String id) {
		return (DataSource) dataSourceMap.get(id);
	}

	/**
	 * estimate whether the datasource id is exist
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isExistDataSource(String id) {
		return dataSourceMap.containsKey(id);
	}

	/**
	 * get corresponding datasource xml configuration according to the datasource id configured in datasource.xml
	 * 
	 * @param id
	 * @return
	 */
	public HashMap getDbConnXmlMap(String id) {
		if (StringHelper.isBlank(id)) {
			id = _default;
		}
		if (StringHelper.isBlank(id)) {
			return null;
		} else {
			return (HashMap) dbConnXmlMap.get(id);
		}
	}

	/**
	 *
	 * @return
	 */
	public static String getAesKey() throws Exception {
		String key = "";
		File keyFile = FileHelper.guessPropFile(Configure.class, KEY_FILE_NAME);
		if (keyFile != null && keyFile.exists()) {
			String fileKey = "B49A86FA425D439dB510A234A3E25A3E";
			byte[] data = org.apache.commons.io.FileUtils
					.readFileToByteArray(keyFile);
			AES aes = new AES(fileKey);
			key = new String(aes.decrypt(data, fileKey.getBytes()));
		}
		return key;
	}
}