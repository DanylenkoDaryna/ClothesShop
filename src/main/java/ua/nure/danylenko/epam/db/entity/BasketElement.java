package ua.nure.danylenko.epam.db.entity;

public class BasketElement extends Entity {

    private Item basketItem;

    private Product basketProduct;

    public BasketElement(){
        basketItem = new Item();
        basketProduct = new Product();
    }

    @Override
    public String toString() {
        return basketItem.getItemName() + " " + basketItem.getColour()+ " " + basketProduct.getBodySize()
                + " " + basketItem.getPrice() ;
    }

    public Item getBasketItem() {
        return basketItem;
    }

    public void setBasketItem(Item basketItem) {
        this.basketItem = basketItem;
    }

    public Product getBasketProduct() {
        return basketProduct;
    }

    public void setBasketProduct(Product basketProduct) {
        this.basketProduct = basketProduct;
    }
}
