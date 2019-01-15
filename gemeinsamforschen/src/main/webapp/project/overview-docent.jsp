<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/overview-docent.js"></script>
</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<div class="row group">

</div>
<main class="projects">
    <div class="col span_content span_2_of_2 centered">
        <!-- filter-->
        <button class="primary" type="button" id="createProject">Projekt erstellen
        </button>

        <h2 id="headLine">Projekt finden</h2>
        <p id="introduction"></p>
        <label class="container">Selbst erstellt
            <input type="checkbox" checked="checked">
            <span class="checkmark"></span>
        </label>
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
                <script id="projectTRTemplate" type="text/x-jQuery-tmpl">
                <div class="card card-project projectDynamic">
                    <div class="card-inner">
                        <h3>${projectName}</h3>
                        <p>
                           ${projectDescription}
                        </p>
                         <label>Stichworte:</label>
                             <div class='tags'>
                             {{each(i) projectTags}}
                                 <span class='tag'>${projectTags[i]}</span><div class="spacing"></div>
                             {{/each}}
                             </div>
                        <button class="primary project_Button" name="${projectName}" id="project_${projectName}"
                            style="margin-top:10px;">Einsehen </button>
                    </div>
                </div>
                </script>
            </div>



    <div class="col span_chat">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>