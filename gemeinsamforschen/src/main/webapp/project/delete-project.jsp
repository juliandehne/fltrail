<%--
  Created by IntelliJ IDEA.
  User: dehne
  Date: 12.09.2018
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="../groupfinding/js/config.js"></script>
    <script src="js/deleteProject.js"></script>

</head>

<body>
<div id="flex-wrapper">
<div class="loader-inactive" id="loader"></div>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="col span_2_of_2">
        <h1>Löschen eines Projekts</h1>
        <p style="margin-left:10px;">Bestätigen Sie das Löschen des Projekts durch Eingabe des Projektnamens:</p>
        <input class="form-control" type="text" id="projectNameInput" name="Project" required
               placeholder="Projekt1" autofocus
               style="max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;">
        <div class="alert alert-warning" role="alert" id="projectIsMissing" style="margin-left:10px;">
            Dieser Projektname existiert nicht.
        </div>
        <div class="alert alert-warning" role="alert" id="noPermission" style="margin-left:10px;">
            Sie sind nicht befugt dieses Projekt zu löschen.
        </div>
        <div class="alert alert-warning" role="alert" id="notAuthor" style="margin-left:10px;">
            Sie haben diesen Kurs nicht erstellt und dürfen ihn daher nicht löschen.
        </div>
        <button id="deleteProject" class="btn primary">
            löschen
        </button>
    </div>

    <div class="col span_chat">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>

</html>