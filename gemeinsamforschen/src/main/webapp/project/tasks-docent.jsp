<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/inCardSolverHTML.js"></script>
    <script src="../assessment/js/assessmentService.js"></script>
    <script src="js/tasks.js"></script>
</head>
<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="project-overview">
    <div class="row group">

     <jsp:include page="../taglibs/timeLine.jsp"/>

         <div class="col span_l_of_2 tasklist">
            <div id="listOfTasks">

            </div>
             <!-- -->
             <script id="taskTemplate" type="text/x-jQuery-tmpl">
             <div></div>   <!-- Without this seemingly useless line, intelliJ does not recognise HTML code-->
                  {{if (current==true)}}
                        <h3 class="phase-heading ${phase} ">${headLine}</h3>
                  {{/if}}
                  <div class="card ${phase}">
                       <div class="col span_s_of_2 icon ${taskType}"> </div>
                       <div class="col span_l_of_2" id="${taskName}">
                           {{if infoText}}
                               <p class="task-info">${infoText}</p>
                           {{/if}}
                           {{if solveTaskWith}}
                               <button class='primary' onClick='${solveTaskWithLink}'>${solveTaskWith}</button>
                           {{/if}}
                           {{if inCardSolver}}
                                {{if inCardSolver=="resizeGroup"}}
                                <div class="inCardSolver">
                                    <p>Sollten Sie sich für eine andere Gruppengröße entschieden haben, können sie dies hier ändern. </p>
                                    <label>Präferierte Gruppengröße <input value='3' type='number' min='3' id='userCount' style='width:40px;' onchange='updateGroupSizeView()'></label>
                                    <a data-toggle='collapse' href='#howToBuildGroups' role='button' aria-expanded='false' aria-controls='howToBuildGroups'>
                                        <i class='fas fa-question'></i>
                                    </a>
                                    Mit dieser Gruppengröße benötigt das Projekt wenigstens <span id='groupSize'>6</span>
                                    Teilnehmer um Gruppen bilden zu können.
                                    <div class='collapse' id='howToBuildGroups'>
                                        <div class='card card-body'>Es werden so viele Gruppen mit Ihrer präferierten
                                        Gruppengröße gebildet wie möglich. Die verbleibenden Studenten werden dann
                                        zufällig auf die bestehenden Gruppen verteilt.
                                        </div>
                                    </div>
                                    <button onClick='resizeGroup();'>speichern</button>
                                </div>
                                {{/if}}
                                {{if inCardSolver=="WAIT_FOR_UPLOAD"}}
                                    <div class="inCardSolver">
                                       {{if taskData.numberOfGroupsWithoutPresentation > 0 }}
                                        Anzahl an Gruppen ohne hochgeladene Präsentation:
                                        ${taskData.numberOfGroupsWithoutPresentation}. <br>
                                       {{/if}}
                                        {{if taskData.numberOfGroupReportsMissing > 0 }}
                                        Anzahl an Gruppen ohne hochgeladene Abgabe ${taskData.numberOfGroupReportsMissing}.
                                       {{/if}}
                                        <button id='startGradingButton' onClick="startGrading(getProjectName());">Bewertung abschließen</button>
                                    </div>
                                {{/if}}
                                 {{if inCardSolver=="WAIT_FOR_GRADING"}}
                                    <div class="inCardSolver">
                                        {{if taskData.numberOfGroupsWithoutExternalAssessment > 0 }}
                                        Anzahl an Gruppen ohne Bewertung durch Peers
                                        bekommen: ${taskData.numberOfGroupsWithoutExternalAssessment} .
                                        {{/if}}
                                        {{if taskData.numberOfStudentsWithoutInternalAsssessment > 0 }}
                                        Anzahl an Studierenden ohne Bewertung durch die Gruppe.
                                        ${taskData.numberOfStudentsWithoutInternalAsssessment} .
                                        {{/if}}
                                        <button id='startGradingButton' onClick="startDocentGrading(getProjectName());">Bewertung abschließen</button>
                                    </div>
                                {{/if}}
                           {{/if}}
                           {{if helpLink}}
                               <div style="width:100%"><a href='${helpLink}'>Hier</a> bekommst du Hilfe.</div>
                           {{/if}}
                       </div>
                       {{if timeFrame}}
                           {{html timeFrame}}
                       {{/if}}
                           <div style="clear:left"></div>
                  </div> <!-- end card -->
                </script>

             <!-- <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
            <div class="card-finished">
                <h3 class="icon closed phase-heading ${phase} " {{if !timeFrame}}style="color:lightgray;"{{/if}}><span>${infoText}</span></h3>
           <p style="text-align:center;">{{html timeFrame}}</p>
            </div>
        </script>-->


             <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
                <div></div>
                    {{if (current==true)}}
                        <h3 class="phase-heading ${phase} ">${headLine}</h3>
                    {{/if}}
                    {{if timeFrame}}
                        <p style="text-align:center;">{{html timeFrame}}</p>
                    {{else}}
            <div class="card ${phase} row">

                    <div class="col span_s_of_2 icon ${taskType}"></div>
                    <div class="col span_l_of_2">
                    <p style="color:gray;">${infoText}</p>
                    {{if inCardSolver}}
                        {{if inCardSolver=="WAIT_FOR_PARTICPANTS"}}
                            <p style="color:gray;">
                                Die Arbeitsgruppen wurden gebildet. Unter diesem
                                <a style="cursor:pointer;" id="groupView">Link</a>
                                können sie die Gruppen sehen.
                            </p>
                        {{/if}}
                    {{/if}}
                    </div>
            </div>
            {{/if}}
        </script>


        </div> <!-- end span L of 2 -->
        <div class="col span_s_of_2 right">
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
                Wenn sie eingeloggt <a data-toggle='collapse' href='#chatCredentials' role='button'
                                       aria-expanded='false' aria-controls='chatCredentials'>
                <i class='fas fa-question'></i>
            </a> sind, können sie Nachrichten an alle Projektteilnehmer senden oder eine Nachricht an ihre Gruppe
                verfassen.
                <div class='collapse' id='chatCredentials'>
                    <div class='card card-body'>
                        Um sich in den Chat einzuloggen, tragen sie die gleiche E-Mailadresse sowie das gleiche Passwort
                        hier ein wie bei der Anmeldung.
                    </div>
                </div>
            </div>

        </div>
    </div><!-- end row -->
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>
</html>
