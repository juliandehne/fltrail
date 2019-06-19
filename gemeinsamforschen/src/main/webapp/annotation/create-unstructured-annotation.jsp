<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<%
    TagUtilities tu = new TagUtilities();
    String fileRole = tu.getParamterFromQuery("fileRole", request);
    if (fileRole == null) {
        fileRole = "Unbekannt";
    }
%>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
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
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den Aufgaben</i></a>
    </div>
    <main>
        <div class="row group">

            <div class="col span_content span_l_of_2">
                <h3>Dossier</h3>
                <div class="leftcontent-text context-menu-one" id="editor"></div>
                <div class="leftcontent-buttons">
                    <div class="leftcontent-buttons-save">
                        <button id="btnSave" type="button" class="btn btn-primary" title="weiter">
                            <i class="far fa-save"></i> speichern
                        </button>
                    </div>
                </div>
            </div>

            <div class="col span_content span_s_of_2">
                <div class="infobox dossier">
                    <p>Ordne allen Textteilen passende Kategorien zu. Markiere den Text, klicke auf die rechte Mousetaste
                        und wähle die passende Kategorie.</p>

                </div>
                <div id="missingAnnotation" class="alert alert-warning"></div>
                <ol id="annotations">
                    <script id="annotationTemplate" type="text/x-jsrender">

                    {{for categories}}
                        <li class="spacing">
                        <div class="row group">
                            <div id="{{>nameLower}}" class="category-card not-added col span_content">
                                <p>{{>name}}</p>
                            </div>
                            <div class="delete-part span_content">
                                <a onClick="deleteCategory('{{>nameLower}}');">
                                    <p>X</p>
                                </a>
                            </div>
                        </div>
                        </li>
                    {{/for}}
                </script>
                </ol>
            </div>

            <div class="col span_chat">
                <chat:chatWindow orientation="right" scope="project"/>
                <chat:chatWindow orientation="right" scope="group"/>
            </div>
        </div>
    </main>
    <jsp:include page="../taglibs/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="true"/>
    </jsp:include>
    <jsp:include page="../taglibs/footer.jsp"/>
</div>
<p id="fileRole" hidden><%= tu.printMe(fileRole)%>

</body>

</html>
