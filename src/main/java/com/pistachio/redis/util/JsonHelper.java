package com.pistachio.redis.util;

import com.pistachio.base.jdbc.DataRow;
import com.pistachio.base.util.StringHelper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * 描述:
 */
public class JsonHelper
{

	private static Logger logger = Logger.getLogger(JsonHelper.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public static String getJSONString(Object obj)
	{
		String result = "";
		try
		{
			result = mapper.writeValueAsString(obj);
		}
		catch (JsonGenerationException e)
		{
			logger.error("", e);
		}
		catch (JsonMappingException e)
		{
			logger.error("", e);
		}
		catch (IOException e)
		{
			logger.error("", e);
		}
		return result;
	}

	public static Object getObjectByJSON(String jsonStr)
	{
		Object obj = null;
		try
		{
			obj = mapper.readValue(jsonStr, Object.class);
		}
		catch (JsonParseException e)
		{
			logger.error("", e);
		}
		catch (JsonMappingException e)
		{
			logger.error("", e);
		}
		catch (IOException e)
		{
			logger.error("", e);
		}
		return obj;
	}

	/**
	 *
	 * 描述：格式化DataRow
	 * @param map
	 */
	public static void formatDataRow(DataRow map)
	{
		Set keys = map.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			String value = map.getString(key).trim();
			if (value.startsWith("."))
			{
				value = "0" + value;
			}
			else if (value.startsWith("-."))
			{
				value = StringHelper.replace(value, "-.", "-0.");
			}
			map.set(key, value);
		}
	}

	public static void main(String[] args)
	{
	}
}
