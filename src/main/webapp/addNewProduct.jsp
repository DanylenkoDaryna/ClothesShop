<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 01.10.2020
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<html lang="en">
<%!private String pageJspName="/products.jsp";%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>
<main>
    <div class="container">
        <section class="text-center mb-4">
            <div class="row wow fadeIn">
                <div class="row">

                    <form id="addNewProduct" method="post" action="controller">
                        <input type="hidden" name="command" value="addNewProduct"/>
                        <table class="table" id="add_new_product">
                            <thead>
                            <h4>Add Product info</h4>
                            </thead>
                        <tbody>
                        <tr>
                            <td colspan="2">Item Name</td>
                            <td colspan="2">
                            <input type="text" maxlength="20" required name="itemName">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">Brand</td>
                            <td colspan="2">
                            <select id="brand" name="brand" required>
                                <c:forEach items="${sessionScope.filterBrands}" var="filterBrand">
                                    <option id="${filterBrand}" value="${filterBrand}">${filterBrand}</option>
                                </c:forEach>
                            </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">Price</td>
                            <td colspan="2"> <input type="number" min="10" max="1000000" name="price"/>$</td>
                        </tr>

                        <tr>
                            <td colspan="2">Release Date</td>
                            <td colspan="2"> <input type="date" name="releaseDate"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">Collection</td>
                            <td colspan="2">
                                <input type="text" maxlength="15" required name="collectionName">
                            </td>
                        </tr>
                        <tr>
                            <td>Available items</td>
                            <td>Available size</td>
                            <td>Colour</td>
                            <td>Image</td>
                        </tr>
                        <tr>
                            <td>
                                <input name="available" type="number" min="1" max="100" required/>
                                <input type="hidden" id="productsNumber" value="1"/>
                            </td>
                            <td>
                                <select id="size" name="size" required>
                                    <c:forEach items="${sessionScope.filterSizes}" var="filterSize">
                                    <option id="${filterSize}" value="${filterSize}">${filterSize}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select id="colour" name="colour" required>
                                    <c:forEach items="${sessionScope.filterColours}" var="filterColour">
                                        <option id="${filterColour}" value="${filterColour}">${filterColour}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <input type="file" required name="image"/>
                            </td>
                        </tr>

                        </tbody>
                        </table>

                        <table class="table" id="add_materials">
                            <tbody>
                            <tr>
                                <td><label for="materials">Material</label></td>
                                <td> <input id="materials" name="materials"/></td>
                                <td> <input id="percents" name="percents" type="number" min="1" max="100"/>%</td>
                            </tr>
                            </tbody>
                        </table>

                        <input type="reset">
                        <input type="submit" value="Create New Item" >
                    </form>

                    <table class="table" id="add_more">
                        <tbody>
                        <tr>
                            <td colspan="2">Add more items?</td>
                            <td><button onclick="addRow()">OK</button></td>
                            <td colspan="2">Add more materials?</td>
                            <td><button onclick="addMaterials()">OK</button></td>
                        </tr>
                        </tbody>
                    </table>
                    </div>
                </div>
        </section>
    </div>
</main>

<script>
    var d = document;

    function addRow()
    {
        // Находим нужную таблицу
        var tableBody = d.getElementById('add_new_product').getElementsByTagName('TBODY')[0];
        // var productsNumber = d.getElementById('productsNumber');
        // d.getElementById('productsNumber').value = productsNumber+1;

        // Создаем строку таблицы и добавляем ее
        var row = d.createElement("TR");
        tableBody.appendChild(row);

        // Создаем ячейки в вышесозданной строке
        // и добавляем тх
        var td1 = d.createElement("TD");
        var td2 = d.createElement("TD");
        var td3 = d.createElement("TD");
        var td4 = d.createElement("TD");

        row.appendChild(td1);
        row.appendChild(td2);
        row.appendChild(td3);
        row.appendChild(td4);

        // Наполняем ячейки
        td1.innerHTML = '<input name="available" type="number" min="1" max="100" required/>';
        td2.innerHTML = '<select id="size" name="size" required>\n' +
            '                                    <c:forEach items="${sessionScope.filterSizes}" var="filterSize">\n' +
            '                                    <option id="${filterSize}" value="${filterSize}">${filterSize}</option>\n' +
            '                                    </c:forEach>\n' +
            '                                </select>';
        td3.innerHTML = '<select id="colour" name="colour" required>\n' +
            '                                    <c:forEach items="${sessionScope.filterColours}" var="filterColour">\n' +
            '                                        <option id="${filterColour}" value="${filterColour}">${filterColour}</option>\n' +
            '                                    </c:forEach>\n' +
            '                                </select>';
        td4.innerHTML = '<input type="file" required name="image"/>';
    }


    function addMaterials()
    {
        // Находим нужную таблицу
        var tableMaterials = d.getElementById('add_materials').getElementsByTagName('TBODY')[0];

        // Создаем строку таблицы и добавляем ее
        var row2 = d.createElement("TR");
        tableMaterials.appendChild(row2);

        // Создаем ячейки в вышесозданной строке
        // и добавляем тх
        var td11 = d.createElement("TD");
        var td21 = d.createElement("TD");
        var td31 = d.createElement("TD");

        row2.appendChild(td11);
        row2.appendChild(td21);
        row2.appendChild(td31);

        // Наполняем ячейки
        td11.innerHTML = '<label for="materials">Material</label>';
        td21.innerHTML = '<input id="materials" name="materials"/>';
        td31.innerHTML = '<input id="percents" name="percents" type="number" min="1" max="100"/>%';
    }
</script>


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



