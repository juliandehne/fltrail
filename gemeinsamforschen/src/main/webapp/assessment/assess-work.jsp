<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="js/finalAssessment.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Assessment for project1 </h1>

                        <!-- Vorschläge für Bewertungen:
                            ++Verantwortungsbewusstsein
                            ++Disskusionsfähigkeit
                            ++Anteil am Produkt
                            ++Kooperationsbereitschaft
                            ++Selbstständigkeit
                            -+Führungsqualität
                            -+Pünktlichkeit
                            -+Motivation
                            -+Gewissenhaftigkeit
                            -+respektvoller Umgang mit anderen
                            -+Wert der Beiträge
                            --kann sich an Vereinbarungen halten
                            --emotionale Stabilität
                            --Hilfsbereitschaft
                        -->

                        <!-- here will be all the content -->
                        <div class="container">
                            <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                                <!-- Wrapper for slides -->
                                <div class="alert alert-info" id="notAllRated">
                                    Es wurden noch nicht alle Studenten vollständig bewertet
                                </div>

                                <div class="carousel-inner" id="peerTable">
                                    <!--<div class="item active">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="Student1">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/1.jpg" alt="student1"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Verantwortungsbewusstsein</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>stark ausgeprägt<input type="radio" value="5"
                                                                     name="responsibilityStudent1"></label>
                                                    <input type="radio" value="4" name="responsibilityStudent1">
                                                    <input type="radio" value="3" name="responsibilityStudent1">
                                                    <input type="radio" value="2" name="responsibilityStudent1">
                                                    <label><input type="radio" value="1" name="responsibilityStudent1">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Anteil am Produkt</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>großer Anteil<input type="radio" value="5"
                                                                     name="partOfWorkStudent1"></label>
                                                    <input type="radio" value="4" name="partOfWorkStudent1">
                                                    <input type="radio" value="3" name="partOfWorkStudent1">
                                                    <input type="radio" value="2" name="partOfWorkStudent1">
                                                    <label><input type="radio" value="1" name="partOfWorkStudent1">
                                                        geringer Anteil</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>sehr kooperativ<input type="radio" value="5" name="cooperationStudent1">
                                                    </label>
                                                    <input type="radio" value="4" name="cooperationStudent1">
                                                    <input type="radio" value="3" name="cooperationStudent1">
                                                    <input type="radio" value="2" name="cooperationStudent1">
                                                    <label><input type="radio" value="1" name="cooperationStudent1">
                                                        nicht kooperativ</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Disskusionsfähigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut kommuniziert und Meinung vertreten<input type="radio" value="5" name="communicationStudent1">
                                                    </label>
                                                    <input type="radio" value="4" name="communicationStudent1">
                                                    <input type="radio" value="3" name="communicationStudent1">
                                                    <input type="radio" value="2" name="communicationStudent1">
                                                    <label><input type="radio" value="1" name="communicationStudent1">
                                                        keine Meinung und schlecht kommuniziert</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>selbstständig<input type="radio" value="5" name="autonomousStudent1">
                                                    </label>
                                                    <input type="radio" value="4" name="autonomousStudent1">
                                                    <input type="radio" value="3" name="autonomousStudent1">
                                                    <input type="radio" value="2" name="autonomousStudent1">
                                                    <label><input type="radio" value="1" name="autonomousStudent1">
                                                        abhängig</label>
                                                </td>
                                            </tr>
                                        </table>
                                        <div align="center">
                                            <button class="btn btn-primary" id="btnJournalStudent1" >zeige Lernzieltagebuch</button>
                                            <div id="eJournalStudent1">
                                                Fasel Blubba Bla
                                            </div>
                                        </div>
                                    </div>

                                    <div class="item">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="Student2">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/2.jpg" alt="Student2"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Verantwortungsbewusstsein</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>stark ausgeprägt<input type="radio" value="5"
                                                                                  name="responsibilityStudent2"></label>
                                                    <input type="radio" value="4" name="responsibilityStudent2">
                                                    <input type="radio" value="3" name="responsibilityStudent2">
                                                    <input type="radio" value="2" name="responsibilityStudent2">
                                                    <label><input type="radio" value="1" name="responsibilityStudent2">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Anteil am Produkt</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>großer Anteil<input type="radio" value="5"
                                                                               name="partOfWorkStudent2"></label>
                                                    <input type="radio" value="4" name="partOfWorkStudent2">
                                                    <input type="radio" value="3" name="partOfWorkStudent2">
                                                    <input type="radio" value="2" name="partOfWorkStudent2">
                                                    <label><input type="radio" value="1" name="partOfWorkStudent2">
                                                        geringer Anteil</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>sehr kooperativ<input type="radio" value="5" name="cooperationStudent2">
                                                    </label>
                                                    <input type="radio" value="4" name="cooperationStudent2">
                                                    <input type="radio" value="3" name="cooperationStudent2">
                                                    <input type="radio" value="2" name="cooperationStudent2">
                                                    <label><input type="radio" value="1" name="cooperationStudent2">
                                                        nicht kooperativ</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Disskusionsfähigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut kommuniziert und Meinung vertreten<input type="radio" value="5" name="communicationStudent2">
                                                    </label>
                                                    <input type="radio" value="4" name="communicationStudent2">
                                                    <input type="radio" value="3" name="communicationStudent2">
                                                    <input type="radio" value="2" name="communicationStudent2">
                                                    <label><input type="radio" value="1" name="communicationStudent2">
                                                        keine Meinung und schlecht kommuniziert</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>selbstständig<input type="radio" value="5" name="autonomousStudent2">
                                                    </label>
                                                    <input type="radio" value="4" name="autonomousStudent2">
                                                    <input type="radio" value="3" name="autonomousStudent2">
                                                    <input type="radio" value="2" name="autonomousStudent2">
                                                    <label><input type="radio" value="1" name="autonomousStudent2">
                                                        abhängig</label>
                                                </td>
                                            </tr>
                                        </table>
                                        <div align="center">
                                            <button class="btn btn-primary" id="btnJournalStudent2">zeige Lernzieltagebuch</button>
                                            <div id="eJournalStudent2">
                                                Fasel Blubba Bla
                                            </div>
                                        </div>
                                    </div>

                                    <div class="item">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="Student3">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/3.jpg" alt="Student3"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Verantwortungsbewusstsein</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>stark ausgeprägt<input type="radio" value="5"
                                                                                  name="responsibilityStudent3"></label>
                                                    <input type="radio" value="4" name="responsibilityStudent3">
                                                    <input type="radio" value="3" name="responsibilityStudent3">
                                                    <input type="radio" value="2" name="responsibilityStudent3">
                                                    <label><input type="radio" value="1" name="responsibilityStudent3">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Anteil am Produkt</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>großer Anteil<input type="radio" value="5"
                                                                               name="partOfWorkStudent3"></label>
                                                    <input type="radio" value="4" name="partOfWorkStudent3">
                                                    <input type="radio" value="3" name="partOfWorkStudent3">
                                                    <input type="radio" value="2" name="partOfWorkStudent3">
                                                    <label><input type="radio" value="1" name="partOfWorkStudent3">
                                                        geringer Anteil</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>sehr kooperativ<input type="radio" value="5" name="cooperationStudent3">
                                                    </label>
                                                    <input type="radio" value="4" name="cooperationStudent3">
                                                    <input type="radio" value="3" name="cooperationStudent3">
                                                    <input type="radio" value="2" name="cooperationStudent3">
                                                    <label><input type="radio" value="1" name="cooperationStudent3">
                                                        nicht kooperativ</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Disskusionsfähigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut kommuniziert und Meinung vertreten<input type="radio" value="5" name="communicationStudent3">
                                                    </label>
                                                    <input type="radio" value="4" name="communicationStudent3">
                                                    <input type="radio" value="3" name="communicationStudent3">
                                                    <input type="radio" value="2" name="communicationStudent3">
                                                    <label><input type="radio" value="1" name="communicationStudent3">
                                                        keine Meinung und schlecht kommuniziert</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>selbstständig<input type="radio" value="5" name="autonomousStudent3">
                                                    </label>
                                                    <input type="radio" value="4" name="autonomousStudent3">
                                                    <input type="radio" value="3" name="autonomousStudent3">
                                                    <input type="radio" value="2" name="autonomousStudent3">
                                                    <label><input type="radio" value="1" name="autonomousStudent3">
                                                        abhängig</label>
                                                </td>
                                            </tr>
                                        </table>
                                        <div align="center">
                                            <button class="btn btn-primary" id="btnJournalStudent3">zeige Lernzieltagebuch</button>
                                            <div id="eJournalStudent3" align="center">
                                                Fasel Blubba Bla
                                            </div>
                                        </div>
                                    </div>-->
                                </div>

                                <!-- Left and right controls -->
                                <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#myCarousel" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                            <button class="btn btn-success" id="assessThePeer">Gruppe bewerten</button>
                        </div>
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
    <footer:footer/>
</div>

</body>

</html>