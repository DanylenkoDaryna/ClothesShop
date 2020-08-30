package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.*;

public class UserDao implements IDao {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    // ConnectionFactory is already a singleton so just get connection
    //like this for less code
    private Connection getConnection() throws DBException {

        return ConnectionFactory.getInstance().getConnection();
    }

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?";
    private static final String SQL_CREATE_USER = "INSERT INTO armadiodb.users (id, login, password, first_name, last_name, role_id) values (DEFAULT,?, ?, ?, ?, ?)";
    private static final String SQL_ADD_USER_INFO_BY_ID = "INSERT INTO armadiodb.user_info values (DEFAULT, country=?, birthday=?, email=?, telephone=?, user_id=?)";
    private static final String SQL_FIND_USER_ID = "SELECT id FROM users";
    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";

    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT name FROM categories WHERE categories.catalogue_id=?";




    @Override
    public void create(Object entity) {
        User user=(User)entity;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_CREATE_USER);
            pstmt.setString(1,user.getLogin());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getFirstName());
            pstmt.setString(4,user.getLastName());
            pstmt.setInt(5,1);

            //pstmt.executeQuery();executes the select query. It returns an instance of ResultSet.

            //executes the query. It is used for create, drop, insert, update, delete etc.
            pstmt.execute();


            //executes the select query. It returns an instance of ResultSet.
            stmt=con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id()");

            if (rs.next()){
                user.setId(rs.getLong(1));
                DB_LOG.info("key="+rs.getLong(1));

            } else{
                DB_LOG.info("Error getting key");
            }

            setUserInfo(con, user);
            con.commit();

        } catch (DBException e) {

            DB_LOG.error(e.getStackTrace());
            DB_LOG.info("trouble with connection");

        } catch (SQLException e) {
            DB_LOG.error(e);
            DB_LOG.info("trouble with commit");
            ConnectionFactory.rollback(con);
        } finally {
            ConnectionFactory.close(con, pstmt);
        }

    }

    @Override
    public Object read() throws DBException {
        return null;
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(long id) {

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
            ConnectionFactory.rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
        }
        return user;
    }

    private void setUserInfo(Connection con, User user){

        try(PreparedStatement ps = con.prepareStatement(SQL_ADD_USER_INFO_BY_ID)){

                ps.setString(1, user.getCountry());
                ps.setDate(2, Date.valueOf(user.getBirthday()));
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getTelephone());
                ps.setLong(5, user.getId());
                ps.execute();

        } catch (SQLException e) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("UserDao.java in setUserInfo() ", e);
        }

    }

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
}
