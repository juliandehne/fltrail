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
    <script src="../assets/js/config.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/myGroupsInit.js"></script>
    <script src="../assets/js/getProjects.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>

</head>

<body>
<p id="user"><?php echo $userName; ?></p>

<div id="wrapper">
    <?php
    include_once 'menu.php'
    ?>
    <div>
        <div class="container">
            <div class="row">
                <div class="col-md-offset-3 col-sm-8 col-xs-1">
                    <h3>Projekt</h3>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown" id="projectDropdown">Projekt auswählen
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu" id="dropdownOptions">
                        </ul>
                    </div>
                </div>
            </div>

            <div>
                <button class="btn btn-info" id="rearrangeButton" type="button" onclick="$('#passwordDiv').show();$('#rearrangeButton').hide();">umverteilen</button>

                <div class="alert alert-info" id="passwordDiv" style="margin-top:10px;">
                    <p>Geben sie das Adminpasswort ein:</p>
                    <input id="adminPassword" placeholder="****">
                    <button class="btn btn-info" type="button" onClick="checkAuthor()">umverteilen</button>
                    <div class="alert alert-danger" style="width:100%;margin-top:5px;" id="wrongPasswordDiv">
                        <p> Das Passwort ist inkorrekt. </p>
                    </div>
                </div>

            </div>

            <div id="tablesHolder"></div>
        </div>
    </div>
</div>
</body>

</html>