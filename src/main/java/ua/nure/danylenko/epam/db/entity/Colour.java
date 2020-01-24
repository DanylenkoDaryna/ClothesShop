package ua.nure.danylenko.epam.db.entity;

public enum Colour {

    RED, BLUE, PINK, YELLOW, GREEN, BLACK, WHITE;


    public String getName() {
        return name().toLowerCase();
    }
}
