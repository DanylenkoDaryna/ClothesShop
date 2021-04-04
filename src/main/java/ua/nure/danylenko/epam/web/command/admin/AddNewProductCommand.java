package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.*;
import ua.nure.danylenko.epam.db.service.CatalogueService;
import ua.nure.danylenko.epam.db.service.ItemsService;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The AddNewProductCommand class extends Command and provides adding new product for particular category by admin
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class AddNewProductCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        WEB_LOG.info("AddNewProductCommand starts");
        HttpSession session = request.getSession();
        List<Item> items = (ArrayList<Item>)session.getAttribute("items");

        Item newItem = new Item();

        if(items.size()!=0) {
            newItem.setCategoryId(items.get(0).getCategoryId());
            WEB_LOG.info("Category Id = " + items.get(0).getCategoryId());
        }else {
            String categoryType = (String) session.getAttribute("currentCategoryName");
            int catalogId = (Integer)session.getAttribute("currentCatalogueId");
            CatalogueService catalogueService = new CatalogueService();
            int categoryId = catalogueService.getDao().getCategoryId(catalogId, categoryType);
            newItem.setCategoryId(categoryId);
            WEB_LOG.info("Category Id = " + categoryId);
        }

        newItem.setBrand(request.getParameter("brand"));
        //check later
        //newItem.setColour(request.getParameter("colour"));
        newItem.setPrice(Double.parseDouble(request.getParameter("price")));
        newItem.setReleaseDate(LocalDate.parse(request.getParameter("releaseDate")));
        newItem.setItemName(request.getParameter("itemName"));

        ArrayList<Product> products = new ArrayList<>();
        String collectionName = request.getParameter("collectionName");

        //int productsNumber=Integer.parseInt(request.getParameter("productsNumber"));
        //WEB_LOG.info(productsNumber);
        String[] available = request.getParameterValues("available");
        for (int i=0; i<available.length; i++){
            products.add(new Product());
            products.get(i).setAvailable(Integer.parseInt(available[i]));
            products.get(i).setName(collectionName);
            WEB_LOG.info(available[i] + " ");
        }

        String[] sizes = request.getParameterValues("size");
        extractBodySizes(products,sizes);

        //check later
        String[] colours = request.getParameterValues("colour");
        extractColours(newItem,colours);

        String[] images = request.getParameterValues("image");
        extractImages(products,images);

        String[] materials = request.getParameterValues("materials");
        String[] percents = request.getParameterValues("percents");
        newItem.setMaterials(extractItems(materials,percents));

        newItem.setContainer(products);
        items.add(newItem);

        for (Product product : products) {

            WEB_LOG.info(product.getName() + " " + product.getAvailable() +  " "+
            product.getBodySize().toString() );
        }

        ItemsService itemsService = new ItemsService();
        itemsService.getDao().create(newItem);

        session.setAttribute("items",items);
        String forward = Path.PAGE_GOOD;

        int i=items.indexOf(newItem);
        WEB_LOG.info("items last added element " + items.get(i).getItemName());
        WEB_LOG.info("AddNewProductCommand finished");

        return forward;

    }


    /**
     * Method for extracting sizes from incoming array of Strings to particular products
     * @param products ArrayList<Product>
     * @param sizes String[] - array from request
     */
    private void extractBodySizes(ArrayList<Product> products, String[] sizes){

        for (int j=0; j<products.size(); j++){
            products.get(j).setBodySize(BodySize.valueOf(sizes[j]));
            WEB_LOG.info(sizes[j] + " ");
        }

    }

    /**
     * Method for extracting colours from incoming array of Strings to particular item
     * @param item Item
     * @param colours String[] - array from request
     */
    private void extractColours(Item item, String[] colours){

        //check later
        item.setColour(Colour.valueOf(colours[0]));
        WEB_LOG.info(item.getColour() + " ");

    }

    /**
     * Method for extracting images from incoming array of Strings to particular products
     * @param products ArrayList<Product>
     * @param images String[] - array from request
     */
    private void extractImages(ArrayList<Product> products, String[] images){

        for (int m=0; m<products.size(); m++){
            products.get(m).setImage(images[m]);
            WEB_LOG.info("Image path - " + products.get(m).getImage());
        }

    }

    /**
     * Method for extracting material positions(name and persentage) from incoming arrays of Strings
     * to particular Material
     * @param materials String[] - array from request
     * @param percents String[] - array from request
     * @return ArrayList <Material>
     */
    private List<Material> extractItems(String[] materials, String[] percents){

        List<Material> materialsList = new ArrayList<>();
        for(int i=0; i<materials.length; i++){
            Material material= new Material();
            material.setName(materials[i]);
            material.setPercent(Integer.parseInt(percents[i]));
            WEB_LOG.info("material - " + materials[i] );
            WEB_LOG.info("percents - " + percents[i] );
            materialsList.add(material);
        }
        return materialsList;

    }
}
