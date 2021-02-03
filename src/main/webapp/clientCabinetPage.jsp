<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 08.08.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="/clientCabinetPage.jsp";%>
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
                    <table class="table" id="cabinetTable">
                        <thead>
                        <h3>PERSONAL INFORMATION</h3>

                        </thead>
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
                            <td>STATUS OF ACCOUNT</td>
                            <td>${sessionScope.sessionUser.getAccountStatus()}</td>
                        </tr>
                        <tr class="danger">
                            <td>BDAY</td>
                            <td>${sessionScope.sessionUser.getBirthday()}</td>
                        </tr>
                        <tr class="danger">
                            <td>COUNTRY</td>
                            <td>${sessionScope.sessionUser.getCountry()}</td>
                        </tr>
                        <tr class="danger">
                            <td>EMAIL</td>
                            <td>${sessionScope.sessionUser.getEmail()}</td>
                        </tr>
                        <tr class="danger">
                            <td>TEL</td>
                            <td>${sessionScope.sessionUser.getTelephone()}</td>
                        </tr>


                        <tr class="danger">
                            <td colspan="2">
                                <h3>LIST OF PRODUCTS</h3>
                            </td>
                        </tr>


                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                                <c:forEach items="${sessionScope.itemsInBasket}" var="itemToOrder">
                        <tr>
                            <td>
                                    ${itemToOrder.getProductName()}
                            </td>
                            <td>
                                <form action="basketCleanerServlet" method="post">
                                    <input title="page to return after delete" hidden name="pageBack" value=<%=pageJspName%>>
                                    <button type="submit" name="IdDeleteFromBasket" value="${itemToOrder.getId()}">
                                        Remove</button>
                                </form>
                            </td>
                        </tr>
                                </c:forEach>
                        </c:if>
                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                            <tr>
                                <td colspan="2">
                            <h4>You can not delete anything, cuz your account is locked. </h4>
                                </td>
                            </tr>
                        </c:if>



                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                            <tr>
                                <td colspan="2">
                                    <form id="order_form" action="controller" method="post" >
                                        <input type="hidden" name="command" value="ordering"/>
                                        <input type="submit" name="orderProducts" value="To order">
                                    </form>
                                </td>
                            </tr>
                        </c:if>

                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                        <tr>
                            <td colspan="2">
                            <h4>You can not order anything, cuz your account is locked. </h4>
                            </td>
                        </tr>
                        </c:if>



                        <tr class="danger">
                            <td colspan="2">
                                <h3>LIST OF MY ORDERS</h3>
                            </td>
                        </tr>
                        <tr>
                            <td>NAME OF ORDER</td>
                            <td>STATUS</td>
                        </tr>
                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='UNLOCKED'}">
                        <tr title="Editing" class="danger">
                                <td colspan="2">
                                    <h3>EDITING</h3>
                                    <button name="editInfo">
                                        <a href="editAccount.jsp"> Edit my information</a>
                                    </button>
                                </td>
                        </tr>
                        </c:if>
                        <c:if test="${sessionScope.sessionUser.getAccountStatus()=='LOCKED'}">
                            <tr>
                                <td colspan="2">
                            <h4>
                                You can not edit anything, cuz your account is locked.
                            <br>For more information please contact our admin by armadio@gmail.com
                            </h4>
                                </td>
                            </tr>
                        </c:if>
                        <tr class="danger">
                                <td colspan="2">
                                    <h3>DELETE ACCOUNT AND EXIT</h3>
                                    <input type="button" name="deleteAccount" value="Delete this Account"
                                           onclick="confirmRemoveAccount()"
                                    >
                                </td>
                        </tr>
                        </tbody>
                        </table>
                    </section>
            </section>
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

    <%--===========================================================================
     This is the SCRIPT, containing the main functions of the page.
    ===========================================================================--%>
    <script>
        function confirmRemoveAccount(){
            if(show_confirm()){
                window.location = "controller?command=deleteAccount";
                this.hide();
            }
            else{
                this.hide();
            }
        }
        function show_confirm()
        {
            return confirm("Are you sure you want to do this?");
        }
    </script>
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
