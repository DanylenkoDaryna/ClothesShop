<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%!private String pageJspName="/currentItem.jsp";%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <!-- для коректного відображення сторінки у старих версіях Інтернет Експлореру-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- для коректного відображення сторінки на мобільних пристроях-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">--%>


    <link href="css/all.css" rel="stylesheet"/>

    <style>
        <%@include file="/css/bootstrap-grid.min.css"%>
        <%@include file="/css/bootstrap-reboot.min.css"%>
        <%@include file="/css/bootstrap.min.css"%>
        <%@include file="/css/main.css"%>
        <%@include file="/css/styles.css"%>
    </style>

    <title>${sessionScope.ItemsContainer2.get(0)}</title>

</head>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>
    <div class="headerwrap">
        <section class="text-center mb-4">
            <h1>${sessionScope.ItemsContainer2.get(0)}</h1>
        </section>

        <div class="row">
            <div class="col-lg-5 col-md-5 ">
                <c:forEach items="${sessionScope.ItemsContainer2}" var="productCurr">
                    <img src="img/${productCurr.getImage()}" class="img-fluid" alt="Why God? why can`t I see that?">
                </c:forEach>
            </div>
            <div class="col-lg-6 col-md-6 ">
                <table class="table" id="current_item_table">
                    <tbody>
                    <tr>
                        <td colspan="2">
                            <i class="fas fa-tag"></i>
                            Product description
                            <i class="fab fa-shopify"></i>
                        </td>
                    </tr>
                    <tr class="active">
                        <td>Brand</td><td>${sessionScope.items.get(0).getBrand()}</td>
                    </tr>
                    <tr class="danger">
                        <td>Price</td>
                        <td>${sessionScope.items.get(0).getPrice()}
                        <i class="fas fa-dollar-sign"></i>
                        </td>
                    </tr>
                    <tr>
                        <td>Choose size</td>
                        <td>
                            <div class="dropdown show">
                                <a class="btn btn-secondary dropdown-toggle btn-info" href="#" role="button" id="dropdownSizes" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Available sizes
                                </a>

                                        <div class="dropdown-menu" aria-labelledby="dropdownSizes">
                                            <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                                <a class="dropdown-item" href="#">${ItemProduct.getBodySize()}</a>
                                                <%--<t>--%>
                                            </c:forEach>

                                        </div>
                                    </div>

                                </td>
                            </tr>
                            <tr>
                                <td>Choose colour</td>
                                <td>
                                    <div class="dropdown show">
                                        <a class="btn btn-secondary dropdown-toggle btn-info" href="#" role="button" id="dropdownColour" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            Available colours
                                        </a>

                                        <div class="dropdown-menu" aria-labelledby="dropdownColour">
                                            <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                                <a class="dropdown-item" href="#">${ItemProduct.getColour()}</a>
                                                <%--<t>--%>
                                            </c:forEach>

                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>Available items</td>
                                <td>
                                    <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                        ${ItemProduct.getAvailable()}<t>
                                    </c:forEach>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <i class="fas fa-tshirt"></i>
                                    Materials
                                </td>
                                <td>
                                    <c:forEach items="${sessionScope.items.get(0).getMaterials()}" var="material">
                                        ${material.toString()}<t>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="active">
                                <td>Release Date</td>
                                <td> ${sessionScope.items.get(0).getReleaseDate()}</td>
                            </tr>
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.sessionUser!=null}">
                                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                                        <form action="basketServlet" method="post" >
                                            <input title="page to return" hidden name="page" value="/currentItem.jsp">
                                            <button type="submit" name="ClothesIdToBasket" value="${sessionScope.items.get(0).getId()}"
                                                    class="btn" >
                                                <i class="fas fa-shopping-cart"></i>
                                                To Basket
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                                        You cannot order anything, cuz your account is locked.<br>
                                        For more information please contact our admin by armadio@gmail.com
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <form action="basketServlet" method="post" >
                                        <input title="page to return" hidden name="page" value="/currentItem.jsp">
                                        <button type="submit" name="ClothesIdToBasket" value="${sessionScope.items.get(0).getId()}"
                                                class="btn" >
                                            <i class="fas fa-shopping-cart"></i>
                                            To Basket
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${sessionScope.sessionUser!=null}">
                                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                                        <form action="wishlistServlet" method="post" >
                                            <input title="page to return" hidden name="page" value="<%=pageJspName%>">
                                            <button type="submit" name="ClothesIdToWishlist" value="${sessionScope.items.get(0).getId()}"
                                                    class="btn" >
                                                <i class="far fa-heart"></i>
                                                To Wishlist
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                                        You cannot order anything, cuz your account is locked.<br>
                                        For more information please contact our admin by armadio@gmail.com
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <form action="wishlistServlet" method="post" >
                                        <input title="page to return" hidden name="page" value="<%=pageJspName%>">
                                        <button type="submit" name="ClothesIdToWishlist" value="${sessionScope.items.get(0).getId()}"
                                                class="btn" >
                                            <i class="far fa-heart"></i>
                                            To Wishlist
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<!-- footer -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="js/jquery-3.4.1.min.js"%>
    <%@include file="js/popper.min.js"%>
    <%@include file="js/bootstrap.bundle.min.js"%>
    <%@include file="js/bootstrap.min.js"%>
</script>

</body>
</html>


