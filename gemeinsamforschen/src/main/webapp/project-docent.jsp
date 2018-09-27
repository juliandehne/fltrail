<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="communication" uri="/communication/chatWindow.tld" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>



<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="0"/>
    <script src="assessment/js/AlertAssessmentOutlier.js"></script>
    <script src="assessment/js/grading.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="0"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <button
                class="btn btn-default" type="button">Exportiere Zwischenstand
        </button>
        <button class="btn btn-default" type="button">Quizfrage erstellen</button>
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
                </div>
            </div>
        </div>
    </div>
    <footer:footer/>
</div>
<communication:chatWindow orientation="right" />
</body>

</html>