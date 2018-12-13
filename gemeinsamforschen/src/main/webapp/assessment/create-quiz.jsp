<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/create-quiz.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main> <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div id="wrapper">
            <div class="page-content-wrapper">
                <div style="margin-left:50px;">
                    <div>
                        <label>Frage: <input placeholder="Ihre Frage" id="question"></label>
                        <!--todo: remember to cut out whitespace and signs (?.,;)-->
                    </div>
                    <div><label><input type="radio" name="type" checked="checked">multiple choice</label></div>
                    <div><label><input type="radio" name="type" disabled>Freitext</label></div>
                    <div><label><input type="radio" name="type" disabled>rhetorische Frage</label></div>
                    <div id="correctAnswers"><input placeholder="korrekte Antwort" id="correctAnswer"></div>
                    <button id="addCorrectAnswer"> +</button>
                    <button id="deleteCorrectAnswer"> -</button>
                    <div id="incorrectAnswers"><input placeholder="inkorrekte Antwort" id="incorrectAnswer"></div>
                    <button id="addIncorrectAnswer"> +</button>
                    <button id="deleteIncorrectAnswer"> -</button>
                    <button id="save">speichern</button>
                </div>
            </div>
        </div>
        <div class="col span_chat"><chat:chatWindow orientation="right" scope="project"/>
            <chat:chatWindow orientation="right" scope="group"/>
        </div>
        <jsp:include page="../taglibs/footer.jsp"/>
</body>
</html>
