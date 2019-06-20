<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html>

<head>
    <!-- dependencies -->
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <!-- prints the menu -->
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- go back to tasks -->
    <div class="backlink">
        <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
            Aufgaben</i></a>
    </div>
    <!-- go back to tasks end-->

    <!-- file upload -->
    <main>
        <div class="row group">
            <div class="col span_content span_2_of_2">
                <h2>Abschlussbericht hochladen</h2>

                <div id="result"></div>
                <script id="headerTemplate" type="text/x-jsrender">
                    <h2>{{:header}} anlegen</h2>
                </script>
                <div class="row">
                    <jsp:include page="../taglibs/upload-file.jsp">
                        <jsp:param name="fileRole" value="FINAL_REPORT"/>
                    </jsp:include>
                    <p id="fileRole" hidden>FINAL_REPORT</p>
                </div>
            </div>
        </div> <!-- flex wrapper -->
    </main>
    <!-- end file upload upload -->

    <jsp:include page="../taglibs/footer.jsp"/>
</body>



</html>
