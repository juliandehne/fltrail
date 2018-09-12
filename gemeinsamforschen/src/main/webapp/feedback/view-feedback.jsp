<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="css/viewfeedback.css">
    <omniDependencies:omniDependencies/>
    <script src="js/utility.js"></script>
    <script src="js/project-student.js"></script>
    <script src="js/givepeerfeedback.js"></script>

</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div class="container-fluid">
            <h1 id="projectId"> PeerFeedback</h1>
        </div>
        <div align="right" class="dropdown">
            <button style= "position: absolute; right: 50px;" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">

                <i class="glyphicon glyphicon-envelope"></i>
            </button>

            <ul class="dropdown-menu">
                <li><a class="viewfeedback" role="button">Feedback A</a></li>
                <li><a class="viewfeedback" role="button">Feedback B</a></li>
                <li><a class="viewfeedback" role="button">Feedback C</a></li>
            </ul>

            <a href="#">
                <span class="glyphicon glyphicon-cog" style="font-size:29px;margin-right:30px;margin-top:3px;"></span>
            </a>

        </div>
        <div>
            <table>
                <tr>
                    <tr>
                        <th>Feedback Nachrichten von Student X</th>
                    </tr>


                        <td  id="filter-feedbacks">


                            <%--<iframe width="90%" height="200%" src="http://rocketchat.westeurope.cloudapp.azure.com/channel/general?layout=embedded"></iframe>
                            --%>
                            <%--<p id="view"></p>      type="hidden"--%>
                            <input  type="hidden" name="peerfeedbackID" id="peerfeedbackID-input" value=""/>
                                <div style="height: 100px; overflow: auto">
                                <div class="feedback-container">
                                    <p>Sender</p>
                                    <span class="time-right">11:00</span>
                                </div>
                                </div>


                        </td>


                        <td id="view-feedbacks">

                            <div style="height: 300px; overflow: auto">

                            <div class="feedback-container">
                                <p>Hello. How are you today?</p>
                                <span class="time-right">11:00</span>
                            </div>

                            <div class="feedback-container">
                                <p>Hey! I'm fine. Thanks for asking!</p>
                                <span class="time-left">11:01</span>
                            </div>

                            <div class="feedback-container">
                                <p>Sweet! So, what do you wanna do today?</p>
                                <span class="time-right">11:02</span>
                            </div>

                            <div id="div1"></div>

                            </div>
                        </td>


                <button class="btn btn-secondary" onclick="goBack()">Zur&uuml;ck</button>

                <script>
                    function goBack() {
                        window.history.back();
                    }
                </script>

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

<script src="js/jquery.min.js"></script>
<script src="../assets/bootstrap/js/bootstrap.min.js"></script>
<script src="js/Sidebar-Menu.js"></script>
</body>

</html>
