package com.pistachio.base.config;

import com.pistachio.base.util.StringHelper;
import com.pistachio.base.util.XMLHelper;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * read the file named configuratoin.xml
 */
public class Configuration
{
    
    private static Logger logger           = Logger.getLogger(Configuration.class);
    
    private static Map    items            = new HashMap();
    
    private static String CONFIG_FILE_NAME = "configuration.xml";
    
    static
    {
        loadConfig();
    }
    
    /**
     * read the configuration file in
     */
    private static void loadConfig()
    {
        try
        {
            
            Document document = XMLHelper.getDocument(Configuration.class, CONFIG_FILE_NAME);
            if ( document != null )
            {
                Element systemElement = document.getRootElement();
                List catList = systemElement.elements("category");
                for (Iterator catIter = catList.iterator(); catIter.hasNext();)
                {
                    Element catElement = (Element) catIter.next();
                    String catName = catElement.attributeValue("name");
                    if ( StringHelper.isEmpty(catName) )
                    {
                        continue;
                    }
                    
                    List itemList = catElement.elements("item");
                    for (Iterator itemIter = itemList.iterator(); itemIter.hasNext();)
                    {
                        Element itemElement = (Element) itemIter.next();
                        String itemName = itemElement.attributeValue("name");
                        String value = itemElement.attributeValue("value");
                        if ( !StringHelper.isEmpty(itemName) )
                        {
                            items.put(catName + "." + itemName, value);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            logger.error("An error occured when read the configuration file in ", ex);
        }
        finally
        {
        }
        
    }
    
    /**
     * get string configuration value
     *
     * @param name
     * @return
     */
    public static String getString(String name)
    {
        String value = (String) items.get(name);
        return (value == null) ? "" : value;
    }
    
    /**
     * get string configuration value, return default value if it's empty
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(String name, String defaultValue)
    {
        String value = (String) items.get(name);
        if ( value != null && value.length() > 0 )
            return value;
        else
            return defaultValue;
    }
    
    /**
     * get int configuration value
     *
     * @param name
     * @return
     */
    public static int getInt(String name)
    {
        String value = getString(name);
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex)
        {
            //logger.debug("configuration file key["+name"] configured error ,return 0", ex);
            return 0;
        }
    }
    
    /**
     * get int configuratio value
     *
     * @param name
     * @return
     */
    public static int getInt(String name, int defaultValue)
    {
        String value = getString(name);
        if ( "".equals(value) )
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex)
        {
            //logger.debug("configuration file key["+name"]configured error, return "+defaultValue, ex);
            return defaultValue;
        }
        
    }
    
    /**
     * get boolean configuration file
     *
     * @param name
     * @return
     */
    public static boolean getBoolean(String name)
    {
        String value = getString(name);
        return Boolean.valueOf(value).booleanValue();
    }
    
    /**
     * get double configuration file
     * 
     * @param name
     * @return
     */
    public static double getDouble(String name, double defaultValue)
    {
        String value = getString(name);
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException ex)
        {
            //logger.debug("configuration file key["+name"]configured error, return "+defaultValue, ex);
            return defaultValue;
        }
    }
    
    public static Map getItems()
    {
        return items;
    }
}
