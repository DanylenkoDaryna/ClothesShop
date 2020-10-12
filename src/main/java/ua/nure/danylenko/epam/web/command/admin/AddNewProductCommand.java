package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.*;
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

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("AddNewProductCommand starts");
        HttpSession session = req.getSession();
        List<Item> items=(List<Item>)session.getAttribute("items");
        Item newItem = new Item();
        newItem.setBrand(req.getParameter("brand"));
        newItem.setPrice(Double.parseDouble(req.getParameter("price")));
        newItem.setReleaseDate(LocalDate.parse(req.getParameter("releaseDate")));
        newItem.setProductName(req.getParameter("itemName"));

        String collection =req.getParameter("collectionName");
        String[] available = req.getParameterValues("available");
        String[] sizes = req.getParameterValues("size");
        String[] colours = req.getParameterValues("colour");
        String[] images = req.getParameterValues("image");

        ArrayList<Product> products = new ArrayList<>();
        for (int i=0; i<available.length; i++){
            products.add(new Product());
            products.get(i).setAvailable(Integer.parseInt(available[i]));
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

        String[] materials = req.getParameterValues("materials");
        String[] percents = req.getParameterValues("percents");
        List<Material> materialsList = (ArrayList<Material>) Material.extractItems(materials,percents);


        items.add(newItem);
        String forward = Path.PAGE_GOOD;
        session.setAttribute("items",items);

        WEB_LOG.info("AddNewProductCommand finished");

        return forward;
    }
}
