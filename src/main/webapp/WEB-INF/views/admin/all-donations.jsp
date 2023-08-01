<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Podarunki</title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css"/>"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="page-container">
    <div class="content-wrap">
        <div class="row" style="width: 100%">
            <div class="col-2">
                <c:import url="fragments/sidebar.jsp"/>
            </div>
            <div class="col-10" >
                <h1>Podarunki</h1>
                <div class="tableFixHead">
                    <table class="table table-sm table-hover table-dark table-bordered text-center table-responsive">
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
                                        <fmt:parseDate value="${ donation.collectionDateTime}" pattern="y-M-dd'T'H:m"
                                                       var="myParseDate"></fmt:parseDate>
                                        <fmt:formatDate value="${myParseDate}"
                                                        pattern="dd.MM.yyyy HH:mm"></fmt:formatDate>
                                    </c:when>
                                    <c:otherwise>
                                        Nie
                                    </c:otherwise>
                                </c:choose></td>
                                <td>
                                    <c:if test="${donation.collected == false}">
                                        <form:form action="/admin/donations/collect-donation" method="post"
                                                   cssClass="d-flex justify-content-center">
                                            <input type="hidden" name="id" value="${donation.id}"/>
                                            <input type="submit" value="Oznacz jako odebrane" class="btn btn-primary"
                                                   data-confirm-collect="Proszę potwierdzić odebranie podarunku id: ${donation.id}"
                                                   onclick="if (!confirm(this.getAttribute('data-confirm-collect'))) return false"/>
                                        </form:form>
                                    </c:if>
                                    <form:form action="/admin/donations/remove-donation" method="post"
                                               cssClass="d-flex justify-content-center">
                                        <input type="hidden" name="id" value="${donation.id}"/>
                                        <input type="submit" value="Usuń" class="btn btn-primary"
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
    </div>
</div>
<script src="<c:url value="${pageContext.request.contextPath}/resources/js/app.js"/>"></script>
<script src="/docs/5.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>