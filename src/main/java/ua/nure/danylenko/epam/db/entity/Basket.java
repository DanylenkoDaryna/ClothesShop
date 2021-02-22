package ua.nure.danylenko.epam.db.entity;

import java.util.LinkedList;
import java.util.List;

public class Basket extends Entity {

    private double totalAmount;
    private List<BasketElement> basketElements;

    public Basket(){

        totalAmount = 45L;
        basketElements=new LinkedList<>();
    }

    public double sumCosts(){

        for(BasketElement be:basketElements){
            totalAmount+=be.getBasketItem().getPrice();
        }

        return totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<BasketElement> getBasketElements() {
        return basketElements;
    }

    public void setBasketElements(List<BasketElement> basketElements) {
        this.basketElements = basketElements;
    }

}
