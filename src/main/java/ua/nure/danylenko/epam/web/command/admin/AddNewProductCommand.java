package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.*;
import ua.nure.danylenko.epam.db.service.ItemsService;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddNewProductCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("AddNewProductCommand starts");
        HttpSession session = request.getSession();
        List<Item> items = (ArrayList<Item>)session.getAttribute("items");

        Item newItem = new Item();
        newItem.setCategoryId(items.get(0).getCategoryId());
        newItem.setBrand(request.getParameter("brand"));
        newItem.setPrice(Double.parseDouble(request.getParameter("price")));
        newItem.setReleaseDate(LocalDate.parse(request.getParameter("releaseDate")));
        newItem.setProductName(request.getParameter("itemName"));

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

        String[] colours = request.getParameterValues("colour");
        extractColours(products,colours);

        String[] images = request.getParameterValues("image");
        extractImages(products,images);

        String[] materials = request.getParameterValues("materials");
        String[] percents = request.getParameterValues("percents");
        newItem.setContainer(products);
        newItem.setMaterials(extractItems(materials,percents));
        items.add(newItem);

        for (Product product : products) {

            WEB_LOG.info(product.getName() + " " + product.getAvailable() + " " + product.getColour().toString() + " "+
            product.getBodySize().toString() );
        }

        ItemsService itemsService = new ItemsService();
        itemsService.getDao().create(newItem);

        String forward = Path.PAGE_GOOD;
        session.setAttribute("items",items);

        WEB_LOG.info("AddNewProductCommand finished");

        return forward;
    }


    public void extractBodySizes(ArrayList<Product> products, String[] sizes){
        for (int j=0; j<products.size(); j++){
            products.get(j).setBodySize(BodySize.valueOf(sizes[j]));
            WEB_LOG.info(sizes[j] + " ");
        }

    }

    public void extractColours(ArrayList<Product> products, String[] colours){
        for (int k=0; k<products.size(); k++){
            products.get(k).setColour(Colour.valueOf(colours[k]));
            WEB_LOG.info(colours[k] + " ");
        }

    }

    public void extractImages(ArrayList<Product> products, String[] images){
        for (int m=0; m<products.size(); m++){
            products.get(m).setImages(new ArrayList<>());
            products.get(m).getImages().add(images[m]);
            WEB_LOG.info("Image path - " + images[m] );
        }
    }

    public List<Material> extractItems(String[] materials, String[] percents){
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
