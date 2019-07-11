<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/courses-student.js"></script>
</head>

<body>
<div id="flex-wrapper">
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="projects">

    <div class="row group">



        <div class="col span_content span_2_of_2 centered">
        <!-- filter-->
            <h3 id="headLine">Projekt finden</h3>
            <p id="introduction"></p>
            <!-- filter-->
            <div class="filter" id="projectDropdown" style="max-width: 500px;">
                <script id="searchingTemplate" type="text/x-jQuery-tmpl">
                <div class="projectDynamic">
                    <select>
                        {{each(prop,val) projects}}
                            <option value="${val}" style="max-width: 450px;">${val}</option>
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
            <script id="projectTRTemplate" type="text/x-jQuery-tmpl">
            <div class="card card-project projectDynamic">
            <div class="card-inner">
                <h3>${projectName}</h3>
                <p>
                    ${projectDescription}
                </p>
                {{if projectTags[0]}}
                <label>Stichworte:</label>
                <div class='tags'>
                    {{each(i) projectTags}}
                    <span class='tag'>${projectTags[i]}</span>
                    {{/each}}
                </div>
                {{/if}}
                {{if isSearching}}
                {{if passwordRequired}}
                <div style="margin-top:25px;">
                    <a data-toggle="collapse" data-target="#passwordDiv_${projectName}">Passwort eingeben</a>
                    <div id="passwordDiv_${projectName}" class="collapse">
                        <label>
                            Passwort
                            <input class="form-control" id="projectPassword" placeholder="********">
                        </label>
                        <div id="projectWrongPassword" class="alert alert-alert" style="display:none;">
                            Falsches Passwort
                        </div>
                    </div>
                </div>
                {{/if}}
                {{/if}}
                <button class="primary project_Button" name="${projectName}" id="project_${projectName}" style="margin-top:10px;">${projectAction} </button>
            </div>
        </div>
        </script>
        </div>

    </div>
</main>

<jsp:include page="../taglibs/footer.jsp"/>
</div>
</body>

</html>