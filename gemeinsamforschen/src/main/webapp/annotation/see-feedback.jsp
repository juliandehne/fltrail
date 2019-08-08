<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>


<!DOCTYPE html>
<html>


<head>
    <%--<jsp:include page="../taglibs/omniDependencies.jsp">         <jsp:param name="hierarchy" value="1"/>     </jsp:include>--%>

    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/jsp/quillJsDependencies.jsp"/>
    <title>Feedback sehen</title>

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
        <script src="../taglibs/js/unstructuredRest.js"></script>  <!-- ! -->
    <!-- js - feedbackScript -->
    <script src="js/seeFeedback.js"></script>
    <script src="js/feedbackUtils.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- back-->
    <div class="backlink">

        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <main id="seeFeedback" class="">
        <button class="btn_fullscreen btn btn-primary" type="button" title="fullscreen">resize</button>


        <div class="col span_2_of_2">
            <h3>Feedback für Ihr <span class="fileRole"></span></h3>

            <div class="three_rows">
                <button id="btnBack" type="button" class="btn btn-primary" title="Zurück">Zurück</button>

                <h4 id="categoryHeadline" class="current-category"></h4>
                <button id="btnContinue" type="button" class="btn btn-primary" title="weiter">Weiter</button>
                <button id="finalize" type="button" class="btn btn-primary" title="finalisieren">Beenden</button>
            </div>
            <div id="editor"></div>
        </div>

        <div class="splitter-horizontal"></div>

        <div class="reply-control">
            <h3>Zu diesem Teil gab es folgendes Feedback: </h3>
            <div id="feedbackEditor"></div>
        </div>

        <jsp:include page="../taglibs/jsp/quillJsEditor.jsp">
            <jsp:param name="readOnly" value="true"/>
        </jsp:include>
        <script>
            const quillFeedback = new Quill('#feedbackEditor', {
                theme: 'snow',
                readOnly: true,
                "modules": {
                    "toolbar": false
                }
            });
        </script>


    </main>


    <div style="height: 200px" id="categoryColor" class="hidden-category-field"></div>
</div>

<script type="text/javascript" src="../libs/jquery/jquery-resizable/src/jquery-resizable.js"></script>
<script>
    $(".span_2_of_2").resizable({
        handleSelector: ".splitter-horizontal",
        resizeHeight: false,

    });

    $(".btn_fullscreen").click(function () {

        // $("#flex-wrapper").addClass("fullscreen");
        $(".btn_fullscreen").toggleClass("max");
        $("#flex-wrapper").toggleClass("fullscreen");

    });


</script>
<jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>

</html>
