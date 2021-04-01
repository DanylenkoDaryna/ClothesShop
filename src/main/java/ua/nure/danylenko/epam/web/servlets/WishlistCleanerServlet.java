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
 * The WishlistCleanerServlet extends HttpServlet
 * Servlet for removing items from wish list and returning back on the page
 * where this servlet was called
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class WishlistCleanerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WEB_LOG.info("WishlistCleanerServlet starts");

        String forward = req.getParameter("pageBack");
        HttpSession session = req.getSession();

        List<Item> wishlist = (ArrayList<Item>)session.getAttribute("wishlist");
        long idToDelete=Long.parseLong(req.getParameter("IdDeleteFromWishlist"));

        if(wishlist.isEmpty()){
            WEB_LOG.error("ERROR: BasketCleanerServlet() -> there is no itemsInWishlist");
            forward = Path.PAGE_ERROR_PAGE;
        }

        try {
            for(Item item: wishlist){
                if(item.getId().equals(idToDelete)){
                    wishlist.remove(item);
                    session.setAttribute("wishlist", wishlist);
                    //need in situation when there is one element:
                    //after deleting element in loop loop dont understand what to do
                    //and throw ConcurrentModificationException
                    break;
                }
            }
        }catch (Exception ex){
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("Exception in WishlistCleanerServlet()" + ex.getMessage());
        }
        WEB_LOG.info("WishlistCleanerServlet ends");
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
