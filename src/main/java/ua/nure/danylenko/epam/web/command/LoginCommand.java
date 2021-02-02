package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.Role;
import ua.nure.danylenko.epam.db.dao.UserDao;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.db.service.UserService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger DB_LOG = Logger.getLogger("jdbc");
    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, AppException {
        WEB_LOG.info("LoginCommand starts");
        String forward = Path.PAGE_GOOD;
        HttpSession session = request.getSession();

        // obtain login and password from a request

        String login = request.getParameter("login");
        DB_LOG.info("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new AppException("Login/password cannot be empty");
        }

        //DBManager manager = DBManager.getInstance();
        //User user = manager.findUserByLogin(login);
        UserService userService= new UserService();
        User user = userService.getDao().findUserByLogin(login);
        DB_LOG.info("Found in DB: user --> " + user.getFirstName());

//        if (user == null) {
//            throw new AppException("Cannot find user with such login");
//        }else
        if(!password.equals(user.getPassword())){

            throw new AppException("Incorrect password. Please try again ");
        }

        Role userRole = Role.getRole(user);
        WEB_LOG.info("userRole --> " + userRole);


        session.setAttribute("sessionUser", user);

        if (userRole == Role.ADMIN) {
            //COMMAND_UPDATING_UPO - adding, removing, editing users, products, orders
            try {
                forward = Path.PAGE_ADMIN_CABINET;
                WEB_LOG.info("Set the session attribute: adminUser --> " + user);
                UserDao userDao = new UserDao();
                ArrayList<User> listOfUsers = (ArrayList<User>) userDao.getAllUsers();
                listOfUsers.remove(user);
                session.setAttribute("listOfUsers", listOfUsers);
            }catch (AppException ae){
                WEB_LOG.error(ae.getMessage());
            }
        }

        if (userRole == Role.CLIENT) {
            forward = Path.PAGE_PERSONAL_CABINET;
            WEB_LOG.info("Set the session attribute: clientUser --> " + user);
        }



        session.setAttribute("userRole", userRole);
        WEB_LOG.info("Set the session attribute: userRole --> " + userRole);

        WEB_LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        WEB_LOG.info("LoginCommand finished");
        return forward;
    }

}