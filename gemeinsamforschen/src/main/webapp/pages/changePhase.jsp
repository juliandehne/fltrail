<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

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
        <div class="alert" id="CourseCreation">
            Projekterstellungsphase
        </div>
        <div class="alert" id="GroupFormation">
            Entwurfsphase
        </div>
        <div class="alert" id="Execution">
            Durchführungsphase
        </div>
        <div class="alert" id="DossierFeedback">
            Feedbackphase
        </div>
        <div class="alert" id="Assessment">
            Bewertungsphase
        </div>
        <div class="alert" id="end">
            Ende
        </div>
    </div>
</div>

</body>
</html>
