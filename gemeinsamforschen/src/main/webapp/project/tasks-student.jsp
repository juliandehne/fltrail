<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 04.10.2018
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Aufgaben</title>

    <script src="js/tasks.js"></script>
    <script src="js/solve-inCardTasks.js"></script>
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main class="project-overview">

        <div class="row group">

            <jsp:include page="../taglibs/jsp/timeLine.jsp"/>

            <div class="col span_l_of_2 tasklist">

                <%--    <div class="infotext ">
                    <p class="icon">Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt
                        ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et
                        ea rebum.
                    </p>
                </div>--%>
                <h1 style="text-align: center;margin-bottom: 15px;"><u>Aufgaben</u></h1>
                <div id="listOfTasks">

                </div>

                <script id="taskTemplate" type="text/x-jQuery-tmpl">
                    <div></div>
                        {{if (current==true)}}
                            <h3 class="phase-heading ${phase} ">${headLine}</h3>
                        {{/if}}
                        <div class="card ${phase} current">
                            <div class="col span_s_of_2 icon ${taskType}"></div>
                                {{if inCardSolver=="CONTACT_GROUP_MEMBERS"}}
                                    <p>
                                        Sagen Sie hallo zu ihren
                                         <a style="cursor:pointer;" class="groupView">Gruppenmitgliedern</a>
                                         über den Chat.
                                    </p>
                                {{else}}
                            <div class="col span_l_of_2" id="${taskName}">
                                {{if infoText}}
                                    <p class="task-info">${infoText}</p>
                                {{/if}}
                                {{if solveTaskWith}}
                                    <button class='primary' onClick='${solveTaskWithLink}'><i class="fas fa-link"></i> ${solveTaskWith}</button>
                                {{/if}}
                                {{if helpLink}}
                                    <div style="width:100%"><a href='${helpLink}'>Hier</a> bekommst du Hilfe.</div>
                                {{/if}}
                            </div>

                            {{if timeFrame}}
                                {{html timeFrame}}
                            {{/if}}

                            <div style="clear:left"></div>
                        </div>
                    {{/if}}
                </script>

                <script id="finishedTaskTemplate" type="text/x-jQuery-tmpl">
                    <div></div>
                        {{if (current==true)}}
                            <h3 class="phase-heading finished ${phase} ">${headLine}</h3>
                        {{/if}}
                        {{if timeFrame}}
                            <p style="text-align:center;">{{html timeFrame}}</p>
                        {{else}}
                <div class="card ${phase}">

                        <div class="col span_s_of_2 icon ${taskType}"></div>
                        <div class="col span_l_of_2">
                            <p style="color:gray;">${infoText}</p>
                                {{if inCardSolver}}
                                    {{if inCardSolver=="WAITING_FOR_GROUP"}}
                                        <p style="color:gray;">
                                            Die Arbeitsgruppen wurden gebildet. Unter diesem
                                            <a style="cursor:pointer;" class="groupView">Link</a>
                                            können Sie die Gruppen sehen.
                                        </p>
                                    {{/if}}
                                    {{if inCardSolver=="CONTACT_GROUP_MEMBERS"}}
                                        <p style="color:gray;">
                                            Sagen Sie hallo zu ihren
                                             <a style="cursor:pointer;" class="groupView">Gruppenmitgliedern</a>
                                             über den Chat.
                                        </p>
                                    {{/if}}
                                {{/if}}
                        </div>
                    <div style="clear:left"></div>
                </div>
                {{/if}}
                </script>
                <script id="inProgressTaskTemplate" type="text/x-jQuery-tmpl">
                    <div></div>
                        {{if (current==true)}}
                            <h3 class="phase-heading ${phase} ">${headLine}</h3>
                        {{/if}}
                <div class="card ${phase}">
                   <div class="card-finished">
                        <div class="col span_s_of_2 icon ${taskType}"></div>
                        <div class="col span_l_of_2">
                            <p class="task-info">${infoText}<i class="fa fa-clock-o" aria-hidden="true"></i></p>
                            {{if solveTaskWith}}
                                <button class='primary' onClick='${solveTaskWithLink}'><i class="fas fa-link"></i> ${solveTaskWith}</button>
                            {{/if}}
                            {{if helpLink}}
                                <div style="width:100%"><a href='${helpLink}'>Hier</a> bekommst du Hilfe.</div>
                            {{/if}}
                              {{html timeFrame}}
                       </div>
                   </div>
                   <div style="clear:left"></div>
                </div>
                </script>
            </div>
            <div class="span-chat">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item active">
                        <a class="nav-link active" href="#projectChat" role="tab" data-toggle="tab">ProjektChat</a>
                    </li>
                    <li class="nav-item" id="liGroupWindow">
                        <a class="nav-link" href="#groupChat" role="tab" data-toggle="tab">Gruppenchat</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane fade in active" id="projectChat">
                        <chat:chatWindow orientation="right" scope="project"/>
                    </div>
                    <div role="tabpanel" class="tab-pane fade" id="groupChat">
                        <chat:chatWindow orientation="right" scope="group"/>
                    </div>
                    Wenn Sie eingeloggt <a data-toggle='collapse' href='#chatCredentials' role='button'
                                           aria-expanded='false' aria-controls='chatCredentials'>
                    <i class='fas fa-question'></i>
                </a> sind, können Sie Nachrichten an alle Projektteilnehmer senden oder eine Nachricht an ihre Gruppe
                    verfassen.
                    <div class='collapse' id='chatCredentials'>
                        <div class='card card-body'>
                            Um sich in den Chat einzuloggen, tragen Sie die gleiche E-Mailadresse sowie das gleiche
                            Passwort
                            hier ein wie bei der Anmeldung.
                        </div>
                    </div>
                </div>

            </div>

        </div> <!-- end row -->
    </main>


</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>
</html>