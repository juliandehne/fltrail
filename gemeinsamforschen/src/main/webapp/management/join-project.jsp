<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gruppenmatcher</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="js/config.js"></script>
    <script src="js/utility.js"></script>
    <script src="js/showProjects.js"></script>
    <script src="js/GETfile.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/Sidebar-Menu.js"></script>

</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>
<div class="loader-inactive" id="loader"></div>
<div id="wrapper" class="wrapper" style="margin:0px;">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
            <div class="row">
                <div class="col-md-12">
                    <h3>Tragen sie sich in ein neues Projekt ein. </h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>
    <fieldset>
        <legend style="margin-left:13px;">Projektnamen</legend>
        <input class="form-control" type="text" id="projectName" name="Project" required=""
               placeholder="Projekt1" autofocus=""
               style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        <div class="alert alert-warning" role="alert" id="projectIsMissing">
            Dieser Projektname existiert nicht.
        </div>

    </fieldset>
    <fieldset>
        <legend style="margin-left:13px;">Passwort</legend>
        <input class="form-control" type="password" id="projectPassword" name="Password" required=""
               placeholder="******"
               style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        <div class="alert alert-warning" role="alert" id="projectWrongPassword">
            Falsches Passwort.
        </div>
    </fieldset>
    <button id="seeProject" class="btn btn-primary">Einsehen</button>
</div>
</body>

</html>