<!DOCTYPE html>
<%@ taglib prefix = "communication" uri = "/communication/chatWindow.tld"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>project-student (copy)</title>
    <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/styles.css">
</head>

<body>
    <h1>dummy Projekt1 für Dozent1</h1><button class="btn btn-default" type="button">Gruppen erstellen</button><button class="btn btn-default" type="button">Projekt finalisieren</button><button class="btn btn-default" type="button">Exportiere Projektergebnisse</button>
    <button
        class="btn btn-default" type="button">Exportiere Zwischenstand</button><button class="btn btn-default" type="button">Quizfrage erstellen</button>
        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                        <div class="table-responsive" style="width:294px;">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Gruppe1 </th>
                                        <th>Beiträge </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>student1 </td>
                                        <td>Interfaces </td>
                                    </tr>
                                    <tr>
                                        <td>student2 </td>
                                        <td>Design </td>
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
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>student3 </td>
                                        <td>Interfaces </td>
                                    </tr>
                                    <tr>
                                        <td>student4 </td>
                                        <td>Design </td>
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
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>student5 </td>
                                        <td>Interfaces </td>
                                    </tr>
                                    <tr>
                                        <td>student6 </td>
                                        <td>Design </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-6">
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
    <communication:chatWindow orientation="right"></communication:chatWindow>
        <script src="../assets/js/jquery.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>