<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="feedback/css/viewfeedback.css">
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="feedback/js/viewpeerfeedback.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div>
            <div>Feedback Nachrichten</div>
            <table>
                <tr>
                        <hr>

                        <td  id="filter-feedbacks" class="feedback-table">

                            <input type="hidden" name="peerfeedbackID" id="peerfeedbackID-input" value=""/>
                            <input type="hidden" id="student" name="student">
                            <input type="hidden" id="project" name="project">
                            <input type="hidden" id="feedbackid" name="id">
                            <input type="hidden" id="reciever" name="reciever">
                            <input type="hidden" id="sender" name="sender">
                            <input type="hidden" id="timestamp" name="timestamp">
                            <input type="hidden" id="category" name="category">
                            <input type="hidden" id="filename" name="filename">

                                <div id="senderlist" >
                                <div class="feedback-container">
                                    <p>Sender</p>
                                    <span class="time-right">11:00</span>
                                </div>
                                </div>


                        </td>


                        <td id="view-feedbacks" class="feedback-table">

                            <div style="overflow: auto">

                            <div class="feedback-container">
                                <p>Hello. How are you today?</p>
                                <span class="time-right">11:00</span>
                            </div>

                            <div id="div1"></div>

                            </div>
                        </td>


                </tr>
            </table>
            <hr>
            <button class="btn btn-secondary" onclick="goBack()">Zur&uuml;ck</button>

            <script>
                function goBack() {
                    window.history.back();
                }
            </script>
        </div>
    </div>
</div>
</body>

</html>
