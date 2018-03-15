<?php
include_once '../database/tokenSetter.php';
if (!isset( $_GET['projectToken'])) {
    header("Location: ../enrollment.php");
}
$projectToken = $_GET['projectToken'];
$query = "SELECT (`id`) from projects u where u.token = '". $_GET['projectToken']."';";
//echo $query;
//echo $db->query($query);
$result = mysqli_query($db, $query);
$resultObj = mysqli_fetch_object($result);
$projectName = $resultObj->id;
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
    <script src="../assets/js/createPreferences.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
</head>
<body>


<p id="user" hidden><?php echo $userName; ?></p>
<p id="projectName" hidden><?php echo $projectName; ?></p>
<div class="loader inactive" id="loader"></div>
<div id="wrapper" class="wrapper" style="margin:0px;">
    <div id="sidebar-wrapper" style="width:190px;">
        <ul class="sidebar-nav" style="width:200px;margin-top:50px;">
            <li><h3 style="color:white;"><?php echo $userName; ?></h3></li>
            <li style="width:146px;"><a href="newproject.php?token=<?php echo $token ?>"
                                        style="margin-top:32px;width:200px;">Projekt erstellen</a></li>

            <li style="width:146px;"><a href="enrollment.php?token=<?php echo $token ?>"
                                        style="margin-top:32px;width:200px;">Projekt beitreten</a>
            </li>
            <li style="width:146px;"><a href="projects.php?token=<?php echo $token ?>"
                                        style="margin-top:32px;width:200px;">Projekte anzeigen</a></li>
            <li style="width:146px;"><a href="mygroups.php?token=<?php echo $token ?>"
                                        style="margin-top:32px;width:200px;"> Gruppen anzeigen</a></li>
            <li style="width:146px;"><a href="../index.php" style="margin-top:134px;width:200px;">Logout </a>
            </li>
        </ul>
    </div>
    <div class="page-content-wrapper">
        <div class="container-fluid"><a class="btn btn-link" role="button" href="#menu-toggle" id="menu-toggle"></a>
            <div class="row">
                <div class="col-md-12">
                    <h3>Geben Sie hier ihre Präferenzen für das Projekt <?php echo $projectName; ?> ein!</h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>

    <fieldset>
        <legend style="margin-left:13px;">Lernziele</legend>
        <div id="competencies">
            <input class="form-control" type="text" id="competencies0" name="competencies" required=""
                   placeholder="Ich möchte folgendes lernen:"
                   style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        </div>
        <button
                class="btn btn-default" type="button"
                style="margin-left:443px;margin-top:-88px;height:36px;width:33px;"
                id="addCompetenceButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="margin-left:10px;margin-top:-88px;height:36px;width:33px;"
                id="subtractCompetenceButton">-
        </button>

    </fieldset>
    <fieldset style="margin-bottom:-3px;">
        <legend style="margin-left:13px;">Forschungsfrage</legend>
        <div id="researchQuestion">
            <input class="form-control" id="researchQuestion0" type="text" name="researchQuestion" required=""
                   autofocus=""
                   placeholder="Meine Forschungsfrage(n): "
                   style="margin:0px;max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        </div>
        <button class="btn btn-default" type="button"
                style="margin-left:443px;margin-top:-88px;height:36px;width:33px;"
                id="addResearchQuestionButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                style="margin-left:10px;margin-top:-88px;height:36px;width:33px;"
                id="subtractCResearchQuestionButton">-
        </button>
    </fieldset>
    <fieldset>
        <legend style="margin-left:13px;">Tags</legend>
        Wähle 2 der hier angegebenen Tags aus, die am ehesten zu deiner Forschungsfrage passen.
        <div id="tags">

        </div>
    </fieldset>
    <button class="btn btn-primary" id="studentFormSubmit" style="width:90px;margin-left:169px;margin-top:13px;">
        Eintragen
    </button>

</body>




