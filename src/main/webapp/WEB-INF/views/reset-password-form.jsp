<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Reset hasła</title>
    <base href="/">
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>
<section class="login-page">
    <h2>Reset hasła</h2>
    <form:form action="/reset-password" method="post" modelAttribute="changePasswordDTO">
        <input type="hidden" name="token" id="token" value="${token}"/>
        <div class="form-group">
            <form:input path="newPassword" type="password" name="newPassword" placeholder="Nowe hasło"/>
            <div><form:errors path="newPassword" cssStyle="color: red"/></div>
        </div>
        <div class="form-group">
            <form:input path="confirmPassword" type="password" name="confirmPassword"
                        placeholder="Powtórz hasło"/>
            <div><form:errors path="confirmPassword" cssStyle="color: red"/></div>
        </div>

        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zapisz</button>
        </div>
    </form:form>
</section>
<footer>
    <div class="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <form>
            <div class="form-group form-group--50">
                <input type="text" name="name" placeholder="Imię"/>
            </div>
            <div class="form-group form-group--50">
                <input type="text" name="surname" placeholder="Nazwisko"/>
            </div>

            <div class="form-group">
            <textarea
                    name="message"
                    placeholder="Wiadomość"
                    rows="1"
            ></textarea>
            </div>

            <button class="btn" type="submit">Wyślij</button>
        </form>
    </div>
    <div class="bottom-line">
        <span class="bottom-line--copy">Copyright &copy; 2018</span>
        <div class="bottom-line--icons">
            <a href="#" class="btn btn--small"
            ><img src="images/icon-facebook.svg"
            /></a>
            <a href="#" class="btn btn--small"
            ><img src="images/icon-instagram.svg"
            /></a>
        </div>
    </div>
</footer>
</body>
</html>
