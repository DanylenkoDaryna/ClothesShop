package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.entity.Order;
import ua.nure.danylenko.epam.exception.DBException;

import java.sql.*;

public class OrderDao implements IDao  {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders (id, login, password, first_name, last_name, " +
                                                    "acc_status, role_id) values (DEFAULT,?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id=?";
    private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE orders SET login=?, password=?, first_name=?, " +
                                                         "last_name=? WHERE users.id=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id=?";


    // ConnectionFactory is already a singleton so just get connection
    //like this for less code
    private Connection getConnection() throws DBException {

        return ConnectionFactory.getInstance().getConnection();
    }


    @Override
    public void create(Object entity) {
        DB_LOG.info("Order creating starts");
        Order order=(Order)entity;
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs= null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(SQL_CREATE_ORDER);
//            pstmt.setString(1,user.getLogin());
//            pstmt.setString(2,user.getPassword());
//            pstmt.setString(3,user.getFirstName());
//            pstmt.setString(4,user.getLastName());
//            pstmt.setString(5,user.getAccountStatus().toString());
//            pstmt.setInt(6,1);
//
//            pstmt.execute();
//
//            stmt=con.createStatement();
//            rs = stmt.executeQuery("SELECT last_insert_id()");
//
//            if (rs.next()){
//                order.setOrderNumber(rs.getLong(1));
//                DB_LOG.info("OrderNumber ="+rs.getLong(1));
//
//            } else{
//                DB_LOG.info("Error getting OrderNumber");
//            }
//
//            con.commit();

        } catch (DBException e) {

            DB_LOG.info("DBException - trouble with connection");

        } catch (SQLException e) {
            DB_LOG.info("SQLException - trouble with commit");
            ConnectionFactory.rollback(con);
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
            ConnectionFactory.close(stmt);
        }

        DB_LOG.info("Order creating ends");
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
}
