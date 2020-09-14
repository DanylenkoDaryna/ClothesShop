<%--
  Created by IntelliJ IDEA.
  User: Дарина
  Date: 10.09.2020
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%!private String pageJspName="/editAccount.jsp";%>
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
                        <select id="country" name="country">
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
                            <input type="password" id="pa1"
                               maxlength="10" disabled minlength="7" required>
                            <br>
                    </label>
                        <br/>
                    <label for="pa2">
                        <span>Repeat Password</span>
                        <input type="password" id="pa2" disabled maxlength="10" minlength="7" required>
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
            </section>
        </section>
    </div>
    <%--===========================================================================
     This is the SCRIPT, containing the main functions
    ===========================================================================--%>
    <script>
        function enableChangePass(){

            var pass1=document.getElementById("pa1");
            pass1.disabled=false;
            var pass2=document.getElementById("pa2");
            pass2.disabled=false;

        }

        var p1 = document.getElementById("pa1");
        var p2 = document.getElementById("pa2");

        p1.addEventListener("input", function (event) {
            if (document.getElementById("pa1").validity.valueMissing) {
                document.getElementById("pa1").setCustomValidity('Password must not be empty');
            } else if(document.getElementById("pa1").validity.rangeUnderflow){
                document.getElementById("pa1").setCustomValidity('Password must have 7 and more characters.');
            }else if(document.getElementById("pa1").validity.rangeOverflow){
                document.getElementById("pa1").setCustomValidity('Password must have 10 or less characters.');
            }
        });

        p2.addEventListener("input", function (event) {
            if (document.getElementById("pa2").validity.valueMissing) {
                document.getElementById("pa2").setCustomValidity('Password must not be empty!!!');
            } else if(document.getElementById("pa2").validity.rangeUnderflow){
                document.getElementById("pa2").setCustomValidity('Password must have 7 and more characters!!!');
            }else if(document.getElementById("pa2").validity.rangeOverflow){
                document.getElementById("pa2").setCustomValidity('Password must have 10 or less characters!!!');

            }
        });

        function equalPasswords(){
            // if(p1.value !== p2.value){
            //     p2.setCustomValidity("Passwords are not equal!");
            //     p1.innerHTML = "Passwords are not equal!";
            //     p1.className = "Passwords are not equal!";
            //
            //     p2.innerHTML = "Passwords are not equal!";
            //     p2.className = "Passwords are not equal!";
            //     event.preventDefault();
            // }
            if (document.getElementById('pa1').value != document.getElementById('pa2').value) {
                document.getElementById('pa2').setCustomValidity('Passwords must match.');
            } else {
                document.getElementById('pa2').setCustomValidity('');
            }
        }

    </script>
    <%--==========================================================================--%>
</main>
</body>
</html>
