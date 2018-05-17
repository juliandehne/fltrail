<?php
include_once '../database/tokenSetter.php';
?>

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
    <script src="../assets/js/config.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/showProjects.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>

</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>
<div id="wrapper" class="wrapper">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h3>Tragen sie sich in ein neues Projekt ein. </h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>
        <fieldset>
            <legend>Projektnamen</legend>
            <input class="form-control" type="text" id="projectName" name="Project" required="" placeholder="Projekt1" autofocus="">
            <div class="alert alert-warning" role="alert" id="projectIsMissing">
                Dieser Projektname existiert nicht.
            </div>

        </fieldset>
        <fieldset>
            <legend>Passwort</legend>
            <input class="form-control" type="password" id="projectPassword" name="Password" required="" placeholder="******">
            <div class="alert alert-warning" role="alert" id="projectWrongPassword">
                Falsches Passwort.
            </div>
        </fieldset>
        <button id="seeProject" class="btn btn-primary">Einsehen</button>
</div>
</body>

</html>