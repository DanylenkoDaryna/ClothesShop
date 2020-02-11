<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 05.02.2020
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
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
        <%@include file="./font-awesome-4.7.0/css/font-awesome.min.css"%>
    </style>
    <link rel="stylesheet" href="./font-awesome-4.7.0/css/font-awesome.min.css">
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
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
        <a role="button" class="btn btn-outline-primary"
           href="controller?command=SimpleFilter&filtration=FromAToZ&page=filteredPage">AZ</a>
        <a role="button" class="btn btn-outline-secondary"
           href="controller?command=SimpleFilter&filtration=FromZToA&page=filteredPage">ZA</a>
        <a role="button" class="btn btn-outline-success"
           href="controller?command=SimpleFilter&filtration=LowPriceFirst&page=filteredPage">$ from max</a>
        <a role="button" class="btn btn-outline-danger"
           href="controller?command=SimpleFilter&filtration=HighPriceFirst&page=filteredPage">$ from min</a>
        <a role="button" class="btn btn-outline-warning"
           href="controller?command=SimpleFilter&filtration=oldFirst&page=filteredPage">Old collection</a>
        <a role="button" class="btn btn-outline-info"
           href="controller?command=SimpleFilter&filtration=newFirst&page=filteredPage">New collection</a>
    </nav>


    <main>
        <div class="row">
            <div class="col-lg-2 col-md-3 ">
                <section class="text-left">
                    <form action="HardFilterServlet" method="post">
                        <div class="form-group">
                            <h6>Price</h6>
                            from:
                            <input type="number" class="form-control-sm" name="InputPriceFrom" placeholder="$" min=40>
                            to:
                            <input type="number" class="form-control-sm" name="InputPriceTo" placeholder="$" min=500>
                            <br>
                        </div>
                        <h6>Size:</h6>
                        <c:forEach items="${sessionScope.filterSizes}" var="filterSize">
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input type="checkbox" name="sizes" class="custom-control-input" value="${filterSize}"
                                       id="${filterSize}">
                                <label class="custom-control-label" for="${filterSize}"> ${filterSize}</label>
                            </div>
                        </c:forEach>
                        <h6>Brand:</h6>
                        <c:forEach items="${sessionScope.filterBrands}" var="filterBrand">
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input type="checkbox" name="brands" class="custom-control-input"  value="${filterBrand}"
                                       id="${filterBrand}">
                                <label class="custom-control-label" for="${filterBrand}"> ${filterBrand}</label>
                            </div>
                            <br>
                        </c:forEach>
                        <h6>Colour:</h6>
                        <c:forEach items="${sessionScope.filterColours}" var="filterColour">
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input type="checkbox" name="colours" class="custom-control-input" value="${filterColour}"
                                       id="${filterColour}">
                                <label class="custom-control-label" for="${filterColour}"> ${filterColour}</label>
                            </div>
                        </c:forEach>
                        <br>
                        <button type="submit" class="btn btn-primary">Sort</button>
                    </form>
                </section>
            </div>
            <div class="container">
                <section class="text-center">
                    <h1>Products</h1>
                    <div class="row wow fadeIn">
                        <c:forEach items="${sessionScope.filteredItems}" var="product1">
                            <div class="col-lg-3 col-md-6">
                                <div class="card">
                                    <div class="view overlay">
                                        <img class="card-img-top"
                                             src="img/${(product1.getContainer().get(0)).getImages().get(0)}.jpg" alt="clothes img">
                                        <a href="#">
                                            <div class="mask rgba-white-slight"></div>
                                        </a>
                                    </div>

                                    <div class="card-body text-center">
                                        <h5>
                                            <strong>
                                                <!--      request.setAttribute("itemContainer",product1.getContainer())
                                        ##<a href="./currentItem.jsp" class="dark-grey-text"> -->
                                                <a href="controller?command=ItemProducts&ItemId=${product1.getId()}" class="dark-grey-text">
                                                        ${product1}
                                                </a>
                                            </strong>
                                        </h5>
                                        <h6 class="font-weight-bold blue-text">
                                            <strong>${product1.getPrice()} $</strong>
                                        </h6>
                                        <h6>Release: ${product1.getReleaseDate()}</h6>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </div>
        </div>
    </main>

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

