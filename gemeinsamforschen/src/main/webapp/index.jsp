<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gemeinsam Forschen</title>
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
    <form method="post" action="rest/user/exists">
        <h2 class="sr-only">Login Formular</h2>
        <div class="illustration"><img src="libs/img/fides-logo.svg"></div>
        <div class="form-group"><input class="form-control" type="email" name="email" placeholder="Email" autofocus>
        </div>
        <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">login</button>
            <!-- scriptlets are terrible. Just tmp for porting the php -->
            <%
                String message = "";

                String userExists = request.getParameter("userExists");
                if (userExists != null) {
                    message = "Nutzer oder Passwort inkorrekt";
                }

                String loginError = request.getParameter("loginError");
                if (loginError != null) {
                    message = "Login bei RocketChat fehlgeschlagen! Bitte kontaktieren Sie den Administrator";
                }

                if (!message.isEmpty()) {
                    try {
                        PrintWriter p = response.getWriter();
                        p.println(
                                "<div class=\"alert alert-danger\" role=\"alert\"> " + message + "</div>");
                    } finally {

                    }
                }
            %>
        </div>
        <a href="register.jsp" class="forgot">registrieren</a></form>

</div>

</body>

</html>