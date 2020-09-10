<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 10.09.2020
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="jsp/editAccount.jsp";%>
<html lang="en">

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
            <h3>EDIT PERSONAL INFORMATION</h3>
            <section class="table mb-lg-2">

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
                        <select id="country" name="country"  value="${sessionScope.sessionUser.getFirstName()}" >
                            <c:if test="${sessionScope.sessionUser.getCountry() eq 'Ukraine' }">
                            <option selected value="Ukraine" >Ukraine</option>
                            <option value="Great Britain">Great Britain</option>
                            </c:if>
                            <c:if test="${sessionScope.sessionUser.getCountry() eq 'Great Britain' }">
                            <option value="Ukraine">Ukraine</option>
                            <option selected value="Great Britain">Great Britain</option>
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
                    <fieldset>
                        <label>Password
                            <input type="password" name="old pass" value="${sessionScope.sessionUser.getPassword()}"/>
                            <button onclick="enableChangePass()">
                                Change password
                            </button>
                            <input type="password" name="password1" minlength="7" disabled required/>
                            <input type="password" name="password2" minlength="7" disabled required oninput ="equalPasswords(password1, password2)"/>
                        </label>
                    </fieldset>
                    <fieldset>
                        <label>Phone number
                            <input type="tel" name="telephone" value="${sessionScope.sessionUser.getTelephone()}"/>
                        </label>
                        <!--pattern="+[0-9]{5}[0-9]{3}-[0-9]{2}-[0-9]{2}" required/>-->
                    </fieldset>

                    <input type="reset">
                    <input type="submit" value="Save changes" >
                        </form>
            </section>
        </section>
    </div>
    <%--===========================================================================
     This is the SCRIPT, containing the main functions
    ===========================================================================--%>
    <script>
        function enableChangePass(){
            $("input").prop('disabled', false);
        }

        function equalPasswords(pass1, pass2){
            if(pass1 !== pass2){
                // var email = document.getElementById("mail");
                pass2.setCustomValidity("Passwords are not equal!");
            }
        }

        function dsafasfaf(){
            if(show_confirm()){
                window.location = "controller?command=deleteAccount";
                this.afdsfa();
            }
            else{
                this.adsfaf();
            }
        }
        function adsfsdfa()
        {
            return confirm("Are you sure you want to do this?");
        }
    </script>
    <%--==========================================================================--%>
</main>
</body>
</html>
