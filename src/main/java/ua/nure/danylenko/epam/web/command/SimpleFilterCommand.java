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

public class SimpleFilterCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        LOG.debug("SimpleFilterCommand starts");

        HttpSession session = request.getSession();
        String filterType = request.getParameter("filtration");
        String pageToReturn = request.getParameter("page");
        List<Item> items = new ArrayList<>();
        if(session.getAttribute("filteredItems")!=null&&"filteredPage".equals(pageToReturn)){
            items = (List<Item>)session.getAttribute("filteredItems");
        }else if("products".equals(pageToReturn)){
            items = (List<Item>)session.getAttribute("items");
        }

        LOG.debug("Request parameters: filtration --> " + filterType);
        LOG.debug("Request parameters: items --> " + items);

        if (items == null || items.isEmpty()){
            throw new AppException("items cannot be empty");
        }else if(filterType.isEmpty()){
            throw new AppException("filterType cannot be empty");
        }
        String forward = "/"+pageToReturn+".jsp";

        switch (filterType){
            case "FromAToZ": {
                filterFromAToZ(items);
                break;
            }
            case "FromZToA": {
                filterFromZToA(items);
                break;
            }
            case "LowPriceFirst":{
                filterFromLowPrice(items);
                break;
            }
            case "HighPriceFirst":{
                filterFromHighPrice(items);
                break;
            }
            case "oldFirst":{
                filterFromOldProducts(items);
                break;
            }
            case "newFirst":{
                filterFromNewProducts(items);
                break;
            }
        }

        if (items == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        return forward;
    }

    private void filterFromAToZ(List<Item> items) {
        for(int i=items.size()-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(items.get(j).getProductName().compareTo(items.get(j+1).getProductName())>0){
                    Item temp = items.get(j);
                    items.set(j,items.get(j+1));
                    items.set(j+1,temp);
                }
            }
        }
    }

    private void filterFromZToA(List<Item> items) {
        for(int i=items.size()-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(items.get(j).getProductName().compareTo(items.get(j+1).getProductName())<0){
                    Item temp = items.get(j);
                    items.set(j,items.get(j+1));
                    items.set(j+1,temp);
                }
            }
        }
    }

    private void filterFromLowPrice(List<Item> items) {
        items.sort((o1, o2) -> {
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

    private void filterFromHighPrice(List<Item> items) {
        items.sort((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
    }

    private void filterFromOldProducts(List<Item> items) {
        items.sort(Comparator.comparing(Item::getReleaseDate));
    }

    private void filterFromNewProducts(List<Item> items) {
        items.sort(Comparator.comparing(Item::getReleaseDate).reversed());
    }
}
