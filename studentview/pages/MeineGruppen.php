<?php
include_once '../database/tokenSetter.php';
?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>student-form-design</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../assets/js/myGroupsInit.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>


</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>

<div id="wrapper" style="margin:0px;">
    <div id="sidebar-wrapper" style="width:190px;">
        <ul class="sidebar-nav" style="width:200px;margin-top:50px;">
            <li><h3 style="color:white;"><?php echo $userName; ?></h3></li>
            <li style="width:146px;"><a href="neuesProjekt.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt erstellen</a></li>

            <li style="width:146px;"><a href="Projekte.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt beitreten</a>
            </li>
            <li style="width:146px;"><a href="overview.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekte anzeigen</a></li>
            <li style="width:146px;"><a href="MeineGruppen.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;"> Gruppen anzeigen</a></li>
            <li style="width:146px;"><a href="../index.php" style="margin-top:134px;width:200px;">Logout </a>
            </li>
        </ul>
    </div>
    <div class="page-content-wrapper">
            <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
                <div class="row">
                    <div class="col-md-12">
                        <p>Hier können sie ihre Gruppen zu allen Projekten einsehen. Klicken sie dafür auf das Dropdownmenü um ihren Kurs auszuwählen.</p>
                        <div class="page-header"></div>
                    </div>
                </div>
            </div>
        </div>

    <div>
        <div class="container" style="margin-left:0px;">
            <div class="row">
                <div class="col-md-offset-3 col-sm-8 col-xs-1">
                    <h3>Projekt:</h3>
                    <div class="dropdown">
                    <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" id="projectDropdown">default Button
                        <span class="caret"></span></button>
                    <ul class="dropdown-menu" id="dropdownOptions">
                        <!--
                        <button onclick="showProject('12345',document.getElementById('user').innerHTML);" class="dropdown-item" type="button">12345</button>
                        <button onclick="showProject('Projekt2',document.getElementById('user').innerHTML);" class="dropdown-item" type="button">Projekt2</button>
                        <button onclick="showProject('Projekt3',document.getElementById('user').innerHTML);" class="dropdown-item" type="button">Projekt3</button>
                        -->
                    </ul>
                </div>
                </div>
            </div>
            <div class="row">
                <div><strong id="student2">Student 2 student@uni-da.de</strong></div>
                <div class="col-md-4" style="width:485px;">
                </div>
            </div>
            <div class="row">
                <div><strong id="student3">Student 3</strong></div>
                <div class="col-md-4" style="width:485px;">
                </div>
            </div>
            <div class="row">
                <div><strong id="student4">Student 4</strong></div>
                <div class="col-md-4" style="width:485px;">
                </div>
            </div>
            <div class="row">
                <div><strong id="student5">Student 5</strong></div>
                <div class="col-md-4" style="width:485px;">
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>