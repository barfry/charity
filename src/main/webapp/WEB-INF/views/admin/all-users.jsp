<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Użytkownicy</title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css"/>"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="page-container">
    <div class="content-wrap">
        <div class="row">
            <div class="col-2">
                <c:import url="fragments/sidebar.jsp"/>
            </div>
            <div class="col-10">
                <h1>Użytkownicy</h1>
                <table class="table table-sm table-hover table-dark table-bordered text-center table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Imię</th>
                        <th scope="col">Nazwisko</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Ilość podarunków</th>
                        <th scope="col">Aktywny</th>
                        <th scope="col">Zweryfikowany</th>
                        <th scope="col">Funkcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.donations.size()}</td>
                            <td><c:choose>
                                <c:when test="${user.enabled == true}">
                                    Tak
                                </c:when>
                                <c:otherwise>
                                    Nie
                                </c:otherwise>
                            </c:choose></td>
                            <td><c:choose>
                                <c:when test="${user.verified == true}">
                                    Tak
                                </c:when>
                                <c:otherwise>
                                    Nie
                                </c:otherwise>
                            </c:choose></td>
                            <td>
                                <a class="btn btn-primary" href="/admin/users/edit-user?id=${user.id}">Edytuj</a>
                                <form:form action="/admin/users/remove-user" method="post"
                                           cssClass="d-flex justify-content-center m-2">
                                    <input type="hidden" name="id" value="${user.id}"/>
                                    <input type="submit" value="Usuń" class="btn btn-primary"
                                           data-confirm-delete="Proszę potwierdzić usunięcie użytkownika ${user.firstName} ${user.lastName} ${user.email}"
                                           onclick="if (!confirm(this.getAttribute('data-confirm-delete'))) return false"/>
                                </form:form>
                                <c:choose>
                                    <c:when test="${user.enabled == true}">
                                        <form:form action="/admin/users/block-user" method="post"
                                                   cssClass="d-flex justify-content-center m-2">
                                            <input type="hidden" name="id" value="${user.id}"/>
                                            <input type="submit" value="Zablokuj" class="btn btn-primary"
                                                   data-confirm-block="Proszę potwierdzić zablokowanie użytkownika ${user.firstName} ${user.lastName} ${user.email}"
                                                   onclick="if (!confirm(this.getAttribute('data-confirm-block'))) return false"/>
                                        </form:form>
                                    </c:when>
                                    <c:otherwise>
                                        <form:form action="/admin/users/unblock-user" method="post"
                                                   cssClass="d-flex justify-content-center m-2">
                                            <input type="hidden" name="id" value="${user.id}"/>
                                            <input type="submit" value="Odblokuj" class="btn btn-primary"
                                                   data-confirm-unblock="Proszę potwierdzić odblokowanie użytkownika ${user.firstName} ${user.lastName} ${user.email}"
                                                   onclick="if (!confirm(this.getAttribute('data-confirm-unblock'))) return false"/>
                                        </form:form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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