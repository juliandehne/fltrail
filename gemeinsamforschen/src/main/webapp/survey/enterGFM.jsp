<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/jquery3.1.1.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="js/survey.jquery.1.0.60.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="./js/translations.js"></script>
    <script src="./js/survey-service.js"></script>
    <script src="./js/survey-viewcontroller.js"></script>

    <%--<script src="./js/enter-gfm.js"></script>--%>
</head>
<body>

<div id="navigationTemplateHolder"></div>
<script id="navigationTemplate" type="text/x-jQuery-tmpl">
<div class="row group" id="naviPagi">
    <nav aria-label="..." style="float:left">
        <ul class="pagination">
            <li class="page-item disabled" id="navBtnPrev">
                <a class="page-link" id="btnPrev"><i class="fas fa-arrow-left"></i></i></a>
            </li>
            <li class="page-item active" id="navLiTextPage">
                <a class="page-link" id="navTextPage">${introduction}</a>
            </li>
            <li class="page-item" id="navLiSurvey">
                <a class="page-link" id="navSurvey">${survey}</a>
            </li>
            <li class="page-item" id="navLiGroupView">
                <a class="page-link" id="navGroupView">${groups}</a>
            </li>
            <li class="page-item" id="navBtnNext">
                <a class="page-link" id="btnNext"><i class="fas fa-arrow-right"></i></a>
            </li>
        </ul>
    </nav>
    <div class="right" style="float:right;margin-top:20px;">
        <a id="buildGroupsLink" style="cursor:pointer; margin-right:150px;">${persist}</a>
        <a id="logout" style="cursor:pointer">${logout}</a>
    </div>
</div>

</script>

<div id="welcomeTextHolder" class="collapse in"></div>
<script id="welcomeTextTemplate" type="text/x-jQuery-tmpl">
       <div class="row group">
           <h2>${welcomeTitle}</h2>
            ${welcomeText}
       </div>

</script>

<div class="row group">
    <div id="theSurvey" class="collapse">
        <div id="noSurveyContextMessage">No context selected!</div>
        <div id="surveyContainer"></div>
        <div id="resultLink"></div>
    </div>
</div>
<div id="theGroupView" class="collapse">

    <!-- To see groups you need to be logged in. To log in, we have this div-->
    <div id="groupViewLoginTemplateHolder"></div>
    <script type="text/x-jQuery-tmpl" id="groupViewTemplate">
        <div class="row group">
            <div class="alert alert-info">
                <label>${enterEmail}
                    <input id="userEmailGroupView" class="form-control">
                </label>
                <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmail">${submit}</button>
            <div class="alert alert-danger" role="alert" id="emailDoesNotExistWarning">
                ${emailDoesntExist}
            </div>
        </div>
    </script>
    <!-- To see groups you need to be logged in. To log in, we have this div-->

    <!-- You logged in successfully -->
    <div id="groupsOrNoParticipantsMessage">
        <div class="row group">

            <!-- title -->
            <div id="titleHolder"></div>
            <script type="text/x-jQuery-tmpl" id="titleTemplate">
                    <h2 id="groupsHeadline">${computedGroups}</h2>
                </script>
            <!-- /title -->


            <div class="col span_content span_2_of_2">

                <!-- There are no groups built yet because there are not enough members -->
                <div id="noGroupMessageHolder"></div>
                <script type="text/x-jQuery-tmpl" id="noGroupTemplate">
                        <div class="alert alert-warning" id="noGroupsYet" style="display: block">
                            <div style="display:block">
                                ${noGroupsYet}
                                <p id="participantsMissing">${participantsMissing}</p>
                                ${comeBackAfterMail}
                            </div>
                            <div style="display:flex">
                                <input id="clpText" value="" readonly class="form-control-plaintext">
                                <button onclick="clpSet();" class="btn btn-secondary"><i class="far fa-clipboard"></i></button>
                            </div>
                        </div>
                    </script>
                <!-- /There are no groups built yet because there are not enough members -->

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
        <!-- /You logged in successfully -->


    </div>
</div>

</body>
</html>