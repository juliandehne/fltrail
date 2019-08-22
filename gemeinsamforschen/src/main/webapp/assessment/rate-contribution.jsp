<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Beiträge bewerten</title>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/inscrybmde@1.11.3/dist/inscrybmde.min.css">
    <script src="js/rateContribution.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <!-- go back to tasks end-->

    <main>
        <div class="row group">
            <div class="span_2_of_2 centered">
                <h3>Bewerte Gruppe <span id="groupId"></span></h3>
                <div id="theSurvey">
                    <div id="surveyContainer"></div>
                    <div id="resultLink"></div>
                </div>
                <div id="taskCompleted" class="alert alert-success">
                    <p>Die Bewertung wurde gespeichert.</p>
                </div>
                <div id="missingFeedback" class="alert alert-warning">
                    <p>Stellen Sie sicher alle Beiträge der Gruppe bewertet zu haben.</p>
                </div>
            </div>
        </div>
    </main>
</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>

</body>

</html>