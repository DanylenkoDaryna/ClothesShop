package ua.nure.danylenko.epam.db;

import ua.nure.danylenko.epam.db.entity.User;

/**
 * The Role enum provides two types of users that could use full ability of site and have their own cabinets
 *
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public enum Role {
    ADMIN, CLIENT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
