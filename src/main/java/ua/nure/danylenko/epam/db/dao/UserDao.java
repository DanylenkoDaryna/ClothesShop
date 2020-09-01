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
    private static final String SQL_GET_USER_INFO_BY_ID = "SELECT * FROM user_info WHERE user_id=?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?";
    private static final String SQL_CREATE_USER = "INSERT INTO armadiodb.users (id, login, password, first_name, last_name, role_id) values (DEFAULT,?, ?, ?, ?, ?)";
    private static final String SQL_ADD_USER_INFO_BY_ID = "INSERT INTO armadiodb.user_info (id, country, birthday, email, telephone, user_id) values (DEFAULT,?,?,?,?,?)";
    private static final String SQL_FIND_USER_ID = "SELECT id FROM users";
    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";

    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT name FROM categories WHERE categories.catalogue_id=?";




    @Override
    public void create(Object entity) {
        User user=(User)entity;
        PreparedStatement pstmt = null;
        Statement stmt;
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

            setUserInfo(con, pstmt, user);
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            assert user != null;
            getUserInfo(con, ps, rs, user);
            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
        } finally {
            ConnectionFactory.close(con, ps, rs);
        }
        return user;
    }

    private void setUserInfo(Connection con, PreparedStatement pstmt, User user){

        try{
            pstmt = con.prepareStatement(SQL_ADD_USER_INFO_BY_ID);

            pstmt.setString(1, user.getCountry());
            pstmt.setDate(2, Date.valueOf(user.getBirthday()));
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getTelephone());
            pstmt.setLong(5, user.getId());
            pstmt.execute();

        } catch (SQLException e) {
            //ConnectionFactory.rollback(con);
            DB_LOG.error("UserDao.java in setUserInfo() ", e);
            ConnectionFactory.close(con,pstmt);
        }

    }

    private void getUserInfo(Connection con, PreparedStatement ps,
            ResultSet rs, User user) throws SQLException {

        ps = con.prepareStatement(SQL_GET_USER_INFO_BY_ID);
        ps.setLong(1, user.getId());
        rs = ps.executeQuery();
        if (rs.next()) {
           addUserInfo(rs, user);
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

    private User addUserInfo(ResultSet rs, User user) throws SQLException {

        user.setCountry(rs.getString(Fields.USER_INFO_COUNTRY));

        Date d = rs.getDate(Fields.USER_INFO_BDAY);
        user.setBirthday(d.toLocalDate());

        user.setTelephone(rs.getString(Fields.USER_INFO_TEL));
        user.setEmail(rs.getString(Fields.USER_INFO_EMAIL));
        return user;
    }
}
