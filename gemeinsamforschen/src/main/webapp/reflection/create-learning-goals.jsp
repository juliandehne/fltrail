<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Lernziele erstellen</title>
    <!-- jsrender -->
    <link rel="stylesheet" type="text/css" href="../taglibs/css/dropDownButton.css">
    <link rel="stylesheet" type="text/css" href="css/create-learning-goals.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
    <script src="../taglibs/js/dropDownButton.js"></script>
    <script src="../taglibs/js/apiClient/learningGoalClient.js"></script>
    <script src="../taglibs/js/apiClient/reflectionQuestionClient.js"></script>
    <script src="js/create-learning-goals.js"></script>
    <script src="../taglibs/js/apiClient/reflectionClientGeneral.js"></script>
    <script src="../taglibs/js/apiClient/taskClient.js"></script>
    <!--<script src="../libs/bootstrap/js/bootstrap.min.js"></script>-->
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


    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Lernziel und Reflexionsfragen auswählen</h4>
                </div>
                <div class="modal-body" role="document" aria-labelledby="myModalLabel">
                    <div id="modalTemplateResult"></div>
                    <script id="modalTemplate" type="text/x-jsrender">
                        <div/>
                            <label>Schritt {{:step}} von 2: {{:stepTitle}}</label>

                            {{if step == 1}}
                                <div class="list-group" id="list-tab" role="tablist">
                                    {{for learningGoals}}
                                        <a class="list-group-item list-group-item-action pointer" id="list-{{:#index}}" data-toggle="list" role="tab" onclick='learningGoalChosen({{:#index}})'>{{:text}}</a>
                                    {{/for}}
                                </div>
                                <input class="form-control" id="customLearningGoalField" placeholder="Benutzerdefiniertes Lernziel">
                                <div class="btn_holder">
                                    <button type="button" onClick='addCustomLearningGoal()' class="btn btn-primary" id="saveButtonCustomLearningGoal()">Hinzufügen</button>
                                </div>
                            {{/if}}
                            {{if step == 2}}
                                <br/><label>Ausgewählte Frage: {{:selectedLearningGoal.text}}</label>
                                <div class="list-group" id="list-tab" role="tablist">
                                    {{for reflectionQuestions}}
                                        <a class="list-group-item list-group-item-action pointer" id="list-{{:#index}}" data-toggle="list" role="tab" onclick='reflectionQuestionChosen("{{:#index}}")'>{{:question}}</a>
                                    {{/for}}
                                </div>
                                <input class="form-control" id="customReflectionQuestion" placeholder="Benutzerdefinierte Frage">
                                <div class="btn_holder">
                                       <button type="button" onClick='addCustomReflectionQuestion()' class="btn btn-primary" id="saveButtonCustomReflectionQuestion()">Hinzufügen</button>
                                </div>
                            {{/if}}

                            <div class="modal-footer">
                                <button type="button" onclick="clickCancelButton()" class="btn btn-default float-left" data-dismiss="modal">Abbrechen</button>
                                {{if step > 1}}
                                    <button type="button" onclick="saveButtonClicked()" class="btn btn-primary float-right">Speichern</button>
                                    <button type="button" onclick="clickBackButton()" class="btn btn-primary float-right">Zurück</button>
                                {{else}}
                                    <button type="button" onclick="clickNextButton()" class="btn btn-primary float-right">Weiter</button>
                                {{/if}}
                            </div>



                    </script>
                </div>

            </div>
        </div>
    </div>


    <main id="create-goals">
        <!-- Button trigger modal -->


        <div id="selectedLearningGoalResult"></div>
        <script id="selectedLearningGoalTemplate" type="text/x-jsrender">
                <div></div>
                <div class="row group">
                    <h1>{{if !finished}} Lernziele und Reflexionsfragen auswählen {{else}} Ausgewählte Lernziele und Reflexionsfragen {{/if}}</h1>
                    {{if !finished}}
                    <button type="button" onclick="setupModal()" class="btn btn-primary btn-lg" data-toggle="modal"
                            data-target="#myModal">
                        Neues Lernziel und Reflexionsfragen hinzufügen
                    </button>
                    {{/if}}
                </div>
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    {{for selectedEntries}}
                        <div class="panel panel-default">
                            <div>
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-{{:learningGoal.id}}" aria-expanded="false" class="collapsed" aria-controls="collapse-{{:learningGoal.id}}">
                                    <div class="btn btn-primary panel-heading" role="tab" id="heading-{{:learningGoal.id}}">
                                        <div class="panel-title pointer">
                                            <div class="row">
                                                <h4>{{:learningGoal.text}}</h4>
                                                <i class="fas fa-chevron-down"></i>
                                                {{if !#root.data.finished}}
                                                    <button class="btn btn-primary float-left" onclick='clickDeleteLearningGoalButton("{{:#getIndex()}}")'> <i class="fas fa-trash"></i></button>
                                                {{/if}}
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                            <div id="collapse-{{:learningGoal.id}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-{{:learningGoal.id}}">
                                <div class="panel-body">
                                    <h3>Reflexionsfragen</h3>
                                    {{for reflectionQuestions}}
                                        <div class="well">
                                            <div class="row">
                                                <h4>{{:#index + 1}}. {{:question}}</h4>
                                            </div>
                                        </div>
                                    {{/for}}
                                 </div>
                            </div>
                        </div>
                    {{/for}}
                </div>
                {{if !finished}}
                    {{if selectedEntries.length > 0}}
                        <div class="row">
                            <button onclick="endLearningGoalSelection()" class="btn btn-primary float-right">Auswahl abschließen</button>
                        </div>
                    {{/if}}
                {{/if}}

        </script>
    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>
