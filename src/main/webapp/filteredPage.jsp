<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 05.02.2020
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%!private String pageJspName="/filteredPage.jsp";%>
<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>


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

<section class="text-center">
    <h1>Products</h1>
</section>

<div id="headerwrap">

    <div class="row">

        <div id="filterProductsSection">
            <%@ include file="/WEB-INF/jspf/hardFilter.jspf" %>
        </div>

        <c:forEach items="${sessionScope.filteredItems}" var="product1">
            <div class="col-lg-3 col-md-3 col-sm-1 col-xs-1">
                <div class="card">
                    <div class="view overlay">
                        <img class="card-img-top"
                             src="img/${product1.getContainer().get(0).getImage()}" alt="clothes img">
                        <a href="controller?command=ItemProducts&ItemId=${product1.getId()}">
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
                        <c:choose>
                            <c:when test="${sessionScope.userRole.getName() eq 'admin'}">
                                <form action="controller" method="post">
                                    <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                    <input type="hidden" name="updateCatalogueType" value="deleteProduct"/>
                                    <input type="hidden" name="productToDelete" value="${product1.getId()}"/>
                                    <button type="submit" class="btn btn-default btn-xs">Delete</button>
                                </form>
                                <form action="controller" method="post">
                                    <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                    <button id="${product1.getId()}toEdit" type="submit" name="ClothesIdToBasket" value="Edit"
                                            class="btn btn-default btn-xs">Edit</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <!--Делать после нажатия кнопки неактивными, чтобы пользователю стало очевидным,
                                что он уже нажал на кнопку, и что форма отправляется
                                Чтобы сервер не загружался лишними запросами, и чтобы уменьшить вероятность какой-либо ошибки-->
                                <form action="basketServlet" method="post" >
                                    <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                    <button id="${product1.getId()}toBasket" type="submit" name="ClothesIdToBasket" value="${product1.getId()}"
                                            class="btn btn-default btn-xs">To Basket</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div>

    <!-- footer -->
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <!-- footer -->

    <!-- Bootstrap -->
    <script type="text/javascript">
        <%@include file="js/jquery-3.4.1.min.js"%>
        <%@include file="js/popper.min.js"%>
        <%@include file="js/bootstrap.bundle.min.js"%>
        <%@include file="js/bootstrap.min.js"%>
    </script>
</body>
</html>

