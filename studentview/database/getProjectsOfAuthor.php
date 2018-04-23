<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 27.03.2018
 * Time: 13:43
 */

/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 26.02.2018
 * Time: 10:09
 */
include_once 'config.php';

if (isset($_GET['author'])) {

    $author = $_GET['author'];
    $projects = array();

    if (!$db) {
        die('Could not connect: ' . mysqli_error($db));
    }

    mysqli_select_db($db, "fltrail");
    $sql = "SELECT a.id FROM projects a WHERE a.author = '" . $author . "'";


    function takeId($array) {
        return $array->id;
    }

    if ($result = mysqli_query($db, $sql)) {
        while ($row = mysqli_fetch_object($result)) {
            $projects[] = $row;
        }
    }
    $projects = array_map("takeId",$projects);
    echo json_encode($projects);
    mysqli_close($db);
}