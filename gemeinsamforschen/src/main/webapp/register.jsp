<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>flTrail</title>
    <link rel="stylesheet" href="libs/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="libs/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="libs/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="libs/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="libs/css/Sidebar-Menu1.css">
    <script src="libs/jquery/jquery.min.js"></script>
    <script src="libs/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
<div class="login-clean">
    <%--<form method="post" action="./servlet/createUser">--%>
    <form method="post" action="rest/user/create">
        <h2 class="sr-only">Login Formular</h2>
        <div class="illustration"><img src="libs/img/fides-logo.svg"></div>
        <div class="form-group"><input class="form-control" name="name" placeholder="Name"></div>
        <div class="form-group"><input class="form-control" type="email" name="email" placeholder="Email"></div>
        <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password">
        </div>
        <div class="form-group"><label>Bitte kein sensibeles Passwort verwenden!</label></div>
        <!--<div class="form-group"><input class="form-control" type="password" name="password"
                                       placeholder="Password wiederholen"></div>-->
        <div class="form-group">
            <label>
                <input id="is_Student" class="form-control" type="checkbox" value="false" name="isStudent">Dozierender
            </label>
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">registrieren</button>
        </div>
        <div class="form-group">
            <!-- scriptlets are terrible. Just tmp for porting the php -->
            <%--     <%
                     String userExists = request.getParameter("userExists");
                     if (userExists != null) {
                         try (PrintWriter p = response.getWriter()) {
                             p.println(
                                     "<div class=\"alert alert-danger\" role=\"alert\"> Es existiert ein Nutzer mit dieser Email oder diesem Benutzernamen! </div>");
                         }
                     }
                 %>--%>

            <%
                String message = "";

                String userExists = request.getParameter("userExists");
                if (userExists != null) {
                    message = "Es existiert ein Nutzer mit dieser Email oder diesem Benutzernamen!";
                }

                String registrationError = request.getParameter("registrationError");
                if (registrationError != null) {
                    message = "Es ist ein Fehler beim Erstellen des Rocket Chat-Nutzers aufgetreten. Bitte kontaktieren Sie den Administrator";
                }

                if (!message.isEmpty()) {
                    try {
                        PrintWriter p = response.getWriter();
                        p.println(
                                "<div class=\"alert alert-danger\" role=\"alert\"> " + message + " </div>");
                    } finally {

                    }
                }
            %>
        </div>
        <a href="index.jsp" class="forgot">einloggen</a></form>
</div>

</body>

</html>