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

/**
 * The DeleteAccountCommand class extends Command and provides deleting personal cabinet by user
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class DeleteAccountCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("DeleteAccountCommand starts");

        HttpSession session = request.getSession();
        User sessionUser = (User)session.getAttribute("sessionUser");
        long userId;

        if (sessionUser != null) {
            //треба якось перевірити щоб не нулі
            userId = sessionUser.getId();
            DB_LOG.info("Parameter for deleting from DB: user id="+ sessionUser.getId());

        }else {
            throw new AppException("sessionUser is null");

        }

        UserService userService=new UserService();
        userService.getDao().delete(userId);

        String forward = Path.PAGE_GOOD;
        session.setAttribute("sessionUser", null);
        session.setAttribute("userRole", null);

        WEB_LOG.info("Set the session attribute: clientUser and userRole --> null");


            WEB_LOG.info("DeleteAccountCommand finished");
        return forward;
    }
}
