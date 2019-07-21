<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <!-- jsrender -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jsrender/1.0.3/jsrender.min.js"></script>
    <script src="../taglibs/js/apiClient/reflectionClientGeneral.js"></script>
    <script src="../libs/bootstrap/js/bootstrap.min.js"></script>
    <script src="js/choose-for-assessment.js"></script>
</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/Menu.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <main>
        <div class="backlink">
            <a id="backToTasks" style="cursor:pointer;"><i class="fas fa-chevron-circle-left"> Zurück zu den
                Aufgaben</i></a>
        </div>
        <h1> Dies ist eine Dummyseite!</h1>
        <button type="button" onClick='skip()' class="btn btn-primary" id="saveButton">Überspringen</button>
    </main>
</div> <!-- flex wrapper -->
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>
