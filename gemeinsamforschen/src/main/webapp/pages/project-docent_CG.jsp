<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "communication" uri = "/communication/chatWindow.tld"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>fltrail</title>
    <link rel="stylesheet" href="../assets/css/styles.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../assets/js/AlertAssessmentOutlier.js"></script>
    <script src="../assets/js/grading.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <script src="../assets/js/utility.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="headline">dummy Projekt1 für Dozent1</h1><button class="btn btn-default" type="button">Gruppen erstellen</button><button class="btn btn-default" type="button" id="ProjektFinalisieren">Projekt finalisieren</button><button class="btn btn-default" type="button">Exportiere Projektergebnisse</button>
        </div>
        <p hidden id="ProjektId">Projekt1</p>
        <button
                class="btn btn-default" type="button">Exportiere Zwischenstand</button><button class="btn btn-default" type="button">Quizfrage erstellen</button>
        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th id="testAjax">Gruppe1 </th>
                                    <th>Beiträge </th>
                                    <th> <button id="ProblemGrp1">!</button> </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student1 </td>
                                    <td>Interfaces </td>
                                    <th> <button id="ProblemGrp1S1">!</button> </th>
                                </tr>
                                <tr>
                                    <td>student2 </td>
                                    <td>Design </td>
                                    <th> <button id="ProblemGrp1S2">!</button> </th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Gruppe2 </th>
                                    <th>Beiträge </th>
                                    <th> <button id="ProblemGrp2">!</button> </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student3 </td>
                                    <td>Interfaces </td>
                                    <th> <button id="ProblemGrp2S3">!</button> </th>
                                </tr>
                                <tr>
                                    <td>student4 </td>
                                    <td>Design </td>
                                    <th> <button id="ProblemGrp2S4">!</button> </th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Gruppe3 </th>
                                    <th>Beiträge </th>
                                    <th> <button id="ProblemGrp3">!</button> </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>student5 </td>
                                    <td>Interfaces </td>
                                    <th> <button id="ProblemGrp3S5">!</button> </th>
                                </tr>
                                <tr>
                                    <td>student6 </td>
                                    <td>Design </td>
                                    <th> <button id="ProblemGrp3S6">!</button> </th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <button id="DiaBlende">Zeitlicher Verlauf</button>
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Liniendiagramm-Beispiel.svg/750px-Liniendiagramm-Beispiel.svg.png", width="280", id = "Diagramm" >
                    </div>
                    <div class="col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">ProjektChat</h3>
                            </div>
                            <div class="panel-body" style="height:233px;">
                                <ul class="list-group">
                                    <li class="list-group-item" style="margin-bottom:6px;">
                                        <div class="media">
                                            <div class="media-left"><a></a></div>
                                            <div class="media-body">
                                                <div class="media" style="overflow:visible;">
                                                    <div class="media-left"><a><img src="../assets/img/1.jpg" class="img-rounded" style="width: 25px; height:25px;"></a></div>
                                                    <div class="media-body" style="overflow:visible;">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p><a href="#">Sara Doe:</a> This guy has been going 100+ MPH on side streets. <br>
                                                                    <small class="text-muted">August 6, 2016 @ 10:35am </small></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item" style="margin-bottom:6px;">
                                        <div class="media">
                                            <div class="media-left"><a></a></div>
                                            <div class="media-body">
                                                <div class="media" style="overflow:visible;">
                                                    <div class="media-left"><a><img src="../assets/img/1.jpg" class="img-rounded" style="width: 25px; height:25px;"></a></div>
                                                    <div class="media-body" style="overflow:visible;">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p><a href="#">Brennan Prill:</a> This guy has been going 100+ MPH on side streets. <br>
                                                                    <small class="text-muted">August 6, 2016 @ 10:35am </small></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul><button class="btn btn-default" type="button" style="margin-left:601px;margin-top:-9px;">Add Comment</button></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<communication:chatWindow orientation="right"></communication:chatWindow>
</body>

</html>