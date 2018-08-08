<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="menu" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="headLine" %>
<%@ taglib uri="../core/pages/gemeinsamForschen.tld" prefix="omniDependencies" %>

<!DOCTYPE html>
<html>

<head>
    <omniDependencies:omniDependencies/>

    <!-- js - jQuery validation plugin -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.min.js"></script>
    <!-- js - jQuery ui position -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" type="text/javascript"></script>

    <!-- css - upload-unstructured -->
    <link rel="stylesheet" type="text/css" href="../assets/css/unstructured-upload.css">
    <!-- js - unstructuredUpload -->
    <script src="../assets/js/unstructuredUpload.js"></script>
    <!-- js - unstructuredUploadRest -->
    <script src="../assets/js/unstructuredUploadRest.js"></script>

</head>

<body>
<div id="wrapper" class="full-height">
    <menu:menu></menu:menu>
    <div class="page-content-wrapper full-height">
        <div class="container-fluid full-height">
            <div class="container-fluid-content">
                <div class="flex">
                    <headLine:headLine/>
                </div>
                <div class="content-mainpage">
                    <form id="upload-textarea-form">
                        <div class="form-group upload-text" id="documentText">
                            <label for="upload-textarea">Texteingabe</label>
                            <textarea class="upload-text-textarea form-control" placeholder="Text einfügen..." id="upload-textarea" name="uploadtextarea"></textarea>
                        </div>
                    </form>

                    <div>
                        <label for="file">Alternativ bitte Datei wählen</label>
                        <input type="file" id="file" name="file">
                    </div>
                    <div class="document-text-buttons">
                        <button type="button" class="btn btn-secondary document-text-buttons-back" id="btnBack">Zurück</button>
                        <button type="button" class="btn btn-primary document-text-buttons-next" id="btnNext">Weiter</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
