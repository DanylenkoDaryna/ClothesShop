package ua.nure.danylenko.epam.exception;

/**
 * The Messages class provides constant messages for log and debug in app
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class Messages {

    private Messages() {
    }

    public static final String ERR_CANNOT_CREATE_ORDER = "Cannot create order";

    public static final String ERR_CANNOT_CREATE_USER = "Cannot create user";

    public static final String ERR_CANNOT_CREATE_ITEM = "Cannot create item";

    public static final String ERR_CANNOT_CREATE_PRODUCT = "Cannot create product for item";

    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

    public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS = "Cannot obtain menu items";

    public static final String ERR_CANNOT_OBTAIN_CATEGORIES = "Cannot obtain categories";

    public static final String ERR_CANNOT_RENAME_MENU_ITEM = "Cannot rename menu item";

    public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_ID = "Cannot obtain orders by id";

    public static final String ERR_CANNOT_OBTAIN_ORDERS = "Cannot obtain orders";

    public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login";

    public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

    public static final String ERR_CANNOT_ADD_NEW_MENU_ITEM = "Cannot add new catalogue item in db";

    public static final String ERR_CANNOT_UPDATE_ORDER_STATUS = "Cannot update order status";

    public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";

    public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

    public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

    public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

    public static final String ERR_CANNOT_REMOVE_CATALOGUE_ITEM_AND_CATEGORIES = "Cannot delete menu item and all its categories";

}
