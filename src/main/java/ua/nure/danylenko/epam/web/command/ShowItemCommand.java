package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.service.ItemsService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


//not used

public class ShowItemCommand extends Command {

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        WEB_LOG.info("ShowItemCommand starts");

        HttpSession session = request.getSession();
        String categoryType = request.getParameter("clothes");
        int catalogId = Integer.parseInt(request.getParameter("catId"));
        WEB_LOG.info("Request parameter: forWho --> " + categoryType);

        if (categoryType == null || categoryType.isEmpty()){
            throw new AppException("categoryType cannot be empty");
        }
        String forward = Path.PAGE_PRODUCTS;

        ItemsService itemsService = new ItemsService();
        List<Item> items = itemsService.getDao().getItemsByCategory(categoryType, catalogId);

        if (items == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }
        session.setAttribute("items", items);
        return forward;
    }
}
