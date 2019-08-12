<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <title>Dossier schreiben</title>

    <!-- css - unstructured-annotation -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
    <link rel="stylesheet" type="text/css" href="css/annotationColorTheme.css">
    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>


    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
            type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.js"
            type="text/javascript"></script>
    <!-- js - unstructuredRest -->
    <script src="../taglibs/js/unstructuredRest.js"></script>
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredAnnotation.js"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>


</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den Aufgaben</a>
    </div>
    <main>
        <div class="row group">

            <div class="col span_content span_l_of_2">


                <h3 id="ownTitle"></h3>
                <div class="leftcontent-text context-menu-one" id="editor"></div>
                <div class="leftcontent-buttons">
                    <div class="leftcontent-buttons-save">
                        <button id="btnReedit" type="button" class="btn" title="edit">
                            <i class="fas fa-link"></i> bearbeiten
                        </button>
                        <button id="btnSave" type="button" class="btn btn-primary" title="weiter">
                            <i class="far fa-save"></i> speichern
                        </button>
                    </div>

                    <div class="alert alert-waring" id="divSaveForReal">
                        <p>
                            <label>Möchten Sie wirklich ihre Annotationen speichern?
                                <input type="checkbox" value="true" id="saveForReal">sicher
                            </label>
                        </p>
                    </div>
                </div>
            </div>

            <div class="col span_content span_s_of_2">
                <div class="infobox dossier">
                    <p>Ordne allen Textteilen passende Kategorien zu. Markiere den Text, klicke auf die rechte Maustaste
                        und wähle die passende Kategorie.</p>

                </div>
                <div id="missingAnnotation" class="alert alert-warning"></div>
                <ol id="annotations" class="create">
                    <script id="annotationTemplate" type="text/x-jsrender">
                    {{for categories}}
                        <li class="spacing">

                            <div id="{{>nameLower}}" class="category-card not-added">
                                <button class="btnCategory" onClick="handleCategoryClick('{{>name}}')">
                                    <p>{{>name}}</p>
                                </button>
                                <button class="delete-part" onClick="deleteCategory('{{>nameLower}}');">
                                    <p>X</p>
                                </button>
                            </div>


                        </li>
                    {{/for}}
                </script>
                </ol>
            </div>
        </div>
    </main>
    <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="true"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</div>
<p id="fileRole" hidden></p>

</body>

</html>
