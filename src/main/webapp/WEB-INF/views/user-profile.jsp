<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Profil użytkownika</title>
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <c:import url="fragments/header.jsp"/>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Dane użytkownika
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Imię: ${userDto.firstName}</div>
                <div class="slogan--steps-title">Nazwisko: ${userDto.lastName}</div>
                <div class="slogan--steps-title">E-mail: ${userDto.email}</div>
                <div class="slogan--steps-title">
                    <a href="/user/edit-profile" class="btn btn-close-white" style="margin-right: 50px">Edytuj dane</a><a href="/user/change-password" class="btn btn-close-white">Zmień hasło</a>
                </div>
            </div>
        </div>
    </div>
</header>
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
