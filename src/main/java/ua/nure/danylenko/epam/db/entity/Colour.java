package ua.nure.danylenko.epam.db.entity;

/**
 * The OrderItem class provides fields and methods for making orders of bying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum Colour {

    RED, BLUE, PINK, YELLOW, GREEN, GREY, BLACK, WHITE;


    public String getName() {
        return name().toLowerCase();
    }
}
