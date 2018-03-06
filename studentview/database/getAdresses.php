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
if (isset($_GET['student3'])) {
    $student3 = $_GET['student3'];
} else{$student3 = "";}
if (isset($_GET['student4'])) {
    $student4 = $_GET['student4'];
}else {$student4 = "";}
if (isset($_GET['student5'])) {
    $student5 = $_GET['student5'];
}else {$student5 = "";}
$adresses = array();

if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");
$sql = "SELECT name,email FROM `users` WHERE `name`='".$student1."' OR `name` = '".$student2."' OR `name` = '".$student3."' OR `name` = '".$student4."';";
if ($result = mysqli_query($db, $sql)) {
    while ($row = mysqli_fetch_array($result)) {
        $adresses[] = $row;
    }
}

echo json_encode($adresses);
mysqli_close($db);
?>