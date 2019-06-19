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
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="js/rate-group-work.js"></script>
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="row group">
        <main class="projects">
            <div class="col span_content span_2_of_2 centered">
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
    <jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>
</html>