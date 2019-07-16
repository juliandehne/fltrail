<%@ page import="com.google.common.base.Strings" %>
<%@ page import="unipotsdam.gf.taglibs.TagUtilities" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<%
    TagUtilities tu = new TagUtilities();
    String learningGoalId = tu.getParamterFromQuery("learningGoalId", request);
    if (Strings.isNullOrEmpty(learningGoalId)) {
        learningGoalId = "";
    }
%>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <jsp:include page="../taglibs/quillJsDependencies.jsp"/>
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="../taglibs/css/unstructured-upload.css"/>
    <link rel="stylesheet" type="text/css" href="css/upload-learning-goal-result.css"/>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
    <script src="js/upload-learning-goal-result.js"></script>
    <script src="../taglibs/js/apiClient/learningGoalClient.js"></script>


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
    <main>
        <div class="row group">
            <div></div>
            <h1>Lernziel-Ergebnisse hochladen</h1>
            <div class="content">
                <div id="editor"></div>


                <div class="document-text-buttons">
                    <%--<button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück
                    </button>--%>
                    <button type="button" class="btn btn-primary document-text-buttons-next save-button" id="btnSave">
                        Speichern
                    </button>
                </div>
            </div>
            <div style="clear:left"></div>
            <%--    <div class="col span_chat">
                    <chat:chatWindow orientation="right" scope="project"/>
                    <chat:chatWindow orientation="right" scope="group"/>
                </div>--%>
        </div> <!-- flex wrapper -->
    </main>
    <jsp:include page="../taglibs/footer.jsp"/>
    <jsp:include page="../taglibs/quillJsEditor.jsp">
        <jsp:param name="readOnly" value="false"/>
    </jsp:include>

    <p id="learningGoalId" hidden><%= tu.printMe(learningGoalId)%>
    </p>
</body>

</html>
