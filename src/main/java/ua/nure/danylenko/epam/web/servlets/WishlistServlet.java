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

/**
 * The WishlistServlet class extends HttpServlet provides request processing about adding item to wish list
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class WishlistServlet extends HttpServlet {
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
        WEB_LOG.info("WishlistServlet starts");
        HttpSession session = req.getSession();
        List<Item> items = new ArrayList<>();
        String forward = req.getParameter("page");

        if(session.getAttribute("items")!=null){
            items = (ArrayList<Item>)session.getAttribute("items");
        }else{
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("ERROR: BasketServlet doPost() -> items container is empty");
        }

        long idToWishlist=Long.parseLong(req.getParameter("ClothesIdToWishlist"));

        for(Item item: items){
            if(item.getId().equals(idToWishlist)){
                if (session.getAttribute("wishlist")==null) {
                    List<Item> wishlist = new ArrayList<>();
                    wishlist.add(item);
                    session.setAttribute("wishlist", wishlist);
                } else {
                    List<Item> wishlist = (List<Item>)session.getAttribute("wishlist");
                    wishlist.add(item);
                    session.setAttribute("wishlist", wishlist);
                }
            }
        }
        WEB_LOG.info("WishlistServlet ends");
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
