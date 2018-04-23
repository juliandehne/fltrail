<?php
include_once '../database/config.php';

if (!isset( $_GET['token'])) {
    header("Location: ../index.php");
}
$token = $_GET['token'];
$query = "SELECT (`name`) from users u where u.token = '". $_GET['token']."';";
//echo $query;
//echo $db->query($query);
$result = mysqli_query($db, $query);
$resultObj = mysqli_fetch_object($result);
if ($resultObj) {
    $userName = $resultObj->name;
} else {
    header("Location: ../index.php");
}
?>