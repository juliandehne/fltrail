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
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="../assets/js/config.js"></script>
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/createPreferences.js"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="../assets/js/Sidebar-Menu.js"></script>
</head>
<body>


<p id="user"><?php echo $userName; ?></p>
<p id="projectNameHidden"><?php echo $projectName; ?></p>
<div class="block-screen-inactive" id="block-screen">
    <div class="loader-inactive" id="loader">
        <div class="sk-cube1 sk-cube"></div>
        <div class="sk-cube2 sk-cube"></div>
        <div class="sk-cube4 sk-cube"></div>
        <div class="sk-cube3 sk-cube"></div>
    </div>
</div>

<div id="wrapper" class="wrapper">
    <?php
    include_once 'menu.php'
    ?>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h3>Geben Sie hier ihre Präferenzen für das Projekt <?php echo $projectName; ?> ein!</h3>
                    <div class="page-header"></div>
                </div>
            </div>
        </div>
    </div>

    <fieldset>
        <legend>Lernziele</legend>
        <div id="competencies">
            <input class="form-control" type="text" id="competencies0" name="competencies" required="" placeholder="Ich möchte folgendes lernen:">
        </div>
        <button
                class="btn btn-default" type="button"
                id="addCompetenceButton">+
        </button>
        <button
                class="btn btn-default" type="button"
                id="subtractCompetenceButton">-
        </button>

    </fieldset>
    <fieldset>
        <legend>Forschungsfrage</legend>
        <div id="researchQuestion">
            <input class="form-control" id="researchQuestion0" type="text" name="researchQuestion" required="" placeholder="Meine Forschungsfrage(n): ">
        </div>
        <button class="btn btn-default" type="button" id="addResearchQuestionButton">+
        </button>
        <button class="btn btn-default" type="button" id="subtractCResearchQuestionButton">-
        </button>
    </fieldset>
    <fieldset>
        <legend>Tags</legend>
        <p  class="alert alert-warning">Wähle 2 der hier angegebenen Tags aus, die am ehesten zu deiner Forschungsfrage passen.</p>
        <div id="tags">

        </div>
    </fieldset>
    <button class="btn btn-primary" id="studentFormSubmit">
        eintragen
    </button>
    <div class="alert alert-warning" role="alert">
        Das Verarbeiten der Lernziele und das Gruppenmatching kann einen Moment dauern!
    </div>
</div>

</body>




