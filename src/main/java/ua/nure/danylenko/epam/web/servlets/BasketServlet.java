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
import java.util.ArrayList;
import java.util.List;

public class BasketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = Path.PAGE_PRODUCTS;
        HttpSession session = req.getSession();
        List<Item> container = new ArrayList<>();

        if(session.getAttribute("items")!=null){
            container = (List<Item>)session.getAttribute("items");
        }else{
            forward = Path.PAGE_ERROR_PAGE;
            LOG.error("ERROR: BasketServlet doPost() -> items container is empty");
        }

        long idToBasket=Long.parseLong(req.getParameter("ClothesIdToBasket"));
        //корзина должна сохраняться в сессии и не создаваться при каждом добавлении новая

            for(Item item: container){
                if(item.getId().equals(idToBasket)){
                    if (session.getAttribute("itemsInBasket")==null) {
                        List<Item> basket = new ArrayList<>();
                        basket.add(item);
                        session.setAttribute("itemsInBasket", basket);
                    } else {
                        List<Item> itemsInBasket = (List<Item>)session.getAttribute("itemsInBasket");
                        itemsInBasket.add(item);
                        session.setAttribute("itemsInBasket", itemsInBasket);
                    }
                }
            }
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
