package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.DBManager;
import ua.nure.danylenko.epam.db.Role;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        LOG.info("LoginCommand starts");

        HttpSession session = request.getSession();

        // obtain login and password from a request

        String login = request.getParameter("login");
        LOG.info("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login/password cannot be empty");
        }

        DBManager manager = DBManager.getInstance();
        User user = manager.findUserByLogin(login);
        LOG.info("Found in DB: user --> " + user);

        if (user == null) {
            throw new AppException("Cannot find user with such login");
        }else if(!password.equals(user.getPassword())){
            throw new AppException("Incorrect password. Please try again ");
        }

        Role userRole = Role.getRole(user);
        LOG.info("userRole --> " + userRole);

        String forward = Path.PAGE_GOOD;

        if (userRole == Role.ADMIN) {
            //COMMAND_UPDATING_UPO - adding, removing, editing users, products, orders
            forward = Path.COMMAND_UPDATING_UPO;
        }

        if (userRole == Role.CLIENT) {
            forward = Path.COMMAND_CABINET_ORDERS;
        }

        session.setAttribute("user", user);
        LOG.debug("Set the session attribute: user --> " + user);

        session.setAttribute("userRole", userRole);
        LOG.debug("Set the session attribute: userRole --> " + userRole);

        LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        LOG.debug("Command finished");
        return forward;
    }

}