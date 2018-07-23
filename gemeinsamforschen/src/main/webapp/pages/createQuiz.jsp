<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

<html>
<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/createQuiz.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu/>
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
</div>

</body>
</html>
