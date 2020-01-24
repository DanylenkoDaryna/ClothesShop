package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.IDao;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.exception.DBException;

public interface ICatalogueService {

    IDao<Catalogue> getDao();

    default Catalogue read() throws DBException {
        return getDao().read();
    }


}
