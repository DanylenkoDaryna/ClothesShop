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
<%! String pageJspName="/ordering.jsp";%>
<%! int averageCost=45;%>
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
                                LIST OF PRODUCTS
                            </h4>
                        </td>
                    </tr>
                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                        <c:forEach items="${sessionScope.itemsInBasket.getBasketElements()}" var="elementToOrder">
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
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Mastercard" value="Mastercard">
                                <label class="form-check-label" for="Mastercard">
                                    <i class="fab fa-cc-mastercard"></i>
                                    Mastercard
                                </label>
                            </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Visa" value="Mastercard" >
                                    <label class="form-check-label" for="Visa">
                                        <i class="fab fa-cc-visa"></i>
                                        Visa
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                       id="Cash" value="Mastercard" >
                                    <label class="form-check-label" for="Cash">
                                        <i class="fas fa-dollar-sign"></i>
                                        Cash
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Bitcoin" value="Bitcoin" >
                                    <label class="form-check-label" for="Bitcoin">
                                        <i class="fab fa-bitcoin"></i>
                                        Bitcoin
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Google-pay" value="Google-pay" >
                                    <label class="form-check-label" for="Google-pay">
                                        <i class="fab fa-google-pay"></i>
                                        Google-pay
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Paypal" value="Paypal" >
                                    <label class="form-check-label" for="Paypal">
                                        <i class="fab fa-cc-paypal"></i>
                                        Paypal
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Apple-pay" value="Apple-pay" >
                                    <label class="form-check-label" for="Apple-pay">
                                        <i class="fab fa-apple-pay"></i>
                                        Apple-pay
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="chosenPayment"
                                           id="Amazon-pay" value="Amazon-pay" >
                                    <label class="form-check-label" for="Amazon-pay">
                                        <i class="fab fa-amazon-pay"></i>
                                        Amazon-pay
                                    </label>
                                </div>
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
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="chosenDelivery"
                                       id="Nova Poshta" value="Nova Poshta" onclick="enableSubmit()">
                                <label class="form-check-label" for="Nova Poshta">
                                    <i class="fas fa-truck-loading"></i>
                                    Nova Poshta
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="chosenDelivery"
                                       id="Ukrposhta" value="Ukrposhta" onclick="enableSubmit()">
                                <label class="form-check-label" for="Ukrposhta">
                                    <i class="fas fa-truck-loading"></i>
                                    Ukrposhta
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="chosenDelivery"
                                       id="Self-pickup" value="Self-pickup" onclick="enableSubmit()">
                                <label class="form-check-label" for="Self-pickup">
                                    <i class="fas fa-car-side"></i>
                                    Self-pickup
                                </label>
                            </div>
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

                            <h4>${sessionScope.itemsInBasket.sumCosts()}$</h4>
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
                                <button type="submit" disabled id="orderingButton">
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
<script>

    function enableSubmit(){
        var orderingButton=document.getElementById("orderingButton");
        orderingButton.disabled=false;
    }

</script>
<%--==========================================================================--%>

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
