<%!private String pageJspName="/products.jsp";%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

    <%-- MAIN_MENU --%>
    <%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
    <%-- MAIN_MENU --%>

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
            <section class="text-left" id="filterProductsSection">
                <%@ include file="/WEB-INF/jspf/hardFilter.jspf" %>
            </section>
         </div>
    <div class="container">
       <section class="text-center">
            <h1>Products</h1>
            <div class="row wow fadeIn">
                <c:choose>
                    <c:when test="${sessionScope.userRole.getName() eq 'admin'}">
                        <%@ include file="WEB-INF/jspf/adminProducts.jspf"%>
                    </c:when>
                    <c:otherwise>
                        <%@ include file="WEB-INF/jspf/clientProducts.jspf"%>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </div>
</div>
</main>
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
