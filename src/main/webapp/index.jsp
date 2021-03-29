<%@ page contentType="text/html; charset=WINDOWS-1251" language="java" pageEncoding="WINDOWS-1251" %>
<!doctype html>
<html lang="en">
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%!private String pageJspName="index.jsp";%>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>

        <main>
            <div class="container-fluid">
                <section class="text-center mb-4">
                    <h1><hi:Greetings/></h1>

                    <br>
                    country = <h3>${country}</h3>
                    <br>
                    language = <h3>${language}</h3>

                    <h3> <fmt:message key="index.content" bundle="${BundleContent}"/></h3>
                    <!-- basket -->
                    <%@ include file="/WEB-INF/jspf/basket.jspf"%>
                    <!-- basket -->
                <div id="carouselIndex" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselIndex" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselIndex" data-slide-to="1"></li>
                        <li data-target="#carouselIndex" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="img/bacgroundImageIndex.jpg" class="d-block w-100" alt="...">
                            <div class="carousel-caption d-none d-md-block d-lg-block" id="black-colored-review">

                                <h5><fmt:message key="index.view1" bundle="${BundleContent}"/></h5>
                                <h3><p>© Загорецька Л.С., пенсіонерка</p></h3>
                                <p>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star-half"></i>
                                </p>

                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="img/armadio2.JPG" class="d-block w-100" alt="...">
                            <div class="carousel-caption d-none d-md-block d-lg-block">
                                <h5><fmt:message key="index.view2" bundle="${BundleContent}"/></h5>
                                <h3><p>© Amy Williams, Vogue journalist</p></h3>
                                <p>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star-half"></i>
                                </p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="img/armadio3.jpg" class="d-block w-100" alt="...">
                            <div class="carousel-caption d-none d-md-block d-lg-block">
                                <h5><fmt:message key="index.view3" bundle="${BundleContent}"/></h5>
                                <h3><p> © Gorgio Armani, cool guy</p></h3>
                                <p>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                </p>
                            </div>
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselIndex" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Prev</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselIndex" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                </section>
            </div>
            <%--<div id="indexContainer">--%>
                <%--<!-- main background image of shop -->--%>
            <%--</div>--%>

        </main>

<!-- footer -->
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="js/jquery-3.4.1.min.js"%>
    <%@include file="js/popper.min.js"%>
    <%@include file="js/bootstrap.bundle.min.js"%>
    <%@include file="js/bootstrap.min.js"%>
    <%@include file="js/fontawesome.min.js"%>
</script>

</body>
</html>

