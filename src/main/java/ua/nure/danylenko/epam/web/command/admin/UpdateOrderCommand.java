package ua.nure.danylenko.epam.web.command.admin;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Order;
import ua.nure.danylenko.epam.db.entity.OrderStatus;
import ua.nure.danylenko.epam.db.service.ItemsService;
import ua.nure.danylenko.epam.db.service.OrderService;
import ua.nure.danylenko.epam.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The UpdateOrderCommand class extends Command and provides changing user status by admin
 * @version 1.0 01/04/2021
 * @author Daryna Danylenko (delibertato)
 */
public class UpdateOrderCommand extends Command {
    private static final long serialVersionUID = 1863978254689586513L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        WEB_LOG.info("UpdateOrderCommand starts");
        HttpSession session = request.getSession();
        List<Order> orders=(List<Order>)session.getAttribute("listOfOrders");

        String forward = Path.PAGE_ADMIN_CABINET;
        long orderToUpdate = Long.parseLong(request.getParameter("orderToUpdate"));
        String updatedStatus= request.getParameter("OrderStatus");
        WEB_LOG.info("OrderStatus = " + updatedStatus);
        OrderStatus status = OrderStatus.valueOf(updatedStatus);
        Order currentOrder = new Order();
        for(Order o1: orders){
            if(o1.getOrderNumber()==orderToUpdate){
                currentOrder=o1;
                currentOrder.setOrderStatus(status);
            }
        }
        OrderService orderService = new OrderService();
        orderService.getDao().updateStatus(status, orderToUpdate);

        switch (status){
            case CANCELED:{
                WEB_LOG.info("Order CANCELED");
                break;
            }case PAID:{
               // orders.remove(currentOrder);
                ItemsService itemsService = new ItemsService();
                itemsService.getDao().deleteProducts(currentOrder.getOrderItems());

                WEB_LOG.info("Order PAID");
                break;
            }case REGISTERED:{
                WEB_LOG.info("Order REGISTERED");
                break;
            }
            default: forward=Path.PAGE_ERROR_PAGE;
        }


        session.setAttribute("listOfOrders", orders);
        WEB_LOG.info("UpdateOrderCommand finished");
        return forward;
    }


}
