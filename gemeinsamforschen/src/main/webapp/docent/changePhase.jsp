<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../core/gemeinsamForschen.tld" prefix="footer" %>


<html>
<head>
    <omniDependencies:omniDependencies/>
    <script src="../assets/js/changePhase.js"></script>
</head>

<body>
<div id="wrapper">
    <menu:menu/>
    <div class="page-content-wrapper">
        <headLine:headLine/>
        <div class="alert"id="CourseCreation">
            <button id="btnCourseCreation" class="btn btn-secondary">Projekterstellungsphase</button>
        </div>
        <div class="alert" id="GroupFormation">
            <button id="btnGroupformation" class="btn btn-secondary">Gruppen erstellen</button>
        </div>
        <div class="alert" id="DossierFeedback">
            <button id="btnDossierFeedback" class="btn btn-secondary">Feedbackphase</button>
        </div>
        <div class="alert" id="Execution">
            <button id="btnExecution" class="btn btn-secondary">Durchführungsphase</button>
        </div>
        <div class="alert" id="Assessment">
            <button id="btnAssessment" class="btn btn-secondary">Bewertungsphase</button>
        </div>
        <div class="alert" id="end">
            <button id="btnProjectfinished" class="btn btn-secondary">Ende</button>
        </div>
    </div>
    <footer:footer/>
</div>
</body>
</html>
