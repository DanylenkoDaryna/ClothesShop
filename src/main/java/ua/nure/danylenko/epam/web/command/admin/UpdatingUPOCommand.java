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

public class UpdatingUPOCommand extends Command {

    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        WEB_LOG.info("UpdatingUPOCommand starts");
        HttpSession session = request.getSession();
        ArrayList<User> users=(ArrayList<User>)session.getAttribute("listOfUsers");

//        List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans();
//        LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);
//
//        Collections.sort(userOrderBeanList, compareById);
//
//        // put user order beans list to request
//        request.setAttribute("userOrderBeanList", userOrderBeanList);
//        LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);


        String forward = Path.PAGE_ADMIN_CABINET;
        String command = request.getParameter("commandType");

        switch (command){
            case "lockingUser":{
                users=lockUser(request,users);
                break;
            }case "unlockingUser":{
                //users=unlockUser(request,users);
                break;
            }
            default: forward=Path.PAGE_ERROR_PAGE;
        }

        session.setAttribute("listOfUsers", users);
        WEB_LOG.info("UpdatingUPOCommand finished");
        return forward;
    }


    private ArrayList<User> lockUser(HttpServletRequest req, ArrayList<User> users) {
        WEB_LOG.info("lock User");
        int idToLock = Integer.parseInt(req.getParameter("idToLock"));
        UserService userService = new UserService();
        userService.getDao().lockUser(idToLock);
        return updateLockedUser(users,idToLock);
    }

    private ArrayList<User> updateLockedUser(ArrayList<User> users, int idToLock){
        for(User user:users){
            if(user.getId()==idToLock){
                user.setAccountStatus(AccountStatus.LOCKED);
            }
        }
        return users;
    }

//    private ArrayList<User> unlockUser(HttpServletRequest req, ArrayList<User> users) {
//        WEB_LOG.info("lock User ");
//        int idToLock = Integer.parseInt(req.getParameter("idToLock"));
//        UserService userService = new UserService();
//        userService.getDao().unlockUser();
//        return updateLockedUser(users,idToLock);
//    }
//
//    private ArrayList<User> updateUnlockedUser(ArrayList<User> users, int idToLock){
//        for(User user:users){
//            if(user.getId()==idToLock){
//                user.setAccountStatus(AccountStatus.LOCKED);
//            }
//        }
//        return users;
//    }

     /* Serializable comparator used with TreeMap container. When the servlet
 * container tries to serialize the session it may fail because the session
 * can contain TreeMap object with not serializable comparator.
 *
 * @author D.Kolesnikov
 *
 */

    /* private static class CompareById implements Comparator<UserOrderBean>, Serializable {
        private static final long serialVersionUID = -1573481565177573283L;

        public int compare(UserOrderBean bean1, UserOrderBean bean2) {
            if (bean1.getId() > bean2.getId()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static Comparator<UserOrderBean> compareById = new CompareById();
    */
}
