package ua.nure.danylenko.epam.web.servlets;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
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
 * The HardFilterServlet class extends HttpServlet provides request processing about filtering items on page by
 * different orders
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class HardFilterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = Path.PAGE_FILTERED_PRODUCTS;
        HttpSession session = req.getSession();
        List<Item> container = new ArrayList<>();

        if(session.getAttribute("items")!=null){
            container = (List<Item>)session.getAttribute("items");
        }else{
            forward = Path.PAGE_ERROR_PAGE;
            WEB_LOG.error("ERROR WHEN HARD FILTER PRODUCTS: empty List<Items> in sessionScope...");
        }


        String[] brands;
        if(req.getParameterValues("brands")!=null){
            brands = req.getParameterValues("brands");
            container=sortByBrands(brands,container);
        }

        if (!req.getParameter("InputPriceFrom").equals("")
                && !req.getParameter("InputPriceTo").equals("")) {
            int priceFrom = Integer.parseInt(req.getParameter("InputPriceFrom"));
            int priceTo = Integer.parseInt(req.getParameter("InputPriceTo"));
            container = sortByPrice(priceFrom, priceTo, container);
        }


        String[] sizes;
        if(req.getParameterValues("sizes")!=null){
            sizes= req.getParameterValues("sizes");
            container=sortBySizes(sizes,container);
        }

        String[] colours;
        if(req.getParameterValues("colours")!=null){
        colours = req.getParameterValues("colours");
        container=sortByColours(colours,container);
        }

        session.setAttribute("filteredItems", container);

        RequestDispatcher rd = req.getRequestDispatcher(forward);
        rd.forward(req, resp);
    }

    /**
     * Method to sort items by chosen brands
     * @param brands chosen brands
     * @param container list of purchases
     * @return updated list of items
     */
    private List<Item> sortByBrands(String[] brands,List<Item> container){
        List<Item> itemsNew = new ArrayList<>();
        for(Item i:container){
            int hitCount=0;
            for(String brand: brands){
                if(i.getBrand().equals(brand)){
                    hitCount=hitCount+1;
                }
            }
            if(hitCount!=0) {
                itemsNew.add(i);
            }
        }
        return itemsNew;
    }

    /**
     * Method to sort items by chosen max-min price
     * @param priceFrom first border value
     * @param priceTo second border value
     * @param container list of purchases
     * @return updated list of items
     */
    private List<Item>  sortByPrice(int priceFrom,int priceTo,List<Item> container){
        List<Item> itemsNew = new ArrayList<>();
        for(Item i:container){
                if(i.getPrice()>priceFrom && i.getPrice()<priceTo){
                    itemsNew.add(i);
                }
        }
        return itemsNew;
    }

    /**
     * Method to sort items by chosen sizes
     * @param sizes chosen sizes
     * @param container list of purchases
     * @return updated list of items
     */
    private List<Item> sortBySizes(String[] sizes,List<Item> container){
        List<Item> itemsNew = new ArrayList<>();
        for(Item i:container){
            int hitCount=0;
            for(Object p:i.getContainer()){
                Product product = (Product)p;
                for(String size: sizes){
                    if(product.getBodySize().toString().equals(size)){
                        hitCount=hitCount+1;
                    }
                }
            }

            if(hitCount!=0) {
                itemsNew.add(i);
            }
        }
        return itemsNew;
    }

    /**
     * Method to sort items by chosen colours
     * @param colours chosen colours
     * @param container list of purchases
     * @return updated list of items
     */
    private List<Item> sortByColours(String[] colours,List<Item> container){
        List<Item> itemsNew = new ArrayList<>();
        for(Item item:container){
            int hitCount=0;
            for(String colour: colours){
                if(item.getColour().toString().equals(colour)){
                    hitCount=hitCount+1;
                }
            }

            if(hitCount!=0) {
                itemsNew.add(item);
            }
        }
        return itemsNew;
    }
}
