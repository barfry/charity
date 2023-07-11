<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Instytucje</title>
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
                <h1>Formularz nowej instytucji</h1>
                <form:form method="post" modelAttribute="institution">
                    <form:hidden path="id" name="id"/>
                    <div class="form-group p-4">
                        <form:input path="name" type="name" name="name" placeholder="Nazwa"/>
                        <div>
                            <form:errors path="name" cssStyle="color: red"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:textarea rows="5" path="description" type="description" name="description" placeholder="Opis"/>
                        <div>
                            <form:errors path="description" cssStyle="color: red"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Zapisz</button>
                    </div>
                </form:form>
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