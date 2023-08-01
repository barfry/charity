<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Podarunki</title>
    <base href="/">
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <c:import url="fragments/header.jsp"/>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Podarunki
            </h1>

            <div class="donations" style="height: 60vh; overflow-y: scroll">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Ilość</th>
                        <th scope="col">Kategorie</th>
                        <th scope="col">Fundacja</th>
                        <th scope="col">Ulica</th>
                        <th scope="col">Miasto</th>
                        <th scope="col">Kod pocztowy</th>
                        <th scope="col">Data odbioru</th>
                        <th scope="col">Godzina odbioru</th>
                        <th scope="col">Numer telefonu</th>
                        <th scope="col">Komentarz</th>
                        <th scope="col">Odebrano</th>
                        <th scope="col">Funkcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${donations}" var="donation">
                        <tr>
                            <td>${donation.id}</td>
                            <td>${donation.quantity}</td>
                            <td>
                                <ul>
                                    <c:forEach items="${donation.categoryList}" var="category">
                                        <li>${category.name}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td>${donation.institution.name}</td>
                            <td>${donation.street}</td>
                            <td>${donation.city}</td>
                            <td>${donation.zipCode}</td>
                            <td>${donation.pickUpDate}</td>
                            <td>${donation.pickUpTime}</td>
                            <td>${donation.phoneNumber}</td>
                            <td>${donation.pickUpComment}</td>
                            <td><c:choose>
                                <c:when test="${donation.collected == true}">
                                    <fmt:parseDate value="${ donation.collectionDateTime}" pattern="y-M-dd'T'H:m" var="myParseDate"></fmt:parseDate>
                                    <fmt:formatDate value="${myParseDate}"  pattern="dd.MM.yyyy HH:mm"></fmt:formatDate >
                                </c:when>
                                <c:otherwise>
                                    Nie
                                </c:otherwise>
                            </c:choose></td>
                            <td>
                                <c:if test="${donation.collected == false}">
                                    <form:form action="/user/donations/collect-donation" method="post"
                                               cssClass="d-flex justify-content-center">
                                        <input type="hidden" name="id" value="${donation.id}"/>
                                        <input type="submit" value="Odebrano" class="btn btn-primary btn--small"
                                               data-confirm-collect="Proszę potwierdzić odebranie podarunku id: ${donation.id}"
                                               onclick="if (!confirm(this.getAttribute('data-confirm-collect'))) return false"/>
                                    </form:form>
                                </c:if>
                                <form:form action="/user/donations/remove-donation" method="post"
                                           cssClass="d-flex justify-content-center">
                                    <input type="hidden" name="id" value="${donation.id}"/>
                                    <input type="submit" value="Usuń" class="btn btn-primary btn--small"
                                           data-confirm-delete="Proszę potwierdzić usunięcie podarunku id: ${donation.id}"
                                           onclick="if (!confirm(this.getAttribute('data-confirm-delete'))) return false"/>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
