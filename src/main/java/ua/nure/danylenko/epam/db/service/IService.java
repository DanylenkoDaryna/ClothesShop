package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.IDao;
import ua.nure.danylenko.epam.exception.DBException;

public interface IService <T> {

    IDao<T> getDao();

    default T read() throws DBException {
        return getDao().read();
    }


}
