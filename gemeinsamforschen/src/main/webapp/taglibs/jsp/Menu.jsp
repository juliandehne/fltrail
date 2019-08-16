<%@ page import="unipotsdam.gf.config.FLTrailConfig" %>
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
               href="<%= hierarchyLevel%>project/courses-student.jsp?all=true">Kurssuche</a>
            <% if (projectName != null && FLTrailConfig.REFLECTION_MODULE_ENABLED) {%>
            <a class="nav-link"
               href="<%=hierarchyLevel%>portfolio/show-portfolio-student.jsp?projectName=<%=projectName%>">E-Portfolio</a>
            <% } %>
            <% } else {%>
            <a class="nav-link"
               href="<%= hierarchyLevel%>project/overview-docent.jsp">Projekte</a>
            <% } %>
            <!--<a class="nav-link" href="<%= hierarchyLevel%>profile/profile.jsp?">Profil</a>-->
        </div>
        <div class="nav-group-right">
            <div class="btn-group" style="margin-right:20px;">
                <button class="btn btn-primary btn-sm" type="button" id="readMe">
                    <i class="fas fa-question"></i> Hilfe
                </button>
                <button type="button" class="btn btn-sm btn-primary dropdown-toggle dropdown-toggle-split"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-caret-down"></i>
                </button>
                <div class="dropdown-menu" style="background-color:#305D84;">
                    <ul>
                        <li>
                            <button class="nav-link btn btn-primary" style="cursor:pointer; width:100%;"
                                    id="readMeGruppenbildung">Gruppenbildung
                            </button>
                        </li>
                        <li>
                            <button class="nav-link btn btn-primary" style="cursor:pointer; width:100%;"
                                    id="readMeEntwurf">Entwurf
                            </button>
                        </li>
                        <li>
                            <button class="nav-link btn btn-primary" style="cursor:pointer; width:100%;"
                                    id="readMeDurchfuhrung">Durchführung
                            </button>
                        </li>
                        <li>
                            <button class="nav-link btn btn-primary" style="cursor:pointer; width:100%;"
                                    id="readMeBewertung">Bewertung
                            </button>
                        </li>
                        <li>
                            <button class="nav-link btn btn-primary" style="cursor:pointer; width:100%;"
                                    id="readMeProjektabschluss">Projektabschluss
                            </button>
                        </li>
                    </ul>

                </div>
            </div>
            <a class="nav-link" id="logout" style="cursor:pointer">Logout</a>
        </div>
    </div>
</header>
<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<p id="hierarchyLevel" hidden><%= tu.printMe(hierarchyLevel)%>
</p>
<p id="userEmail" hidden><%= tu.printMe(userEmail)%>
</p>
<p id="projectName" hidden><%= tu.printMe(projectName)%>
</p>
<input id="isStudent" value="<%= tu.printMe(isStudent)%>" hidden>
<div class="row group">
    <div class="titlerow"><% if (projectName != null) {%>
        <h1 id="projectHeadline"><%= tu.printMe(projectName)%>
        </h1>
        <% } else {%>
        <h1 id="projectHeadline">Projektunterstützung für forschendes Lernen</h1>
        <%}%>
    </div>
</div>