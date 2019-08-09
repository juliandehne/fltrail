<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Gruppenbewertung</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://surveyjs.azureedge.net/1.0.60/survey.css" type="text/css" rel="stylesheet"/>
    <script src="https://surveyjs.azureedge.net/1.0.60/survey.jquery.min.js"></script>
    <script type="text/javascript" src="../libs/jquery/jqueryTemplate.js"></script>
</head>
<body>
<div id="theTextPageGer">
    <div class="row group">
        <h1>Bewertung Ihrer Gruppe</h1>
        <br>
        <p> Vielen Dank für Ihre Zeit und Ihr Interesse an dieser Studie bis zum Ende teil zu nehmen.
            Hier bewerten Sie die Teamfähigkeit Ihrer Gruppenmitglieder.<br>
            Sollten Sie noch nicht wenigstens 5 Spiele mit Ihrer Gruppe gespielt haben,
            bitten wir Sie dies zu gewährleisten bevor Sie Ihre Gruppe hier bewerten.<br>

            <br>
            Vielen Dank für Ihre Teilnahme
        </p>
    </div>
</div>
<div id="theTextPageEn" class="collapse">
    <div class="row group">
        <h1>Assess your group</h1>
        <br>
        <p> Thanks a lot for your time and your interest to participate in this study.
            Here you can evaluate your group members by meanings of their skills to work in a team.<br>
            If you did not have at least 5 games with your group, we ask you to complete these
            before you attend to assess your group.
            <br>
            Thank you for your participation!
        </p>
    </div>
</div>
<div class="row group">
    <div id="groupAssessment">
        <h1>under Construction</h1>
        <div id="ifUserIsSet">
            <jsp:include page="../assessment/assess-work-body.jsp"/>
        </div>
        <div class="alert alert-info" id="ifNoUserIsSetGer">
            <p>Bitte geben Sie Ihre E-Mail an:</p>
            <input id="userEmailGroupViewGer" class="form-control">
            <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmailGer">bestätigen</button>
        </div>
        <div class="alert alert-info" id="ifNoUserIsSetEn">
            <p>Please enter your email:</p>
            <input id="userEmailGroupViewEn" class="form-control">
            <button class="btn btn-primary" style="margin-top:10px;" id="btnSetUserEmailEn">submit</button>
        </div>
    </div>
</div>
<div id="thankYou">

</div>

</body>
</html>