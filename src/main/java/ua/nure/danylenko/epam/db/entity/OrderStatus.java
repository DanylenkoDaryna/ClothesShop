package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

public enum OrderStatus implements Serializable {

    REGISTERED, PAID, CANCELED;
    public String getName() {
        return name().toLowerCase();
    }
}
