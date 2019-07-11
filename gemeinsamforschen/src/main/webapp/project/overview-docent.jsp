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
<div id="flex-wrapper">
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

            <h3 id="headLine">Projekt finden</h3>
            <p id="introduction"></p>
            <!--<label class="container" id="selfMade">Selbst erstellt
                <input type="checkbox" checked="checked">
                <span class="checkmark"></span>
            </label>-->
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
                    <div class="right">
                        <a href="delete-project.jsp">
                            <i class="fa fa-trash" aria-hidden="true"></i>
                        </a>
                    </div>
                        <h3>${projectName}</h3>
                         {{if projectTags[0]}}
                            <label>Stichworte:</label>
                            <div class='tags'>
                                {{each(i) projectTags}}
                                <span class='tag'>${projectTags[i]}</span>
                                {{/each}}
                            </div>
                         {{/if}}
                        <p>
                           ${projectDescription}
                        </p>

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
</div>
</body>

</html>