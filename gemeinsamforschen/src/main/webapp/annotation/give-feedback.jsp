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
    <!-- js - jQuery ui position
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script> -->
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
        <script src="../taglibs/js/apiClient/contributionFeedbackClient.js"></script>
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

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <main id="giveFeedback" class="">
        <div id="unauthorized" hidden class="alert alert-warning unauthorized">
            <p>
                Gerade von einem anderen Mitglied ein Feedback verfasst.<br>
                Bitte versuchen Sie es in Kürze erneut.
            </p>
        </div>
            <div class="col span_2_of_2">
                <h2>Feedback geben </h2>
                <h4>Dossier: <span id="ownTitle"></span></h4>

                <div class="three_rows">
                    <button id="btnBack" type="button" class="btn btn-primary" title="Zurück">Zurück</button>

                    <h4 id="categoryHeadline" class="current-category"></h4>
                    <button id="btnContinue" type="button" class="btn btn-primary" title="weiter">Weiter</button>
                    <button id="finalize" type="button" class="btn btn-primary" title="finalisieren">Senden</button>
                </div>
                <div id="editor"></div>

            </div>


        <!-- feedback writing -->
        <div class="splitter-horizontal"></div>
        <div class="reply-control">

            <label>Gib dein Feedback ein.</label>
            <div id="feedbackEditor"></div>
            <!-- <button id="btnContinueBot" type="button" class="btn btn-primary" title="weiter">
                weiter
            </button> -->
        </div>

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
    </main>





    <div style="height: 200px" id="categoryColor" class="hidden-category-field"></div>
</div>




<!-- end feedback -->

<script type="text/javascript" src="../libs/jquery/jquery-resizable/src/jquery-resizable.js"></script>
<script>
    $(".span_2_of_2").resizable({
        handleSelector: ".splitter-horizontal",
        resizeHeight: false,

    });


</script>




<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
