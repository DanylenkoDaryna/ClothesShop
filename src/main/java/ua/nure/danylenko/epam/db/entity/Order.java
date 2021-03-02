package ua.nure.danylenko.epam.db.entity;

import java.util.LinkedList;
import java.util.List;

public class Order {

    private long orderNumber;
    private OrderStatus orderStatus;
    private String paymentType;
    private String deliveryType;
    private double totalAmount;
    private long userId;
    private List<OrderItem> orderItems;

    public Order(){
        orderItems = new LinkedList<>();
        orderStatus=OrderStatus.CANCELED;
    }

    public void extractOrderStatus(String value){
        OrderStatus os = OrderStatus.valueOf(value.toUpperCase());
        this.setOrderStatus(os);
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
