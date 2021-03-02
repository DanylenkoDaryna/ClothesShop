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

                    <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"></li>
                            <li data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"></li>
                            <li data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="img/bacgroundImageIndex.jpg" class="d-block w-100" alt="armadio1">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>First slide label</h5>
                                    <p>Shop of the best clothes for all your family!</p>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img src="img/armadio2.JPG" class="d-block w-100" alt="armadio2">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>Second slide label</h5>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img src="img/armadio3.jpg" class="d-block w-100" alt="armadio3">
                                <div class="carousel-caption d-none d-md-block">
                                    <h5>Third slide label</h5>
                                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                                </div>
                            </div>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
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

