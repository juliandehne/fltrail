<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <title>Aufgaben</title>

    <script src="../assessment/js/assessmentService.js"></script>
    <script src="js/tasks.js"></script>
    <script src="js/solve-inCardTasks.js"></script>
    <script src="../taglibs/js/quill/quillFileGenerator.js"></script>
    <script src="../taglibs/js/apiClient/reflectionClientGeneral.js"></script>
    <script src="../taglibs/js/quill/quillArrayEntryObject.js"></script>
    <script src="../taglibs/js/quill/quillJsObject.js"></script>
</head>
<body>
<div class="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="project-overview">
    <div class="row group">

        <jsp:include page="../taglibs/jsp/timeLine.jsp"/>

         <div class="col span_l_of_2 tasklist">
             <div class="infobox dossier" style="margin-bottom:30px;" >
                 <p>
                 <h3 style="margin-left: 20px;">Aufgabenliste</h3><br>
                 Hier sehen Sie eine Übersicht der Aufgaben, die die Studierenden gerade bearbeiten.
                 Von hier aus steuern Sie die Projektphasen.
                 Wenn Sie Informationen über die einzelnen Projektphasen benötigen, gehen Sie zu zum Hilfe-Menü oben
                 rechts.
                 </p>
                 <hr>
             </div>
            <div id="listOfTasks">

            </div>
             <!-- -->
             <script id="taskTemplate" type="text/x-jQuery-tmpl">
             <div></div>   <!-- Without this seemingly useless line, intelliJ does not recognise HTML code-->
                  {{if (current==true)}}
                        <div id="angle-down"><i class="fas fa-angle-double-down"></i></div>
                        <h3 class="phase-heading ${phase} ">${headLine}</h3>
                  {{/if}}

                  <div class="card ${phase}">
                       <div class="col span_s_of_2 icon ${taskType}"> </div>
                       <div class="col span_l_of_2" id="${taskName}">
                           {{if infoText}}
                               <p class="task-info">${infoText}</p>
                           {{/if}}
                           {{if solveTaskWith}}
                               <button class='primary' onClick='${solveTaskWithLink}'><i class="fas fa-link"></i> ${solveTaskWith}</button>
                           {{/if}}
                           {{if inCardSolver}}
                                {{if inCardSolver=="RESIZE_GROUP"}}
                                <div class="inCardSolver">
                                    <p>Sollten Sie sich für eine andere Gruppengröße entschieden haben, können sie dies hier ändern. </p>
                                    <label>Präferierte Gruppengröße <input value='${groupSize}' type='number' min='3' id='userCount' style='width:40px;' onchange='updateGroupSizeView()'></label>
                                    <a data-toggle='collapse' href='#howToBuildGroups' role='button' aria-expanded='false' aria-controls='howToBuildGroups'>
                                        <i class='fas fa-question'></i>
                                    </a>
                                    Mit dieser Gruppengröße müssen wenigstens <span id='groupSize'>${memberCount}</span>
                                    Personen dem Projekt beitreten um Gruppen bilden zu können.
                                    <div class='collapse' id='howToBuildGroups'>
                                        <div class='card card-body'>Es werden so viele Gruppen mit Ihrer präferierten
                                        Gruppengröße gebildet wie möglich. Die verbleibenden Studierenden werden dann
                                        zufällig auf die bestehenden Gruppen verteilt.
                                        </div>
                                    </div>
                                    <button style="margin-top:20px;"  onClick='resizeGroup();'>speichern</button>
                                </div>
                                {{/if}}
                                {{if inCardSolver=="WAIT_FOR_UPLOAD"}}
                                    <div class="inCardSolver">
                                       {{if taskData.numberOfGroupsWithoutPresentation > 0 }}
                                        Anzahl an Gruppen ohne hochgeladene Präsentation:
                                        ${taskData.numberOfGroupsWithoutPresentation}. <br>
                                        {{else}}
                                        Jede Gruppe hat eine Präsentation hochgeladen. <br>
                                       {{/if}}
                                        {{if taskData.numberOfGroupReportsMissing > 0 }}
                                        Anzahl an Gruppen ohne hochgeladene Abgabe ${taskData.numberOfGroupReportsMissing}.
                                        {{else}}
                                        Jede Gruppe hat einen abschließenden Report hochgeladen. <br>
                                       {{/if}}
                                        <button id='startGradingButton' style="margin-top:20px;" class='btn btn-primary' onClick="startGrading(getProjectName());">Upload Phase abschließen</button>
                                    </div>
                                {{/if}}
                                {{if inCardSolver=="CLOSE_EXECUTION_PHASE"}}
                                    <div class="inCardSolver">
                                        <p><u>Fortschritt Reflexionsfragen </u></p>
                                        {{if taskData.numberOfMissingReflectionQuestions === 0}}
                                            <p>Alle Studierende haben die Reflexionsfragen beantwortet.</p>
                                        {{else}}
                                            {{if taskData.numberOfMissingReflectionQuestions > 3}}
                                                <p>Es müssen noch ${taskData.numberOfMissingReflectionQuestions} Studierende die Reflexionsfragen beantworten.</p>
                                            {{else}}
                                                <p>Folgende Studierenden müssen die Reflexionsfragen noch beantworten:</p>
                                                <ul>
                                                {{each taskData.userUnansweredReflectionQuestions}}
                                                    <li> - ${name} (${email})</li>
                                                {{/each}}
                                                </ul>
                                            {{/if}}
                                        {{/if}}
                                        <br/>
                                        <p><u>Fortschritt Auswahl der Portfolio-<br>Einträge fürs Assessment </u></p>
                                        {{if taskData.numberOfMissingForAssessmentChosen === 0}}
                                            <p>Alle Studierenden haben ihre Abgaben für das Assessment gewählt.</p>
                                        {{else}}
                                            {{if taskData.numberOfMissingForAssessmentChosen > 3}}
                                                <p>Es müssen ${taskData.numberOfMissingForAssessmentChosen} Studierende ihre Auswahl <br/> fürs Assessment treffen.</p>
                                            {{else}}
                                                <p>Es müssen folgende Studierenden die <br/> Auswahl fürs Assessment treffen:</p>
                                                <ul>
                                                {{each taskData.userUnchosenAssessmentMaterial}}
                                                    <li> - ${name} (${email})</li>
                                                {{/each}}
                                                </ul>
                                            {{/if}}
                                        {{/if}}
                                        <button style="margin-top:20px; margin-bottom:20px;" onClick="saveQuillFileAndClose('Execution', getProjectName());" class="btn btn-primary"><i class="fas fa-link"></i> Projektsphase beenden </button>
                                    </div>
                                {{/if}}
                                {{if inCardSolver=="CLOSE_PEER_ASSESSMENTS_PHASE"}}
                                    <div class="inCardSolver">
                                        {{if taskData.numberOfGroupsWithoutExternalAssessment > 0 }}
                                            Anzahl an Gruppen ohne Bewertung durch Peers
                                            : ${taskData.numberOfGroupsWithoutExternalAssessment}
                                            <br>
                                        {{else}}
                                            Alle Abgaben wurden von den Gruppen bewertet.
                                            </br>
                                        {{/if}}
                                        {{if taskData.numberOfStudentsWithoutInternalAsssessment > 0 }}
                                            Anzahl an Studierenden ohne Bewertung durch die Gruppe:
                                            ${taskData.numberOfStudentsWithoutInternalAsssessment}
                                        {{else}}
                                            Alle Studierenden haben sich gegenseitig bewertet
                                        {{/if}}
                                        <button style="margin-top:20px; margin-bottom:20px;" onClick="closePhase('Assessment', getProjectName());" class="btn btn-primary"><i class="fas fa-link"></i> Studentische Bewertung abschließen </button>
                                    </div>
                                {{/if}}
                                {{if inCardSolver=="GIVE_EXTERNAL_ASSESSMENT_TEACHER"}}
                                       {{if taskData.progressData.numberNeeded == 1}}
                                        Es fehlt noch eine Gruppe.
                                        {{else}}
                                        Es fehlen noch ${taskData.progressData.numberNeeded} Gruppen.
                                        {{/if}}
                                {{/if}}
                           {{/if}}
                           {{if helpLink}}
                               <div style="width:100%" style="margin-top:20px;"><a href='${helpLink}'>Hier</a> bekommen Sie Hilfe.</div>
                           {{/if}}
                       </div>
                       {{if timeFrame}}
                           {{html timeFrame}}
                       {{/if}}
                           <div style="clear:left"></div>
                  </div> <!-- end card -->
                </script>


             <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
                <div></div>
                    {{if (current==true)}}
                        <div id="angle-down"><i class="fas fa-angle-double-down"></i></div>
                        <h3 class="phase-heading finished ${phase} ">${headLine}</h3>
                    {{/if}}
                    {{if closedPhase}}
                        {{html closedPhase}}
                    {{else}}
            <div class="card card-fnished ${phase}">
                    <div class="col span_s_of_2 icon ${taskType}"></div>
                    <div class="col span_l_of_2">
                    {{if timeFrame}}
                            {{html timeFrame}}
                        {{/if}}
                        <p style="color:gray;">${infoText}</p>
                        {{if inCardSolver}}
                            {{if inCardSolver=="WAIT_FOR_PARTICPANTS"}}
                                <p style="color:gray;">
                                    Die Arbeitsgruppen wurden gebildet. Unter diesem
                                    <a style="cursor:pointer;" class="groupView">Link</a>
                                    können Sie die Gruppen sehen.
                                </p>
                            {{/if}}
                        {{/if}}
                    </div>
                    <div style="clear:left"></div>
            </div>
            {{/if}}
        </script>


        </div> <!-- end span L of 2 -->
        <div class="span-chat">
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item active">
                    <a class="nav-link active" href="#projectChat" role="tab" data-toggle="tab">ProjektChat</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="projectChat">
                    <chat:chatWindow orientation="right" scope="project"/>
                </div>
                Wenn Sie eingeloggt <a data-toggle='collapse' href='#chatCredentials' role='button'
                                       aria-expanded='false' aria-controls='chatCredentials'>
                <i class='fas fa-question'></i>
            </a> sind, können Sie Nachrichten an alle Projektteilnehmer senden oder eine Nachricht an ihre Gruppe
                verfassen.
                <div class='collapse' id='chatCredentials'>
                    <div class='card card-body'>
                        Um sich in den Chat einzuloggen, tragen Sie die gleiche E-Mailadresse sowie das gleiche Passwort
                        hier ein wie bei der Anmeldung.
                    </div>
                </div>
            </div>

        </div>
    </div><!-- end row -->
</main>
</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>
<div hidden id="editor"></div>
<jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
    <jsp:param name="readOnly" value="true"/>
</jsp:include>
</body>
</html>
