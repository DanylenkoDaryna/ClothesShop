package ua.nure.danylenko.epam.db.entity;

public enum AccountStatus {

    LOCKED, UNLOCKED;

    public String getName() {
        return name().toLowerCase();
    }

}
