package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The NoCommand class extends Command and provides command if some command can not be found or not existed
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class NoCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        WEB_LOG.info("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        WEB_LOG.info("Set the request attribute: errorMessage --> " + errorMessage);

        WEB_LOG.info("Command finished");
        return Path.PAGE_ERROR_PAGE;
    }

}