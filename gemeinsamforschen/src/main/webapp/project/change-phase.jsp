<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>


<html>
<head>
    <omniDependencies:omniDependencies hierarchy="1"/>
    <script src="js/changePhase.js"></script>
    <link href="css/changePhase.css" rel="stylesheet">
</head>

<body>
<menu:menu hierarchy="1"/>
<div class="page-content-wrapper">
    <headLine:headLine/>
    <div class="container-fluid">
        <input type="image" src="../libs/img/arrow.png" class="arrow" id="changePhase"/>
        <div class="alert" id="CourseCreation">
            <p>Projekterstellungsphase</p>
        </div>
        <div class="alert" id="GroupFormation">
            <p>Gruppen erstellen</p>
        </div>
        <div class="alert" id="DossierFeedback">
            <p>Feedbackphase</p>
        </div>
        <div class="alert" id="Execution">
            <p>Durchführungsphase</p>
        </div>
        <div class="alert" id="Assessment">
            <p>Bewertungsphase</p>
        </div>
        <div class="alert" id="Projectfinished">
            <p>Ende</p>
        </div>
    </div>
</div>
<footer:footer/>
</body>
</html>
