<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="css/viewfeedback.css">
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/viewpeerfeedback.js"></script>
</head>

<body>
<menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div class="container-fluid">
            <h1 id="projectName"> PeerFeedback</h1>
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


                <td id="filter-feedbacks">


                    <%--<iframe width="90%" height="200%" src="http://rocketchat.westeurope.cloudapp.azure.com/channel/general?layout=embedded"></iframe>
                    --%>
                    <%--<p id="view"></p>      type="hidden"--%>
                    <input type="hidden" name="peerfeedbackID" id="peerfeedbackID-input" value=""/>
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
        <div class="container">
            <h3 class=" text-center">Feedback Nachrichten</h3>
            <div class="messaging">
                <div class="inbox_msg">
                    <div class="inbox_people">
                        <div class="headind_srch">
                            <div class="recent_heading" style="padding-left: 152px;">
                                <h4>Übersicht</h4>
                            </div>
                            <hr>
                            <div id="recieved" class="recent_heading" style="padding-left: 37px; width:50%; float: left; border-right: 1px solid #cdcdcd;">
                                <h5>Empfangen</h5>
                            </div>
                            <input type="hidden" id="list" name="list">
                            <div id="sended" class="recent_heading" style="float: right; padding-left: 37px;">
                                <h5>Gesendet</h5>
                            </div>
                        </div>
                        <div class="inbox_chat" id="inbox_chat">
                        </div>
                    </div>
                    <div class="mesgs">
                        <div class="msg_history" id="msg_history">
                        </div>
                    </div>
                </div>
            </div></div>
        <button class="btn btn-secondary" onclick="goBack()">Zur&uuml;ck</button>

                    </td>
                </tr>
            </table>
        </div>
    </div>
    <footer:footer/>
</body>

</html>
