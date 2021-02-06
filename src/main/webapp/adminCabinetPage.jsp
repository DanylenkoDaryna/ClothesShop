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
                    <%-- first tab --%>
                    <%@ include file="/WEB-INF/jspf/admin_description.jspf"%>
                    <%-- first tab --%>

                    <%-- second tab --%>
                    <%@ include file="/WEB-INF/jspf/admin_updating_users.jspf"%>
                    <%-- second tab --%>

                    <!-- third tab -->
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
                    <!-- third tab -->


                        <!-- content for third tab -->
                        <div class="tab-content">
                            <!-- first tab for third tab -->
                            <%@ include file="/WEB-INF/jspf/admin_updating_catalogue.jspf"%>
                            <!-- first tab for third tab -->

                            <!-- second tab for third tab -->
                            <%@ include file="/WEB-INF/jspf/admin_updating_categories.jspf"%>
                            <!-- second tab for third tab -->

                            <!-- third tab for third tab-->
                            <div class="tab-pane fade" id="productsUpdate">

                            </div>
                            <!-- third tab for third tab-->
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

