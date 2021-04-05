package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.entity.Category;
import ua.nure.danylenko.epam.db.entity.Item;
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
import java.util.List;

/**
 * The UpdateCatalogueCommand class extends Command and provides changing catalogue (and)or categories lists by admin
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class UpdateCatalogueCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

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
                cat=addCatalogueItem(request,cat);
                break;
            }
            case "delete":{
                cat=removeCatalogueItem(request,cat);
                break;
            }case "edit":{
                cat=editCatalogueItem(request,cat);
                break;
            } case "addCategory":{
                cat=addCategory(request,cat);
                break;
            }
            case "deleteCategory":{
                cat=removeCategory(request,cat);
                break;
            }case "editCategory":{
                cat=editCategory(request,cat);
                break;
            }case "deleteProduct":{
                session.setAttribute("items",deleteProduct(request,session));
                break;
            }default:{
                forward=Path.PAGE_ERROR_PAGE;
            }

        }

        context.setAttribute("catalogue",cat);


        WEB_LOG.info("UpdateCatalogueCommand finished");

        return forward;
    }

    /**
     * Method for adding new catalogue item in the catalogue menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
    private Catalogue addCatalogueItem(HttpServletRequest req, Catalogue cat) {

        WEB_LOG.info("add item to catalogue");
        String catItemName = req.getParameter("catalogueName");
        WEB_LOG.info("catItemName="+catItemName);

        cat.getContainer().put(catItemName,new ArrayList<Category>());
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().addCatalogueItem(catItemName);
        return cat;
    }


    /**
     * Method for removing catalogue item from the catalogue menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
     private Catalogue removeCatalogueItem(HttpServletRequest req, Catalogue cat) {
         WEB_LOG.info("delete item from catalogue");
         String catItemToDelete = req.getParameter("itemToDelete");
         WEB_LOG.info("catItemToDelete="+catItemToDelete);
         cat.getContainer().remove(catItemToDelete);
         CatalogueService catalogueService = new CatalogueService();
         catalogueService.getDao().removeCatalogueItem(catItemToDelete);
         return cat;
        }

    /**
     * Method for changing name of catalogue item in menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
    private Catalogue editCatalogueItem(HttpServletRequest req, Catalogue cat) {

        WEB_LOG.info("edit item from catalogue");
        String itemToEdit = req.getParameter("itemToEdit");
        String editedName = req.getParameter("editedItem");
        ArrayList<Category> categories = (ArrayList<Category>)cat.getContainer().get(itemToEdit);
        cat.getContainer().remove(itemToEdit);
        cat.getContainer().put(editedName,categories);
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().renameCatalogueItem(itemToEdit,editedName);
        return cat;
    }

    /**
     * Method for adding new category in one of the catalogue items in main menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
     private Catalogue addCategory(HttpServletRequest req, Catalogue cat) {

        WEB_LOG.info("add new Category");
            String catalogueItem = req.getParameter("CatalogueToAddTo");
            String itemName = req.getParameter("categoryName");
            Category category= new Category();
            category.setName(itemName);
            cat.getContainer().get(catalogueItem).add(category);
            CatalogueService catalogueService = new CatalogueService();
            catalogueService.getDao().addCategory(catalogueItem,itemName);
            return cat;
        }


    /**
     * Method for removing category in one of the catalogue items in main menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
     private Catalogue removeCategory(HttpServletRequest req, Catalogue cat) {

         WEB_LOG.info("delete Category from catalogue");
         String catalogueToDeleteFrom = req.getParameter("CatalogueToDeleteFrom");
         String itemNameToDelete = req.getParameter("itemToDelete");
         for (int i=0; i<cat.getContainer().get(catalogueToDeleteFrom).size(); i++){
             if(cat.getContainer().get(catalogueToDeleteFrom).get(i).getName().equals(itemNameToDelete)){
                 cat.getContainer().get(catalogueToDeleteFrom).remove(i);
             }
         }
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().removeCategory(catalogueToDeleteFrom,itemNameToDelete);
        return cat;
        }

    /**
     * Method for editing the name of category in one of the catalogue items in main menu
     * @param req HttpServletRequest
     * @param cat Catalogue
     * @return updated Catalogue
     */
    private Catalogue editCategory(HttpServletRequest req, Catalogue cat) {

        WEB_LOG.info("edit Category from catalogue");
        String catalogue = req.getParameter("CatalogueToEditFrom");
        String oldCategory = req.getParameter("itemToEdit");
        String newCategory = req.getParameter("editedCategory");
        WEB_LOG.info("catslogue="+catalogue+" oldCategory="+oldCategory+" newCategory="+newCategory);
        for (int i=0; i<cat.getContainer().get(catalogue).size(); i++){
            if(cat.getContainer().get(catalogue).get(i).getName().equals(oldCategory)){
                cat.getContainer().get(catalogue).remove(i);
            }
        }
        Category category= new Category();
        category.setName(newCategory);
        cat.getContainer().get(catalogue).add(category);
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().renameCategory(catalogue,oldCategory,newCategory);
        return cat;
    }


    /**
     * Method for deleting item in db throw catalogue Service and in items list
     * @param req HttpServletRequest
     * @param session HttpSession
     * @return updated list of items
     */
    private List<Item> deleteProduct(HttpServletRequest req, HttpSession session) {

        WEB_LOG.info("delete Product from catalogue");
        long idToDelete = Long.parseLong(req.getParameter("productToDelete"));
        List<Item> items = (List<Item>)session.getAttribute("items");
        items.remove(idToDelete);
        for (int i=0; i<items.size(); i++){
            if(items.get(i).getId().equals(idToDelete)){
                items.remove(i);
            }
        }
        CatalogueService catalogueService = new CatalogueService();
        catalogueService.getDao().removeProduct(idToDelete);
        return items;

    }

}
