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

public class WishlistCleanerServlet extends HttpServlet {
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
        WEB_LOG.info("WishlistCleanerServlet starts");

        String forward = req.getParameter("pageBack");
        HttpSession session = req.getSession();

        List<Item> wishlist = (List<Item>)session.getAttribute("wishlist");
        long idToDelete=Long.parseLong(req.getParameter("IdDeleteFromWishlist"));

        if(wishlist==null){
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("ERROR: BasketCleanerServlet() -> there is no itemsInWishlist");
        }

        try {
            for(Item item: wishlist){
                if(item.getId().equals(idToDelete)){
                    wishlist.remove(item);
                    session.setAttribute("wishlist", wishlist);
                }
            }
        } catch (NullPointerException ne) {
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("NullPointerException in WishlistCleanerServlet()" + ne.getMessage());
        }catch (Exception ex){
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("Exception in WishlistCleanerServlet()" + ex.getMessage());
        }
        WEB_LOG.info("WishlistCleanerServlet ends");
        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }
}
