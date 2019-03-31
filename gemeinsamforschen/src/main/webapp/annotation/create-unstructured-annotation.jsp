<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- css - unstructured-annotation -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-annotation.css">
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
    <!-- js - rangy Core -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-core.js" type="text/javascript"></script>
    <!-- js - rangy TextRange Module -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rangy/1.3.0/rangy-textrange.js" type="text/javascript"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredAnnotation.js"></script>

    <!-- quilljs -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.2/jsrender.js"></script>


</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="row group">

                    <div class="col span_content span_l_of_2">
                        <h3>Dossier</h3>
                        <div class="leftcontent-text context-menu-one" id="editor"></div>
                        <div class="leftcontent-buttons">
                            <div class="leftcontent-buttons-save">
                                <button id="btnSave" type="button" class="btn btn-primary">Speichern</button>
                            </div>
                        </div>
                    </div>

                <div class="col span_content span_s_of_2">
                    <div class="infobox dossier">
                        <p>Ordne allen Textteilen passende Kategorien zu. Markiere den Text, klicke auf die rechte Mousetaste und wähle die passende Kategorie.</p>

            </div>
            <div id="missingAnnotation" class="alert alert-warning"></div>
            <ol id="annotations">
            <script id="annotationTemplate" type="text/x-jsrender">

                {{for categories}}
                    <li class="spacing">
                        <div id="{{>name}}" class="category-card not-added">
                            <p>{{>name}}</p>
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
<script>
    const quill = new Quill('#editor', {
        theme: 'snow',
        readOnly: true,
        "modules": {
            "toolbar": false
        }
    });
</script>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
