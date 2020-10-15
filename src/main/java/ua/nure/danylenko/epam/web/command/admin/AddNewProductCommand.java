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
        WEB_LOG.info("AAAAAAAAAA"+request.getParameter("itemName"));

        String collection =request.getParameter("collectionName");
        String[] available = request.getParameterValues("available");
        String[] sizes = request.getParameterValues("size");
        String[] colours = request.getParameterValues("colour");
        String[] images = request.getParameterValues("image");

        ArrayList<Product> products = new ArrayList<>();
        for (int i=0; i<available.length; i++){
            products.add(new Product());
            products.get(i).setAvailable(Integer.parseInt(available[i]));
            products.get(i).setName(collection);
            WEB_LOG.info(available[i] + " ");
        }
        for (int j=0; j<sizes.length; j++){
            products.add(new Product());
            products.get(j).setBodySize(BodySize.valueOf(sizes[j]));
            WEB_LOG.info(sizes[j] + " ");
        }
        for (int k=0; k<colours.length; k++){
            products.add(new Product());
            products.get(k).setColour(Colour.valueOf(colours[k]));
            WEB_LOG.info(colours[k] + " ");
        }
        for (int m=0; m<images.length; m++){
            products.add(new Product());
            products.get(m).setImages(new ArrayList<>());
            products.get(m).getImages().add(images[m]);
            WEB_LOG.info("Image path - " + images[m] );
        }

        for (int y=0; y<newItem.getContainer().size(); y++){

            WEB_LOG.info("Products - " + newItem.getContainer().get(y).toString() );
        }
        String[] materials = request.getParameterValues("materials");
        String[] percents = request.getParameterValues("percents");
        List<Material> materialsList = Material.extractItems(materials,percents, newItem.getId());
        newItem.setMaterials(materialsList);

        items.add(newItem);

        ItemsService itemsService = new ItemsService();
        itemsService.getDao().create(newItem);


        String forward = Path.PAGE_GOOD;
        session.setAttribute("items",items);

        WEB_LOG.info("AddNewProductCommand finished");

        return forward;
    }
}
