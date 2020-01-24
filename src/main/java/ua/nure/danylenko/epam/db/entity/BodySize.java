package ua.nure.danylenko.epam.db.entity;

public enum BodySize {

    XXS, XS, S, M, L, XL, XXL, XXXL;

    public String getName() {
        return name().toLowerCase();
    }
}
