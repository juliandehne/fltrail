<%--suppress XmlDuplicatedId --%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu"%>

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
    <link rel="stylesheet" href="../assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/css/Community-ChatComments.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu-1.css">
    <link rel="stylesheet" href="../assets/css/Sidebar-Menu.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    <script src="../assets/js/utility.js"></script>
    <script src="../assets/js/viewfeedback.js"></script>
    <script src="../assets/js/givefeedback.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu></menu:menu>

    <div class="page-content-wrapper">
        <div class="container-fluid">
            <h1 id="projectId">Project 1</h1>
        </div>
        <div align="right" class="dropdown">
            <button style= "position: absolute; right: 50px;" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">

                <i class="glyphicon glyphicon-envelope"></i>
            </button>

            <ul class="dropdown-menu">
                <li><a id="viewfeedback" role="button">Feedback A</a></li>
                <li><a id="viewfeedback" role="button">Feedback B</a></li>
                <li><a id="viewfeedback" role="button">Feedback C</a></li>
            </ul>

            <a href="#">
                <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-right:30px;margin-top:3px;"></span>
            </a>

        </div>
        <div>
            <table>
                <tr>
                    <td  id="yourContent">
                        <h1>Feedbackable Students</h1>
                        <!-- here will be all the content -->
                        <table>
                            <tr>

                                <td width="100px" valign="top">
                                    <h3>student1</h3>
                                    <img src="../assets/img/1.jpg">
                                    <a href="#">student1@uni.de</a>
                                    <hr>
                                    <ul>

                                        <li><a  role="button" onclick="getUserTokenFromUrl()" >
                                            Projektuebersicht hochgeladen
                                            <i class="far fa-comments"></i>
                                            </a>
                                        </li>
                                        <li><a  role="button" onclick="getUserTokenFromUrl()">
                                            Blumen ins Hausaufgabenheft geklebt
                                            <i class="far fa-comments"></i>
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
                                        <li><a id="givefeedback" role="button">
                                            feedback zu Herbarium im Hausaufgabenheft gegeben
                                            <i class="far fa-comments"></i>
                                        </a></li>
                                        <li><a id="givefeedback" role="button">
                                            Blumen an Vegetarier verfuettert
                                            <i class="far fa-comments"></i>
                                            </a></li>
                                        <li><a id="givefeedback" role="button">
                                            Die armen Vegetarier
                                            <i class="far fa-comments"></i>
                                            </a></li>
                                    </ul>
                                </td>
                                <td></td>

                                <td width="100px" valign="top">
                                    <h3>student3</h3>
                                    <img src="../assets/img/3.jpg">
                                    <a href="#">student3@uni.de</a>
                                    <hr>
                                    <ul>
                                        <li><a id="givefeedback" role="button">
                                            Viva la Floristika
                                            <i class="far fa-comments"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </td>

                            </tr>
                        </table>
                        <div>
                            <button class="btn btn-light" id="project1Link">Back</button>
                        </div>
                    </td>
                    <td  id="chat">
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