<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:37
 */

include_once 'config.php';

$student1 = $_GET['student1'];
$student2 = $_GET['student2'];
$student3 = $_GET['student3'];

$addresses = array();

if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");
$sql = "SELECT name,email FROM `users` WHERE `name`='".$student1."' OR `name` = '".$student2."' OR `name` = '".$student3."'";

if (isset($_GET['student5'])) {
    $sql = $sql . "OR `name` = '".$_GET['student4']."' ". "OR `name` = '".$_GET['student5']."'";
} else {
    if (isset($_GET['student4'])) {
        $sql = $sql . "OR `name` = '" . $_GET['student4'] . "'";
    }
}
$sql = $sql . ";";


if ($result = mysqli_query($db, $sql)) {
    while ($row = mysqli_fetch_array($result)) {
            $addresses[] = $row;
    }
}

function filter ($arrayelem) {
    return array($arrayelem[0], $arrayelem[1]);
};

$addresses = array_map('filter', $addresses);

$students = array($student1, $student2, $student3);
if (isset($_GET['student5']) && $_GET['student5'] != "undefined" ){
    $students = array($student1, $student2, $student3, $_GET['student4'], $_GET['student5']);
}else {
    if (isset($_GET['student4']) && $_GET['student4'] != "undefined") {
        $students = array($student1, $student2, $student3, $_GET['student4']);
    }
}

function attributeEmail($studentName, $addresses) {
    foreach ($addresses as $value) {
        if ($value[0] == $studentName) {
            return $value[1];
        }
    }
}


$finalArray = array();

foreach ($students as $student) {
    $email = attributeEmail($student, $addresses);
    if (!$email) {
        $email = "";
    }
    array_push($finalArray, array("name" => $student, "email" => $email));
}

echo json_encode($finalArray);
mysqli_close($db);
?>