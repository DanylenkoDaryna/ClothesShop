package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Item;
import ua.nure.danylenko.epam.db.service.ItemsService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ProductsCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");
    private static final Logger DB_LOG = Logger.getLogger("jdbc");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        WEB_LOG.info("ProductsCommand starts");

        HttpSession session = request.getSession();
        String categoryType = request.getParameter("clothes");
        int catalogId = Integer.parseInt(request.getParameter("catId"));
        DB_LOG.info("ProductsCommand request from db parameter: for Who --> " + categoryType);
        DB_LOG.info("ProductsCommand request from db parameter: for Who -->");
        if (categoryType == null || categoryType.isEmpty()){
            DB_LOG.error("categoryType cannot be empty");
            throw new AppException("categoryType cannot be empty");

        }
        String forward = Path.PAGE_PRODUCTS;

        ItemsService itemsService = new ItemsService();
        List<Item> items = itemsService.getDao().getItemsByCategory(categoryType, catalogId);

        if (items == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        session.setAttribute("items", items);
        DB_LOG.info("Products from DB by category id: " + items);

        Map<String,List> filterParameters = itemsService.getDao().getSizesColoursBrands();
        if (filterParameters == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        Set<String> colorSorted = new TreeSet<>();
        colorSorted.addAll(filterParameters.get("colours"));
        Set<String> brandSorted = new TreeSet<>();
        brandSorted.addAll(filterParameters.get("brands"));
        Set<String> sizeSorted = new TreeSet<>();
        sizeSorted.addAll(filterParameters.get("sizes"));

        session.setAttribute("filterColours", colorSorted);
        session.setAttribute("filterBrands", brandSorted);
        session.setAttribute("filterSizes", sizeSorted);


        session.setAttribute("currentCatalogueId", catalogId);
        session.setAttribute("currentCategoryName", categoryType);

        return forward;
    }

}
