<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/edit-description.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/timeLine.jsp"/>
    <div class="col span_content">
        <h1> Projektbeschreibung bearbeiten </h1>

        <form id="descriptionform" class="form-journal" method="POST"
              action="rest/projectdescription/saveText">

            <textarea id="editor" name="text" form="descriptionform"></textarea>
        </form>
    </div>
    <div class="col span_chat"><chat:chatWindow orientation="right" scope="project"/> <chat:chatWindow
            orientation="right" scope="group"/></div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
</body>

</html>