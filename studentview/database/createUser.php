<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:39
 */


include_once 'config.php';

// get the user data from the GET

// write it to the db

$name = $_POST['name'];
$password = $_POST['password'];
$email = $_POST['email'];
$token = uniqid();

$db->query("use fltrail;");

$query = "SELECT (u.email) from users u where u.email = \"".$email."\";";

$queryObj = mysqli_query($db, $query);
$result = mysqli_fetch_object($queryObj);
if ($result) {
    header("Location: ../register.php?emailExists=true");
    die();
}

// if user exists login
$query = "SELECT (u.token) from users u where u.password = \"".$password. "\" and u.email=\""
    .$email."\";";

$queryObj = mysqli_query($db, $query);
$result = mysqli_fetch_object($queryObj);
if ($result) {
    header("Location: ../pages/overview.php?token=".$result->token);
    die();
}

// is user does not exist create
$db->query("INSERT INTO `users`(`name`, `password`, `email`, `token` ) VALUES ('" . $name . "','" . $password . "','" . $email
    . "','" . $token . "');");
$db->commit();

header("Location: ../pages/overview.php?token=".$token);
die();
