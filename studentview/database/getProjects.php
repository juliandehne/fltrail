<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 26.02.2018
 * Time: 10:09
 */
include_once 'config.php';

if (isset($_GET['project'])&&isset($_GET['password'])){
    $project = $_GET['project'];
    $password = $_GET['password'];

    if (!$db) {
        die('Could not connect: ' . mysqli_error($db));
    }

    mysqli_select_db($db,"fltrail");
    $sql="SELECT * FROM projects WHERE id = '".$project."'";
    $result = mysqli_query($db,$sql);

    $row = mysqli_fetch_array($result);
    if ($row['password'] === $password){
        echo 'true';
    }else {
        echo 'false';
    }
    mysqli_close($db);
}
?>