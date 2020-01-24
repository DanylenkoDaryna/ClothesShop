package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.ItemsDao;

public class ItemsService {

    public ItemsDao getDao() {
        return new ItemsDao();
    }
}
