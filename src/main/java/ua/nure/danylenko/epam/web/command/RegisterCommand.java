package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.db.service.UserService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
        SimpleDateFormat formatter = new SimpleDateFormat();
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
        WEB_LOG.info(birthday);

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

        User newUser=new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setLogin(email);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setTelephone(telephone);
        newUser.setBirthday(birthday);
        newUser.setCountry(country);
        UserService userService=new UserService();
        userService.getDao().create(newUser);

        String forward = Path.COMMAND_CABINET_ORDERS;
        session.setAttribute("sessionUser", newUser);
        WEB_LOG.info("Set the session attribute: sessionUser --> " + newUser);
//
//        session.setAttribute("userRole", userRole);
//        WEB_LOG.info("Set the session attribute: userRole --> " + userRole);
//
//        WEB_LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        WEB_LOG.info("Command finished");
        return forward;
    }
}
