<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
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
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>

    <main id="create-goals">

        <div class="row group">

            <div class="col span_2_of_2">
                <h3> Lernziele und Reflexionsfragen erstellen </h3>
                <div  id="creationTemplateResult"></div>
                 <script id="creationTemplate" type="text/x-jsrender">


                <div class="col">
                    <label>Schritt 1 von 3: Lernziel auswählen</label>
                    <div class="list-group" id="list-tab" role="tablist">
                        {{for learningGoals}}
                            <a class="list-group-item list-group-item-action pointer {{:active}}" id="list-{{:text}}" data-toggle="list" role="tab" onclick='learningGoalChosen("{{:text}}",{{:custom}})'>{{:text}}</a>
                        {{/for}}
                    </div>
                    <input class="form-control" id="customLearningGoalField" placeholder="Benutzerdefiniertes Lernziel">
                   <div class="btn_holder">
                        <button type="button" onClick='addCustomLearningGoal()' class="btn btn-primary" id="saveButtonCustomLearningGoal()">Hinzufügen</button>
                    </div>
                </div>

                {{if reflectionQuestions}}
                    <div class="col">
                        <label>Schritt 2 von 3: Reflexionsfragen auswählen</label>
                        <div class="list-group" id="list-tab" role="tablist">
                            {{for reflectionQuestions}}
                                <a class="list-group-item list-group-item-action pointer {{:active}}" id="list-{{:id}}" data-toggle="list" role="tab" onclick='reflectionQuestionChosen("{{:id}}")'>{{:question}}</a>
                            {{/for}}
                        </div>
                        <input class="form-control" id="customReflectionQuestion" placeholder="Benutzerdefinierte Frage">
                          <div class="btn_holder">
                                <button type="button" onClick='addCustomReflectionQuestion()' class="btn btn-primary" id="saveButtonCustomLearningGoal()">Hinzufügen</button>
                           </div>
                    </div>
                {{/if}}
                {{if choseReflectionQuestion}}

                    <div class="col">
                        <label>Schritt 3 von 3: Speichern und beenden</label>
                        <div class="btn_holder">
                        <button type="button" onClick='addAdditionalLearningGoalPressed()' class="btn btn-primary" id="saveButton">Weiteres Lernziel hinzufügen</button>
                        </div>
                        <div class="btn_holder">
                        <button type="button" onClick='saveButtonPressed()' class="btn btn-primary" id="saveButton">Speichern und Beenden</button>
                        </div>
                    </div>
                {{/if}}
                {{if showExitButton}}
                    <div class="col">
                        <label>Letzer Schritt: Speichern sie alle breits erstellten Lernziele</label>
                        <div class="btn_holder">
                        <button type="button" onClick='exitButtonPressed()' class="btn btn-primary" id="saveButton">Beenden</button>
                        </div>
                     </div>
                {{/if}}

            </div>
        </div>



 </script>

    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>
