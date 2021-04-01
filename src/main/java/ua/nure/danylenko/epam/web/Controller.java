package ua.nure.danylenko.epam.web;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;
import ua.nure.danylenko.epam.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Controller class extends HttpServlet implements manipulating with requested commands
 * @version 1.0 30/03/2021
 * @author Daryna Danylenko (delibertato)
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 2423353715955164816L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        WEB_LOG.info("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        WEB_LOG.info("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        WEB_LOG.info("Obtained command --> " + command);

        // execute command and get forward address
        String forward = Path.PAGE_ERROR_PAGE;
        try {
            forward = command.execute(request, response);
        } catch (AppException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        WEB_LOG.info("Forward address --> " + forward);

        WEB_LOG.info("Controller finished, now go to forward address --> " + forward);

        // go to forward
        request.getRequestDispatcher(forward).forward(request, response);
    }
}