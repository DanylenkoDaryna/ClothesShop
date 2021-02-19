<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 24.08.2020
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
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

<%-- MAIN_MENU --%>
<%@ include file="/WEB-INF/jspf/mainMenu.jspf"%>
<%-- MAIN_MENU --%>

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
                                <input class="form-control" placeholder="First name" name="first name" required/><br/>
                            </fieldset><br/>
                                <fieldset>
                                    <legend>Last name</legend>
                                    <input class="form-control" placeholder="Last name" name="last name" required/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <label for="country">Country</label>
                                    <select id="country" name="country" required>
                                        <option value="Ukraine">Ukraine</option>
                                        <option value="Great Britain">Great Britain</option>
                                    </select>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Date of Birth</legend>
                                    <input name="birthday" type="date" required/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <!--це заглушка для логіна, бо логін має бути унікальним, а емейли унікальні-->
                                    <legend>Email</legend>
                                    <input class="form-control" placeholder="Email" type="email" name="email" required/><br/>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Password</legend>
                                    <input class="form-control" placeholder="Password" type="password" id="first_pass"
                                           name="password" maxlength="10"  minlength="7" required/>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Repeat Password</legend>
                                <input  class="form-control" placeholder="Password" type="password" id="second_pass"  name="password2" maxlength="10"  minlength="7" required>
                                </fieldset><br/>
                                <fieldset>
                                    <legend>Phone number</legend>
                                    <input type="tel"  class="form-control" placeholder="+38" name="telephone" required/>
                                           <!--pattern="+[0-9]{5}[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>-->
                                </fieldset><br/>

                            <input type="submit" value="Register" onclick="equalPass()">
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

===========================================================================--%>
<script>

    function equalPass(){

        if (document.getElementById('first_pass').value != document.getElementById('second_pass').value) {
            document.getElementById('second_pass').setCustomValidity('Passwords must match.');
        }else {
            document.getElementById('second_pass').setCustomValidity('');
        }
    }

</script>
<%--==========================================================================--%>

<!-- Bootstrap -->
<script type="text/javascript">
    <%@include file="/js/jquery-3.4.1.min.js"%>
    <%@include file="/js/popper.min.js"%>
    <%@include file="/js/bootstrap.bundle.min.js"%>
    <%@include file="/js/bootstrap.min.js"%>
</script>

</body>
</html>


