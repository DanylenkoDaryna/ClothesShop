package ua.nure.danylenko.epam.db.entity;

/**
 * The AccountStatus enum provides types of account status, admins to manipulate with
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum AccountStatus {

    LOCKED, UNLOCKED;

    public String getName() {
        return name().toLowerCase();
    }

}
