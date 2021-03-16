package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

public enum BodySize implements Serializable {

    XXS, XS, S, M, L, XL, XXL, XXXL;

    public String getName() {
        return name().toLowerCase();
    }
}
