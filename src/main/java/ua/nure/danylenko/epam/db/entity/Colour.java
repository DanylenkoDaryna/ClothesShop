package ua.nure.danylenko.epam.db.entity;

public enum Colour {

    RED, BLUE, PINK, YELLOW, GREEN, GREY, BLACK, WHITE;


    public String getName() {
        return name().toLowerCase();
    }
}
