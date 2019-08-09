<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Gruppenfindung</title>

    <script src="js/jquery3.1.1.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="js/survey.jquery.1.0.60.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>

    <script  type="text/javascript" src="./js/translations.js"></script>
    <script  type="text/javascript" src="./js/survey-service.js"></script>
    <script  type="text/javascript" src="./js/saveGroupsSurvey.js"></script>
    <link rel="stylesheet" href="./css/survey.css"/>

</head>
<body>
<div class="row group" id="naviPagi">
    <button class="btn btn-secondary" id="backToSurvey" onclick="history.back(-1)" />Zurück zum Survey</button>
    <div class="alert alert-info" id="authenticationPanel">
        <label>Bitte geben Sie das Passwort zur Gruppenbildung ein
            <input id="password" class="form-control">
        </label>
        <button class="btn btn-primary" id="btnBuildGroups">Gruppen bilden</button>
    </div>
    <div class="alert alert-danger" role="alert" id="wrongAuthentication">
        Das Passwort ist falsch. Versuchen Sie es erneut!
    </div>
    <div id="eMailVerified">
        <div class="alert alert-warning" role="alert" id="NoParticipantsInfo">
            Es gibt noch keine Teilnehmer in diesem Projekt.
        </div>
        <div style="display: flex">
            <div style="display:block">

                <!-- There are groups and they will be displayed here -->
                <div class="list-group" style="display: flex; flex-wrap: wrap;" id="groupsInProject"></div>
                <script id="groupTemplate" type="text/x-jQuery-tmpl">
                            <div style="" class="grouplists" id="${groupName}">
                                <ul class="complex-list">
                                    <li class="label">
                                        <div type="button" class="list-group-item list-group-item-action">${groupName}</div>
                                    </li>
                                    {{each groupMember}}
                                    <li>
                                        <div type="button" name="student" class="list-group-item list-group-item-action">
                                            <span>name: ${name}</span>
                                            <p name="userEmail">E-mail: ${email}</p>
                                            {{if discordid}}
                                                <p name="discordId"> discordId: ${discordid}</p>
                                            {{/if}}
                                        </div>
                                    </li>
                                    {{/each}}
                                    <li>
                                        <p name="chatRoomId" hidden>${chatRoomId}</p>
                                    </li>
                                </ul>
                            </div>
                        </script>
                <!-- /There are groups and they will be displayed here -->

            </div>
        </div>
    </div>
</div>
</body>
</html>