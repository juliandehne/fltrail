<?php
include_once '../database/tokenSetter.php';
include_once '../database/config.php';
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
    <link rel="stylesheet" href="../assets/css/enrollment.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="../assets/js/config.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/showProjects.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>

</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>
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
    <script>
        /* When the user clicks on the button,
         toggle between hiding and showing the dropdown content */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        function filterFunction() {
            var input, filter, ul, li, a, i;
            input = document.getElementById("projectName");
            filter = input.value.toUpperCase();
            div = document.getElementById("myDropdown");
            a = div.getElementsByTagName("a");

            for (i = 0; i < a.length; i++) {
                if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
                    a[i].style.display = "";
                } else {
                    a[i].style.display = "none";
                }
            }
        }

        function writeProjectName(projectName) {
            document.getElementById('projectName').value = projectName;

            document.getElementById("myDropdown").classList.toggle("show");
        }

    </script>
    <div id="projectNameFieldset" tabindex="1" onblur="myFunction()" >
        <fieldset>
            <legend style="margin-left:13px;">Projektnamen</legend>
            <div class="dropdown">
                <input class="form-control" type="text" id="projectName" onfocus="myFunction()"
                       onkeyup="filterFunction()" name="Project"
                       required=""
                       placeholder="Projekt1" autofocus=""
                       style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
                <div id="myDropdown" class="dropdown-content">
                    <?php

                    $db->query("use fltrail;");
                    $query = "SELECT (u.id) from projects u;";
                    $queryObj = mysqli_query($db, $query);
                    while ($row = mysqli_fetch_object($queryObj)) {
                        echo '<a onclick="writeProjectName(' . "'" . $row->id . "'" . ');">' . $row->id . '</a>';
                    }
                    ?>
                </div>
            </div>
            <div class="alert alert-warning" role="alert" id="projectIsMissing">
                Dieser Projektname existiert nicht.
            </div>
        </fieldset>
    </div>

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