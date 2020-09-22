package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.entity.Category;
import ua.nure.danylenko.epam.db.service.CatalogueService;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateCatalogueCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    //private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        WEB_LOG.info("UpdateCatalogueCommand starts");
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        Catalogue cat=(Catalogue)context.getAttribute("catalogue");


        String forward = Path.PAGE_ADMIN_CABINET;
        String command = request.getParameter("updateCatalogueType");
        switch (command){
            case "add":{
                cat=addItem(request,cat);
                break;
            }
            case "delete":{
                System.out.println("delete");
                break;
            }case "edit":{
                System.out.println("edit");
                break;
            }default:{
                forward=Path.PAGE_ERROR_PAGE;
            }

        }

        context.setAttribute("catalogue",cat);


        WEB_LOG.info("UpdateCatalogueCommand finished");

        return forward;
    }

    private Catalogue addItem(HttpServletRequest req, Catalogue cat) {
        String itemName = req.getParameter("catalogueName");
        cat.getContainer().put(itemName,new ArrayList<Category>());
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().addItem(itemName);
        return cat;
    }


    private void addCategory(HttpSession session, HttpServletRequest req, Catalogue cat) {

        CatalogueService catalogueService = new CatalogueService();
        //catalogueService.getDao().addItem(req.getParameter("catalogueName"));
    }
}
