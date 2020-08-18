package ua.nure.danylenko.epam.web.command.client;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetMyCabinetCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");
    private static final Logger APP_LOG = Logger.getLogger("armadio");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        WEB_LOG.info("GetMyCabinetCommand starts");
        APP_LOG.debug("GetMyCabinetCommand starts in app");
//
//        // get menu items list
//        List<MenuItem> menuItems = DBManager.getInstance().findMenuItems();
//        LOG.trace("Found in DB: menuItemsList --> " + menuItems);
//
//        // sort menu by category
//        Collections.sort(menuItems, new Comparator<MenuItem>() {
//            public int compare(MenuItem o1, MenuItem o2) {
//                return (int)(o1.getCategoryId() - o2.getCategoryId());
//            }
//        });
//
//        // put menu items list to the request
//        request.setAttribute("menuItems", menuItems);
//        LOG.trace("Set the request attribute: menuItems --> " + menuItems);
//
//        LOG.debug("Command finished");
        return Path.PAGE_PERSONAL_CABINET;
       // return null;
    }
}
