<?php
/**
 * Created by IntelliJ IDEA.
 * User: dehne
 * Date: 23.02.2018
 * Time: 12:38
 */

include_once 'config.php';

// get the user data from the GET
$name = $_POST['name'];
$password = password_hash($_POST['password'],PASSWORD_DEFAULT);

// check if the user data is correct
$db->query("use fltrail;");
$db->query("SELECT FROM `users`(`name`, `password`) VALUES ('".$name."','".$password."');");
$db->commit();


// write a success message here

header('Content-Type: application/json');