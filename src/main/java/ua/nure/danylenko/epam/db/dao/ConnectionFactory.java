package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The ConnectionFactory class provides methods to get the connection, to work with connection and transactions and
 * to clear connection and other resources
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class ConnectionFactory {
    // singleton
    private static ConnectionFactory instance;

    public static synchronized ConnectionFactory getInstance() throws DBException {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }
    //////
    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private DataSource dataSource;

    public ConnectionFactory() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // ST4DB - the name of data source
            dataSource = (DataSource) envContext.lookup("jdbc/MARKET");
            DB_LOG.info("Data source ==> " + dataSource);
        } catch (NamingException ex) {
            DB_LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con;
        try {
            con = dataSource.getConnection();
            DB_LOG.info("CONNECTION - "+con);
        } catch (SQLException ex) {
            DB_LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    /**
     * Closes a connection.
     *
     * @param con
     *            Connection to be closed.
     */
    protected static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                DB_LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    protected static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                DB_LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    protected static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                DB_LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    protected static void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Closes resources.
     */
    protected static void close(Connection con, Statement stmt) {
        close(stmt);
        close(con);
    }


    /**
     * Rollbacks a connection.
     *
     * @param con
     *            Connection to be rollbacked.
     */
    protected static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                DB_LOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}
