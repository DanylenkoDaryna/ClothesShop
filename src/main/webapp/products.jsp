<!doctype html>
<html lang="en">
    <%!private String pageJspName="/products.jsp";%>

<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>
<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>


    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
        <a role="button" class="btn btn-outline-primary"
           href="controller?command=SimpleFilter&filtration=FromAToZ&page=products">
            <i class="fas fa-sort-alpha-down"></i>
        </a>
        <a role="button" class="btn btn-outline-secondary"
           href="controller?command=SimpleFilter&filtration=FromZToA&page=products">
            <i class="fas fa-sort-alpha-down"></i>
            </a>
        <a role="button" class="btn btn-outline-success"
           href="controller?command=SimpleFilter&filtration=LowPriceFirst&page=products">
            $
            <i class="fas fa-sort-numeric-down"></i>
        </a>
        <a role="button" class="btn btn-outline-danger"
           href="controller?command=SimpleFilter&filtration=HighPriceFirst&page=products">
            $
            <i class="fas fa-sort-numeric-down-alt"></i>
        </a>
        <a role="button" class="btn btn-outline-warning"
           href="controller?command=SimpleFilter&filtration=oldFirst&page=products">
            <i class="far fa-calendar-alt"></i>
            <i class="fas fa-sort-down"></i>
            Old Collection
            </a>
        <a role="button" class="btn btn-outline-info"
           href="controller?command=SimpleFilter&filtration=newFirst&page=products">
            <i class="far fa-calendar-alt"></i>
            <i class="fas fa-sort-up"></i>
            New Collection
            </a>
    </nav>

<%-- якщо тут щось змінюєш - зміни і в filteredPage.jsp !!! --%>
    <section class="text-center">
        <h1>Products</h1>
    </section>

    <div id="headerwrap">

        <div class="row">

            <div id="filterProductsSection">
                <%@ include file="/WEB-INF/jspf/hardFilter.jspf" %>
            </div>

            <c:choose>
                    <c:when test="${sessionScope.userRole.getName() eq 'admin'}">
                        <div class="col-lg-3 col-md-3 col-sm-1 col-xs-1">
                            <div class="card">
                                <div class="view overlay">
                                    <a href="#">
                                        <div class="mask rgba-white-slight"></div>
                                    </a>
                                </div>
                                <div class="card-body text-center">
                                    <h5>
                                        <strong>
                                            <a href="addNewProduct.jsp"
                                               class="dark-grey-text">
                                                <i class="far fa-plus-square"></i>
                                                Add new Product
                                            </a>
                                        </strong>
                                    </h5>
                                </div>
                            </div>
                        </div>

                    </c:when>

            </c:choose>


            <c:forEach items="${sessionScope.items}" var="item1">
                <div class="col-lg-3 col-md-3 col-sm-2 col-xs-1">
                    <div class="card">
                        <div class="view overlay">
                            <img class="card-img-top" src="img/${item1.getContainer().get(0).getImage()}" alt="clothes img">
                            <%--<img class="card-img-top" src="img/bomber_gucci_pride_blue.jpg" alt="clothes img">--%>

                            <%--<img class="card-img-top"--%>
                                 <%--src="img/${(sessionScope.images.get(sessionScope.items.indexOf(item1)))}" alt="clothes img">--%>
                            <%----%>
                                <a href="controller?command=ItemProducts&ItemId=${item1.getId()}">
                                <div class="mask rgba-white-slight"></div>
                            </a>
                        </div>

                        <div class="card-body text-center">
                            <h5>
                                <strong>
                                    <a href="controller?command=ItemProducts&ItemId=${item1.getId()}" class="dark-grey-text">
                                            ${item1}
                                    </a>
                                </strong>
                            </h5>
                            <h6 class="font-weight-bold blue-text">
                                <strong>${item1.getPrice()} $</strong>
                            </h6>
                            <h6>Release: ${item1.getReleaseDate()}</h6>


                            <c:choose>
                            <c:when test="${sessionScope.userRole.getName() eq 'admin'}">
                                <form action="controller" method="post">
                                    <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                    <input type="hidden" name="updateCatalogueType" value="deleteProduct"/>
                                    <input type="hidden" name="productToDelete" value="${item1.getId()}"/>
                                    <button type="submit" class="btn btn-default btn-xs">Delete</button>
                                </form>
                                <form action="controller" method="post">
                                    <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                    <button id="${item1.getId()}toEdit" type="submit" name="ClothesIdToBasket" value="Edit"
                                            class="btn btn-default btn-xs">Edit</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                            <!--Делать после нажатия кнопки неактивными, чтобы пользователю стало очевидным,
                            что он уже нажал на кнопку, и что форма отправляется
                            Чтобы сервер не загружался лишними запросами, и чтобы уменьшить вероятность какой-либо ошибки-->
                            <form action="basketServlet" method="post" >
                                <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                                <button id="${item1.getId()}toBasket" type="submit" name="ClothesIdToBasket" value="${item1.getId()}"
                                        class="btn btn-default btn-xs">
                                    <i class="fas fa-shopping-cart"></i>
                                    To Basket</button>
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

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="js/jquery-3.4.1.min.js"%>
    <%@include file="js/popper.min.js"%>
    <%@include file="js/bootstrap.bundle.min.js"%>
    <%@include file="js/bootstrap.min.js"%>
</script>
</body>
</html>
