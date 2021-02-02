<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 17.09.2020
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<html lang="en">
<%!private String pageJspName="/adminCabinetPage.jsp";%>
<%-- HEAD --%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%-- HEAD --%>

<body>
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
                    <%--<li class="nav-item">--%>
                        <%--<a class="nav-link" data-toggle="tab" href="#updatingAdmin">--%>
                            <%--<i class="fas fa-user-edit"></i>--%>
                            <%--Updating Admin Info--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <!-- 2 вкладка -->
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
                            <tr class="danger">
                                <td>ROLE</td>
                                <td>${sessionScope.userRole.getName()}</td>
                            </tr>
                            <tr class="danger">
                                <td>YOUR ACCOUNT</td>
                                <td>${sessionScope.sessionUser.getAccountStatus()}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- 2 блок -->
                    <div class="tab-pane fade" id="updatingUsers">

                        <section class="text-center">
                            <table class="admin_users_table">
                                <thead><tr><td>List of users</td></tr></thead>
                                <tbody>
                                <tr class="active">
                                    <td>USER ID</td>
                                    <td>
                                        <i class="fas fa-trash-alt"></i>
                                        USER NAME
                                    </td>
                                    <td>
                                        <i class="fas fa-trash-alt"></i>
                                        USER SECOND NAME
                                    </td>
                                    <td>
                                        <i class="fas fa-trash-alt"></i>
                                        USER LOGIN
                                    </td>
                                    <td>
                                        <i class="fas fa-trash-alt"></i>
                                        USER ROLE
                                    </td>
                                    <td>
                                        <i class="fas fa-edit"></i>
                                        STATUS OF ACCOUNT
                                    </td>
                                    <td>
                                        <i class="fas fa-edit"></i>
                                        LOCKING/UNLOCKING ACCOUNT
                                    </td>
                                </tr>
                                <c:forEach items="${sessionScope.listOfUsers}" var="user">
                                    <tr>
                                        <td>${user.getId()}</td>
                                        <td>${user.getFirstName()}</td>
                                        <td>${user.getLastName()}</td>
                                        <td>${user.getLogin()}</td>
                                       <c:if test="${user.getRoleId()==0}">
                                           <td>ADMIN</td>
                                       </c:if>
                                        <c:if test="${user.getRoleId()==1}">
                                        <td>CLIENT</td>
                                        </c:if>
                                        <td>${user.getAccountStatus()}</td>
                                        <td>
                                            <form method="post" id="lockAccount" action="controller">
                                                <input type="hidden" name="command" value="UpdatingUPOCommand"/>
                                                <input type="hidden" name="commandType" value="lockingUser"/>
                                                <input type="hidden" name="idToLock" value="${user.getId()}"/>
                                                <input type="submit" value="Lock">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </section>
                    </div>

                    <!-- 3 блок -->
                    <div class="tab-pane fade" id="updatingData">
                        <h4>Here you can choose what you want to update(add new/delete/edit) -
                            catalogue, category, products?</h4>
                        <ul class="nav nav-tabs">
                            <!-- 1 вкладка (активна) -->
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#catalogueUpdate">Catalogue update</a>
                            </li>
                            <!-- 2 вкладка -->
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#categoriesUpdate">Categories update</a>
                            </li>
                        </ul>
                        <!-- Блоки з контентом -->
                        <div class="tab-content">
                            <!-- 1 блок (он отображается по умолчанию, т.к. имеет классы show и active) -->
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



    <%-- FOOTER --%>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
    <%-- FOOTER --%>


    <%--===========================================================================
     This is the SCRIPT, containing the main functions of the page.
    ===========================================================================--%>

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

