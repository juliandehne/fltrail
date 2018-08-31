<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--suppress XmlDuplicatedId --%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>


<!--todo: E-mail an Studenten als Notifikation für Phasenwechsel -->


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/project-student.js"></script>
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">

</head>

<body>
<div id="wrapper">
    <menu:menu/>

    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Feedbackable Students</h1>
                        <!-- here will be all the content -->
                        <table id="myGroupMembers">
                            <tr>

                                <td width="100px" valign="top">
                                    <h3>student1</h3>
                                    <img src="../assets/img/1.jpg">
                                    <a href="#">student1@uni.de</a>
                                    <hr>
                                    <ul>

                                        <li>
                                            Projektübersicht hochgeladen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Blumen ins Hausaufgabenheft geklebt
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        </a>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>student2</h3>
                                    <img src="../assets/img/2.jpg">
                                    <a href="#">student2@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li>
                                            Blumen an Vegetarier verfüttert
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Literaturverzeichnis hochgeladen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                        <li>
                                            Die armen Vegetarier
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>student3</h3>
                                    <img src="../assets/img/3.jpg">
                                    <a href="#">student3@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li>
                                            "Viva la Floristika" - Titel hochgeladen
                                            <a class="annotationview" role="button">
                                                <label style="font-size:10px;"><i class="far fa-comments"
                                                                                  style="font-size:15px;"></i>feedback</label>
                                            </a>
                                        </li>
                                    </ul>
                                </td>

                            </tr>
                        </table>

                        <button onclick="goBack()" class="btn btn-secondary">Zur&uuml;ck</button>

                        <script>
                            function goBack() {
                                window.history.back();
                            }
                        </script>

                    </td>
                    <td id="chat">
                        <div class="card">
                            <div class="card-header">
                                <h6 class="mb-0">Gruppen+Projekt Chat</h6>
                            </div>
                            <div class="card-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <div class="media">
                                            <div></div>
                                            <div class="media-body">
                                                <div class="media" style="overflow:visible;">
                                                    <div><img src="../assets/img/1.jpg" class="mr-3"
                                                              style="width: 25px; height:25px;"></div>
                                                    <div class="media-body" style="overflow:visible;">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p><a href="#">Sara Doe:</a> This guy has been going
                                                                    100+ MPH on side streets. <br>
                                                                    <small class="text-muted">August 6, 2016 @ 10:35am
                                                                    </small>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <div class="media">
                                            <div></div>
                                            <div class="media-body">
                                                <div class="media" style="overflow:visible;">
                                                    <div><img src="../assets/img/2.jpg" class="mr-3"
                                                              style="width: 25px; height:25px;"></div>
                                                    <div class="media-body" style="overflow:visible;">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <p><a href="#">Brennan Prill:</a> This guy has been
                                                                    going 100+ MPH on side streets. <br>
                                                                    <small class="text-muted">August 6, 2016 @ 10:35am
                                                                    </small>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                                <button class="btn btn-light">
                                    Add Comment
                                </button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <footer>
        <div class="container">
            <div class="progress">
                <div class="progress-bar pg-groups" role="progressbar" id="progressbar">
                </div>
                <div>
                    Assessment - Präsentationsphase - Dossier - Reflexionsphase - Feedbackphase - Gruppenbildung
                </div>
                <div class="progress-bar pg-rest" role="progressbar">
                </div>
            </div>
            <button id="nextPhase" class="btn btn-light">nächste Phase</button>
            <button id="btnUnstructuredUpload" class="btn btn-light">Unstrukturierte Abgabe</button>
        </div>
    </footer>
</div>

</body>

</html>