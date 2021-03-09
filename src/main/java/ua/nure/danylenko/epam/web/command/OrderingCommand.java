package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.Path;
import ua.nure.danylenko.epam.db.entity.*;
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
        String[] amountOfPurchases = request.getParameterValues("NumProds");
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
        clientOrder.setUserId(client.getId());

        List<OrderItem> purchases = new LinkedList<>();

        List<BasketElement> basketElements = basket.getBasketElements();
        int i=0;
        for(BasketElement be: basketElements){
            OrderItem temp = new OrderItem();
            temp.setProductId(be.getBasketProduct().getId());
            temp.setName(be.getBasketItem().getItemName());
            temp.setBrand(be.getBasketItem().getBrand());
            temp.setProductSize(be.getBasketProduct().getBodySize());
            temp.setColour(be.getBasketItem().getColour());
            temp.setAmount(Integer.parseInt(amountOfPurchases[i]));
            WEB_LOG.info("amount = "+ amountOfPurchases[i]);
            purchases.add(temp);
            i+=1;
        }

        double res = clientOrder.sumTotalAmount(purchases, basket.getBasketElements());
        clientOrder.setTotalAmount(res);
        WEB_LOG.info("purchases size =" + purchases.size() );
        WEB_LOG.info("BasketElements size =" + basket.getBasketElements().size() );
        WEB_LOG.info("sumTotalAmount =" + res );

        clientOrder.setOrderItems(purchases);
        OrderService orderService = new OrderService();
        clientOrder = orderService.getDao().createOrder(clientOrder);


        if (session.getAttribute("orderList")==null) {
            List<Order> ordersList = new LinkedList<>();
            ordersList.add(clientOrder);
            session.setAttribute("orderList", ordersList);

        } else {
            List<Order>  orderList = (List<Order>)session.getAttribute("orderList");
            orderList.add(clientOrder);
            session.setAttribute("orderList", orderList);

        }

        session.setAttribute("totalAmount", 0);
        session.setAttribute("Basket", new Basket());
        WEB_LOG.info("OrderingCommand finished");
        return forward;
    }
}
