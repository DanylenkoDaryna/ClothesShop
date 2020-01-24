package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Catalogue;
import ua.nure.danylenko.epam.db.service.CatalogueService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowCatalogueCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        HttpSession session = request.getSession();
        LOG.debug("Command starts");
        CatalogueService catalogueService = new CatalogueService();
        String forward = Path.PAGE_GOOD;
        Catalogue catalogue = catalogueService.getDao().read();
        if (catalogue == null) {
            forward = Path.PAGE_ERROR_PAGE;
        }

        session.setAttribute("catalogue", catalogue);
        LOG.debug("Set the session attribute: catalogue --> " + catalogue);

        LOG.debug("Command finished");
        return forward;
    }
}
