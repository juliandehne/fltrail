<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>
    <script src="js/overview-docent.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">     <jsp:param name="hierarchy" value="1"/> </jsp:include> <main> <jsp:include page="../taglibs/timeLine.jsp" />
<div class="col span_content">
    <h1>Meine Projekte</h1>
    <div class="row group projects-grid" id="projects">
        <script id="projectTRTemplate" type="text/x-jQuery-tmpl">
    <div class="card">
        <div>
            <h3>${projectName}</h3>
            <p>
               ${projectDescription}
            </p>
            <button class="primary" id="project_${projectId}">Einsehen </button>
        </div>
    </div>
    </script>
</div>

<button class="btn btn-default" type="button" id="createProject" style="margin-top:15px;">Projekt
    erstellen
</button>
</div><div class="col span_chat">

</div>
</main><jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>