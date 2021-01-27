<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 17.09.2020
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="/adminCabinetPage.jsp";%>
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
<%--<%@ include file="/WEB-INF/jspf/header.jspf"%>--%>
<%-- HEADER --%>

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>


<main>
    <div class="container-fluid">
        <section class="text-center">
            <hi:Greetings/>
            <section class="table mb-lg-2">



                <ul class="nav nav-tabs">
                    <!-- 1 вкладка (активна) -->
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#description">Personal info</a>
                    </li>
                    <!-- 2 вкладка -->
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#updatingAdmin">
                            <i class="fas fa-user-edit"></i>
                            Updating Admin Info
                        </a>
                    </li>
                    <!-- 3 вкладка -->
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#updatingUsers">
                            <i class="fas fa-user-edit"></i>
                            Updating Users
                        </a>
                    </li>
                    <!-- 4 вкладка -->
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#updatingData">
                            <i class="fas fa-edit"></i>
                            Updating Catalogue Data
                        </a>
                    </li>

                </ul>


                <!-- Блоки с контентом -->
                <div class="tab-content">
                    <!-- 1 блок (он отображается по умолчанию, т.к. имеет классы show и active) -->
                    <div class="tab-pane fade show active" id="description">

                        <table class="table" id="cabinetTable">
                            <tbody>
                            <tr class="active">
                                <td>NAME</td>
                                <td>${sessionScope.sessionUser.getFirstName()}</td>
                            </tr>
                            <tr class="danger">
                                <td>SECOND NAME</td>
                                <td>${sessionScope.sessionUser.getLastName()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- 2 блок  -->
                    <div class="tab-pane fade" id="updatingAdmin">
                        <i class="fas fa-user-edit"></i>
                        <form id="editAccount_form" action="controller" method="post">
                            <%--===========================================================================
                            Hidden field. In the query it will act as command=login.
                            The purpose of this to define the command name, which have to be executed
                            after you submit current form.
                            ===========================================================================--%>
                            <input type="hidden" name="command" value="editAccount"/>
                            <fieldset>
                                <label>First name
                                    <input name="first name" value="${sessionScope.sessionUser.getFirstName()}" />
                                </label>
                            </fieldset>
                            <fieldset>
                                <label>Last name
                                    <input name="last name" value="${sessionScope.sessionUser.getLastName()}" />
                                </label>
                            </fieldset>
                            <fieldset >
                                <label for="country">Country</label>
                                <select id="country" name="country">
                                    <c:if test="${sessionScope.sessionUser.getCountry() eq 'Ukraine' }">
                                        <option selected value="Ukraine" >Ukraine</option>
                                        <option value="Great Britain">Great Britain</option>
                                    </c:if>
                                    <c:if test="${sessionScope.sessionUser.getCountry() eq 'Great Britain' }">
                                        <option value="Ukraine">Ukraine</option>
                                        <option selected value="Great Britain">Great Britain</option>
                                    </c:if>
                                    <c:if test="${sessionScope.sessionUser.getCountry() eq null }">
                                        <option value="Ukraine" >Ukraine</option>
                                        <option value="Great Britain">Great Britain</option>
                                    </c:if>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>Date of Birth
                                    <input name="birthday" type="date" value="${sessionScope.sessionUser.getBirthday()}" />
                                </label>
                            </fieldset>
                            <fieldset>
                                <!--це заглушка для логіна, бо логін має бути унікальним, а емейли унікальні-->
                                <label>Email
                                    <input type="email" name="email" value="${sessionScope.sessionUser.getEmail()}" />
                                </label>
                            </fieldset>

                            <%--<label>Password--%>
                            <%--<input type="password" name="old pass" value="${sessionScope.sessionUser.getPassword()}"/>--%>
                            <%--<input type="button" value="Change password?" onclick="enableChangePass()">--%>
                            <%--<br/>--%>
                            <%--<input type="password" id="pa1" name="password1" minlength="7" maxlength="10" disabled required />--%>
                            <%--<br/>--%>
                            <%--<input type="password" id="pa2" name="password2" minlength="7" maxlength="10" disabled required />--%>
                            <%--&lt;%&ndash;oninput ="equalPasswords(password1, password2)"&ndash;%&gt;--%>
                            <%--</label>--%>
                            <fieldset>
                                <label for="old pass">
                                    <span>Old Password</span>

                                    <input type="password" id="old pass" name="old pass" value="${sessionScope.sessionUser.getPassword()}"/>
                                    <input type="button" value="Change password?" onclick="enableChangePass()">
                                    <br>
                                </label>
                            </fieldset>
                            <fieldset>
                                <label for="pa1">
                                    <span>New Password</span>
                                    <input type="password" id="pa1" name="password1"
                                           maxlength="10" disabled minlength="7" required>
                                    <br>
                                </label>
                                <br/>
                                <label for="pa2">
                                    <span>Repeat Password</span>
                                    <input type="password" id="pa2" name="password2" disabled maxlength="10" minlength="7" required>
                                </label>
                                <br>
                            </fieldset>

                            <fieldset>
                                <label>Phone number
                                    <input type="tel" name="telephone" value="${sessionScope.sessionUser.getTelephone()}"/>
                                </label>
                                <!--pattern="+[0-9]{5}[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>-->
                            </fieldset>


                            <input type="reset">
                            <input type="submit" value="Save changes" onclick="equalPasswords()" >
                        </form>
                    </div>
                    <!-- 3 блок -->
                    <div class="tab-pane fade" id="updatingUsers">

dsafadfsfafasf
                    </div>

                    <!-- 3 блок -->
                    <div class="tab-pane fade" id="updatingData">
                        <h4>Here you can choose what you want to update(add new/delete/edit) -
                            catalogue, category, products?</h4>
                        <ul class="nav nav-tabs">
                            <!-- Первая вкладка (активная) -->
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#catalogueUpdate">Catalogue update</a>
                            </li>
                            <!-- 2 вкладка -->
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#categoriesUpdate">Categories update</a>
                            </li>
                        </ul>
                        <!-- Блоки с контентом -->
                        <div class="tab-content">
                            <!-- Первый блок (он отображается по умолчанию, т.к. имеет классы show и active) -->
                            <div class="tab-pane fade show active" id="catalogueUpdate">
                                <section class="text-center">
                                <table class="admin_data_table">
                                    <tbody>
                                    <tr class="active">
                                        <td>CATALOGUE ITEMS</td>
                                        <td>
                                            <i class="fas fa-trash-alt"></i>
                                            DELETE
                                        </td>
                                        <td>
                                            <i class="fas fa-edit"></i>
                                            EDIT
                                        </td>
                                    </tr>
                                    <c:forEach items="${catalogue.container.keySet()}" var="entry1">
                                        <tr>
                                            <td>${entry1}</td>
                                            <td>
                                                <form method="post" id="delete_catalogue_form" action="controller">
                                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                                    <input type="hidden" name="updateCatalogueType" value="delete"/>
                                                    <input type="hidden" name="itemToDelete" value="${entry1}"/>
                                                    <input type="submit" value="Delete">
                                                </form>
                                            </td>
                                            <td>
                                                <form method="post" id="edit_catalogue_form" action="controller">
                                                    <input type="hidden" name="command" value="updateCatalogue"/>
                                                    <input type="hidden" name="updateCatalogueType" value="edit"/>
                                                    <input type="hidden" name="itemToEdit" value="${entry1}"/>
                                                    <label>New name
                                                        <input name="editedItem"/>
                                                    </label>
                                                    <input type="submit" value="Save">
                                                </form>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="3">
                                        <form method="post" id="add_catalogue_form" action="controller">
                                            <input type="hidden" name="command" value="updateCatalogue"/>
                                            <input type="hidden" name="updateCatalogueType" value="add"/>
                                            <label>New Catalogue Item name
                                                <i class="fas fa-plus-circle"></i>
                                                <input name="catalogueName" maxlength="30" value="Enter name">
                                            </label>
                                            <input type="submit" value="Add New Item">
                                        </form>

                                    </tr>
                                    </tbody>
                                </table>
                                </section>
                            </div>
                            <!-- 2 блок  -->
                            <div class="tab-pane fade" id="categoriesUpdate">
                                <table class="admin_data_table">
                                    <tbody >
                                    <tr class="active">
                                        <td>CATALOGUE ITEMS</td>
                                        <td>DELETE/EDIT/ADD NEW</td>
                                    </tr>

                                    <c:forEach items="${catalogue.container.keySet()}" var="entry1">
                                    <tr>
                                        <td>${entry1}</td>

                                        <td>
                                        <c:forEach items="${catalogue.container.get(entry1)}" var="linker1">

                                            ${linker1.getName()}

                                            <form method="post" id="delete_category_form" action="controller">
                                                   <input type="hidden" name="command" value="updateCatalogue"/>
                                                   <input type="hidden" name="updateCatalogueType" value="deleteCategory"/>
                                                   <input type="hidden" name="CatalogueToDeleteFrom" value="${entry1}"/>
                                                   <input type="hidden" name="itemToDelete" value="${linker1.getName()}"/>
                                                   <input type="submit" value="Delete">
                                            </form>

                                           <form method="post" id="edit_category_form" action="controller">
                                               <input type="hidden" name="command" value="updateCatalogue"/>
                                               <input type="hidden" name="updateCatalogueType" value="editCategory"/>
                                               <input type="hidden" name="CatalogueToEditFrom" value="${entry1}"/>
                                               <input type="hidden" name="itemToEdit" value="${linker1.getName()}"/>
                                               <input name="editedCategory"/>
                                               <input type="submit" value="Save">
                                           </form>

                                        </c:forEach>
                                            <form method="post" id="add_category_form" action="controller">
                                                <input type="hidden" name="command" value="updateCatalogue"/>
                                                <input type="hidden" name="updateCatalogueType" value="addCategory"/>
                                                <input type="hidden" name="CatalogueToAddTo" value="${entry1}"/>
                                                <label>New Category
                                                    <input name="categoryName" maxlength="30" required>
                                                </label>
                                                <input type="submit" value="Add">
                                            </form>
                                        </td>


                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="tab-pane fade" id="productsUpdate">

                            </div>
                    </div>
                    <!-- 3 блок -->
                    <div class="tab-pane fade" id="updatingOrders">

                    </div>

                </div>
                </div>
            </section>
        </section>
    </div>


                <%--<table class="table" id="cabinetTable">--%>
                    <%--<thead>--%>
                    <%--<h3>PERSONAL INFORMATION</h3>--%>

                    <%--</thead>--%>
                    <%--<tbody>--%>
                    <%--<tr class="active">--%>
                        <%--<td>NAME</td>--%>
                        <%--<td>${sessionScope.sessionUser.getFirstName()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td>SECOND NAME</td>--%>
                        <%--<td>${sessionScope.sessionUser.getLastName()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td>BDAY</td>--%>
                        <%--<td>${sessionScope.sessionUser.getBirthday()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td>COUNTRY</td>--%>
                        <%--<td>${sessionScope.sessionUser.getCountry()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td>EMAIL</td>--%>
                        <%--<td>${sessionScope.sessionUser.getEmail()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td>TEL</td>--%>
                        <%--<td>${sessionScope.sessionUser.getTelephone()}</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td colspan="2">--%>
                            <%--<h3>LIST OF PRODUCTS</h3>--%>
                        <%--</td>--%>
                    <%--</tr>--%>

                    <%--<c:forEach items="${sessionScope.itemsInBasket}" var="itemToOrder">--%>
                        <%--<tr>--%>
                            <%--<td>--%>
                                    <%--${itemToOrder.getProductName()}--%>
                            <%--</td>--%>
                            <%--<td>--%>
                                <%--<form action="basketCleanerServlet" method="post">--%>
                                    <%--<input title="page to return after delete" hidden name="pageBack" value=<%=pageJspName%>>--%>
                                    <%--<button type="submit" name="IdDeleteFromBasket" value="${itemToOrder.getId()}">--%>
                                        <%--Remove</button>--%>
                                <%--</form>--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    <%--</c:forEach>--%>
                    <%--<tr>--%>
                        <%--<td colspan="2">--%>
                            <%--<form id="order_form" action="controller" method="post">--%>
                                <%--<input type="hidden" name="command" value="register"/>--%>
                                <%--<input type="submit" name="orderProducts" value="To order">--%>
                            <%--</form>--%>
                        <%--</td>--%>
                    <%--</tr>--%>


                    <%--<tr class="danger">--%>
                        <%--<td colspan="2">--%>
                            <%--<h3>LIST OF MY ORDERS</h3>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>NAME OF ORDER</td>--%>
                        <%--<td>STATUS</td>--%>
                    <%--</tr>--%>
                    <%--<tr title="Editing" class="danger">--%>
                        <%--<td colspan="2">--%>
                            <%--<h3>EDITING</h3>--%>
                            <%--<button name="editInfo">--%>
                                <%--<a href="editAccount.jsp"> Edit my information</a>--%>
                            <%--</button>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr class="danger">--%>
                        <%--<td colspan="2">--%>
                            <%--<h3>DELETE ACCOUNT AND EXIT</h3>--%>
                            <%--<input type="button" name="deleteAccount" value="Delete this Account"--%>
                                   <%--onclick="confirmRemoveAccount()"--%>
                            <%-->--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--</tbody>--%>
                <%--</table>--%>
            <%--</section>--%>
        <%--</section>--%>
    <%--</div>--%>

    <%-- FOOTER --%>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <%-- FOOTER --%>


    <%--===========================================================================
     This is the SCRIPT, containing the main functions of the page.
    ===========================================================================--%>
    <%--<script>--%>
        <%--function confirmRemoveAccount(){--%>
            <%--if(show_confirm()){--%>
                <%--window.location = "controller?command=deleteAccount";--%>
                <%--this.hide();--%>
            <%--}--%>
            <%--else{--%>
                <%--this.hide();--%>
            <%--}--%>
        <%--}--%>
        <%--function show_confirm()--%>
        <%--{--%>
            <%--return confirm("Are you sure you want to do this?");--%>
        <%--}--%>
    <%--</script>--%>
    <%--==========================================================================--%>

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

