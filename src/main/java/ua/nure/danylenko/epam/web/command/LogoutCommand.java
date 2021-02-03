package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        WEB_LOG.info("LogoutCommand starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        WEB_LOG.info("LogoutCommand finished");
        return Path.PAGE_LOGIN;
    }

}