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
    <ul>
        <% if (phase != null) {%>
        <% if (phase == Phase.GroupFormation) {%>
        <li class="neutral icon closed">Projektinitialisierung</li>
        <li class="draft icon ">Entwurfsphase</li>
        <li class="icon inactive">Feedbackphase</li>
        <li class="icon inactive">Reflextionsphase</li>
        <li class="icon inactive">Assessment</li>
        <li class="icon inactive">Noten</li>
        <%} else if (phase == Phase.DossierFeedback) {%>
        <li class="neutral icon closed">Projektinitialisierung</li>
        <li class="draft icon closed">Entwurfsphase</li>
        <li class="feedback icon">Feedbackphase</li>
        <li class="icon inactive">Reflextionsphase</li>
        <li class="icon inactive">Assessment</li>
        <li class="icon inactive">Noten</li>
        <%} else if (phase == Phase.Execution) {%>
        <li class="neutral icon closed">Projektinitialisierung</li>
        <li class="draft icon closed">Entwurfsphase</li>
        <li class="feedback icon closed">Feedbackphase</li>
        <li class="icon">Reflextionsphase</li>
        <li class="icon inactive">Assessment</li>
        <li class="icon inactive">Noten</li>
        <%} else if (phase == Phase.Assessment) {%>
        <li class="neutral icon closed">Projektinitialisierung</li>
        <li class="draft icon closed">Entwurfsphase</li>
        <li class="feedback icon closed">Feedbackphase</li>
        <li class="icon closed">Reflextionsphase</li>
        <li class="icon">Assessment</li>
        <li class="icon inactive">Noten</li>
        <%} else if (phase == Phase.Projectfinished) {%>
        <li class="neutral icon closed">Projektinitialisierung</li>
        <li class="draft icon closed\">Entwurfsphase</li>
        <li class="feedback icon closed\">Feedbackphase</li>
        <li class="icon closed">Reflextionsphase</li>
        <li class="icon closed">Assessment</li>
        <li class="icon">Noten</li>
        <%}%>
        <%}%>
    </ul>
</div>
<%!

%>