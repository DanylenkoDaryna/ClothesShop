package ua.nure.danylenko.epam.db.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * The Basket class provides fields and methods for describing basket entity
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class Basket extends Entity {

    private double totalAmount;
    private List<BasketElement> basketElements;

    public Basket(){
        basketElements=new LinkedList<>();
    }

    /**
     * Method to generate total sum of cost all the purchases from basket for ordering them
     * @return round double sum
     */
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
