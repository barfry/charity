<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
</head>
<body>
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name != null}">
        <nav class="container container--70">
            <ul class="nav--actions">
                <li class="logged-user">
                    Witaj Agata
                    <ul class="dropdown">
                        <li><a href="#">Profil</a></li>
                        <li><a href="#">Moje zbiórki</a></li>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <li><a href="#">Panel admina</a></li>
                        </sec:authorize>
                        <li><a href="#">Wyloguj</a></li>
                    </ul>
                </li>
            </ul>

            <ul>
                <li><a href="index.html" class="btn btn--without-border active">Start</a></li>
                <li><a href="index.html#steps" class="btn btn--without-border">O co chodzi?</a></li>
                <li><a href="index.html#about-us" class="btn btn--without-border">O nas</a></li>
                <li><a href="index.html#help" class="btn btn--without-border">Fundacje i organizacje</a></li>
                <li><a href="index.html#contact" class="btn btn--without-border">Kontakt</a></li>
            </ul>
        </nav>

        <div class="slogan container container--90">
            <div class="slogan--item">
                <h1>
                    Oddaj rzeczy, których już nie chcesz<br />
                    <span class="uppercase">potrzebującym</span>
                </h1>

                <div class="slogan--steps">
                    <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                    <ul class="slogan--steps-boxes">
                        <li>
                            <div><em>1</em><span>Wybierz rzeczy</span></div>
                        </li>
                        <li>
                            <div><em>2</em><span>Spakuj je w worki</span></div>
                        </li>
                        <li>
                            <div><em>3</em><span>Wybierz fundację</span></div>
                        </li>
                        <li>
                            <div><em>4</em><span>Zamów kuriera</span></div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>f
    </c:when>
    <c:otherwise>
        <nav class="container container--70">
            <ul class="nav--actions">
                <li><a href="" class="btn btn--small btn--without-border">Zaloguj</a></li>
                <li><a href="#" class="btn btn--small btn--highlighted">Załóż konto</a></li>
            </ul>

            <ul>
                <li><a href="#" class="btn btn--without-border active">Start</a></li>
                <li><a href="#" class="btn btn--without-border">O co chodzi?</a></li>
                <li><a href="#" class="btn btn--without-border">O nas</a></li>
                <li><a href="#" class="btn btn--without-border">Fundacje i organizacje</a></li>
                <li><a href="#" class="btn btn--without-border">Kontakt</a></li>
            </ul>
        </nav>
        <div class="slogan container container--90">
            <div class="slogan--item">
                <h1>
                    Zacznij pomagać!<br/>
                    Oddaj niechciane rzeczy w zaufane ręce
                </h1>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>