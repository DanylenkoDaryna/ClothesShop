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
                    <form id="editAccount_form" action="controller" method="post">
                        <h4>Add Product info</h4>
                        <input type="hidden" name="command" value="editAccount"/>
                        <fieldset>
                            <label>Brand
                                <input name="Brand"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Price
                                <input name="Price"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Available items
                                <input name="Available items"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Available size
                                <input name="Available size"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Colours
                                <input name="Colours"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Materials
                                <input name="Materials"/>
                            </label>
                        </fieldset>
                        <fieldset>
                            <label>Release Date
                                <input type="date" name="Release Date"/>
                            </label>
                        </fieldset>
                        <input type="reset">
                        <input type="submit" value="Create" >
                    </form>
                    </div>
                </div>
            </div>

        </section>
    </div>
</main>

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



