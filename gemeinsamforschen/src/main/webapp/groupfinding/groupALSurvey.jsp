<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 18.09.2018
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Gruppenfindung</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <link rel="stylesheet" href="../groupfinding/css/create-groups-manual.css">
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
    <script src="js/groupALSurvey.js"></script>
</head>
<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<!-- back-->
<div class="row group">
<main class="projects">
    <div class="col span_content span_2_of_2 centered">
    <h5>
        Ihre Daten werden vertraulich behandelt und an keinen Dritten weitergegeben. Diese Erhebung
        dient ausschließlich der Zusammensetzung der Gruppen.
    </h5>
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
