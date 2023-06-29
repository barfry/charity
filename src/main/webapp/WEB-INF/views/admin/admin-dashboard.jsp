<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Admin dashboard</title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css"/>"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<header>
    <!-- Sidebar -->
    <div class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark" style="width: 10vw; height: 100vh">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <span class="fs-4 h3">Admin dashboard</span>
        </a>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li class="nav-item">
                <a href="#" class="nav-link text-white h4">
                    Main page
                </a>
            </li>
            <li>
                <a href="/admin/institutions" class="nav-link text-white h4">
                    Institutions
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white h4">
                    Donations
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white h4">
                    Categories
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white h4">
                    Users
                </a>
            </li>
            <li>
                <a href="#" class="nav-link text-white h4">
                    Admins
                </a>
            </li>
        </ul>
        <hr>
    </div>
</header>

<script src="<c:url value="${pageContext.request.contextPath}/resources/js/app.js"/>"></script>
<script src="/docs/5.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>