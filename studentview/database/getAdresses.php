<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:37
 */

include_once 'config.php';

$students = $_GET['students'];
$addresses = array();

if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");
$sql = "SELECT name,email FROM `users` WHERE `name`='".$students[0]."'";
foreach ($students as $student){
    $sql = $sql." OR `name` = '".$student."'";
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