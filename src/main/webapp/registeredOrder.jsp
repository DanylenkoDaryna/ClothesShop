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
<%! String pageJspName="/registeredOrder.jsp";%>
<html lang="en">

<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>

<h4>THANK YOU, YOUR ORDER WAS REGISTERED!</h4>


<%--<c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">--%>
    <%--<c:forEach items="${sessionScope.itemsInBasket}" var="elementToOrder">--%>
        <%--<tr>--%>
            <%--<td>--%>
                <%--<h4>--%>
                        <%--${elementToOrder.getBasketItem().getItemName()},--%>
                        <%--${elementToOrder.getBasketItem().getBrand()},--%>
                        <%--${elementToOrder.getBasketItem().getPrice()}--%>
                        <%--${elementToOrder.getBasketItem().getColour()}--%>
                        <%--${elementToOrder.getBasketProduct().getBodySize().toString()}--%>
                <%--</h4>--%>
            <%--</td>--%>
            <%--<td>--%>

                <%--<div class="form-group" id="chooseNum">--%>
                    <%--<h4>--%>
                        <%--CHOOSE THE NUMBER OF PRODUCTS--%>
                    <%--</h4>--%>
                    <%--<input type="number" class="form-control-md" name="NumProds"--%>
                           <%--placeholder="1" min=1 max="${elementToOrder.getBasketProduct().getAvailable()}">--%>
                <%--</div>--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
<%--</c:if>--%>
<%--<c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">--%>
    <%--<tr>--%>
        <%--<td colspan="2">--%>
            <%--<h4>You can not delete anything, cuz your account is locked. </h4>--%>
        <%--</td>--%>
    <%--</tr>--%>
<%--</c:if>--%>

<button type="button" value="Go back to cabinet">
    <a href="clientCabinetPage.jsp">
        <i class="fas fa-sign-out-alt"></i>
        Go back to cabinet
    </a>
    </button>

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

