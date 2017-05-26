
package com.pistachio.base.jdbc.session;

import com.pistachio.base.jdbc.connection.ConnManager;
import com.pistachio.base.jdbc.session.Impl.SessionImpl;

import java.sql.Connection;

public final class SessionFactory
{
    /**
     * get corresponding session object according to the datasource id configured in configuration.xml
     *
     * @param id  datasource id
     * @return
     */
    public static Session getSession(String id)
    {
        Connection conn = ConnManager.getConnection(id);
        return new SessionImpl(conn);
    }

    /**
     * get the default datasource session object
     *
     * @return
     */
    public static Session getSession()
    {
        Connection conn = ConnManager.getConnection();
        return new SessionImpl(conn);
    }
}
