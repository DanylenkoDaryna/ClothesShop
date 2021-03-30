package ua.nure.danylenko.epam.db.entity;

/**
 * The OrderItem class provides fields and methods for making orders of bying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum AccountStatus {

    LOCKED, UNLOCKED;

    public String getName() {
        return name().toLowerCase();
    }

}
