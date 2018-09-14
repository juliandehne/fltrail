<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/create-quiz.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu hierarchy="1"/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div style="margin-left:50px;">
            <div>
            <label>Frage: <input placeholder="Ihre Frage" id="question"></label><!--todo: remember to cut out whitespace and signs (?.,;)-->
            </div>
            <div><label><input type="radio" name="type" checked="checked">multiple choice</label></div>
            <div><label><input type="radio" name="type" disabled>Freitext</label></div>
            <div><label><input type="radio" name="type" disabled>rhetorische Frage</label></div>
            <div id="correctAnswers"><input placeholder="korrekte Antwort" id="correctAnswer"></div>
            <button id="addCorrectAnswer"> + </button><button id="deleteCorrectAnswer"> - </button>
            <div id="incorrectAnswers"><input placeholder="inkorrekte Antwort" id="incorrectAnswer"></div>
            <button id="addIncorrectAnswer"> + </button><button id="deleteIncorrectAnswer"> - </button>
            <button id="save">speichern</button>
        </div>
    </div>
    <footer:footer/>
</div>
</body>
</html>
