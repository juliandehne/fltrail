<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- jsrender -->
    <link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
    <link rel="stylesheet" type="text/css" href="css/create-learning-goals.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
    <script src="../taglibs/js/dropDownButton.js"></script>
    <script src="../taglibs/js/apiClient/learningGoalClient.js"></script>
    <script src="../taglibs/js/apiClient/reflectionQuestionClient.js"></script>
    <script src="js/create-learning-goals.js"></script>
    <script src="../taglibs/js/apiClient/reflectionClientGeneral.js"></script>
    <script src="../libs/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main>
        <div class="backlink">
            <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
                Aufgaben</i></a>
        </div>
        <div id="creationTemplateResult"></div>
        <script id="creationTemplate" type="text/x-jsrender">
            <div/>
                <div class="group">
                    <div class="row justify-content-center">
                        <h1> Lernziele und Reflexionsfragen erstellen </h1>
                        <div class="col">
                            <h2>Lernziel auswählen</h2>
                            <div class="list-group" id="list-tab" role="tablist">
                                {{for learningGoals}}
                                    <a class="list-group-item list-group-item-action pointer {{:active}}" id="list-{{:text}}" data-toggle="list" role="tab" onclick='learningGoalChosen("{{:text}}",{{:custom}})'>{{:text}}</a>
                                {{/for}}
                            </div>
                            <input class="form-control" id="customLearningGoalField" placeholder="Benutzerdefiniertes Lernziel">
                            <div class="row">
                                <button type="button" onClick='addCustomLearningGoal()' class="btn btn-primary col pull-right" id="saveButtonCustomLearningGoal()">Hinzufügen</button>
                            </div>
                        </div>
                        {{if reflectionQuestions}}
                            <div class="col">
                                <h2> Reflexionsfragen auswählen</h2>
                                <div class="list-group" id="list-tab" role="tablist">
                                    {{for reflectionQuestions}}
                                        <a class="list-group-item list-group-item-action pointer {{:active}}" id="list-{{:id}}" data-toggle="list" role="tab" onclick='reflectionQuestionChosen("{{:id}}")'>{{:question}}</a>
                                    {{/for}}
                                </div>
                            <input class="form-control" id="customReflectionQuestion" placeholder="Enter Reflexion Question">
                            <div class="row">
                                <button type="button" onClick='addCustomReflectionQuestion()' class="btn btn-primary col pull-right" id="saveButtonCustomLearningGoal()">Hinzufügen</button>
                            </div>
                            </div>
                        {{/if}}
                        {{if choseReflectionQuestion}}
                            <button type="button" onClick='addAdditionalLearningGoalPressed()' class="btn btn-primary col pull-right" id="saveButton">Weiteres Lernziel hinzufügen</button>
                            <button type="button" onClick='saveButtonPressed()' class="btn btn-primary col pull-right" id="saveButton">Speichern und Beenden</button>
                        {{/if}}
                </div>
            </div>
        </script>
    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
