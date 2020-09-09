<!doctype html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%!private String pageJspName="/index.jsp";%>
</head>


<body>
<%@ include file="/WEB-INF/jspf/hat.jspf"%>
<%--<table id="main-container" border="1">--%>
    <%-- HEADER --%>
    <%-- <%@ include file="/WEB-INF/jspf/header.jspf"%>
   HEADER --%>
        <main>

            <div class="container-fluid">
                <section class="text-center mb-4">
                    <section class="text-center">
                        <hi:Greetings/>
                    </section>
                    <!-- basket -->
                    <%@ include file="/WEB-INF/jspf/basket.jspf"%>
                    <!-- footer -->
                    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
                </section>
            </div>


        </main>


<%--</table>--%>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="./js/jquery-3.4.1.min.js"%>
    <%@include file="./js/popper.min.js"%>
    <%@include file="./js/bootstrap.bundle.min.js"%>
    <%@include file="./js/bootstrap.min.js"%>
</script>

</body>
</html>

