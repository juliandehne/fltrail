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
    <script src="./js/enter-gfm.js"></script>
</head>
<body>
<div class="row group" id="naviPagi">

    <nav aria-label="..." style="float:left">
        <ul class="pagination">
            <li class="page-item disabled" id="navBtnPrev">
                <a class="page-link" id="btnPrev"><-</a>
            </li>
            <li class="page-item active" id="navLiTextPage">
                <a class="page-link" id="navTextPage">Einleitung</a>
            </li>
            <li class="page-item" id="navLiSurvey">
                <a class="page-link" id="navSurvey">Umfrage</a>
            </li>
            <li class="page-item" id="navLiGroupView">
                <a class="page-link" id="navGroupView">Gruppen</a>
            </li>
            <li class="page-item" id="navBtnNext">
                <a class="page-link" id="btnNext">-></a>
            </li>
        </ul>
    </nav>
    <div class="right" style="float:right;margin-top:20px;">
        <a id="buildGroupsLink" style="cursor:pointer; margin-right:150px;">Gruppen speichern</a>
        <a id="logout" style="cursor:pointer">logout</a>
    </div>

</div>
<div id="theTextPageGer" class="collapse">
    <div class="row group">

        <h2>Willkommen</h2>
        <br>
        <p>Vielen Dank, für Ihr Interesse an diesem Experiment teil zu nehmen.
            Es geht um Gruppenbildung basierend auf verschiedenen Kriterien. Hierzu gehen Sie bitte auf
            die "Umfragen-Seite" und beantworten die dortigen Fragen. Dies dauert etwa 10 Minuten.<br>
            Sobald 30 Teilnehmer die Umfrage ausgefüllt haben, werden Gruppen gebildet, die Sie dann
            unter dem Reiter "Gruppen" finden werden. So lange noch keine 30 Teilnehmer den Fragebogen
            ausgefüllt haben, ist die "Gruppen-Seite" leer. <br>
            <%--Wir bitten Sie ihre Erfahrung mit der Gruppe zu <a
                    href="http://fleckenroller.cs.uni-potsdam.de/limesurvey/index.php/356615?lang=en">hier</a> zu bewerten.<br>--%>
            <br>
            Vielen Dank für Ihre Teilnahme!
        </p>
    </div>
</div>
<div id="theTextPageEn" class="collapse">
    <div class="row group">
        <h2>Welcome</h2>
        <br>
        <p>Thank you for your interest and your participation in our survey.
            We are here to build and evaluate groups based on different criteria. Therefore use the
            "survey-page" and answer the questions. This will last about 10 minutes.<br>
            When 30 people committed their answers, you can see which group you are in on the "group-page".
            As long as there are not enough participants, you can't see your group.<br>
            <%--Please evaluate your group afterwards <a
                    href="http://fleckenroller.cs.uni-potsdam.de/limesurvey/index.php/356615?lang=en">here</a>.<br>--%>
            <br>
            Thank you for your participation!
        </p>
    </div>
</div>
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