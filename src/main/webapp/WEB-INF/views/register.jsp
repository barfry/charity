<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pl">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Register form</title>
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>" />
  </head>
  <body>
    <header>
      <c:import url="fragments/header.jsp"/>
    </header>

    <section class="login-page">
      <h2>Załóż konto</h2>
      <form>
        <div class="form-group">
          <input type="email" name="email" placeholder="Email" />
        </div>
        <div class="form-group">
          <input type="password" name="password" placeholder="Hasło" />
        </div>
        <div class="form-group">
          <input type="password" name="password2" placeholder="Powtórz hasło" />
        </div>

        <div class="form-group form-group--buttons">
          <a href="login.html" class="btn btn--without-border">Zaloguj się</a>
          <button class="btn" type="submit">Załóż konto</button>
        </div>
      </form>
    </section>

    <footer>
      <div class="contact">
        <h2>Skontaktuj się z nami</h2>
        <h3>Formularz kontaktowy</h3>
        <form>
          <div class="form-group form-group--50">
            <input type="text" name="name" placeholder="Imię" />
          </div>
          <div class="form-group form-group--50">
            <input type="text" name="surname" placeholder="Nazwisko" />
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
