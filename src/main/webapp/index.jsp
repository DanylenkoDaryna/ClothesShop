<!doctype html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                    <h3>Shop of the best clothes for all your family!</h3>
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
                            <div class="carousel-caption d-none d-md-block d-lg-block">
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="img/armadio2.JPG" class="d-block w-100" alt="...">
                            <div class="carousel-caption d-none d-md-block d-lg-block">
                                <h5>"You could find here many purchases for yourself! Amazing! True prices!"</h5>
                                <p>© Amy Williams, Vogue journalist</p>
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
                                <h5>"I love colours and brands! Would buy here everything for myself, very good quality!"</h5>
                                <p> © Gorgio Armani, cool guy</p>
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

