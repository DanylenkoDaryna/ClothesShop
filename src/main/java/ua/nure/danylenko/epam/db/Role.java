package ua.nure.danylenko.epam.db;

import ua.nure.danylenko.epam.db.entity.User;

public enum Role {
    ADMIN, CLIENT, GUEST;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
