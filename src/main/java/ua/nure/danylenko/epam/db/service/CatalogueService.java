package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.CatalogueDao;
import ua.nure.danylenko.epam.db.dao.IDao;
import ua.nure.danylenko.epam.db.entity.Catalogue;

public class CatalogueService implements IService {
    @Override
    public IDao<Catalogue> getDao() {
        return new CatalogueDao();
    }
}
