<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="css/viewfeedback.css">
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/viewpeerfeedback.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
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

        <script>
            function goBack() {
                window.history.back();
            }
        </script>
    </div>
</div>
</body>

</html>
