package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.exception.DBException;
import ua.nure.danylenko.epam.exception.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private static final String SQL_CREATE_USER = "INSERT INTO users VALUES(DEFAULT, login=?, password=?, first_name=?, last_name=?, role_id=?)";
    private static final String SQL_FIND_USER_ID = "SELECT id FROM users";
    private static final String SQL_FIND_FULL_CATALOGUE = "SELECT * FROM catalogue";

    private static final String SQL_FIND_CATEGORIES_BY_ID = "SELECT name FROM categories WHERE categories.catalogue_id=?";




    @Override
    public void create(Object entity) {
        User user=(User)entity;
        DB_LOG.info(user.getFirstName());
        DB_LOG.info(user.getBirthday());
        PreparedStatement pstmt = null;
        ResultSet rs = null;
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
            pstmt.executeUpdate();

            //executes the select query. It returns an instance of ResultSet.
            rs = pstmt.executeQuery("SELECT last_insert_id()");
            if (rs.next()){
                user.setId(rs.getLong(1));
                DB_LOG.info("key="+rs.getLong(1));

            } else{
                DB_LOG.info("Error getting key");
            }

            //setUserInfo(con, user);
            //con.commit();
        } catch (DBException e) {

            DB_LOG.error(e.getStackTrace());
            DB_LOG.info("trouble with connection");

        } catch (SQLException e) {
            DB_LOG.error(e);
            DB_LOG.info("trouble with commit");
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
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

//    private void setUserInfo(Connection con, User user){
//        ResultSet rs = null;
//        try(PreparedStatement pstmt = con.prepareStatement(SQL_FIND_USER_ID)){
//
//                pstmt.setLong(1, item.getId());
//                rs = pstmt.executeQuery();
//                while (rs.next()) {
//
//                    Product product = extractProduct(rs);
//                    item.getContainer().add(product);
//                    getMaterialsByProduct(con, product);
//                    getProductImages(con,product);
//                }
//            }
//
//        }finally {
//            ConnectionFactory.close(rs);
//        }
//    }
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
