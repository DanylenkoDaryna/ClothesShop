package ua.nure.danylenko.epam.db;

import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    // //////////////////////////////////////////////////////////
    // singleton
    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    // //////////////////////////////////////////////////////////

    private DataSource ds;

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // ST4DB - the name of data source
            ds = (DataSource) envContext.lookup("jdbc/MARKET");
           // DBLOG.info("Data source ==> " + ds);
        } catch (NamingException ex) {
           // DBLOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }



    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?"
            + "	WHERE id=?";
    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";
    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT name FROM categories WHERE categories.catalogue_id=?";


    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            //DBLOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login
     *            User login.
     * @return User entity.
     * @throws DBException
     */
    public User findUserByLogin(String login) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return user;
    }

    public Catalogue getCatalogue() throws DBException {
        Catalogue catalogue = new Catalogue();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
            try{
                con = getConnection();
                stmt = con.createStatement();
                rs = stmt.executeQuery(SQL_FIND_FULL_CATALOGUE);
                while (rs.next()) {
                    int id=rs.getInt(Fields.ENTITY_ID);
                    String name = rs.getString(Fields.CATEGORY_NAME);
                    //catalogue.addInCatalogue(name,getCategories(con, id));
                }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException("getCatalogue beee", ex);
        } finally {
            close(con, stmt, rs);
        }
        return catalogue;
    }

    private List<String> getCategories(Connection con, int id) throws DBException, SQLException {
        List<String> clothes = new ArrayList<>();

        ResultSet rs = null;
        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_CATEGORIES_BY_ID)){
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                clothes.add(rs.getString(Fields.CATEGORY_NAME));
            }
        } catch (SQLException ex) {
            throw new DBException("getCategories beee", ex);
        } finally {
            if(rs!=null){
                rs.close();
            }
        }
        return clothes;
    }


    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////


    /**
     * Extracts a user entity from the result set.
     *
     * @param rs
     *            Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        return user;
    }

    /**
     * Closes a connection.
     *
     * @param con
     *            Connection to be closed.
     */
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
               // DBLOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
               // DBLOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                //DBLOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con
     *            Connection to be rollbacked.
     */
    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                //DBLOG.error("Cannot rollback transaction", ex);
            }
        }
    }
}
