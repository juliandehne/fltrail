

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/jsp/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="js/edit-description.js"></script>

</head>

<body>
<div id="flex-wrapper">
    <jsp:include page="../taglibs/jsp/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <jsp:include page="../taglibs/jsp/timeLine.jsp"/>
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
    <jsp:include page="../taglibs/jsp/footer.jsp"/>
</div>
</body>

</html>