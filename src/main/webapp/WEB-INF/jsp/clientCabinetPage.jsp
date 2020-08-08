<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 08.08.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="/jsp/clientCabinetPage.jsp";%>
<html lang="en">


<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>

<%--===========================================================================
Here we use a table layout.
Class page corresponds to the '.page' element in included CSS document.
===========================================================================--%>

<%-- HEADER --%>
<%--===========================================================================
        This is the HEADER, containing a top menu.
        header.jspf contains all necessary functionality for it.
        Just included it in this JSP document.
===========================================================================--%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%-- HEADER --%>

<%-- HAT --%>
<%@ include file="/WEB-INF/jspf/hat.jspf"%>
<%-- HAT --%>

<!-- BASKET -->
<%@ include file="/WEB-INF/jspf/basket.jspf"%>
<!-- BASKET -->

<main>
    <div class="container-fluid">
        <section class="text-center mb-4">
            <hi:Greetings/>
            <div class="row wow fadeIn">
                <div class="row">
                    <div class="col-lg-6 col-md-6 ">
                        <table class="table">
                            <thead>
                            <tr>
                                <th><h1>PERSONAL INFORMATION
                                    </h1></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="active">
                                <td>NAME</td>
                            </tr>
                            <tr class="danger">
                                <td>SECOND NAME</td><td>ffaasdfdf</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <table class="table">
                <tbody>
                <%--===========================================================================
                This is the CONTENT, containing the main part of the page.
                ===========================================================================--%>
                <tr>
                    <td class="content center">
                        <%-- FOOTER --%>
                        <%@ include file="/WEB-INF/jspf/footer.jspf"%>
                        <%-- FOOTER --%>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>

</main>
<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="/js/jquery-3.4.1.min.js"%>
    <%@include file="/js/popper.min.js"%>
    <%@include file="/js/bootstrap.bundle.min.js"%>
    <%@include file="/js/bootstrap.min.js"%>
</script>

</body>
</html>
