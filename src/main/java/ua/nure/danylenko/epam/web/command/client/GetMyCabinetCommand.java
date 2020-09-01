package ua.nure.danylenko.epam.web.command.client;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.db.service.UserService;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GetMyCabinetCommand extends Command {

    private static final long serialVersionUID = 7732286214029478505L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");
    private static final Logger APP_LOG = Logger.getLogger("armadio");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        WEB_LOG.info("GetMyCabinetCommand starts");
        APP_LOG.debug("GetMyCabinetCommand starts in app");


        UserService userService=new UserService();
        User newUser = (User)userService.getDao().read();
        //String forward = Path.COMMAND_CABINET_ORDERS;
        HttpSession session = request.getSession();
        //session.setAttribute("clientUser", newUser);

        return Path.PAGE_PERSONAL_CABINET;

    }
}
