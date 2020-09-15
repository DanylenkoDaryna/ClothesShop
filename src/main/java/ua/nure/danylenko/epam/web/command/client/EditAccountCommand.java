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
import java.time.LocalDate;

public class EditAccountCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("EditAccountCommand starts");

        HttpSession session = request.getSession();
        User sessionUser = (User)session.getAttribute("sessionUser");


        if (sessionUser != null) {

            sessionUser.setFirstName(request.getParameter("first name"));
            sessionUser.setLastName(request.getParameter("last name"));
            sessionUser.setLogin(request.getParameter("email"));
            sessionUser.setPassword(request.getParameter("password2"));
            sessionUser.setEmail(request.getParameter("email"));
            sessionUser.setTelephone(request.getParameter("telephone"));
            sessionUser.setBirthday(LocalDate.parse(request.getParameter("birthday")));
            sessionUser.setCountry(request.getParameter("country"));
            DB_LOG.info("Information for updating user information - " + sessionUser.getLastName());

        }else {
            WEB_LOG.error("sessionUser is null");
            throw new AppException("sessionUser is null");
        }

        UserService userService=new UserService();
        userService.getDao().update(sessionUser);

        String forward = Path.PAGE_PERSONAL_CABINET;
        session.setAttribute("sessionUser", sessionUser);

        WEB_LOG.info("Updated the session attribute for client: sessionUser ");
        WEB_LOG.info("EditAccountCommand finished");

        return forward;

    }
}
