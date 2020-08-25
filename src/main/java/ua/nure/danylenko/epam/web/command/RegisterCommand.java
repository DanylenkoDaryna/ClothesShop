package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.DBManager;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("RegisterCommand starts");

        HttpSession session = request.getSession();

        // obtain user`s info from a request
        String firstName = request.getParameter("first name");
        String lastName = request.getParameter("last name");
        String country = request.getParameter("country");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telephone");

        DB_LOG.info("Request parameter: first name, last name country" +
                " birthday email password telephone --> " + firstName + " "
                + lastName + " "
                + country + " "
                + birthday + " "
                + email + " "
                + password + " "
                + telephone);


        if (password == null || email == null || password.isEmpty() || email.isEmpty()) {
            throw new AppException("email/password cannot be empty");
        }

        DBManager manager = DBManager.getInstance();
//        User user = manager.findUserByLogin(login);
//        DB_LOG.info("Found in DB: user --> " + user);
//
//        if (user == null) {
//            throw new AppException("Cannot find user with such login");
//        }else if(!password.equals(user.getPassword())){
//            throw new AppException("Incorrect password. Please try again ");
//        }
//
//        Role userRole = Role.getRole(user);
//        WEB_LOG.info("userRole --> " + userRole);
//
//        String forward = Path.PAGE_GOOD;
//
//        if (userRole == Role.ADMIN) {
//            //COMMAND_UPDATING_UPO - adding, removing, editing users, products, orders
//            forward = Path.COMMAND_UPDATING_UPO;
//        }

//        if (userRole == Role.CLIENT) {
//            forward = Path.COMMAND_CABINET_ORDERS;
//        }

        String forward = Path.COMMAND_CABINET_ORDERS;
//        session.setAttribute("user", user);
//        WEB_LOG.info("Set the session attribute: user --> " + user);
//
//        session.setAttribute("userRole", userRole);
//        WEB_LOG.info("Set the session attribute: userRole --> " + userRole);
//
//        WEB_LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        WEB_LOG.info("Command finished");
        return forward;
    }
}
