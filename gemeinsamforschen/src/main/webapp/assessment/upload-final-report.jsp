<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/jsp/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>
    <!-- dependencies -->
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>
    <script src="js/upload-final-report.js" type="text/javascript"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <!-- prints the menu -->
    <jsp:include page="../taglibs/jsp/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"></i> Zurück zu den
            Aufgaben</a>
    </div>
    <!-- go back to tasks end-->

    <!-- file upload -->
    <main id="uploadPresentation">
        <div id="unauthorized" hidden class="alert alert-warning unauthorized">
            <p>
                Ein anderes Gruppenmitglied lädt gerade eine Datei hoch.
            </p>
        </div>
        <div class="row group">
            <div class="col span_content span_l_of_2">
                <h3>Abschlussbericht hochladen</h3>

                <div id="result"></div>
                <script id="headerTemplate" type="text/x-jsrender">
                    <h2>{{:header}} anlegen</h2>
                </script>

                    <div class="alert alert-warning" id="divFinalContribution" style="display:flex;">
                        <label>
                            Finale Abgabe
                        <input id="finalContribution" type="checkbox">
                        </label>

                            Dieser Bericht ist die finale Abgabe ihrer Gruppe.
                            Sie wird hiermit zur Bewertung freigegeben.

                    </div>
                <jsp:include page="../taglibs/jsp/upload-file.jsp">
                        <jsp:param name="fileRole" value="FINAL_REPORT"/>
                    </jsp:include>
                    <p id="fileRole" hidden>FINAL_REPORT</p>


            </div>
        </div> <!-- flex wrapper -->
    </main>
    <!-- end file upload upload -->

    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</body>



</html>
