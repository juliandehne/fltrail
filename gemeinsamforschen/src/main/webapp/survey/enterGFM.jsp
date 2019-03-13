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
                <a class="page-link" id="btnPrev"><-</a>
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
                <a class="page-link" id="btnNext">-></a>
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
        <div id="messageHolder">No context selected!</div>
        <div id="surveyContainer"></div>
        <div id="resultLink"></div>
    </div>
</div>
<div id="theGroupView" class="collapse">
    <div class="row group">
        <div class="alert alert-info" id="ifNoUserIsSetGer">
            <label>Bitte geben Sie Ihre E-Mail an:
                <input id="userEmailGroupViewGer" class="form-control">
            </label>
            <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmailGer">bestätigen</button>
            <div class="alert alert-danger" role="alert" id="emailDoesNotExistWarning">
                Diese Email existiert nicht. Bitte nehmen Sie an der Umfrage teil / This Email does not exist. Please
                enter the survey.
            </div>
        </div>
        <div class="alert alert-info" id="ifNoUserIsSetEn">
            <label>Please enter your email:
                <input id="userEmailGroupViewEn" class="form-control">
            </label>
            <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmailEn">submit</button>
        </div>
    </div>
    <div id="ifUserIsSet">
        <jsp:include page="../groupfinding/view-groups-body.jsp"/>
    </div>

</div>

</body>
</html>