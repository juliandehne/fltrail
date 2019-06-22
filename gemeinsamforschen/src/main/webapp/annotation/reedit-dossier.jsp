<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>


<!DOCTYPE html>
<html>


<head>
    <%--<jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>--%>

    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>

    <!-- css - contextMenu -->
    <link href="https://swisnl.github.io/jQuery-contextMenu/dist/jquery.contextMenu.css" rel="stylesheet"
          type="text/css"/>
    <!-- css - annotationStyle -->
    <link rel="stylesheet" type="text/css" href="css/annotationStyle.css">
    <link rel="stylesheet" type="text/css" href="css/annotationColorTheme.css">
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>
    <!-- js - contextMenu script -->
    <script src="../libs/jquery/jqueryContextMenu.js"
            type="text/javascript"></script>
    <!-- js - scrollTo -->
    <script src="../libs/jquery/jqueryScrollTo.js"></script>
    <!-- js - rangy Core -->
    <script src="../libs/jquery/jqueryRangy.js" type="text/javascript"></script>
    <!-- js - rangy TextRange Module -->
    <script src="../libs/jquery/jqueryRangyTextRange.js" type="text/javascript"></script>
    <!-- js - annotation REST script -->
        <script src="../taglibs/js/unstructuredRest.js"></script>  <!-- ! -->
    <!-- js - feedbackScript -->
        <script src="js/reedit-dossier.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <main id="seeFeedback" class="">
        <div class="row group">
            <div class="col span_2_of_2">
                <h3>Hier können Sie Ihr <span id="fileRole"></span> überarbeiten</h3>
                <br>
                <div id="editor" style="border:solid 1px"></div>
                <div style="display:flex;">
                    <button id="btnSave" type="button" class="btn btn-primary" title="weiter">
                        <i class="far fa-save"></i> speichern
                    </button>
                    <label class="checkbox" for="finalizeReedit">
                        Dies ist die finale
                        <a data-toggle='collapse' href='#whatIs' role='button'
                           aria-expanded='false' aria-controls='whatIs'>
                            <i class='fas fa-question'></i>
                        </a>
                        Abgabe des Dossiers
                        <input id="finalizeReedit" style="margin:2px 0 0 0" type="checkbox" title="finalisieren">
                    </label>
                </div>

                <div class='collapse' id='whatIs'>
                    <div class='card card-body'>
                        Bestätigen Sie hier, wenn dies die Version ihres Dossiers ist, die später bewertet werden
                        soll.
                    </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="../taglibs/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="false"/>
    </jsp:include>
    <jsp:include page="../taglibs/footer.jsp"/>

    <div style="height: 200px" id="categoryColor" class="hidden-category-field"></div>
</div>
</body>

</html>
