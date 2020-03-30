<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 07.11.2019
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <style>
        <%@include file="./css/bootstrap-grid.min.css"%>
        <%@include file="./css/bootstrap-reboot.min.css"%>
        <%@include file="./css/bootstrap.min.css"%>
        <%@include file="./css/fontawesome.min.css"%>
        <%@include file="./css/main.css"%>
    </style>

    <title>ARMADIO Login</title>

</head>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%--===========================================================================
Here we use a table layout.
Class page corresponds to the '.page' element in included CSS document.
===========================================================================--%>
<table id="main-container">

    <%--===========================================================================
    This is the HEADER, containing a top menu.
    header.jspf contains all necessary functionality for it.
    Just included it in this JSP document.
    ===========================================================================--%>

    <%-- HEADER --%>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%-- HEADER --%>

    <%--===========================================================================
    This is the CONTENT, containing the main part of the page.
    ===========================================================================--%>
    <tr >
        <td class="content center">
            <%-- CONTENT --%>

            <%--===========================================================================
            Defines the web form.
            ===========================================================================--%>
            <form id="login_form" action="controller" method="post">

                <%--===========================================================================
                Hidden field. In the query it will act as command=login.
                The purpose of this to define the command name, which have to be executed
                after you submit current form.
                ===========================================================================--%>
                <input type="hidden" name="command" value="login"/>

                <fieldset>
                    <legend>Login</legend>
                    <input name="login"/><br/>
                </fieldset><br/>
                <fieldset>
                    <legend>Password</legend>
                    <input type="password" name="password"/>
                </fieldset><br/>

                <input type="submit" value="Login" >
            </form>

            <%-- CONTENT --%>

        </td>
    </tr>

        <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</table>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="./js/jquery-3.4.1.min.js"%>
    <%@include file="./js/popper.min.js"%>
    <%@include file="./js/bootstrap.bundle.min.js"%>
    <%@include file="./js/bootstrap.min.js"%>
</script>

</body>
</html>

