package ua.nure.danylenko.epam.db.entity;

public enum OrderStatus {

    REGISTERED, PAID, CANCELED;
    public String getName() {
        return name().toLowerCase();
    }
}
