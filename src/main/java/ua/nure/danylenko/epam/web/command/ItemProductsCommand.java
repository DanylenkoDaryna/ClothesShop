package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Product;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ItemProductsCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("ItemProductsCommand starts");

        HttpSession session = request.getSession();
        List<Product> container = (List<Product>)request.getAttribute("itemContainer");
        LOG.debug("Request parameter: container --> " + container);

        if (container == null || container.isEmpty()){
            throw new AppException("container cannot be empty");
        }
        String forward = Path.PAGE_ITEM_PRODUCTS;

        ServletContext context = getServletContext();
        session.setAttribute("itemsContainer", container);
        return forward;
    }
}
