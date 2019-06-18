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
    <!-- js - annotation websocket script -->
    <script src="js/annotationWebsocket.js"></script>
    <!-- js - annotation REST script -->
    <script src="js/giveFeedbackRest.js"></script>
        <script src="../taglibs/js/unstructuredRest.js"></script>
    <!-- js - feedbackScript -->
    <script src="js/giveFeedback.js"></script>
        <script src="js/feedbackUtils.js"></script>


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
                <h3>Feedback geben </h3>

                <br>
                <div class="three_rows">
                    <button id="btnBack" type="button" class="btn btn-primary" title="Zurück">&#xf053;</button>

                    <h4 id="categoryHeadline" class="current-category"></h4>
                    <button id="btnContinue" type="button" class="btn btn-primary" title="weiter">&#xf054;</button>
                    <button id="finalize" type="button" class="btn btn-primary" title="finalisieren">&#xf00c;</button>
                </div>
                <div id="editor"></div>
                <h3>Gib dein Feedback ein.</h3>
                <div id="feedbackEditor"></div>
            </div>
        </div>
    </main>
    <jsp:include page="../taglibs/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="true"/>
    </jsp:include>
    <script>
        const quillFeedback = new Quill('#feedbackEditor', {
            theme: 'snow',
            readOnly: false,
            "modules": {
                "toolbar": true
            }
        });
    </script>

    <jsp:include page="../taglibs/footer.jsp"/>

    <div style="height: 200px" id="categoryColor" class="hidden-category-field"></div>
</div>
</body>

</html>
