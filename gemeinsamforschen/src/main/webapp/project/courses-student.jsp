<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/courses-student.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">     <jsp:param name="hierarchy" value="1"/> </jsp:include> <main> <jsp:include page="../taglibs/timeLine.jsp" />
<div class="col span_content">
    <div>
            <h1 id="headLine"></h1>
            <p class="introduction" id="introduction"> Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
                tempor
                invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo
                dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit
                amet.</p>

            <!-- filter-->
            <div class="filter" id="projectDropdown">
                <script id="searchingTemplate" type="text/x-jQuery-tmpl">
            <div class="projectDynamic">
                <select>
                    {{each(prop,val) projects}}
                        <option value="${val}">${val}</option>
                    {{/each}}
                </select>


                </script>
                <div class="select_arrow"></div>
            </div>
        <div class="search">
            <input id="searchField" type="text" name="suche" placeholder="Suche">
            <i class="fas fa-search"></i>
        </div>
    </div>

    <div class="row group projects-grid" id="projects">

    </div>
</div>
</main><jsp:include page="../taglibs/footer.jsp"/>
</body>
<script id="projectTemplate" type="text/x-jQuery-tmpl">
            <div class="card card-project projectDynamic">
                <div>
                    <h3>${projectName}</h3>
                    <p>
                        ${projectDescription}
                    </p>
                    <label>Tags:</label>
                        <div class='tags'>
                        {{each(i) projectTags}}
                            <span class='tag'>${projectTags[i]}</span><div class="spacing"></div>
                        {{/each}}
                        </div>

                    <button class="primary project_Button" name="${projectName}" id="project_${projectName}"
                    style="margin-top:10px;">${projectAction} </button>
                </div>
            </div>
        </script>
</html>