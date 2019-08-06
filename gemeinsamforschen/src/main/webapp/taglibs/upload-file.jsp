<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String fileRole = request.getParameter("fileRole");
%>
<script src="../taglibs/js/file-Upload.js"></script>
<p hidden id="uploadFileRole"><%= fileRole%></p>

<!--begin data upload-->
<form id="uploadForm" method="POST" enctype="multipart/form-data">
    <label>Select a file: <input type="file" name="file" size="45" accept=".pdf, .pptx, .docx"/></label>
    <button id="uploadSubmit" class="btn btn-primary"><i class="far fa-save"></i> Upload File</button>
</form>

<div id="taskCompleted" class="alert alert-success">Die Datei wurde erfolgreich gespeichert.</div>
<div id="errorUpload" class="alert alert-warning">Ein Fehler ist beim Upload der Datei aufgetreten.</div>
<!--end data upload-->

<%! %>