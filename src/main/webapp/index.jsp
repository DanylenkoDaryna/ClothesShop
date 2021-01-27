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
                </section>
            </div>
            <div id="indexContainer">
                <!-- main background image of shop -->
            </div>
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

