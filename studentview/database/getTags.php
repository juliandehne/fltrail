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
    $tags = array();

    if (!$db) {
        die('Could not connect: ' . mysqli_error($db));
    }

    mysqli_select_db($db, "fltrail");
    $sql = "SELECT * FROM tags WHERE projectId = '" . $project . "'";

    if ($result = mysqli_query($db, $sql)) {
        while ($row = mysqli_fetch_array($result)) {
            $tags[] = $row;
        }
    }
    echo json_encode($tags);
    mysqli_close($db);
}
?>