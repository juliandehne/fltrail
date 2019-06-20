<%@ page import="unipotsdam.gf.process.phases.Phase" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");
    TagUtilities tu = new TagUtilities();
    String projectName = tu.getParamterFromQuery("projectName", request);
    Phase phase = tu.getPhase(projectName);
%>

<div class="col span_timeline timeline span_s_of_2">
    <!--begin timeLine -->
    <ul>
        <% if (phase != null) {%>
            <% if (phase == Phase.GroupFormation) {%>
                <li class="icon  phase1">Projektinitialisierung</li>
                <li class="icon inactive  phase2 ">Entwurfsphase</li>
        <li class="icon inactive phase4">Durchführung</li>
                <li class="icon inactive phase5">Assessment</li>
                <li class="icon inactive phase6">Noten</li>
            <%} else if (phase == Phase.DossierFeedback) {%>
                <li class="icon closed phase1">Projektinitialisierung</li>
                <li class="icon  phase2">Entwurfsphase</li>
        <li class="icon inactive phase4">Durchführung</li>
                <li class="icon inactive phase5">Assessment</li>
                <li class="icon inactive phase6">Noten</li>
            <%} else if (phase == Phase.Execution) {%>
                <li class="neutral icon closed phase1">Projektinitialisierung</li>
                <li class="draft icon closed phase2">Entwurfsphase</li>
        <li class="icon phase4">Durchführung</li>
                <li class="icon inactive phase5">Assessment</li>
                <li class="icon inactive phase6">Noten</li>
            <%} else if (phase == Phase.Assessment) {%>
                <li class="neutral icon closed phase1">Projektinitialisierung</li>
                <li class="draft icon closed phase2">Entwurfsphase</li>
        <li class="icon closed phase4">Durchführung</li>
                <li class="icon phase5">Assessment</li>
                <li class="icon inactive phase6">Noten</li>
        <%} else if (phase == Phase.GRADING) {%>
                <li class="icon closed phase1">Projektinitialisierung</li>
                <li class="icon closed phase2">Entwurfsphase</li>
        <li class="icon closed phase4">Durchführung</li>
                <li class="icon closed phase5">Assessment</li>
                <li class="icon phase6">Noten</li>
            <%}%>
        <%}%>
    </ul>
    <!-- end timeLine-->

    <!--begin data deletion and download-->
    <script src="../taglibs/js/fileStorage.js"></script>
    <div style="margin-top:50px;"></div>
    <h4 id="fileManagementHeader">Ergebnisse</h4>
    <ul id="listOfFiles">
        <script id="listOfFilesTemplate" type="text/x-jQuery-tmpl">
            <li>
                <a id="${fileCount}" href="../rest/fileStorage/download/fileLocation/${fileLocation}">${fileName}</a>
                <!--<a name="${fileLocation}" class="deleteFile" style="cursor: pointer;"><i class="fa fa-trash" aria-hidden="true"></i></a>-->
                <!-- use this comment for debugging deletion stuff -->
            </li>
        </script>
    </ul>

    <div id="fileDeleted" class="alert alert-success">Die Datei wurde erfolgreich gelöscht.</div>
    <div id="errorDeletion" class="alert alert-warning">Ein Fehler ist aufgetreten beim Löschen der Datei.</div>
    <!--end data deletion and download-->

</div>
<%!

%>