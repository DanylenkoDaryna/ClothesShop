package ua.nure.danylenko.epam.db;

public class Fields {
    // entities
    public static final String ENTITY_ID = "id";

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_ACC_STATUS = "acc_status";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_INFO_COUNTRY = "country";
    public static final String USER_INFO_BDAY = "birthday";
    public static final String USER_INFO_TEL = "telephone";
    public static final String USER_INFO_EMAIL="email";


    public static final String ORDER_BILL = "bill";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_STATUS_ID= "status_id";

    public static final String CATEGORY_NAME = "name";
    public static final String CATALOGUE_ID = "catalogue_id";

    public static final String ITEM_NAME = "product_name";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_RELEASE_DATE = "release_date";
    public static final String ITEM_BRAND = "brand";
    public static final String ITEM_CATEGORY_ID = "category_id";

    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_AVAILABLE = "available";
    public static final String PRODUCT_SIZE = "product_size";
    public static final String PRODUCT_COLOUR = "colour";
    public static final String PRODUCT_ITEM_ID = "category_id";

    public static final String MAERIAL_NAME = "material";
    public static final String MAERIAL_PERCENT = "percent";

    public static final String IMAGE_NAME = "img_name";

    public static final String MENU_ITEM_PRICE = "price";
    public static final String MENU_ITEM_NAME = "name";
    public static final String MENU_ITEM_CATEGORY_ID = "category_id";

    // beans
    public static final String USER_ORDER_BEAN_ORDER_ID = "id";
    public static final String USER_ORDER_BEAN_USER_FIRST_NAME = "first_name";
    public static final String USER_ORDER_BEAN_USER_LAST_NAME = "last_name";
    public static final String USER_ORDER_BEAN_ORDER_BILL = "bill";
    public static final String USER_ORDER_BEAN_STATUS_NAME = "name";
}
