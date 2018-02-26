<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:37
 */

include_once 'config.php';

$password = $_POST['password'];
$email = $_POST['email'];
$db->query("use fltrail;");

$query = "SELECT (u.token) from users u where u.password = \"".$password. "\" and u.email=\""
.$email."\";";

$queryObj = mysqli_query($db, $query);
$result = mysqli_fetch_object($queryObj);
if ($result) {
    header("Location: ../pages/overview.php?token=".$result->token);
    die();
}

header("Location: ../index.php?userExists=false");
die();