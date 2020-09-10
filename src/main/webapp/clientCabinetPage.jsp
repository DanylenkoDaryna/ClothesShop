<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 08.08.2020
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="clientCabinetPage.jsp";%>
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
                                    <h3>ORDERS</h3>
                                    sadfasfasfsadfsafasfdasf<br>
                                    asdfasfd<br>
                                    asdfaf<br>
                                </td>
                            </tr>
                            <tr title="Editing" class="danger">
                                <td colspan="2">

                                    <h3>EDITING</h3>

                                    <button name="editInfo">
                                    <a href="jsp/editAccount.jsp"> Edit my information</a>
                                    </button>

                                        </td>
                                    </tr>
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
