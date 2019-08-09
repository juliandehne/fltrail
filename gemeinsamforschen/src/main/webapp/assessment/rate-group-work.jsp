<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 18.09.2018
  Time: 13:36
  This JSP holds the questions that are used to assess the group work
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Mitarbeit bewerten</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="js/rate-group-work.js"></script>
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>


    <div class="row group">
        <main class="projects">
            <div class="span_2_of_2 centered">
                <div id="taskTemplateDiv">

                </div>
                    <script id="taskTemplate" type="text/x-jQuery-tmpl">
                        <h3>Bewertung des Gruppenmitglieds ${name}</h3>
                    </script>
                <div id="theSurvey">
                    <div id="surveyContainer"></div>
                    <div id="resultLink"></div>
                </div>
            </div>
        </main>
    </div>

</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>
</html>
