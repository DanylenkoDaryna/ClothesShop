<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%!private String pageJspName="/currentItem.jsp";%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <style>
        <%@include file="/css/bootstrap-grid.min.css"%>
        <%@include file="/css/bootstrap-reboot.min.css"%>
        <%@include file="/css/bootstrap.min.css"%>
        <%@include file="/css/main.css"%>
        <%@include file="/css/styles.css"%>
        <%@include file="/css/fontawesome.min.css"%>
        <%@include file="/font-awesome-4.7.0/css/font-awesome.min.css"%>
    </style>
    <title>${sessionScope.ItemsContainer2.get(0)}</title>
    <style>
        .btn {
            background: coral; /* Синий цвет фона */
            color: black; /* Белые буквы */
            font-size: 9pt; /* Размер шрифта в пунктах */
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jspf/hat.jspf"%>
<main>
    <div class="container">
        <section class="text-center mb-4">
            <div class="row wow fadeIn">
                <h1>${sessionScope.ItemsContainer2.get(0)}</h1>
                <div class="row">
                    <div class="col-lg-6 col-md-6 ">
                        <c:forEach items="${sessionScope.ItemsContainer2}" var="product">
                            <c:forEach items="${product.getImages()}" var="img">
                                <img src="img/${img}.jpg" class="img-fluid" alt="clothes img">
                            </c:forEach>
                        </c:forEach>

                    </div>
                    <div class="col-lg-6 col-md-6 ">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Product description</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="active">
                                <td>Brand</td><td>${sessionScope.items.get(0).getBrand()}</td>
                            </tr>
                            <tr class="danger">
                                <td>Price</td><td>${sessionScope.items.get(0).getPrice()} $</td>
                            </tr>
                            <tr class="active">
                                <td>Available items</td>
                                <td>
                                <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                    ${ItemProduct.getAvailable()}<t>
                                </c:forEach>
                                </td>
                            </tr>
                            <tr class="success">
                                <td>Available size</td>
                                <td>
                                    <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                    ${ItemProduct.getBodySize()}<t>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="info">
                                <td>Colours</td>
                                <td>
                                    <c:forEach items="${sessionScope.ItemsContainer2}" var="ItemProduct">
                                        ${ItemProduct.getColour()}
                                        <t>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="warning">
                                <td>Materials</td>
                                <td>
                                    <c:forEach items="${sessionScope.ItemsContainer2.get(0).getMaterials()}" var="material">
                                        ${material.toString()}<t>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="active">
                                <td>Release Date</td>
                                <td> ${sessionScope.items.get(0).getReleaseDate()}</td>
                            </tr>
                            </tbody>
                        </table>
                        <form action="basketServlet" method="post" >
                            <input title="page to return" hidden name="page" value="/currentItem.jsp">
                            <button type="submit" name="ClothesIdToBasket"
                                    value="${sessionScope.items.get(0).getId()}"
                                    class="btn" >To Basket</button>
                        </form>
                    </div>
                </div>
            </div>

        </section>
    </div>
</main>
<!-- basket -->
<%@ include file="/WEB-INF/jspf/basket.jspf"%>
<!-- footer -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="./js/jquery-3.4.1.min.js"%>
    <%@include file="./js/popper.min.js"%>
    <%@include file="./js/bootstrap.bundle.min.js"%>
    <%@include file="./js/bootstrap.min.js"%>
</script>

</body>
</html>


