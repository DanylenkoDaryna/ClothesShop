package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.Basket;
import ua.nure.danylenko.epam.db.entity.Order;
import ua.nure.danylenko.epam.db.entity.OrderStatus;
import ua.nure.danylenko.epam.db.entity.User;
import ua.nure.danylenko.epam.db.service.OrderService;
import ua.nure.danylenko.epam.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OrderingCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger WEB_LOG = Logger.getLogger("servlets");


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        WEB_LOG.info("OrderingCommand starts");
        String forward = Path.PAGE_REGISTERED_ORDER;
        HttpSession session = request.getSession();
        String paymentType = request.getParameter("chosenPayment");
        String deliveryType = request.getParameter("chosenDelivery");
        User client =(User)session.getAttribute("sessionUser");

        Basket basket = (Basket)session.getAttribute("Basket");
        if (basket.getBasketElements().isEmpty()){
            WEB_LOG.error("order cannot be empty");
            return Path.PAGE_ERROR_PAGE;
        }

        Order clientOrder = new Order();
        clientOrder.setOrderStatus(OrderStatus.REGISTERED);
        clientOrder.setPaymentType(paymentType);
        clientOrder.setDeliveryType(deliveryType);
        clientOrder.setTotalAmount(basket.sumCosts());
        clientOrder.setUserId(client.getId());
        //clientOrder.setProductId(basket.getBasketElements());

        OrderService orderService = new OrderService();
        orderService.getDao().create(clientOrder);


        if (session.getAttribute("OrdersList")==null) {
            List<Order> ordersList = new LinkedList<>();
            ordersList.add(clientOrder);
            session.setAttribute("OrdersList", ordersList);

        } else {
            List<Order>  ordersList = (List<Order> ) session.getAttribute("OrdersList");
            ordersList.add(clientOrder);
            session.setAttribute("OrdersList", ordersList);

        }

        WEB_LOG.info("OrderingCommand finished");
        return forward;
    }
}
