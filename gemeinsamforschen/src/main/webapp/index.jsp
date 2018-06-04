<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gruppenmatcher</title>
    <link rel="stylesheet" href="core/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="core/assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="core/assets/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="core/assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="core/assets/css/Sidebar-Menu1.css">

    <script src="core/assets/js/jquery.min.js"></script>
    <script src="core/assets/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
<div class="login-clean">
    <form method="post" action="./servlet/userExists">
        <h2 class="sr-only">Login Formular</h2>
        <div class="illustration"><img src="core/assets/img/fides-logo.svg"></div>
        <div class="form-group"><input class="form-control" type="email" name="email" placeholder="Email" autofocus>
        </div>
        <div class="form-group"><input class="form-control" type="password" name="password" placeholder="Password">
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">login</button>
            <!-- scriptlets are terrible. Just tmp for porting the php -->
            <%
                String param = request.getParameter("userExists");
                if (param != null) {
                    try {
                        PrintWriter p = response.getWriter();
                        p.println(
                         "<div class=\"alert alert-danger\" role=\"alert\"> Nutzer oder Passwort inkorrekt</div>");
                    } finally {

                    }
                }
            %>
        </div>
        <a href="register.jsp" class="forgot">registrieren</a></form>

</div>

</body>

</html>