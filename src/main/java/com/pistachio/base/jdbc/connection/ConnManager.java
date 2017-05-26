
package com.pistachio.base.jdbc.connection;

import com.pistachio.base.jdbc.exception.JdbcException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @desc database connection management
 */

public final class ConnManager
{
    private static Logger logger = Logger.getLogger(ConnManager.class);
    private static Configure configure = Configure.getInstance();

    /**
     * get corresponding database connection according to the datasource id configured in datasource.xml
     *
     * @param id datasource id
     * @return conn
     */
    public static Connection getConnection(String id)
    {
        try
        {
            DataSource dataSource = configure.getDataSource(id);
            Connection conn = dataSource.getConnection();
            return conn;
        }
        catch (SQLException ex)
        {
            throw new JdbcException(ex);
        }
    }

    /**
     * get default database connection
     *
     * @return conn
     */
    public static Connection getConnection()
    {
        try
        {
            DataSource dataSource = configure.getDataSource();
            Connection conn = dataSource.getConnection();
            return conn;
        }
        catch (SQLException ex)
        {
            throw new JdbcException(ex);
        }
    }

    /**
     * start database transaction
     * @param conn
     */
    public static void begin(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.setAutoCommit(false);
            }
        }
        catch (Exception ex)
        {
            throw new JdbcException(ex);
        }
    }

    /**
     * commit database transaction
     * @param conn
     */
    public static void commit(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.commit();
            }
        }
        catch (Exception ex)
        {
            throw new JdbcException(ex);
        }
    }

    /**
     * rollback database transaction
     * @param conn
     */
    public static void rollback(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.rollback();
            }
        }
        catch (Exception ex)
        {
            throw new JdbcException(ex);
        }
    }

    /**
     * close database connection
     * @param conn
     */
    public static void close(Connection conn)
    {

        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
        catch (Exception ex)
        {
            throw new JdbcException(ex);
        }
    }
    
    /**
     * 
     *  get single database connection
     * @param id
     * @return
     */
    public static Connection getSingleConn(String id)
    {
        try
        {
        	HashMap xmlMap = configure.getDbConnXmlMap(id);
        	String driverName = (String) xmlMap.get("driver-name");
            String url = (String) xmlMap.get("url");
            String user = (String) xmlMap.get("user");
            String password = (String) xmlMap.get("password");
        	Class.forName(driverName).newInstance();
        	Connection conn = DriverManager.getConnection(url, user,password);
	        return conn;
        }
        catch (Exception ex)
        {
        	throw new JdbcException(ex);
        }
    }
    
}
