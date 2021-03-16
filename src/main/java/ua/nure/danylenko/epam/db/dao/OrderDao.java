package ua.nure.danylenko.epam.db.dao;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.db.Fields;
import ua.nure.danylenko.epam.db.entity.Order;
import ua.nure.danylenko.epam.db.entity.OrderItem;
import ua.nure.danylenko.epam.db.entity.OrderStatus;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.exception.DBException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderDao implements IDao  {

    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    private static final String SQL_CREATE_ORDER = "INSERT INTO armadiodb.orders (id, order_status, payment_type, delivery_type, total_amount, user_id ) " +
            "values (DEFAULT, ?, ?, ?, ?, ?);";
    private static final String SQL_CREATE_NEW_ORDER_ITEM = "INSERT INTO armadiodb.order_items " +
            "(id, product_id, order_name, brand, product_size, colour, amount, orders_id) " +
            "values (DEFAULT, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ALL_ORDERS = "SELECT * FROM orders;";
    private static final String SQL_FIND_ORDERS_BY_ID = "SELECT * FROM orders WHERE user_id=?";
    private static final String SQL_FIND_ORDER_ITEMS_BY_ORDER_ID = "SELECT * FROM order_items WHERE orders_id=?";

    private static final String SQL_UPDATE_ORDER_BY_ID = "UPDATE orders SET login=?, password=?, first_name=?, " +
                                                         "last_name=? WHERE users.id=?";
    private static final String SQL_UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET order_status=? WHERE orders.id=?";
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
            rs = stmt.executeQuery("SELECT last_insert_id();");

            if (rs.next()){
                DB_LOG.info("OrderNumber ="+rs.getLong(1));
                order.setOrderNumber(rs.getLong(1));

            } else{
                DB_LOG.info("Error getting OrderNumber");
            }
            order.setOrderItems(
                    createOrderItems(con, order.getOrderItems(), order.getOrderNumber())
            );

            con.commit();

        } catch (DBException e) {

            DB_LOG.info("DBException - trouble with connection in createOrder()");

        } catch (SQLException e) {
            DB_LOG.info("SQLException - trouble with commit in createOrder()");
            ConnectionFactory.rollback(con);
        } finally {
            ConnectionFactory.close(con, pstmt, rs);
            ConnectionFactory.close(stmt);
        }

        DB_LOG.info("Order creating ends");
        return order;
    }

    private List<OrderItem> createOrderItems(Connection con, List<OrderItem> orderItems, long orderId){
        DB_LOG.info("createOrderItems starts");
        PreparedStatement prep = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            for (OrderItem oi : orderItems) {
                prep = con.prepareStatement(SQL_CREATE_NEW_ORDER_ITEM);
                prep.setLong(1, oi.getProductId());
                prep.setString(2, oi.getName());
                prep.setString(3, oi.getBrand());
                prep.setString(4, oi.getProductSize().toString());
                prep.setString(5, oi.getColour().toString());
                prep.setInt(6, oi.getAmount());
                prep.setLong(7, orderId);
                prep.execute();

                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT last_insert_id()");
                if (rs.next()) {
                    oi.setId(rs.getLong(1));
                    DB_LOG.info("Product id = " + rs.getLong(1));
                } else {
                    DB_LOG.info("Error getting Product id!");
                }
            }
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In createOrderItems() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,prep);
        }finally {
            ConnectionFactory.close(prep);
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stmt);
        }
        DB_LOG.info("createOrderItems ends");
        return orderItems;
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
                order.setOrderItems(readOrderItems(con, order.getOrderNumber()));
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


    private List<OrderItem> readOrderItems(Connection con, long orderId){
        LinkedList<OrderItem> orderItems = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = con.prepareStatement(SQL_FIND_ORDER_ITEMS_BY_ORDER_ID);
            prep.setLong(1, orderId);
            rs = prep.executeQuery();
            while (rs.next()) {
                orderItems.add(extractOrderItem(rs));
            }
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("In readOrderItems() SQLException! Trouble with commit: ", ex);
            ConnectionFactory.close(con,prep);
        }finally {
            ConnectionFactory.close(prep);
            ConnectionFactory.close(rs);
        }
        return orderItems;
    }


    public List<Order> getAllOrders() throws AppException {
        List<Order> orders = new LinkedList<>();
        ResultSet rs = null;
        Connection con = null;
        try{
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_GET_ALL_ORDERS);
            rs=pstmt.executeQuery();
            while (rs.next()) {
                Order order=extractOrder(rs);
                order.setOrderItems(readOrderItems(con, order.getOrderNumber()));
                orders.add(order);
            }
            con.commit();
        } catch (SQLException ex) {
            DB_LOG.error("SQLException in getAllOrders() - Trouble with commit", ex);
            ConnectionFactory.rollback(con);
        } catch (DBException e) {
            DB_LOG.error("DBException in getAllOrders() - trouble with connection", e);
            ConnectionFactory.rollback(con);
        } finally {
            ConnectionFactory.close(rs);
        }
        if(orders.isEmpty()){
            DB_LOG.error("There are no orders from DB ");
            throw new AppException("There are no orders from DB");
        }else{
            return orders;
        }
    }

    @Override
    public void update(Object entity) {

    }

    public void updateStatus(OrderStatus updatedStatus, long orderId) {
        PreparedStatement pst = null;
        Connection con = null;
        try{
            con = getConnection();
            pst = con.prepareStatement(SQL_UPDATE_ORDER_STATUS_BY_ID);
            pst.setString(1,updatedStatus.toString());
            pst.setLong(2,orderId);
            pst.execute();

            con.commit();
        } catch (SQLException ex) {
            ConnectionFactory.rollback(con);
            DB_LOG.error("SQLException - trouble with commit in updateStatus()", ex);
        }catch (DBException dbx) {
            DB_LOG.error("DBException - trouble with connection in updateStatus()", dbx);
        } finally {
            ConnectionFactory.close(con, pst);
        }
    }

    @Override
    public void delete(long id) {

    }

    private Order extractOrder(ResultSet rs) throws SQLException {
        DB_LOG.info("extractOrder() starts");
        Order temp = new Order();

        temp.setOrderNumber(rs.getLong(Fields.ORDER_NUMBER));
        temp.extractOrderStatus(rs.getString(Fields.ORDER_STATUS));
        temp.setPaymentType(rs.getString(Fields.ORDER_PAYMENT_TYPE));
        temp.setDeliveryType(rs.getString(Fields.ORDER_DELIVERY_TYPE));
        temp.setTotalAmount(rs.getDouble(Fields.ORDER_TOTAL_AMOUNT));
        temp.setUserId(rs.getLong(Fields.ORDER_USER_ID));
        DB_LOG.info("extractOrder() ends");
        return temp;

    }

    private OrderItem extractOrderItem(ResultSet rs) throws SQLException {
        DB_LOG.info("extractOrderItem() starts");
        OrderItem ordIt = new OrderItem();
        ordIt.setId(rs.getLong(Fields.ENTITY_ID));
        ordIt.setProductId(rs.getLong(Fields.ORDERITEM_PROD_ID));
        ordIt.setName(rs.getString(Fields.ORDERITEM_NAME));
        ordIt.setBrand(rs.getString(Fields.ORDERITEM_BRAND));
        ordIt.extractProductSize(rs.getString(Fields.ORDERITEM_PRODUCT_SIZE));
        ordIt.extractColour(rs.getString(Fields.ORDERITEM_COLOUR));
        ordIt.setAmount(rs.getInt(Fields.ORDERITEM_AMOUNT));
        ordIt.setOrderId(rs.getLong(Fields.ORDERITEM_ORDER_ID));
        DB_LOG.info("extractOrderItem() ends");
        return ordIt;

    }
}
