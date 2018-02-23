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
$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
$email = $_POST['email'];
$token = uniqid();

$db->query("use fltrail;");
$db->query("INSERT INTO `users`(`name`, `password`, `email`, `token` ) VALUES ('" . $name . "','" . $password . "','" . $email
    . "','" . $token . "');");
$db->commit();
// write a success message here

//header('Content-Type: application/json');
header("Location: ../pages/overview.php?token=".$token);
die();
