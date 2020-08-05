package ua.nure.danylenko.epam.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Item;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BasketCleanerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //String forward = Path.PAGE_PRODUCTS;
        String forward = req.getParameter("pageBack");
        HttpSession session = req.getSession();

        List<Item> basket = (List<Item>)session.getAttribute("itemsInBasket");
        long idToDelete=Long.parseLong(req.getParameter("IdDeleteFromBasket"));

        if(basket==null){
            forward = Path.PAGE_ERROR_PAGE;
            LOG.error("ERROR: BasketCleanerServlet doPost() -> itemsInBasket container is empty");
        }

        try {
            for(Item item: basket){
                if(item.getId().equals(idToDelete)){
                    basket.remove(item);
                    session.setAttribute("itemsInBasket", basket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(" in BasketCleanerServlet 27.06.2020");
        }
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }

}
