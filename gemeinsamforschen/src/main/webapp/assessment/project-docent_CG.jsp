<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>


<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/AlertAssessmentOutlier.js"></script>
    <script src="js/grading.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>
</head>



<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <headLine:headLine/>
            <button class="btn btn-default" type="button">Gruppen erstellen</button>
            <button class="btn btn-default" type="button" id="ProjektFinalisieren">Projekt finalisieren</button>
            <button class="btn btn-default" type="button">Exportiere Projektergebnisse</button>
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
                        <canvas height="480" width="480", id = "Diagramm" ></canvas>
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