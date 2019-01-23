<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="./js/enter-gfm.js"></script>
</head>
<body>
<div class="row group">

    <nav aria-label="...">
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
</div>
<div id="theTextPage" class="collapse in">
    <div class="row group">

        <h2>Willkommen</h2>
        <br>
        <p>Vielen Dank, für Ihr Interesse an diesem Experiment teil zu nehmen.
            Es geht um Gruppenbildung basierend auf verschiedenen Kriterien. Hierzu gehen Sie bitte auf
            die "Umfragen-Seite" und beantworten die dortigen Fragen. Dies dauert etwa 10 Minuten.<br>
            Sobald 30 Teilnehmer die Umfrage ausgefüllt haben, werden Gruppen gebildet, die Sie dann
            unter dem Reiter "Gruppen" finden werden. So lange noch keine 30 Teilnehmer den Fragebogen
            ausgefüllt haben, ist die "Gruppen-Seite" leer. <br>
            Sobald Sie Ihre Gruppe gefunden haben, können Sie sich gegenseitig auf Discord adden und
            Ihr Spiel starten. Nachdem Sie wenigstens 5 Spiele gemeinsam bestritten haben, bitten wir Sie
            Ihre Erfahrung mit der Gruppe zu <a href="#">hier</a> zu bewerten.<br>
            <br>
            Vielen Dank für Ihre Teilnahme
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
        <div class="alert alert-info" id="ifNoUserIsSet">
            <p>Bitte geben Sie Ihre E-Mail an:</p>
            <input id="userEmailGroupView" class="form-control">
            <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmail">bestätigen</button>
        </div>
    </div>
    <div id="ifUserIsSet">
        <jsp:include page="../groupfinding/view-groups-body.jsp"/>
    </div>
</div>
</body>
</html>