<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 07.06.2018
 * Time: 12:41
 */
include_once 'config.php';

if (isset($_GET['project'])) {
    $project = $_GET['project'];
} else $project = "";

if (isset($_GET['password'])) {
    $adminpassword = $_GET['password'];
} else $adminpassword = "";

if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");

$sql = "SELECT * FROM projects WHERE id = '" . $project . "'" . " and adminpassword = '" . $adminpassword . "';";
$result = mysqli_query($db, $sql);
$row = mysqli_fetch_array($result);
if (!$row || count($row) == 0) {
    echo 'false';
} else {
    echo 'true';
}

?>