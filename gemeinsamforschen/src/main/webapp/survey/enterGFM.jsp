<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <script src="../taglibs/js/utility.js"></script>
    <script src="./js/enter-gfm.js"></script>
    <title>Title</title>
    <link rel="stylesheet" href="../libs/bootstrap/css/bootstrap3.3.7.min.css">
    <script src="../libs/jquery/jquery.3.3.7.min.js"></script>

</head>
<body>
<nav aria-label="...">
    <ul class="pagination">
        <li class="page-item disabled" id="navBtnPrev">
            <a class="page-link" id="btnPrev">Previous</a>
        </li>
        <li class="page-item" id="navLiTextPage">
            <a class="page-link" id="navTextPage">Einleitung</a>
        </li>
        <li class="page-item active" id="navLiSurvey">
            <a class="page-link" id="navSurvey">Umfrage</a>
        </li>
        <li class="page-item" id="navLiGroupView">
            <a class="page-link" id="navGroupView">Gruppen</a>
        </li>
        <li class="page-item" id="navBtnNext">
            <a class="page-link" id="btnNext">Next</a>
        </li>
    </ul>
</nav>
<div id="theTextPage" class="collapse in">
<p>bla bla bla bla bla bla bla </p>
</div>
<div id="theSurvey" class="collapse">
    <div id="messageHolder">No context selected!</div>
    <div id="surveyContainer"></div>
    <div id="resultLink"></div>
</div>
<div id="theGroupView" class="collapse">
    <jsp:include page="../groupfinding/view-groups-body.jsp"/>
</div>
</body>
</html>