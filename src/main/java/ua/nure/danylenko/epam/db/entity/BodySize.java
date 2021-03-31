package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

/**
 * The BodySize enum provides types of size for products
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum BodySize implements Serializable {

    XXS, XS, S, M, L, XL, XXL, XXXL;

    public String getName() {
        return name().toLowerCase();
    }
}
