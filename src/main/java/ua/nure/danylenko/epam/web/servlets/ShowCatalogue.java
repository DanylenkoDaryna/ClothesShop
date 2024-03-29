package ua.nure.danylenko.epam.web.servlets;

import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.service.CatalogueService;
import ua.nure.danylenko.epam.exception.DBException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The ShowCatalogue class extends HttpServlet provides the main request processing about catalogue
 * and categories for creating proper menu view
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class ShowCatalogue extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();

        CatalogueService catalogueService = new CatalogueService();
        String forward = Path.PAGE_GOOD;
        Catalogue catalogue = null;
        try {
            catalogue = (Catalogue)catalogueService.getDao().read();
        } catch (DBException e) {
            e.printStackTrace();

        }
        if (catalogue == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        context.setAttribute("catalogue", catalogue);
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }
}
