package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.AccountStatus;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.db.service.UserService;
import ua.nure.danylenko.epam.exception.AppException;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The UpdatingUPOCommand class extends Command and provides locking/unlocking users
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class UpdatingUPOCommand extends Command {

    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        WEB_LOG.info("UpdatingUPOCommand starts");
        HttpSession session = request.getSession();
        ArrayList<User> users=(ArrayList<User>)session.getAttribute("listOfUsers");

        String forward = Path.PAGE_ADMIN_CABINET;
        String command = request.getParameter("commandType");

        switch (command){
            case "lockingUser":{
                lockUser(request,users);
                break;
            }case "unlockingUser":{
                unlockUser(request,users);
                break;
            }
            default: forward=Path.PAGE_ERROR_PAGE;
        }

        session.setAttribute("listOfUsers", users);
        WEB_LOG.info("UpdatingUPOCommand finished");
        return forward;
    }


    /**
     * Method for extracting sizes from incoming array of Strings to particular products
     * @param req
     * @param users
     * @return
     */
    private ArrayList<User> lockUser(HttpServletRequest req, ArrayList<User> users) {

        WEB_LOG.info("lock User");
        int idToLock = Integer.parseInt(req.getParameter("idToLock"));
        UserService userService = new UserService();
        userService.getDao().lockUser(idToLock);
        return updateLockedUser(users,idToLock);
    }

    /**
     * Method for extracting sizes from incoming array of Strings to particular products
     * @param users
     * @param idToLock
     * @return
     */
    private ArrayList<User> updateLockedUser(ArrayList<User> users, int idToLock){

        for(User user:users){
            if(user.getId()==idToLock){
                user.setAccountStatus(AccountStatus.LOCKED);
            }
        }
        return users;
    }

    /**
     * Method for extracting sizes from incoming array of Strings to particular products
     * @param req
     * @param users
     * @return
     */
    private ArrayList<User> unlockUser(HttpServletRequest req, ArrayList<User> users) {

        WEB_LOG.info("unlock User");
        int idToUnlock = Integer.parseInt(req.getParameter("idToUnlock"));
        UserService userService = new UserService();
        userService.getDao().unlockUser(idToUnlock);
        return updateUnlockedUser(users,idToUnlock);
    }

    /**
     * Method for extracting sizes from incoming array of Strings to particular products
     * @param users
     * @param idToUnlock
     * @return
     */
    private ArrayList<User> updateUnlockedUser(ArrayList<User> users, int idToUnlock){

        for(User user:users){
            if(user.getId()==idToUnlock){
                user.setAccountStatus(AccountStatus.UNLOCKED);
            }
        }
        return users;
    }

}
