package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Order;
import ua.nure.danylenko.epam.exception.DBException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDao implements IDao  {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private static final String SQL_CREATE_ORDER = "INSERT INTO orders (id, order_status, paymentType, deliveryType, totalAmount, " +
                                                    "user_id) values (DEFAULT,?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ORDERS_BY_ID = "SELECT * FROM orders WHERE user_id=?";
    private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE orders SET login=?, password=?, first_name=?, " +
                                                         "last_name=? WHERE users.id=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id=?";


    // ConnectionFactory is already a singleton so just get connection
    //like this for less code
    private Connection getConnection() throws DBException {

        return ConnectionFactory.getInstance().getConnection();
    }



    public Order createOrder(Object entity) {
        DB_LOG.info("Order creating starts");
        Order order=(Order)entity;
        Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
        ResultSet rs= null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(SQL_CREATE_ORDER);
            pstmt.setString(1,order.getOrderStatus().toString());
            pstmt.setString(2,order.getPaymentType());
            pstmt.setString(3,order.getDeliveryType());
            pstmt.setDouble(4,order.getTotalAmount());
            pstmt.setLong(5,order.getUserId());

            pstmt.execute();

            stmt=con.createStatement();
            rs = stmt.executeQuery("SELECT last_insert_id()");

            if (rs.next()){
                order.setOrderNumber(rs.getLong(1));
                DB_LOG.info("OrderNumber ="+rs.getLong(1));

            } else{
                DB_LOG.info("Error getting OrderNumber");
            }

            con.commit();

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
        return order;
    }


    @Override
    public void create(Object entity) {

    }

    @Override
    public Object read() throws DBException {
        return null;
    }


    public List<Order> read(long user_id) throws DBException {
        DB_LOG.info("Orders reading starts");
        List<Order> orders = new LinkedList<>();
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = null;
        try{
            con = getConnection();
            pst = con.prepareStatement(SQL_FIND_ORDERS_BY_ID);
            pst.setLong(1,user_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Order order = extractOrder(rs);
                orders.add(order);

            }

            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("SQLException - trouble with commit in read(long user_id)", ex);
        }catch (DBException dbx) {
            DB_LOG.error("DBException - trouble with connection in read(long user_id)", dbx);
        } finally {
            ConnectionFactory.close(con, pst, rs);
        }

        DB_LOG.info("Orders reading ends");
        return orders;
    }


    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(long id) {

    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        Order temp = new Order();

        temp.setOrderNumber(rs.getLong(Fields.ORDER_NUMBER));
        temp.extractOrderStatus(rs.getString(Fields.ORDER_STATUS));
        temp.setPaymentType(rs.getString(Fields.ORDER_PAYMENT_TYPE));
        temp.setDeliveryType(rs.getString(Fields.ORDER_DELIVERY_TYPE));
        temp.setTotalAmount(rs.getDouble(Fields.ORDER_TOTAL_AMOUNT));
        temp.setUserId(rs.getLong(Fields.ORDER_USER_ID));
        return temp;

    }
}
