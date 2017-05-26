package com.pistachio.base.dao;

import com.pistachio.base.jdbc.JdbcTemplate;
import com.pistachio.base.jdbc.session.Session;
import com.pistachio.base.jdbc.session.SessionFactory;

public class BaseDao
{
    /**
     * get database access Session
     * @return
     */
    public Session getSession()
    {
        return SessionFactory.getSession();
    }

    /**
     * get Session Object according to the returned datasource id  configured in configuration.xml
     *
     * @param id datasourceId
     * @return
     */
    public Session getSession(String id)
    {
        return SessionFactory.getSession(id);
    }

    /**
     * return data manipulating object
     *
     * @return
     */
    public JdbcTemplate getJdbcTemplate()
    {
        return new JdbcTemplate();
    }

    /**
     * get the structured data manipulating Object according to the returned datasource id  configured in configuration.xml
     *
     * @param id datasource id
     */
    public JdbcTemplate getJdbcTemplate(String id)
    {
        return new JdbcTemplate(id);
    }

    /**
     * return sql statement string separated by comma with parenthesis according to an int array
     *
     * @param intArray
     * @return
     */
    public String getBracketSqlStr(int[] intArray)
    {
        int length = intArray.length;
        if (length == 0)
            return "";

        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        for (int i = 0; i < intArray.length; i++)
        {
            if (i == length - 1)
                buffer.append(String.valueOf(intArray[i]));
            else
                buffer.append(String.valueOf(intArray[i] + ","));
        }
        buffer.append(")");
        return buffer.toString();
    }
}
