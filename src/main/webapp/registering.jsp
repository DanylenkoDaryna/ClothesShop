<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 24.08.2020
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="/registering.jsp";%>
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
            <table class="table">
                <tbody>
                <%--===========================================================================
                This is the CONTENT, containing the main part of the page.
                ===========================================================================--%>
                <tr>
                    <td class="content center">
                        <%-- CONTENT --%>
                        <h1>Registering</h1>

                        <%--===========================================================================
                        Defines the web form.
                        ===========================================================================--%>
                        <form id="registering_form" action="controller" method="post">

                            <%--===========================================================================
                            Hidden field. In the query it will act as command=login.
                            The purpose of this to define the command name, which have to be executed
                            after you submit current form.
                            ===========================================================================--%>
                            <input type="hidden" name="command" value="register"/>

                            <fieldset>
                                <legend>First name</legend>
                                <input name="first name"/><br/>
                            </fieldset><br/>
                                <fieldset>
                                    <legend>Last name</legend>
                                    <input name="last name"/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <label for="country">Country</label>
                                    <select id="country" name="country">
                                        <option value="volvo">Ukraine</option>
                                        <option value="saab">Great Britain</option>
                                    </select>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Date of Birth</legend>
                                    <input name="birthday" type="date"/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Email</legend>
                                    <input type="email" name="email"/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Password</legend>
                                    <input type="password" name="password"/>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Phone number</legend>
                                    <input type="tel" name="telephone" pattern="+38 (0[0-9]{2})[0-9]{3}-[0-9]{2}-[0-9]{2}"/>
                                </fieldset><br/>

                            <input type="submit" value="Register" >
                                <input type="reset">

                        </form>
                    </td>
                </tr>
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


