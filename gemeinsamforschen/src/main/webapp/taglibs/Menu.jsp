<%@ page import="unipotsdam.gf.session.GFContexts" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% String hierarchyLevel = request.getParameter("hierarchy");
    String isStudent = (String) request.getSession().getAttribute(GFContexts.ISSTUDENT);
    String projectName = request.getParameter("projectName");
    String userEmail = (String) request.getSession().getAttribute(GFContexts.USEREMAIL);
%>
<%!
    private String hierarchyToString(String hierarchyLevel) {
        StringBuilder resultBuilder = new StringBuilder();
        String result;
        for (int count = 0; count < Integer.parseInt(hierarchyLevel); count++) {
            resultBuilder.append("../");
        }
        result = resultBuilder.toString();
        return result;
    }
    private String printMe(String text){
        return text;
    }
%>
<header>
    <div class="row">
        <div class="nav-group-left">
            <% if (isStudent.equals("isStudent")) {%>
            <a class="nav-link" style="color:white;"
               href="<%=hierarchyToString(hierarchyLevel)%>project/courses-student.jsp">Home</a>
            <a class="nav-link" style="color:white;"
               href="<%=hierarchyToString(hierarchyLevel)%>project/courses-student.jsp?all=true">suche Kurs</a>
            <% } else {%>
            <a class="nav-link" style="color:white;"
               href="<%=hierarchyToString(hierarchyLevel)%>project/overview-docent.jsp">meine Projekte</a>
            <% } %>
            <a class="nav-link" href="<%=hierarchyToString(hierarchyLevel)%>profile/profile.jsp?">Profil</a>
        </div>
        <div class="nav-group-right">
            <a class="nav-link" id="logout" style="cursor:pointer">Logout</a>
        </div>
    </div>
</header>
<p id="hierarchyLevel" hidden><%=printMe(hierarchyLevel)%></p>
<p id="userEmail" hidden><%=printMe(userEmail)%></p>
<p id="projectName" hidden><%=printMe(projectName)%></p>
<div class="titlerow">
    <h1 id="projectHeadline"><%=printMe(projectName)%></h1>
</div>