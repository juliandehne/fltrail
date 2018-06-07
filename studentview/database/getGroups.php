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
    $sql = ("SELECT * FROM `groups` WHERE `projectID`='".$projectId."';");
    $finalArray = array();

    if ($result = mysqli_query($db, $sql)) {
        while ($row = mysqli_fetch_array($result)) {
            $groups[] = $row;
        }
    }


    function filter ($arrayelem) {
        return array($arrayelem[0], $arrayelem[1]);
    };

    $groups = array_map('filter', $groups);

    function attributeGroup($studentName, $groups) {
        foreach ($groups as $group) {
            if ($group[0] == $studentName) {
                return $group[1];
            }
        }
    }

    foreach ($result as $student) {
        $group = attributeGroup($student, $groups);
        array_push($finalArray, array("student" => $student));
    }
    echo json_encode($finalArray);
    die();
}
?>