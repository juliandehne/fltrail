<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>
<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="../groupfinding/js/config.js"></script>
    <script src="js/deleteProject.js"></script>

</head>

<body>
<div class="loader-inactive" id="loader"></div>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="col span_content">
        <legend style="margin-left:13px;">Projektnamen</legend>
        <input class="form-control" type="text" id="projectName" name="Project" required
               placeholder="Projekt1" autofocus
               style="max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        <div class="alert alert-warning" role="alert" id="projectIsMissing">
            Dieser Projektname existiert nicht
        </div>
        <button id="deleteProject" class="btn btn-danger">löschen</button>
    </div>
    <div class="col span_chat">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>