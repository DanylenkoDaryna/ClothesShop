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

    <title>ARMADIO-shop</title>

</head>
<body>

<div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
        <a href="./index.jsp" class="navbar-brand">ARMADIO</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav nav-tabs">
                <li class="nav-item active">
                    <a class="nav-link" href="./index.jsp">Home <span class="sr-only">(current)</span></a>
                </li>

                <c:forEach items="${catalogue.container.keySet()}" var="entry1">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown0" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${entry1}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                            <c:forEach items="${catalogue.container.get(entry1)}" var="linker1">
    <a href="controller?command=listProducts&catId=${linker1.getCatalogueId()}&clothes=${linker1.getName()}"
       class="dropdown-item">
                                        ${linker1.getName()}</a>
                                <div class="dropdown-divider"></div>
                            </c:forEach>

                        </div>
                    </li>
                </c:forEach>

                <li class="nav-item">
                    <a class="nav-link disabled" href="#">Disabled</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
</div>

<table id="main-container" border="1">
    <%-- HEADER --%>
    <%-- <%@ include file="/WEB-INF/jspf/header.jspf"%>
   HEADER --%>

    <tr> <p>Welcome to Armadio Internet-shop!</p></tr>


    <c:forEach items="${catalogue.container.keySet()}" var="entry">
        <div class="dropdown">
            <button class="dropbtn">${entry}</button>
            <div class="dropdown-content">
                <c:forEach items="${catalogue.container.get(entry)}" var="linker">
                    <a href="controller?command=listProducts&catId=${linker.getCatalogueId()}&clothes=${linker.getName()}">
                            ${linker.getName()}</a>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
    <br>



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

