/*
 * Copyright (c) 2016 Your Corporation. All Rights Reserved.
 */

package com.pistachio.base.jdbc;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * @desc data row, inherited from HashMap, convenient for data access
 */
public class DataRow extends HashMap {
	private static Logger logger = Logger.getLogger(DataRow.class);
	
    public void set(String name, String value) {
        if (name == null || name.equals(""))
            return;

        if (value == null)
            put(name, "");
        else
            put(name, value);
    }

    public void set(String name, int value) {
        put(name, new Integer(value));
    }

    public void set(String name, boolean value) {
        put(name, new Boolean(value));
    }

    public void set(String name, long value) {
        put(name, new Long(value));
    }

    public void set(String name, float value) {
        put(name, new Float(value));
    }

    public void set(String name, double value) {
        put(name, new Double(value));
    }

    public void set(String name, Object value) {
        put(name, value);
    }


    public String getString(String name) {
        if (name == null || name.equals(""))
            return "";

        Object obj = this.get(name);
        return (obj == null) ? "" : obj.toString();
    }


    public int getInt(String name) {
        if (name == null || name.equals(""))
            return 0;

        int value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Integer)) {
            try {
                value = Integer.parseInt(obj.toString());
            }
            catch (Exception ex) {
    			logger.debug(name+" corresponding value isn't int, return 0", ex);
                value = 0;
            }
        } else {
            value = ((Integer) obj).intValue();
            obj = null;
        }

        return value;
    }


    public long getLong(String name) {
        if (name == null || name.equals(""))
            return 0;

        long value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Long)) {
            try {
                value = Long.parseLong(obj.toString());
            }
            catch (Exception ex) {
    			logger.error(name+" corresponding value isn't long, return 0", ex);
                value = 0;
            }
        } else {
            value = ((Long) obj).longValue();
            obj = null;
        }

        return value;
    }

    public float getFloat(String name) {
        if (name == null || name.equals(""))
            return 0;

        float value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Float)) {
            try {
                value = Float.parseFloat(obj.toString());
            }
            catch (Exception ex) {
    			logger.error(name+" corresponding value isn't float, return 0", ex);
                value = 0;
            }
        } else {
            value = ((Float) obj).floatValue();
            obj = null;
        }

        return value;
    }

    public double getDouble(String name) {
        if (name == null || name.equals(""))
            return 0;

        double value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Double)) {
            try {
                value = Double.parseDouble(obj.toString());
            }
            catch (Exception ex) {
    			logger.error(name+" corresponding value isn't double, return 0", ex);
                value = 0;
            }
        } else {
            value = ((Double) obj).doubleValue();
            obj = null;
        }

        return value;
    }

    public boolean getBoolean(String name) {
        if (name == null || name.equals(""))
            return false;

        boolean value = false;
        if (containsKey(name) == false)
            return false;
        Object obj = this.get(name);
        if (obj == null)
            return false;

        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }

        value = Boolean.valueOf(obj.toString()).booleanValue();
        obj = null;
        return value;
    }

    public Object getObject(String name) {
        if (name == null || name.equals(""))
            return null;
        //if this key doesn't exist, then return
        if (containsKey(name) == false)
            return null;
        return this.get(name);
    }
}
