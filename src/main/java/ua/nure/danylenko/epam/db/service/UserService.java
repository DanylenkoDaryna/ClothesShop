package ua.nure.danylenko.epam.db.service;


import ua.nure.danylenko.epam.db.dao.UserDao;
import ua.nure.danylenko.epam.exception.DBException;

public class UserService implements IService{


    @Override
    public UserDao getDao() {
        return new UserDao();
    }

    @Override
    public Object read() throws DBException {
        return null;
    }
}
