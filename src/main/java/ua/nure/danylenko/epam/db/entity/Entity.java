package ua.nure.danylenko.epam.db.entity;

import java.io.Serializable;

/**
 * The Entity abstract class provides main fields and methods for entity classes
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 8466257860808346236L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
