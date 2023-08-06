<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head></head>
<body>
<section id="contact" class="contact">
    <h2>Skontaktuj się z nami</h2>
    <h3>Formularz kontaktowy</h3>
    <form:form action="/message-us" method="post" class="form--contact" modelAttribute="messageDTO">
        <div class="form-group form-group--50">
            <form:input path="firstName" type="text" id="firstName" name="firstName" placeholder="Imię"/>
            <div>
                <form:errors path="firstName" cssStyle="color: red"/>
            </div>
        </div>
        <div class="form-group form-group--50">
            <form:input path="lastName" type="text" id="lastName" name="lastName" placeholder="Nazwisko"/>
            <div>
                <form:errors path="lastName" cssStyle="color: red"/>
            </div>
        </div>
        <div class="form-group">
            <form:textarea path="message" id="message" name="message" placeholder="Wiadomość" rows="4"/>
            <div>
                <form:errors path="message" cssStyle="color: red"/>
            </div>
        </div>
        <form:button class="btn" type="submit">Wyślij</form:button>
    </form:form>
</section>
<div class="bottom-line">
    <span class="bottom-line--copy">Copyright &copy; 2018</span>
    <div class="bottom-line--icons">
        <a href="#" class="btn btn--small"><img src="<c:url value="resources/images/icon-facebook.jpg"/>"/></a>
        <a href="#" class="btn btn--small"><img src="<c:url value="resources/images/icon-instagram.jpg"/>"/></a>
    </div>
</div>
</body>
</html>