<%--
  Created by IntelliJ IDEA.
  User: fides-WHK
  Date: 05.04.2019
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/upload-File.js"></script>
</head>
<body>
<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="project-overview">


    <!-- this is what we are here for -->
    <form id="uploadForm" method="POST"  enctype="multipart/form-data">
        <p>Select a file: <input type="file" name="file" size="45" accept=".pdf, .pptx"/></p>
        <button id="uploadSubmit" class="btn btn-primary">Upload File</button>
    </form>
    <div id="successUpload" class="alert alert-success">Die Datei wurde erfolgreich gespeichert.</div>
    <div id="errorUpload" class="alert alert-warning">Ein Fehler ist beim Upload der Datei aufgetreten.</div>
    <!-- this is what we are here for -->
    <ol id="listOfFiles">
        <script id="listOfFilesTemplate" type="text/x-jQuery-tmpl">
            <li><a id="${fileCount}" href="../rest/fileStorage/download/fileLocation/${fileLocation}">${fileName}</a></li>
        </script>
    </ol>

    <div class="col span_chat span_l_of_3 right">
        <chat:chatWindow orientation="right" scope="project"/>
        <chat:chatWindow orientation="right" scope="group"/>
        <a id="groupView" style="cursor:pointer;">Gruppenansicht</a>
    </div>
    <div class="row">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>

</body>
</html>