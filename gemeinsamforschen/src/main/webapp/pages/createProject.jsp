<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fltrail</title>
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/createProject.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">
        <div class="container-fluid">

            <h1>Einschreiben in einen Kurs</h1>
            <a href="#"><span class="glyphicon glyphicon-envelope"
                              style="font-size:27px;margin-top:-17px;margin-left:600px;"></span></a>
            <a href="#"><span class="glyphicon glyphicon-cog"
                              style="font-size:29px;margin-left:5px;margin-top:-25px;"></span></a>
        </div>
        <div style="margin-left: 2%;">
            <br><br>
            <label>Projektname: <input placeholder="Projektname"></label>
            <label>Passwort: <input placeholder="Passwort"></label>
        </div>
        <button class="btn btn-default" type="button" id="submit">beitreten</button>
    </div>
</div>


</body>

</html>