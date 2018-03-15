<?php
/**
 * Created by PhpStorm.
 * User: fides-WHK
 * Date: 26.02.2018
 * Time: 10:09
 */

$project = intval($_GET['project']);

$con = mysqli_connect('localhost','root','','fltrail');
if (!$con) {
    die('Could not connect: ' . mysqli_error($con));
}

mysqli_select_db($con,"fltrail");
$sql="SELECT * FROM projects WHERE id = '".$project."'";
$result = mysqli_query($con,$sql);

$row = mysqli_fetch_array($result);
echo '{\n';
echo '"project": "'.$row['id'].'"\n';
echo '"password": "'.$row['password'].'"\n}';
mysqli_close($con);
?>