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

if (isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest') {
    $projectId = $_GET['projectId'];
    $_POST = json_decode(file_get_contents('php://input'), true);
    $students = $_POST;
    $db->query("use fltrail;");

    mysqli_select_db($db, "fltrail");
// is user does not exist create
    $result = $db->query("SELECT * FROM `groups`;");
    $db->commit();
    if (count(mysqli_fetch_array($result))==0){
        foreach ($students as $i) {
            $db->query("INSERT INTO `groups`(`projectId`, `groupId`, `student`) VALUES ('" . $projectId . "','" . $i['group'] . "','" . $i['student'] . "');");
        }
    } else{
        foreach ($students as $i) {
            $db->query("UPDATE `groups` SET `groupId`='" . $i['group'] . "' WHERE `student`='" . $i['student'] . "' ;");
        }
    }
    $db->commit();
}
die();
?>