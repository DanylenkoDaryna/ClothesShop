package ua.nure.danylenko.epam.db.dao;

import ua.nure.danylenko.epam.exception.DBException;

/**
 The OrderItem class provides fields and methods for making orders of bying purchases by customer
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 * @param <E>
 */
public interface IDao<E> {

    void create(E entity);

    E read() throws DBException;

    void update(E entity);

    void delete(long id);
}
