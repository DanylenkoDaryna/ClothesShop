package ua.nure.danylenko.epam;

public class Path {
    // pages
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_GOOD = "index.jsp";
    public static final String PAGE_HEAD = "/WEB-INF/jspf/head.jspf";
    public static final String PAGE_PRODUCTS = "/products.jsp";
    public static final String PAGE_ORDERING = "/ordering.jsp";
    public static final String PAGE_REGISTERED_ORDER = "/registeredOrder.jsp";
    public static final String PAGE_REGISTERING = "/registering.jsp";
    public static final String PAGE_FILTERED_PRODUCTS = "filteredPage.jsp";
    public static final String PAGE_ITEM_PRODUCTS = "/currentItem.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_PERSONAL_CABINET = "/clientCabinetPage.jsp";
    public static final String PAGE_ADMIN_CABINET = "/adminCabinetPage.jsp";

    // commands
    public static final String COMMAND_UPDATING_UPO = "/controller?command=updatingUPO";
    public static final String COMMAND_CABINET_ORDERS = "/controller?command=getMyCabinet";
}
