<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 26.02.2018
 * Time: 10:09
 */
include_once 'config.php';

if (isset($_GET['project'])) {
    $project = $_GET['project'];
} else $project = "";

if (isset($_GET['password'])) {
    $adminpassword = $_GET['password'];
} else $adminpassword = "";

$token = $_GET['token'];

if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");

$sql = "SELECT * FROM projects WHERE id = '" . $project . "'" . " and adminpassword = '" . $adminpassword . "';";
$result = mysqli_query($db, $sql);
$row = mysqli_fetch_array($result);
if (!$row || count($row) == 0) {
    echo 'project missing';
} else {
    if ($row['adminpassword'] === $adminpassword) {
        $sql = "DELETE FROM `projects` WHERE id = '" . $project . "'" . " and adminpassword = '" . $adminpassword . "';";
        $result = mysqli_query($db, $sql);
        $sql = "DELETE FROM `tags` WHERE projectId = '" . $project . "';";
        $result = mysqli_query($db, $sql);
        echo $project . " has been deleted";
    } else {
        echo 'wrong password';
    }
}
mysqli_close($db);

?>