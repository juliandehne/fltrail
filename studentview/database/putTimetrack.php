<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:39
 */
include_once 'config.php';



// get the user data from the GET

// write it to the db

//if (isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest'){
    $projectID = urldecode($_GET['projectID']);
    $lernziele = urldecode($_GET['lernziele']);
    $forschungsfrage = urldecode($_GET['forschungsfrage']);
    $_POST = json_decode(file_get_contents('php://input'), true);
    $time = $_GET['dauer'];

    $db->query("use fltrail;");

    mysqli_select_db($db, "fltrail");
// is user does not exist create
    $db->query("INSERT INTO `timetrack`(`projectId`, `Lernziel`, `Forschungsfrage`, `Dauer`) VALUES ('" . $projectID . "','" . $lernziele . "', '".$forschungsfrage."', '".$time."');");
    $db->commit();
//}
echo "succes";
die();
?>