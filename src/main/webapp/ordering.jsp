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
<%!private String pageJspName="/ordering.jsp";%>
<html lang="en">

<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>

<main>
    <div class="container-fluid">
        <section class="text-center">
            <section class="table mb-lg-2">
                <table class="table" id="cabinetTable">
                    <thead>
                    <h3>Order Description</h3>
                    </thead>
                    <tbody>

                    <tr class="active">
                        <td colspan="2">
                            <h3>
                                <i class="fas fa-shopping-bag"></i>
                                LIST OF PRODUCTS</h3>
                        </td>
                    </tr>
                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                        <c:forEach items="${sessionScope.itemsInBasket}" var="itemToOrder">
                            <tr>
                                <td>
                                        ${itemToOrder.getProductName()},
                                        ${itemToOrder.getBrand()},
                                        ${itemToOrder.getPrice()}

                                </td>
                                <td>
                                    +-
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
                            <h3>
                                <i class="fas fa-wallet"></i>
                                TOTAL COST
                            </h3>
                        </td>
                        <td>
                            <h3>0$</h3>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h3>METHOD OF PAYMENT</h3>
                        </td>
                        <td>
                            <i class="fab fa-google-pay"></i>
                            <i class="fab fa-cc-mastercard"></i>
                            <i class="fab fa-cc-paypal"></i>
                            <i class="fab fa-cc-visa"></i>
                            <i class="fab fa-bitcoin"></i>
                            <i class="fab fa-apple-pay"></i>
                            <i class="fab fa-amazon-pay"></i>
                            <select id="payment" name="type_of_payment">
                                <option selected value="Mastercard" >Mastercard</option>
                                <option value="LiqPay">LiqPay</option>
                                <option value="Cash">
                                    <i class="fas fa-money-bill-wave"></i>
                                    Cash
                                </option>

                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h3>METHOD OF DELIVERY</h3>
                        </td>
                        <td>
                            <select id="delivery" name="type_of_delivery">
                                <option selected value="Ukraine" >Нова Пошта</option>
                                <option value="Great Britain">Укрпошта</option>
                                <option value="Ukraine">Self-pickup</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <h3>PRICE OF DELIVERY</h3>
                        </td>
                        <td>
                            <h3>
                                <i class="fas fa-hryvnia"></i>
                                0$
                                <i class="fas fa-dollar-sign"></i>
                            </h3>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form action="" method="post">
                                <input title="" hidden name="" >
                                <button type="submit">
                                    <i class="fas fa-angle-left"></i>
                                    Cancel</button>
                            </form>
                        </td>
                        <td>
                            <form action="" method="post">
                                <input title="" hidden name="" >
                                <button type="submit">
                                    <i class="fas fa-angle-right"></i>
                                    Continue
                                    <i class="fas fa-handshake"></i>
                                </button>
                            </form>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </section>
        </section>
    </div>


    <%--===========================================================================
     This is the SCRIPT, containing the main functions of the page.
    ===========================================================================--%>

    <%--==========================================================================--%>

</main>
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
