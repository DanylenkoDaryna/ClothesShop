package ua.nure.danylenko.epam.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Basket;
import ua.nure.danylenko.epam.db.entity.BasketElement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *The BasketCleanerServlet class extends HttpServlet provides request processing about removing item from basket
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class BasketCleanerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward = req.getParameter("pageBack");
        HttpSession session = req.getSession();

        Basket basket = (Basket)session.getAttribute("Basket");
        long idToDelete=Long.parseLong(req.getParameter("IdDeleteFromBasket"));

        if(basket.getBasketElements().isEmpty()){
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("ERROR: BasketCleanerServlet doPost() -> Basket container is empty");
        }

        try {
            for(BasketElement element: basket.getBasketElements()){
                if(element.getBasketProduct().getId().equals(idToDelete)){
                    basket.getBasketElements().remove(element);
                    session.setAttribute("totalAmount", basket.sumCosts());
                    session.setAttribute("Basket", basket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            WEB_LOG.error(" in BasketCleanerServlet 27.06.2020");
        }
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }

}
