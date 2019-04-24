<%--
  Created by IntelliJ IDEA.
  User: fides-WHK
  Date: 05.04.2019
  Time: 09:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib uri="../taglibs/gemeinsamForschen.tld" prefix="chat" %>

<!DOCTYPE html>
<html lang="de">
<head>
    <jsp:include page="../taglibs/omniDependencies.jsp">
        <jsp:param name="hierarchy" value="1"/>
    </jsp:include>
    <script src="../taglibs/js/fileStorage.js" defer></script>
</head>
<body>
<div class="loader-inactive" id="loader">
    <div class="sk-cube1 sk-cube"></div>
    <div class="sk-cube2 sk-cube"></div>
    <div class="sk-cube4 sk-cube"></div>
    <div class="sk-cube3 sk-cube"></div>
</div>
<jsp:include page="../taglibs/Menu.jsp">
    <jsp:param name="hierarchy" value="1"/>
</jsp:include>
<main class="project-overview">


    <!-- this is what we are here for -->
    <ul id="listOfFiles">
        <script id="listOfFilesTemplate" type="text/x-jQuery-tmpl">
            <li>
                <a id="${fileCount}" href="../rest/fileStorage/download/fileLocation/${fileLocation}">${fileName}</a>
                <a name="${fileLocation}" class="deleteFile" style="cursor: pointer;"><i class="fa fa-trash" aria-hidden="true"></i></a>
            </li>
        </script>
    </ul>
    <form id="uploadForm" method="POST"  enctype="multipart/form-data">
        <label>Select a file: <input type="file" name="file" size="45" accept=".pdf, .pptx"/></label>
        <button id="uploadSubmit" class="btn btn-primary">Upload File</button>
    </form>
    <!--works just with chrome -.- <script>
        let XUploadPrototype = Object.create(HTMLElement.prototype);
        XUploadPrototype.createdCallback = function(){
            let shadow = this.createShadowRoot();
            let fileForm = document.createElement('form');
            fileForm.id = "uploadForm";
            let fileBtn = document.createElement('input');
            fileBtn.innerText = "Suche Datei";
            fileBtn.type = "file";
            let uploadBtn = document.createElement('button');
            uploadBtn.addEventListener('click', function(){
                uploadForm(this.getAttribute('data-projectName'));
            });
            uploadBtn.className = "btn btn-primary";
            fileForm.appendChild(fileBtn);
            fileForm.appendChild(uploadBtn);
            shadow.appendChild(fileForm);
        };

        let XUploadElements = document.registerElement('x-upload',
            {prototype: XUploadPrototype});

    </script>-->
    <fl-upload data-projectName="CheckThisOut"></fl-upload>

    <div id="successUpload" class="alert alert-success">Die Datei wurde erfolgreich gespeichert.</div>
    <div id="errorUpload" class="alert alert-warning">Ein Fehler ist beim Upload der Datei aufgetreten.</div>
    <div id="fileDeleted" class="alert alert-success">Die Datei wurde erfolgreich gelöscht.</div>
    <div id="errorDeletion" class="alert alert-warning">Ein Fehler ist aufgetreten beim Löschen der Datei.</div>
    <!-- this is what we are here for -->


    <div class="col span_chat span_l_of_3 right">
        <a id="groupView" style="cursor:pointer;">Gruppenansicht</a>
    </div>
    <div class="row">

    </div>
</main>
<jsp:include page="../taglibs/footer.jsp"/>

</body>
</html>