<%@ page import="unipotsdam.gf.session.GFContexts" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    TagUtilities tu = new TagUtilities();
    String hierarchyLevel = request.getParameter("hierarchy");
    hierarchyLevel = tu.hierarchyToString(hierarchyLevel);
    String isStudent = (String) request.getSession().getAttribute(GFContexts.ISSTUDENT);
    String projectName = tu.getParamterFromQuery("projectName", request);
    String userEmail = (String) request.getSession().getAttribute(GFContexts.USEREMAIL);
    if (isStudent == null || userEmail == null) {
        return;
    }
%>

<header>
    <div class="row">
        <div class="nav-group-left">
            <% if (isStudent.equals("isStudent")) {%>
            <a class="nav-link"
               href="<%= hierarchyLevel%>project/courses-student.jsp">Home</a>
            <a class="nav-link"
               href="<%= hierarchyLevel%>project/courses-student.jsp?all=true">suche Kurs</a>
            <% } else {%>
            <a class="nav-link"
               href="<%= hierarchyLevel%>project/overview-docent.jsp">meine Projekte</a>
            <% } %>
            <a class="nav-link" href="<%= hierarchyLevel%>profile/profile.jsp?">Profil</a>
        </div>
        <div class="nav-group-right">
            <a class="nav-link" id="logout" style="cursor:pointer">Logout</a>
        </div>
    </div>
</header>
<p id="hierarchyLevel" hidden><%= tu.printMe(request.getParameter("hierarchy"))%>
</p>
<p id="userEmail" hidden><%= tu.printMe(userEmail)%>
</p>
<p id="projectName" hidden><%= tu.printMe(projectName)%>
</p>
<div class="row group">
    <div class="titlerow"><% if (projectName != null) {%>
        <h1 id="projectHeadline"><%= tu.printMe(projectName)%>
        </h1>
        <% } else {%>
        <h1 id="projectHeadline">Ueberschrift muesste angepasst werden</h1>
        <%}%>
    </div>
</div>