
package com.pistachio.base.jdbc;

import com.pistachio.base.jdbc.session.Session;
import com.pistachio.base.jdbc.session.SessionFactory;
import com.pistachio.base.util.StringHelper;

import java.util.List;

/**
 * @desc:	 JDBC template
 */
public class JdbcTemplate
{
    private String id = "";
    private String generatedKeys = "";

    /**
     *  construct data access object
     */
    public JdbcTemplate()
    {
    }

    /**
     * construct data access object according to the datasource id configured in datasource.xml
     *
     * @param id datasource id
     */
    public JdbcTemplate(String id)
    {
        this.id = id;
    }


    private Session getSession()
    {
        if (!StringHelper.isEmpty(id))
            return SessionFactory.getSession(id);
        else
            return SessionFactory.getSession();
    }

    /**
     * add a record in corresponding table
     *
     * @param tableName the name of table to be added
     * @param data      the data to be added
     * @return
     */
    public int insert(String tableName, DataRow data)
    {
        Session session = null;
        try
        {
            session = getSession();
            int result = session.insert(tableName, data);
            generatedKeys = session.getGeneratedKeys();
            return result;
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * update a record in table
     *
     * @param tableName     the name of table to be updated
     * @param data          the information to be updated
     * @param identify      the name of identify column
     * @param identifyValue the value of identify column
     * @return
     */
    public int update(String tableName, DataRow data, String identify, Object identifyValue)
    {
        Session session = null;
        try
        {
            session = getSession();
            int result = session.update(tableName, data, identify, identifyValue);
            generatedKeys = session.getGeneratedKeys();
            return result;
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }
    

    /**
     * update records in table
     *
     * @param tableName     the name of table to be updated
     * @param data          the information to be updated
     * @param identifys      the names of identify column
     * @param identifyValues the values of identify column
     * @return
     */
    public int update(String tableName, DataRow data, String[] identifys, Object[] identifyValues)
    {
    	Session session = null;
    	try
    	{
    		session = getSession();
    		int result = session.update(tableName, data, identifys, identifyValues);
    		generatedKeys = session.getGeneratedKeys();
    		return result;
    	}
    	finally
    	{
    		if (session != null)
    		{
    			session.close();
    			session = null;
    		}
    	}
    }

    /**
     * delete a record in table
     *
     * @param tableName     the name of table to be deleted
     * @param identify      the name of identify column
     * @param identifyValue the value of identify column
     * @return
     */
    public int delete(String tableName, String identify, Object identifyValue)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.delete(tableName, identify, identifyValue);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * ִexecute specified sql statement and return the updated number of records
     *
     * @param sql SQL statement
     * @return the updated number
     */
    public int update(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.update(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * ִexecute specified sql statement and return the updated number of records
     *
     * @param sql  sql statement
     * @param args
     * @return the number of updated records
     */
    public int update(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.update(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * execute update in batch and return the number of updated records each time
     *
     * @param sqlArray SQL statement array
     * @return the updated number array of each executing update
     */
    public int[] batchUpdate(String[] sqlArray)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.batchUpdate(sqlArray);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * execute update in batch and return the number of updated records each time
     *
     * @param sql  SQL statement
     * @param args
     * @return
     */
    public int[] batchUpdate(String sql, Object[][] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.batchUpdate(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query an int result
     *
     * @param sql
     * @return the int value of the first column of first row been queried
     */
    public int queryInt(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryInt(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query an int result
     *
     * @param sql
     * @param args
     * @return the int value of the first column of first row been queried
     */
    public int queryInt(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryInt(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query an int array
     *
     * @param sql SQL���
     * @return the int value of first column in multiple records that been queried
     */
    public int[] queryIntArray(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryIntArray(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query an int array
     *
     * @param sql SQL
     * @param args
     * @return the int value of first column in multiple records that been queried
     */
    public int[] queryIntArray(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryIntArray(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a long result
     *
     * @param sql SQL
     * @return the long value of first column in first record that been queried
     */
    public long queryLong(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryLong(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a long result
     *
     * @param sql SQL
     * @param args
     * @return the long value of first column in first record that been queried
     */
    public long queryLong(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryLong(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a long array
     *
     * @param sql SQL
     * @return the long value of first column in multiple records that been queried
     */
    public long[] queryLongArray(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryLongArray(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a long array
     *
     * @param sql SQL
     * @param args
     * @return the long value of first column in multiple records that been queried
     */
    public long[] queryLongArray(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryLongArray(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a string result
     *
     * @param sql SQL
     * @return the string value of first column in first record that been queried
     */
    public String queryString(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryString(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a string result
     *
     * @param sql SQL
     * @param args the value in parameter
     * @return the string value of first column in first record that been queried
     */
    public String queryString(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryString(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a string array
     *
     * @param sql SQL
     * @return the string value of first column in multiple records that been queried
     */
    public String[] queryStringArray(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryStringArray(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a string array
     *
     * @param sql SQL
     * @param args
     * @return the string value of first column in multiple records that been queried
     */
    public String[] queryStringArray(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryStringArray(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a record, the return type is DataRow
     *
     * @param sql SQL
     * @return the first row of queried result, returned column names are all in lowercase
     */
    public DataRow queryMap(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryMap(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query a record, the return type is DataRow
     *
     * @param sql SQL
     * @param args
     * @return the first row of queried result, returned column names are all in lowercase
     */
    public DataRow queryMap(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryMap(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @return query all the results
     */
    public List query(String sql)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @param args
     * @return query all the results
     */
    public List query(String sql, Object[] args)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql, args);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @param rows the number of returned results
     * @return query results in specified number
     */
    public List query(String sql, int rows)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql, rows);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @param args
     * @param rows the number of returned results
     * @return query results in specified number
     */
    public List query(String sql, Object[] args, int rows)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql, args, rows);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }


    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @param startRow the number of start row
     * @param rows the number of results
     * @return query all union set results
     */
    public List query(String sql, int startRow, int rows)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql, startRow, rows);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query an object list result, each row in list is a DataRow
     *
     * @param sql SQL
     * @param args
     * @param startRow the number of start row
     * @param rows the number of results
     * @return query all union set results
     */
    public List query(String sql, Object[] args, int startRow, int rows)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.query(sql, args, startRow, rows);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a result in page
     *
     * @param sql        SQL
     * @param curPage    current page number
     * @param numPerPage the number of rows each page
     * @return page object
     */
    public DBPage queryPage(String sql, int curPage, int numPerPage)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryPage(sql, curPage, numPerPage);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }

    /**
     * query a page result list
     *
     * @param sql        SQL
     * @param args       the value in parameter
     * @param curPage    current page number
     * @param numPerPage the number of rows to display in  per page
     * @return ��ҳ����
     */
    public DBPage queryPage(String sql, Object[] args, int curPage, int numPerPage)
    {
        Session session = null;
        try
        {
            session = getSession();
            return session.queryPage(sql, args, curPage, numPerPage);
        }
        finally
        {
            if (session != null)
            {
                session.close();
                session = null;
            }
        }
    }
    
    /**
     * get self-generated primary keys
     */
    public String getGeneratedKeys()
    {
    	return generatedKeys;
    }

}
