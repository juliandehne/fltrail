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

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <main id="reEdit" class="">
        <div class="row group">
            <div class="col span_2_of_2">

                <h3 id="ownTitle"></h3>
                <p>Hier können Sie Ihr <span id="fileRole"></span> überarbeiten</p>
                <br>
                <div id="editor"></div>
                <div class="leftcontent-buttons-next">
                    <button id="btnSave" type="button" class="btn btn-primary" title="weiter">
                        <i class="far fa-save"></i> speichern
                    </button>


                    <div style="display: block">
                        <div style="display: inline-flex;">
                            <input id="finalizeReedit" type="checkbox" title="finalisieren" style="margin-top:-2px">
                            <label for="finalizeReedit" style="margin-right:5px">Dies ist die finale
                                <a data-toggle='collapse' href='#whatIs' role='button'
                                   aria-expanded='false' aria-controls='whatIs'>
                                    <i class='fas fa-question'></i>
                                </a> Abgabe des Dossiers</label>
                        </div>
                        <div class='collapse' id='whatIs'>
                            <div class='card card-body card-whatIs'>
                                Bestätigen Sie hier, wenn dies die Version ihres Dossiers ist, die später bewertet
                                werden soll.
                            </div>
                        </div>
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
