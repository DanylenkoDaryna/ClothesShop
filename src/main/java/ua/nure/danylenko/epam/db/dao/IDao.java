package ua.nure.danylenko.epam.db.dao;

import ua.nure.danylenko.epam.exception.DBException;

public interface IDao<E> {

    void create(E entity);

    E read() throws DBException;

    void update(E entity);

    void delete(long id);
}
