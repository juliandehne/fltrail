<%@ page import="unipotsdam.gf.process.phases.Phase" %>
<%@ page import="unipotsdam.gf.session.GFContexts" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    TagUtilities tu = new TagUtilities();
    String projectName = tu.getParamterFromQuery("projectName", request);
    Phase phase = tu.getPhase(projectName);
    String isStudent = (String) request.getSession().getAttribute(GFContexts.ISSTUDENT);
    // TODO build a taglib for this
    if (isStudent == null) {
        response.sendRedirect("../../index.jsp");
    }
%>

<div class="col span_timeline timeline span_s_of_2">
    <!--begin timeLine -->
    <h2>Projektphase</h2>
    <ul>
        <% if (phase != null) {%>
            <% if (phase == Phase.GroupFormation) {%>
        <li class="icon  phase1">Gruppenbildung</li>
        <li class="icon inactive  phase2 ">Entwurf</li>
        <li class="icon inactive phase4">Projekt</li>
        <li class="icon inactive phase5">Bewertung</li>
        <li class="icon inactive phase6">Projektabschluss</li>
            <%} else if (phase == Phase.DossierFeedback) {%>
        <li class="icon closed phase1">Gruppenbildung</li>
        <li class="icon  phase2">Entwurf</li>
        <li class="icon inactive phase4">Projekt</li>
        <li class="icon inactive phase5">Bewertung</li>
        <li class="icon inactive phase6">Projektabschluss</li>
            <%} else if (phase == Phase.Execution) {%>
        <li class="neutral icon closed phase1">Gruppenbildung</li>
        <li class="draft icon closed phase2">Entwurf</li>
        <li class="icon phase4">Projekt</li>
        <li class="icon inactive phase5">Bewertung</li>
        <li class="icon inactive phase6">Projektabschluss</li>
            <%} else if (phase == Phase.Assessment) {%>
        <li class="neutral icon closed phase1">Gruppenbildung</li>
        <li class="draft icon closed phase2">Entwurf</li>
        <li class="icon closed phase4">Projekt</li>
        <li class="icon phase5">Bewertung</li>
        <li class="icon inactive phase6">Projektabschluss</li>
        <%} else if (phase == Phase.GRADING) {%>
        <li class="icon closed phase1">Gruppenbildung</li>
        <li class="icon closed phase2">Entwurf</li>
        <li class="icon closed phase4">Projekt</li>
        <li class="icon closed phase5">Bewertung</li>
        <li class="icon phase6">Projektabschluss</li>
            <%}%>
        <%}%>
    </ul>
    <!-- end timeLine-->


    <!--begin data deletion and download-->
    <%if (isStudent!= null && isStudent.equals("isStudent")) {%>
    <script src="../taglibs/js/fileStorage.js"></script>
    <div style="margin-top:50px;"></div>
    <h2 id="fileManagementHeader">Ergebnisse</h2>
    <ul id="listOfFiles">
        <script id="listOfFilesTemplate" type="text/x-jQuery-tmpl">
            <li>
                <a id="${fileCount}" href="../rest/fileStorage/download/fileLocation/${fileLocation}"><i class="fas fa-file-download">&nbsp;</i>${fileName}</a>
                <!--<a name="${fileLocation}" class="deleteFile" style="cursor: pointer;"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                <!-- use this comment for debugging deletion stuff -->
            </li>
        </script>
    </ul>

    <div id="fileDeleted" class="alert alert-success">Die Datei wurde erfolgreich gelöscht.</div>
    <div id="errorDeletion" class="alert alert-warning">Ein Fehler ist aufgetreten beim Löschen der Datei.</div>
    <!--end data deletion and download-->
    <%}%>
</div>
<%!


%>

