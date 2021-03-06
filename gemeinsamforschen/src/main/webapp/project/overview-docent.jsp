<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <title>Projektübersicht</title>

    <script src="js/overview-docent.js"></script>
</head>

<body>
<div class="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
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


        <!-- Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteProject"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteProject">Projekt löschen</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Sind Sie sich sicher das Projekt "<span id="deleteProjectName"></span>"
                        unwiderruflich löschen zu wollen?
                    </div>
                    <div class="modal-footer">
                        <%--  <button type="button" class="btn btn-secondary" data-dismiss="modal">schließen
                          </button>--%>
                        <button type="button" id="btnDelete" class="btn btn-warning">
                            löschen
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <div class="row group projects-grid" id="projects">
            <script id="projectTRTemplate" type="text/x-jQuery-tmpl">
                <div class="card card-project projectDynamic">
                    <div class="card-inner">
                    <div class="right">
                        <a class="delete" name="${projectName}" data-toggle="modal" data-target="#deleteModal">
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
</div>
<jsp:include page="../taglibs/jsp/footer.jsp"/>

</body>

</html>