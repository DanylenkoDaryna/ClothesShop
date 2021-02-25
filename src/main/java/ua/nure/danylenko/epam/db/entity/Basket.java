package ua.nure.danylenko.epam.db.entity;

import java.util.LinkedList;
import java.util.List;

public class Basket extends Entity {

    private double totalAmount;
    private List<BasketElement> basketElements;

    public Basket(){
        basketElements=new LinkedList<>();
    }

    public double sumCosts(){

        double result=45.0;
        for(BasketElement be:basketElements){
            result=result+be.getBasketItem().getPrice();
        }
        return Math.round(result);
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        totalAmount = totalAmount;
    }

    public List<BasketElement> getBasketElements() {
        return basketElements;
    }

    public void setBasketElements(List<BasketElement> basketElements) {
        this.basketElements = basketElements;
    }

}