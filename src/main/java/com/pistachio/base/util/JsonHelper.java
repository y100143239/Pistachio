package com.pistachio.base.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @desc JSON Helper class
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

}
