package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

/**
 * The OrderItem class provides fields and methods for making orders of buying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class OrderItem extends Entity implements Serializable {

    private static final long serialVersionUID = -6889036256149495388L;

    private long productId;
    private String name;
    private String brand;
    private BodySize productSize;
    private Colour colour;
    private int amount;
    private long orderId;

    public OrderItem(){

        name="empty";
        brand="empty";
        productSize=BodySize.S;
        colour=Colour.WHITE;

    }


    /**
     * Method for converting string of Size we have from db to the exact enum value
     * @param value Size with String type
     */
    public void extractProductSize(String value){

        BodySize size = BodySize.valueOf(value.toUpperCase());
        this.setProductSize(size);

    }

    /**
     * Method for converting string of color we have from db to the exact enum value
     * @param value Colour with String type
     */
    public void extractColour(String value){

        Colour prodColour = Colour.valueOf(value.toUpperCase());
        this.setColour(prodColour);

    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BodySize getProductSize() {
        return productSize;
    }

    public void setProductSize(BodySize productSize) {
        this.productSize = productSize;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
