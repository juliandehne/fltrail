<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>muster-gemeinsam-forschen</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.js"></script>
    <script src="https://cdn.rawgit.com/showdownjs/showdown/1.8.5/dist/showdown.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="../assets/js/rateContribution.js"></script>
    <script src="../assets/js/utility.js"></script>
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId">project1
                <a href="#">
                <span class="glyphicon glyphicon-envelope"
                      style="font-size:27px;margin-top:-17px;margin-left:600px;"></span>
                </a>
                <a href="#">
                    <span class="glyphicon glyphicon-cog"
                          style="font-size:29px;margin-left:5px;margin-top:-25px;"></span>
                </a></h1>
        </div>
        <div>
            <table>
                <tr>
                    <td id="yourContent">
                        <h1>letzter Schritt im Projekt1 </h1>
                        <table class="table-striped peerStudent"
                               style="width:100%;border:1px solid; margin:auto;" id="student1">
                            <tr>
                                <td align="center">
                                    <img src="../assets/img/1.jpg" alt="student1"
                                         style="width:20%;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Lernzieltagebuch:
                                    Lernen ist wie Rudern gegen den Strom. Hört man damit auf, treibt man zurück.
                                    <textarea id="ejournalFeedback">
				                        meine Bewertung
			                        </textarea>
                                    <label><input type="radio" name="ejournalRating">Perfekt</label>
                                    <label><input type="radio" name="ejournalRating">Makellos</label>
                                    <label><input type="radio" name="ejournalRating">regulär</label>
                                    <label><input type="radio" name="ejournalRating">Makelhaft</label>
                                    <label><input type="radio" name="ejournalRating">Lädiert</label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Dossier:
                                    Die meisten Menschen sind bereit zu lernen, aber nur die wenigsten, sich belehren zu
                                    lassen.
                                    <textarea id="dossierFeedback">
				                        meine Bewertung
			                        </textarea>
                                    <label><input type="radio" name="dossierlRating">Perfekt</label>
                                    <label><input type="radio" name="dossierRating">Makellos</label>
                                    <label><input type="radio" name="dossierRating">regulär</label>
                                    <label><input type="radio" name="dossierRating">Makelhaft</label>
                                    <label><input type="radio" name="dossierRating">Lädiert</label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Präsentation: <a href="#"><i class="fa fa-paperclip"></i></a>
                                    <textarea id="presentationFeedback">
				                        meine Bewertung
			                        </textarea>
                                    <label><input type="radio" name="presentationRating">Perfekt</label>
                                    <label><input type="radio" name="presentationRating">Makellos</label>
                                    <label><input type="radio" name="presentationRating">regulär</label>
                                    <label><input type="radio" name="presentationRating">Makelhaft</label>
                                    <label><input type="radio" name="presentationRating">Lädiert</label>
                                </td>
                            </tr>
                        </table>
                        <button id="submit" class="btn btn-success">Feedback hochladen</button>
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
</body>

</html>