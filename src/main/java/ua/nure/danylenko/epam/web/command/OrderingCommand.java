package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderingCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("OrderingCommand starts");

        String forward = Path.PAGE_PERSONAL_CABINET;

        WEB_LOG.info("OrderingCommand finished");
        return forward;
    }
}
