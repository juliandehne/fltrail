<?php
include_once '../database/tokenSetter.php';
?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>dozent-view-alternativ slider</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700">
    <link rel="stylesheet" href="../assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="../assets/css/Navigation-with-Button1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu1.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link rel="stylesheet" type="text/css" href="../assets/jQuery-Tags-Input-master/src/jquery.tagsinput.css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript" src="../assets/jQuery-Tags-Input-master/src/jquery.tagsinput.js"></script>
    <script src="../assets/js/newProject.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
</head>

<body>
<p id="user" hidden><?php echo $userName; ?></p>
<div class="loader inactive" id="loader"></div>

<div id="wrapper" style="margin:0px;">
    <div id="sidebar-wrapper" style="width:190px;">
        <ul class="sidebar-nav" style="width:200px;margin-top:50px;">
            <li><h3 style="color:white;"><?php echo $userName; ?></h3></li>
            <li style="width:146px;"><a href="newproject.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt erstellen</a></li>

            <li style="width:146px;"><a href="enrollment.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekt beitreten</a>
            </li>
            <li style="width:146px;"><a href="projects.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;">Projekte anzeigen</a></li>
            <li style="width:146px;"><a href="mygroups.php?token=<?php echo $token ?>" style="margin-top:32px;width:200px;"> Gruppen anzeigen</a></li>
            <li style="width:146px;"><a href="../index.php" style="margin-top:134px;width:200px;">Logout </a>
            </li>
        </ul>
    </div>
    <div class="page-content-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div>
                            <p></p>
                            <h3> Erstellen Sie ein neues Projekt.</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="contact-clean">
        <div style="margin-left:336px;">
            <p>Name des Projekts</p>
            <div class="alert alert-warning" role="alert" id="projectNameExists">
                Dieser Projektname exisitiert bereits.
            </div>
            <div class="alert alert-warning" role="alert" id="projectIsMissing">
                Tragen sie einen Projektnamen ein.
            </div>
            <div class="form-group"><input class="form-control" type="text" name="name" placeholder="Name" style="width:286px;margin-left:50px;" id="nameProject"></div>
            <p>Passwort (optional) </p>
            <div class="form-group"><input class="form-control" type="email" name="password" placeholder="Passwort" style="width:287px;margin-left:51px;" id="passwordProject"></div>
            <p>Admin-Passwort</p>
            <div class="form-group"><input class="form-control" type="email" name="adminpassword" placeholder="Passwort" style="width:287px;margin-left:51px;" id="adminPassword"></div>
            <p>Tags </p>
            <div id="tagHelper">
                Fügen sie zudem 5 Tags zu ihrem Projekt hinzu, welche ihr Projekt inhaltlich umreißen.
            </div>
            <div class="form-group"><input class="tags" data-role="tags" name="Tags" placeholder="Tags" id="tagsProject"></div>
            <label>An Kurs selbst teilnehmen <input type="checkbox" id="Teilnehmer"></label>

            <div class="form-group"><button class="btn btn-primary" style="margin-left:129px;" id="sendProject">erstellen </button></div>
        </div>
    </div>

</body>

</html>