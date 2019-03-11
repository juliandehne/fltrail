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
    <script src="./js/saveGroupsSurvey.js"></script>
</head>
<body>
<div class="row group" id="naviPagi">
    <div class="alert alert-info" style="margin-top: 50px;">
        <label>Bitte geben sie das Passwort zur Gruppenbildung ein
            <input id="password" class="form-control">
        </label>
        <button class="btn btn-primary" style="margin-top:10px;" id="btnBuildGroups">bestätigen</button>
    </div>
    <div id="eMailVerified">
        <jsp:include page="../groupfinding/view-groups-body.jsp"/>
    </div>
</div>
</body>
</html>