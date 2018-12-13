<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/changePhase.js"></script>
    <link href="css/changePhase.css" rel="stylesheet">
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <div class="page-content-wrapper">
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
    </div>
    <div class="col span_chat">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>
</html>
