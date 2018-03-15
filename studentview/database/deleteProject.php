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


if (!$db) {
    die('Could not connect: ' . mysqli_error($db));
}

mysqli_select_db($db, "fltrail");
$sql = "DELETE FROM `projects` WHERE id = '" . $project . "'";
$result = mysqli_query($db, $sql);
echo $project." has been deleted";
mysqli_close($db);

?>