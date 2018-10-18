<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="omniDependencies" %>
<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="footer" %>


<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies hierarchy="1"/>

    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
            type="text/javascript"></script>

    <!-- css - upload-unstructured -->
    <link rel="stylesheet" type="text/css" href="css/unstructured-upload.css">
    <!-- js - unstructuredUpload -->
    <script src="js/unstructuredUpload.js"></script>
    <!-- js - unstructuredRest -->
    <script src="js/unstructuredRest.js"></script>

</head>

<body>
<menu:menu hierarchy="1"/>
<div class="col span_l_of_2"> <!-- col right-->
    <headLine:headLine/>
    <form id="upload-textarea-form">
        <div class="form-group upload-text" id="documentText">
            <label for="upload-textarea">Texteingabe</label>
            <textarea class="upload-text-textarea form-control" placeholder="Text einfügen..."
                      id="upload-textarea" name="uploadtextarea"></textarea>
        </div>
    </form>

    <div>
        <label for="file">Alternativ bitte Datei wählen</label>
        <input type="file" id="file" name="file">
    </div>
    <div class="document-text-buttons">
        <%--<button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück
        </button>--%>
        <button type="button" class="btn btn-primary document-text-buttons-next" id="btnNext">Weiter
        </button>
    </div>
</div>
<footer:footer/>
</body>

</html>
