package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.CatalogueDao;

public class CatalogueService implements IService {
    @Override
    public CatalogueDao getDao() {
        return new CatalogueDao();
    }
}
