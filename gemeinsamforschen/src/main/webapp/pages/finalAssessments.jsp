<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/finalAssessment.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>Assessment for project1 </h1>
                        <!-- here will be all the content -->
                        <div class="container">
                            <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                                <!-- Indicators -->
                                <ol class="carousel-indicators">
                                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                    <li data-target="#myCarousel" data-slide-to="1"></li>
                                    <li data-target="#myCarousel" data-slide-to="2"></li>
                                </ol>

                                <!-- Wrapper for slides -->
                                <div class="carousel-inner">
                                    <div class="item active">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="student1">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/1.jpg" alt="student1"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Führungsqualität</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut<input type="radio" value="5"
                                                                     name="leadership1"></label>
                                                    <input type="radio" value="4" name="leadership1">
                                                    <input type="radio" value="3" name="leadership1">
                                                    <input type="radio" value="2" name="leadership1">
                                                    <label><input type="radio" value="1" name="leadership1">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="cooparation1">
                                                        gut</label>
                                                    <input type="radio" value="4" name="cooparation1">
                                                    <input type="radio" value="3" name="cooparation1">
                                                    <input type="radio" value="2" name="cooparation1">
                                                    <label><input type="radio" value="1" name="cooparation1">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Pünktlichkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="punctual1">
                                                        gut</label>
                                                    <input type="radio" value="4" name="punctual1">
                                                    <input type="radio" value="3" name="punctual1">
                                                    <input type="radio" value="2" name="punctual1">
                                                    <label><input type="radio" value="1" name="punctual1">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="autonomous1">
                                                        gut</label>
                                                    <input type="radio" value="4" name="autonomous1">
                                                    <input type="radio" value="3" name="autonomous1">
                                                    <input type="radio" value="2" name="autonomous1">
                                                    <label><input type="radio" value="1" name="autonomous1">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>

                                    <div class="item">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="student2">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/2.jpg" alt="student2"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Führungsqualität</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut<input type="radio" value="5"
                                                                     name="leadership2"></label>
                                                    <input type="radio" value="4" name="leadership2">
                                                    <input type="radio" value="3" name="leadership2">
                                                    <input type="radio" value="2" name="leadership2">
                                                    <label><input type="radio" value="1" name="leadership2">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="cooparation2">
                                                        gut</label>
                                                    <input type="radio" value="4" name="cooparation2">
                                                    <input type="radio" value="3" name="cooparation2">
                                                    <input type="radio" value="2" name="cooparation2">
                                                    <label><input type="radio" value="1" name="cooparation2">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Pünktlichkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="punctual2">
                                                        gut</label>
                                                    <input type="radio" value="4" name="punctual2">
                                                    <input type="radio" value="3" name="punctual2">
                                                    <input type="radio" value="2" name="punctual2">
                                                    <label><input type="radio" value="1" name="punctual2">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="autonomous2">
                                                        gut</label>
                                                    <input type="radio" value="4" name="autonomous2">
                                                    <input type="radio" value="3" name="autonomous2">
                                                    <input type="radio" value="2" name="autonomous2">
                                                    <label><input type="radio" value="1" name="autonomous2">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>

                                    <div class="item">
                                        <table class="table-striped peerStudent"
                                               style="width:70%;border:1px solid; margin:auto;" id="student3">
                                            <tr>
                                                <td align="center">
                                                    <img src="../assets/img/3.jpg" alt="student3"
                                                         style="width:20%;">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Führungsqualität</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label>gut<input type="radio" value="5"
                                                                     name="leadership3"></label>
                                                    <input type="radio" value="4" name="leadership3">
                                                    <input type="radio" value="3" name="leadership3">
                                                    <input type="radio" value="2" name="leadership3">
                                                    <label><input type="radio" value="1" name="leadership3">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Kooperationsbereitschaft</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="cooparation3">
                                                        gut</label>
                                                    <input type="radio" value="4" name="cooparation3">
                                                    <input type="radio" value="3" name="cooparation3">
                                                    <input type="radio" value="2" name="cooparation3">
                                                    <label><input type="radio" value="1" name="cooparation3">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Pünktlichkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="punctual3">
                                                        gut</label>
                                                    <input type="radio" value="4" name="punctual3">
                                                    <input type="radio" value="3" name="punctual3">
                                                    <input type="radio" value="2" name="punctual3">
                                                    <label><input type="radio" value="1" name="punctual3">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <h3>Selbstständigkeit</h3>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <label><input type="radio" value="5" name="autonomous3">
                                                        gut</label>
                                                    <input type="radio" value="4" name="autonomous3">
                                                    <input type="radio" value="3" name="autonomous3">
                                                    <input type="radio" value="2" name="autonomous3">
                                                    <label><input type="radio" value="1" name="autonomous3">
                                                        ungenügend</label>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
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
</div>

<script src="../assets/js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/js/Sidebar-Menu.js"></script>
</body>

</html>