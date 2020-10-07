package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddNewProductCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("AddNewProductCommand starts");
        HttpSession session = req.getSession();
        List<Item> items=(List<Item>)session.getAttribute("items");
        Item newItem = new Item();
        newItem.setBrand(req.getParameter("brand"));
        newItem.setPrice(Double.parseDouble(req.getParameter("price")));
        newItem.setReleaseDate(LocalDate.parse(req.getParameter("releaseDate")));
        newItem.setProductName(req.getParameter("itemName"));

        String img=req.getParameter("image");

        items.add(newItem);
        String forward = Path.PAGE_GOOD;
        session.setAttribute("items",items);

        WEB_LOG.info("AddNewProductCommand finished");

        return forward;
    }
}
