package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The SimpleFilterCommand class extends Command and provides popular types of filtering for products
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class SimpleFilterCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");
    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("SimpleFilterCommand starts");

        HttpSession session = request.getSession();
        String filterType = request.getParameter("filtration");
        String pageToReturn = request.getParameter("page");
        List<Item> itemstoFilter = new ArrayList<>();
       // if(session.getAttribute("filteredItems")!=null & "filteredPage".equals(pageToReturn)){
        if("filteredPage".equals(pageToReturn)){
            itemstoFilter = (List<Item>)session.getAttribute("filteredItems");
        }else if("products".equals(pageToReturn)){
            itemstoFilter = (List<Item>)session.getAttribute("items");
        }

        DB_LOG.info("Request parameters: filtration --> " + filterType);
        DB_LOG.info("Request parameters: items --> " + itemstoFilter);

        if (itemstoFilter == null || itemstoFilter.isEmpty()){
            throw new AppException("items cannot be empty");
        }else if(filterType.isEmpty()){
            throw new AppException("filterType cannot be empty");
        }
        String forward = pageToReturn+".jsp";

        switch (filterType){
            case "FromAToZ": {
                filterFromAToZ(itemstoFilter);
                break;
            }
            case "FromZToA": {
                filterFromZToA(itemstoFilter);
                break;
            }
            case "LowPriceFirst":{
                filterFromLowPrice(itemstoFilter);
                break;
            }
            case "HighPriceFirst":{
                filterFromHighPrice(itemstoFilter);
                break;
            }
            case "oldFirst":{
                filterFromOldProducts(itemstoFilter);
                break;
            }
            case "newFirst":{
                filterFromNewProducts(itemstoFilter);
                break;
            }
        }

        if (itemstoFilter == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        return forward;
    }

    private void filterFromAToZ(List<Item> itemstoFilter) {
        for(int i=itemstoFilter.size()-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(itemstoFilter.get(j).getItemName().compareTo(itemstoFilter.get(j+1).getItemName())>0){
                    Item temp = itemstoFilter.get(j);
                    itemstoFilter.set(j,itemstoFilter.get(j+1));
                    itemstoFilter.set(j+1,temp);
                }
            }
        }
    }

    private void filterFromZToA(List<Item> itemstoFilter) {
        for(int i=itemstoFilter.size()-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(itemstoFilter.get(j).getItemName().compareTo(itemstoFilter.get(j+1).getItemName())<0){
                    Item temp = itemstoFilter.get(j);
                    itemstoFilter.set(j,itemstoFilter.get(j+1));
                    itemstoFilter.set(j+1,temp);
                }
            }
        }
    }

    private void filterFromLowPrice(List<Item> itemstoFilter) {
        itemstoFilter.sort((o1, o2) -> {
            if (o1.getPrice() > o2.getPrice())
                return -1;           // Neither val is NaN, thisVal is smaller
            if (o1.getPrice() < o2.getPrice())
                return 1;            // Neither val is NaN, thisVal is larger

            // Cannot use doubleToRawLongBits because of possibility of NaNs.
            long thisBits    = Double.doubleToLongBits(o1.getPrice());
            long anotherBits = Double.doubleToLongBits(o2.getPrice());

            return (thisBits == anotherBits ?  0 : // Values are equal
                    (thisBits > anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                            1));                          // (0.0, -0.0) or (NaN, !NaN)

        });
    }

    private void filterFromHighPrice(List<Item> itemstoFilter) {
        itemstoFilter.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
    }

    private void filterFromOldProducts(List<Item> itemstoFilter) {
        itemstoFilter.sort(Comparator.comparing(Item::getReleaseDate));
    }

    private void filterFromNewProducts(List<Item> itemstoFilter) {
        itemstoFilter.sort(Comparator.comparing(Item::getReleaseDate).reversed());
    }
}
