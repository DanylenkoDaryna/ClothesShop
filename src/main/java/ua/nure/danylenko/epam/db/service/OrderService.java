package ua.nure.danylenko.epam.db.service;

import ua.nure.danylenko.epam.db.dao.OrderDao;

public class OrderService implements IService {

    @Override
    public OrderDao getDao() {
        return new OrderDao();
    }
}
