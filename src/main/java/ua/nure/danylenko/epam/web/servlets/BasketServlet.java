package ua.nure.danylenko.epam.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Basket;
import ua.nure.danylenko.epam.db.entity.BasketElement;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.entity.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The BasketServlet class extends HttpServlet provides request processing about adding item to basket
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class BasketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WEB_LOG.info("BasketServlet starts");
        HttpSession session = req.getSession();
        List<Item> container = new ArrayList<>();
        BasketElement element = new BasketElement();
        String forward = req.getParameter("page");

        String chosenSize = req.getParameter("chosenSize");
        WEB_LOG.info("chosenSize="+chosenSize);
        List<Product> productsOfItem = (ArrayList<Product>)session.getAttribute("productsOfItem");
        for(Product prod:productsOfItem){
            if(prod.getBodySize().toString().equals(chosenSize)){
                element.setBasketProduct(prod);
                WEB_LOG.info("chosen product to add in basket = " + prod.getId());
                break;
            }
        }

        if(session.getAttribute("items")!=null){
            container = (ArrayList<Item>)session.getAttribute("items");
        }else{
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("ERROR: BasketServlet doPost() -> items container is empty");
        }

        long idToBasket=Long.parseLong(req.getParameter("ClothesIdToBasket"));
        WEB_LOG.info("ClothesIdToBasket =" + idToBasket);

        //корзина має зберігатися в сесії а не створювати себе заново після кожного додавання продукту

            for(Item item: container){
                if(item.getId().equals(idToBasket)){
                    element.setBasketItem(item);
                    if (session.getAttribute("Basket")==null) {
                        Basket basket = new Basket();
                        basket.getBasketElements().add(element);
                        session.setAttribute("totalAmount", basket.sumCosts());
                        session.setAttribute("Basket", basket);

                    } else {
                        Basket basket = (Basket) session.getAttribute("Basket");
                        basket.getBasketElements().add(element);
                        session.setAttribute("totalAmount", basket.sumCosts());
                        session.setAttribute("Basket", basket);

                    }
                }
            }
        WEB_LOG.info("BasketServlet ends");
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
