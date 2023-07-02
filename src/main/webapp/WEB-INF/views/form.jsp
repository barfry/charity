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
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/style.css"/>"/>
</head>
<body>
<header class="header--form-page">
    <c:import url="fragments/header.jsp"/>


    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br/>
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
    </div>
</header>

<section class="form--steps">
    <div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1" class="active">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter">Krok <span>1</span>/4</div>

        <form:form method="post" modelAttribute="donation" id="myForm">
            <!-- STEP 1: class .active is switching steps -->
            <div data-step="1" class="active">
                <h3>Zaznacz co chcesz oddać:</h3>
                <c:forEach items="${categories}" var="category" varStatus="loop">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input id="category" type="checkbox" name="categoryList"
                                   value="${category.id}"
                                   title="${category.name}"
                                   <c:if test="${fn:contains(donation.categoryList, category)}">checked="checked"
                            </c:if>/>
                            <span class="checkbox"></span>
                            <span class="description">${category.name}</span>
                        </label>
                    </div>
                </c:forEach>
                <form:errors path="categoryList" cssStyle="color: red"/>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 2 -->
            <div data-step="2">
                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>

                <div class="form-group form-group--inline">
                    <label>
                        Liczba 60l worków:
                        <form:input id="quantity" path="quantity" type="number" name="bags" step="1" min="1"/>
                    </label>
                </div>
                <form:errors path="quantity" cssStyle="color: red"/>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>


            <!-- STEP 4 -->
            <div data-step="3">
                <h3>Wybierz organizacje, której chcesz pomóc:</h3>
                <br/>
                <c:forEach items="${institutions}" var="institution">
                    <div class="form-group form-group--checkbox">
                        <label>
                            <input id="institution" type="radio" name="institution" value="${institution.id}"
                                   title='${institution.name}' <c:if test="${fn:contains(donation.institution, institution)}">checked="checked"
                            </c:if>/>
                            <span class="checkbox radio"></span>
                            <span class="description">
                  <div class="title">${institution.name}</div>
                  <div class="subtitle">
                    Cel i misja: ${institution.description}
                  </div>
                </span>
                        </label>
                    </div>
                </c:forEach>
                <form:errors path="institution" cssStyle="color: red"/>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step">Dalej</button>
                </div>
            </div>

            <!-- STEP 5 -->
            <div data-step="4">
                <h3>Podaj adres oraz termin odbioru rzecz przez kuriera:</h3>

                <div class="form-section form-section--columns">
                    <div class="form-section--column">
                        <h4>Adres odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label> Ulica <form:input path="street" id="street" type="text" name="address"/> </label>
                            <form:errors path="street" cssStyle="color: red"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label> Miasto <form:input path="city" id="city" type="text" name="city"/> </label>
                            <form:errors path="city" cssStyle="color: red"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Kod pocztowy <form:input path="zipCode" id="zipCode" type="text" name="postcode"/>
                            </label>
                            <form:errors path="zipCode" cssStyle="color: red"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Numer telefonu <form:input path="phoneNumber" id="phoneNumber" type="phone"
                                                           name="phone"/>
                            </label>
                            <form:errors path="phoneNumber" cssStyle="color: red"/>
                        </div>
                    </div>

                    <div class="form-section--column">
                        <h4>Termin odbioru</h4>
                        <div class="form-group form-group--inline">
                            <label> Data <form:input path="pickUpDate" id="pickUpDate" type="date"
                                                     name="data"/> </label>
                            <form:errors path="pickUpDate" cssStyle="color: red"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label> Godzina <form:input path="pickUpTime" id="pickUpTime" type="time"
                                                        name="time"/> </label>
                            <form:errors path="pickUpTime" cssStyle="color: red"/>
                        </div>

                        <div class="form-group form-group--inline">
                            <label>
                                Uwagi dla kuriera
                                <form:textarea path="pickUpComment" id="pickUpComment" name="more_info" rows="5"/>
                            </label>
                            <form:errors path="pickUpComment" cssStyle="color: red"/>
                        </div>
                    </div>
                </div>
                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <button type="button" class="btn next-step" onclick="formSumUp();">Dalej</button>
                </div>
            </div>

            <!-- STEP 6 -->
            <div data-step="5">
                <h3>Podsumowanie Twojej darowizny</h3>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text" id="summary--quantity"
                                ></span
                                >
                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text" id="summary--institution"
                                ></span
                                >
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li id="summary--street"></li>
                                <li id="summary--city"></li>
                                <li id="summary--zipCode"></li>
                                <li id="summary--phoneNumber"></li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li id="summary--pickUpDate"></li>
                                <li id="summary--pickUpTime"></li>
                                <li id="summary--pickUpComment"></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                    <button type="button" class="btn prev-step">Wstecz</button>
                    <input type="submit" value="Wyślij"/>
                </div>
            </div>
        </form:form>
    </div>
</section>
<footer>
    <c:import url="fragments/footer.jsp"/>
</footer>

<script src="<c:url value="${pageContext.request.contextPath}/resources/js/app.js"/>"></script>
<script>
    function formSumUp() {
        debugger

        var categories = document.querySelectorAll('input[type="checkbox"]:checked');
        var input = "";
        for (var checkbox of categories) {
            input += checkbox.title + ' '
        }
        var quantity = document.getElementById("quantity").value;
        var institution = document.querySelector('input[name="institution"]:checked').title;
        var street = document.getElementById("street").value;
        var city = document.getElementById("city").value;
        var zipCode = document.getElementById("zipCode").value;
        var phoneNumber = document.getElementById("phoneNumber").value;
        var pickUpDate = document.getElementById("pickUpDate").value;
        var pickUpTime = document.getElementById("pickUpTime").value;
        var pickUpComment = document.getElementById("pickUpComment").value;

        document.getElementById("summary--quantity").innerHTML = quantity + " worki " + input;
        document.getElementById("summary--institution").innerHTML = institution;
        document.getElementById("summary--street").innerHTML = street;
        document.getElementById("summary--city").innerHTML = city;
        document.getElementById("summary--zipCode").innerHTML = zipCode;
        document.getElementById("summary--phoneNumber").innerHTML = phoneNumber;
        document.getElementById("summary--pickUpDate").innerHTML = pickUpDate;
        document.getElementById("summary--pickUpTime").innerHTML = pickUpTime;
        document.getElementById("summary--pickUpComment").innerHTML = pickUpComment;

    }
</script>
</body>
</html>