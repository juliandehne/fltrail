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
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../assets/js/myGroupsInit.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>


</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>

<div id="wrapper" style="margin:0px;">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
            <div class="row">
                <div class="col-md-12">

                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>

    <div>
        <div class="container" style="margin-left:0px;">
            <div class="row">
                <div class="col-md-offset-3 col-sm-8 col-xs-1">
                    <h3>Projekt</h3>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"
                                id="projectDropdown">Projekt auswählen
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu" id="dropdownOptions">
                        </ul>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-bordered table-list" style="width: 40%;margin-top: 10px;">
                <thead id="tableHead">
                <tr>
                    <th class="hidden-xs">Student</th>
                    <th>E-Mail</th>
                </tr>
                </thead>
                <tbody>
                <tr id="student2">

                </tr>
                <tr id="student3">

                </tr>
                <tr id="student4">

                </tr>
                <tr id="student5">

                </tr>

                </tbody>

            </table>
            <p>Hier können sie ihre Gruppen zu allen Projekten einsehen. Klicken sie dafür auf das Dropdownmenü
                um ihren Kurs auszuwählen.</p>
        </div>
    </div>
</div>
</body>

</html>