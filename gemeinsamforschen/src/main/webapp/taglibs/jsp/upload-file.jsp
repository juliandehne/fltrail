<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String fileRole = request.getParameter("fileRole");
%>
<script src="../taglibs/js/file-Upload.js"></script>
<p hidden id="uploadFileRole"><%= fileRole%>
</p>

<!--begin data upload-->
<form id="uploadForm" method="POST" enctype="multipart/form-data">
    <label>Wählen Sie eine Datei in .pdf , .pptx, oder .docx: <input type="file" name="file" size="45"
                                                                     accept=".pdf, .pptx, .docx"/></label>

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="speichernDialog"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="speichernDialog">
                        <% if (fileRole.equals("FINAL_REPORT")) { %>
                        Abschlussbericht
                        <% } else {
                            if (fileRole.equals("PRESENTATION")) { %>
                        Präsentation
                        <% }
                        } %>
                        hochladen</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Ihre Gruppe hat dann nicht mehr die Möglichkeit
                    <% if (fileRole.equals("FINAL_REPORT")) { %>
                    den Abschlussbericht
                    <% } else {
                        if (fileRole.equals("PRESENTATION")) { %>
                    die Präsentation
                    <% }
                    } %>
                    zu überarbeiten.
                </div>
                <div class="modal-footer">
                    <%--  <button type="button" class="btn btn-secondary" data-dismiss="modal">schließen
                      </button>--%>
                    <button type="button" id="uploadSubmit" class="btn btn-warning">
                        <i class="far fa-save"></i> final upload
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
        <i class="far fa-save"></i> Upload File
    </button>
</form>

<div id="taskCompleted" class="alert alert-success">Die Datei wurde erfolgreich gespeichert.</div>
<div id="errorUpload" class="alert alert-warning">Ein Fehler ist beim Upload der Datei aufgetreten.</div>
<!--end data upload-->

<%! %>