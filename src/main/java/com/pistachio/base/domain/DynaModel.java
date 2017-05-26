package com.pistachio.base.domain;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DynaModel
{
	private static Logger logger = Logger.getLogger(DynaModel.class);
	
    private HashMap innerMap = new HashMap();

    /**
     * Initialize dynamic Domain from Map
     * @param map
     */
    public DynaModel fromMap(Map map)
    {
        innerMap.putAll(map);
        return this;        
    }

    /**
     * transfer Domain model to Map
     * @return
     */
    public Map toMap()
    {
        return innerMap;
    }

    protected void set(String name, String value)
    {
        if (name == null || name.equals(""))
            return;

        if (value == null)
            innerMap.put(name, "");
        else
            innerMap.put(name, value);
    }

    protected void set(String name, int value)
    {
        innerMap.put(name, new Integer(value));
    }

    protected void set(String name, boolean value)
    {
        innerMap.put(name, new Boolean(value));
    }

    protected void set(String name, long value)
    {
        innerMap.put(name, new Long(value));
    }

    protected void set(String name, float value)
    {
        innerMap.put(name, new Float(value));
    }

    protected void set(String name, double value)
    {
        innerMap.put(name, new Double(value));
    }

    protected void set(String name, Object value)
    {
        innerMap.put(name, value);
    }


    protected String getString(String name)
    {
        if (name == null || name.equals(""))
            return "";

        String value = "";
        if (innerMap.containsKey(name) == false)
            return "";
        Object obj = innerMap.get(name);
        if (obj != null)
            value = obj.toString();
        obj = null;

        return value;
    }


    protected int getInt(String name)
    {
        if (name == null || name.equals(""))
            return 0;

        int value = 0;
        if (innerMap.containsKey(name) == false)
            return 0;

        Object obj = innerMap.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Integer))
        {
            try
            {
                value = Integer.parseInt(obj.toString());
            }
            catch (Exception ex)
            {
            	logger.debug(name+" corresponding value isn't a number, return 0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Integer) obj).intValue();
            obj = null;
        }

        return value;
    }


    protected long getLong(String name)
    {
        if (name == null || name.equals(""))
            return 0;

        long value = 0;
        if (innerMap.containsKey(name) == false)
            return 0;

        Object obj = innerMap.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Long))
        {
            try
            {
                value = Long.parseLong(obj.toString());
            }
            catch (Exception ex)
            {
            	logger.error(name+" corresponding value isn't a number, return 0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Long) obj).longValue();
            obj = null;
        }

        return value;
    }

    protected float getFloat(String name)
    {
        if (name == null || name.equals(""))
            return 0;

        float value = 0;
        if (innerMap.containsKey(name) == false)
            return 0;

        Object obj = innerMap.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Float))
        {
            try
            {
                value = Float.parseFloat(obj.toString());
            }
            catch (Exception ex)
            {
            	logger.error(name+" coresponding value isn't a number, return 0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Float) obj).floatValue();
            obj = null;
        }

        return value;
    }

    protected double getDouble(String name)
    {
        if (name == null || name.equals(""))
            return 0;

        double value = 0;
        if (innerMap.containsKey(name) == false)
            return 0;

        Object obj = innerMap.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Double))
        {
            try
            {
                value = Double.parseDouble(obj.toString());
            }
            catch (Exception ex)
            {
            	logger.error(name+" corresponding value isn't a number, return 0", ex);
                value = 0;
            }
        }
        else
        {
            value = ((Double) obj).doubleValue();
            obj = null;
        }

        return value;
    }

    protected boolean getBoolean(String name)
    {
        if (name == null || name.equals(""))
            return false;

        boolean value = false;
        if (innerMap.containsKey(name) == false)
            return false;
        Object obj = innerMap.get(name);
        if (obj == null)
            return false;

        if (obj instanceof Boolean)
        {
            return ((Boolean) obj).booleanValue();
        }

        value = Boolean.valueOf(obj.toString()).booleanValue();
        obj = null;
        return value;
    }

    protected Object getObject(String name)
    {
        if (name == null || name.equals(""))
            return null;
        //if this key doesn't exist, then return
        if (innerMap.containsKey(name) == false)
            return null;
        return innerMap.get(name);
    }
}