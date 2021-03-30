package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

/**
 * The OrderItem class provides fields and methods for making orders of bying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum BodySize implements Serializable {

    XXS, XS, S, M, L, XL, XXL, XXXL;

    public String getName() {
        return name().toLowerCase();
    }
}
