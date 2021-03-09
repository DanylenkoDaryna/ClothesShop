package ua.nure.danylenko.epam.db.entity;

public class OrderItem extends Entity {


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


    public void extractProductSize(String value){
        BodySize size = BodySize.valueOf(value.toUpperCase());
        this.setProductSize(size);
    }

    public void extractColour(String value){
        Colour colour = Colour.valueOf(value.toUpperCase());
        this.setColour(colour);
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