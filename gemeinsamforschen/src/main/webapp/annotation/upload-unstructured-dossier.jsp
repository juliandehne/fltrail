<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>--%>


<!DOCTYPE html>
<html>

<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>

    <!-- js - jQuery validation plugin -->
    <script src="../libs/jquery/jqueryValidate.js"></script>
    <!-- js - jQuery ui position -->
    <script src="../libs/jquery/jqueryUI.js" type="text/javascript"></script>

    <!-- css - upload-unstructured -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-upload.css">
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredUpload.js"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>

    <!-- quilljs -->
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

</head>

<body>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main>
    <div class="row group">
        <div class="col span_content span_l_of_2">
            <div class="upload-text" id="documentText">
                <label for="editor">Texteingabe</label>
                <div id="editor"></div>
            </div>

            <!--<div>
                <label for="file">Alternativ bitte Datei wählen</label>
                <input type="file" id="file" class="primary" name="file">
            </div>-->
        </div>
        <div class="document-text-buttons">
            <%--<button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück
            </button>--%>
            <button type="button" class="btn btn-primary document-text-buttons-next" id="btnNext">Weiter
            </button>
        </div>
        <div class="col span_content span_l_of_2"></div>
        <div class="col span_chat">
            <chat:chatWindow orientation="right" scope="project"/>
            <chat:chatWindow orientation="right" scope="group"/>
        </div>
    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>
<script>
    const quill = new Quill('#editor', {
        theme: 'snow'
    });
</script>
</body>

</html>
