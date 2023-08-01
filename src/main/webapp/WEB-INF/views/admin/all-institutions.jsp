<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Fundacje</title>
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
                <h1>Fundacje</h1>
                <a class="btn btn-primary my-4" href="/admin/institutions/add-new-institution">Dodaj nową fundację</a>
                <div class="tableFixHead">
                <table class="table table-sm table-hover table-dark table-bordered text-center table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Nazwa</th>
                        <th scope="col">Opis</th>
                        <th scope="col">Funkcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${institutions}" var="institution">
                        <tr>
                            <td>${institution.id}</td>
                            <td>${institution.name}</td>
                            <td>${institution.description}</td>
                            <td>
                                <a class="btn btn-primary m-2" href="/admin/institutions/edit-institution?id=${institution.id}">Edytuj</a>
                                <form:form action="/admin/institutions/remove-institution" method="post"
                                           cssClass="d-flex justify-content-center">
                                    <input type="hidden" name="id" value="${institution.id}" />
                                    <input type="submit" value="Usuń" class="btn btn-primary"
                                           data-confirm-delete="Proszę potwierdzić usunięcie fundacji: ${institution.name}"
                                           onclick="if (!confirm(this.getAttribute('data-confirm-delete'))) return false" />
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