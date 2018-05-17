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

if (isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest'){
    $projectId = $_GET['projectId'];
    $groupId = $_GET['groupId'];
    $_POST = json_decode(file_get_contents('php://input'), true);
    $students = $_POST;

    var_dump($students);
    $db->query("use fltrail;");

    mysqli_select_db($db, "fltrail");
// is user does not exist create
    foreach ($students as $student) {
        $db->query("INSERT INTO `groups`(`projectId`, `groupId`, `student`) VALUES ('" . $projectId . "','" . $groupId . "','". $student."');");
    }
    $db->commit();
}
die();
?>