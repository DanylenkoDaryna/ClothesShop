<%!private String pageJspName="/products.jsp";%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
    <%@ include file="/WEB-INF/jspf/hat.jspf"%>
    <div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
        <a role="button" class="btn btn-outline-primary"
           href="controller?command=SimpleFilter&filtration=FromAToZ&page=products">AZ</a>
        <a role="button" class="btn btn-outline-secondary"
           href="controller?command=SimpleFilter&filtration=FromZToA&page=products">ZA</a>
        <a role="button" class="btn btn-outline-success"
           href="controller?command=SimpleFilter&filtration=LowPriceFirst&page=products">$ from max</a>
        <a role="button" class="btn btn-outline-danger"
           href="controller?command=SimpleFilter&filtration=HighPriceFirst&page=products">$ from min</a>
        <a role="button" class="btn btn-outline-warning"
           href="controller?command=SimpleFilter&filtration=oldFirst&page=products">Old collection</a>
        <a role="button" class="btn btn-outline-info"
           href="controller?command=SimpleFilter&filtration=newFirst&page=products">New collection</a>
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
                <c:forEach items="${sessionScope.items}" var="item1">
                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="view overlay">
                            <img class="card-img-top"
                                 src="img/${(item1.getContainer().get(0)).getImages().get(0)}.jpg" alt="clothes img">
                            <a href="#">
                                <div class="mask rgba-white-slight"></div>
                            </a>
                        </div>

                        <div class="card-body text-center">
                            <h5>
                                <strong>
                                    <!--      request.setAttribute("itemContainer",product1.getContainer())
                            ##<a href="./currentItem.jsp" class="dark-grey-text"> -->
                            <a href="controller?command=ItemProducts&ItemId=${item1.getId()}" class="dark-grey-text">
                                    ${item1}
                            </a>
                                </strong>
                            </h5>
                            <h6 class="font-weight-bold blue-text">
                                <strong>${item1.getPrice()} $</strong>
                            </h6>
                                <h6>Release: ${item1.getReleaseDate()}</h6>
    <!--Делать после нажатия кнопки неактивными, чтобы пользователю стало очевидным,
    что он уже нажал на кнопку, и что форма отправляется
    Чтобы сервер не загружался лишними запросами, и чтобы уменьшить вероятность какой-либо ошибки-->
                            <form action="basketServlet" method="post">
                                <input title="page to return" hidden name="page" value=<%=pageJspName%>>
                            <button id="${item1.getId()}toBasket" type="submit" name="ClothesIdToBasket" value="${item1.getId()}"
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
