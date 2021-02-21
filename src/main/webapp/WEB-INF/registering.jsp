<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 05.02.2021
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 08.08.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%! String pageJspName="/registering.jsp";%>
<html lang="en">

<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>

<div class="container-fluid">
    <%--<section class="text-center">--%>
    <section class="table mb-lg-2">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="ordering"/>

            <table class="table" id="cabinetTable">
                <tbody>
                <tr class="active">
                    <td colspan="2">
                        <h4>
                            <i class="fas fa-shopping-bag"></i>
                            THANK YOU, YOUR ORDER VAS REGISTERED!
                        </h4>
                    </td>
                </tr>
                <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                    <c:forEach items="${sessionScope.itemsInBasket}" var="elementToOrder">
                        <tr>
                            <td>
                                <h4>
                                        ${elementToOrder.getBasketItem().getItemName()},
                                        ${elementToOrder.getBasketItem().getBrand()},
                                        ${elementToOrder.getBasketItem().getPrice()}
                                        ${elementToOrder.getBasketItem().getColour()}
                                        ${elementToOrder.getBasketProduct().getBodySize().toString()}
                                </h4>
                            </td>
                            <td>

                                <div class="form-group" id="chooseNum">
                                    <h4>
                                        CHOOSE THE NUMBER OF PRODUCTS
                                    </h4>
                                    <input type="number" class="form-control-md" name="NumProds"
                                           placeholder="1" min=1 max="${elementToOrder.getBasketProduct().getAvailable()}">
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                    <tr>
                        <td colspan="2">
                            <h4>You can not delete anything, cuz your account is locked. </h4>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <h4>
                            <i class="fas fa-comment-dollar"></i>
                            CHOOSE THE METHOD OF PAYMENT
                        </h4>
                    </td>
                    <td>
                        <div class="dropdown show" >
                            <a class="btn btn-secondary dropdown-toggle btn-info" href="#" role="button"
                               id="payment" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                                Type of payment
                            </a>
                            <div class="dropdown-menu"  aria-labelledby="dropdownSizes">
                                <a class="dropdown-item"  href="#">
                                    <i class="fab fa-cc-mastercard"></i>
                                    Mastercard
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-cc-visa"></i>
                                    Visa
                                </a>
                                <a class="dropdown-item" href="#">
                                    LiqPay
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-dollar-sign"></i>
                                    Cash
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-bitcoin"></i>
                                    bitcoin
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-google-pay"></i>
                                    google-pay
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-cc-paypal"></i>
                                    paypal
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-apple-pay"></i>
                                    apple-pay
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fab fa-amazon-pay"></i>
                                    amazon-pay
                                </a>

                            </div>
                        </div>

                        <%--<select id="payment2" name="type_of_payment">--%>
                        <%--<option selected value="Mastercard" >Mastercard</option>--%>
                        <%--<option value="LiqPay">LiqPay</option>--%>
                        <%--<option value="Cash">--%>
                        <%--<i class="fas fa-money-bill-wave"></i>--%>
                        <%--Cash--%>
                        <%--</option>--%>

                        <%--</select>--%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <i class="fas fa-truck"></i>
                            CHOOSE THE METHOD OF DELIVERY
                        </h4>
                    </td>
                    <td>

                        <div class="dropdown show">
                            <a class="btn btn-secondary dropdown-toggle btn-info" href="#" role="button" id="delivery" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Type of delivery
                            </a>
                            <div class="dropdown-menu" aria-labelledby="dropdownSizes">
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-truck-loading"></i>
                                    Nova Poshta
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-truck-loading"></i>
                                    Ukrposhta
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-car-side"></i>
                                    Self-pickup
                                </a>
                            </div>
                        </div>


                        <%--<select id="delivery" name="type_of_delivery">--%>
                        <%--<option selected value="Ukraine" >Nova Poshta</option>--%>
                        <%--<option value="Great Britain">Ukrposhta</option>--%>
                        <%--<option value="Ukraine">Self-pickup</option>--%>
                        <%--</select>--%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>PRICE OF DELIVERY</h4>
                    </td>
                    <td>
                        <h4>
                            45
                            <i class="fas fa-hryvnia"></i>
                        </h4>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h4>
                            <i class="fas fa-wallet"></i>
                            TOTAL COST
                        </h4>
                    </td>
                    <td>
                        <h4>${(45+10)}$</h4>
                    </td>
                </tr>
                <tr>
                    <%--<td>--%>
                    <%--<form action="" method="post">--%>
                    <%--<input title="" hidden name="" >--%>
                    <%--<button type="submit">--%>
                    <%--<i class="fas fa-angle-left"></i>--%>
                    <%--Cancel</button>--%>
                    <%--</form>--%>
                    <%--</td>--%>
                    <td colspan="2">

                        <input title="" hidden name="" >
                        <button type="submit">
                            To order
                            <i class="fas fa-handshake"></i>
                        </button>

                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </section>

</div>


<%--===========================================================================
 This is the SCRIPT, containing the main functions of the page.
===========================================================================--%>

<%--==========================================================================--%>
<%-- FOOTER --%>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<%-- FOOTER --%>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="/js/jquery-3.4.1.min.js"%>
    <%@include file="/js/popper.min.js"%>
    <%@include file="/js/bootstrap.bundle.min.js"%>
    <%@include file="/js/bootstrap.min.js"%>
</script>

</body>
</html>

