<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 05.02.2020
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%!private String pageJspName="/filteredPage.jsp";%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">
<%@ include file="/WEB-INF/jspf/head.jspf" %>

</head>
<body>
<%@ include file="/WEB-INF/jspf/hat.jspf"%>
<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
        <a role="button" class="btn btn-outline-primary"
           href="controller?command=SimpleFilter&filtration=FromAToZ&page=filteredPage">AZ</a>
        <a role="button" class="btn btn-outline-secondary"
           href="controller?command=SimpleFilter&filtration=FromZToA&page=filteredPage">ZA</a>
        <a role="button" class="btn btn-outline-success"
           href="controller?command=SimpleFilter&filtration=LowPriceFirst&page=filteredPage">$ from max</a>
        <a role="button" class="btn btn-outline-danger"
           href="controller?command=SimpleFilter&filtration=HighPriceFirst&page=filteredPage">$ from min</a>
        <a role="button" class="btn btn-outline-warning"
           href="controller?command=SimpleFilter&filtration=oldFirst&page=filteredPage">Old collection</a>
        <a role="button" class="btn btn-outline-info"
           href="controller?command=SimpleFilter&filtration=newFirst&page=filteredPage">New collection</a>
    </nav>


    <main>
        <div class="row">
            <div class="col-lg-2 col-md-3 ">
                <section class="text-left">
                    <%@ include file="/WEB-INF/jspf/hardFilter.jspf" %>
                </section>
            </div>
            <div class="container">
                <section class="text-center">
                    <h1>Products</h1>
                    <div class="row wow fadeIn">
                        <c:forEach items="${sessionScope.filteredItems}" var="product1">
                            <div class="col-lg-3 col-md-6">
                                <div class="card">
                                    <div class="view overlay">
                                        <img class="card-img-top"
                                             src="img/${(product1.getContainer().get(0)).getImages().get(0)}.jpg" alt="clothes img">
                                        <a href="#">
                                            <div class="mask rgba-white-slight"></div>
                                        </a>
                                    </div>

                                    <div class="card-body text-center">
                                        <h5>
                                            <strong>
                                                <!--      request.setAttribute("itemContainer",product1.getContainer())
                                        ##<a href="./currentItem.jsp" class="dark-grey-text"> -->
                                                <a href="controller?command=ItemProducts&ItemId=${product1.getId()}" class="dark-grey-text">
                                                        ${product1}
                                                </a>
                                            </strong>
                                        </h5>
                                        <h6 class="font-weight-bold blue-text">
                                            <strong>${product1.getPrice()} $</strong>
                                        </h6>
                                        <h6>Release: ${product1.getReleaseDate()}</h6>
                                        <form action="basketServlet" method="post">
                                            <input title="page to return" hidden name="page" value="<%=pageJspName%>">
                                            <button type="submit" name="ClothesIdToBasket" value="${product1.getId()}"
                                                    class="btn btn-default btn-xs">To Basket</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </div>
        </div>
    </main>
</div>
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

