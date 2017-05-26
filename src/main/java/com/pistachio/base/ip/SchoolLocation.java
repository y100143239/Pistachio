package com.pistachio.base.ip;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SchoolLocation
{
	private static Logger logger = Logger.getLogger(SchoolLocation.class);
	
    public static final InputStream ins = SchoolLocation.class.getResourceAsStream("/school.txt");

    public static final Map<String, Location> yellowPages = new HashMap<String, Location>();

    static
    {
        // read the data file into map
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = in.readLine()) != null)
            {
                String[] info = line.split(",");
                if (info.length == 3)
                {
                    yellowPages.put(info[0], new Location(info[1], info[2]));
                }
            }
        }
        catch (FileNotFoundException e)
        {
        	logger.error("not found file school.txt", e);
        }
        catch (IOException e)
        {
        	logger.error("An error occurred when reading the file school.txt", e);
        }
    }
}
