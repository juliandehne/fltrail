$(document).ready(function () {
    $('#btnUnstructuredUpload').click(function () {
        location.href = "annotation/upload-unstructured-annotation.jsp?token=" + getUserEmail() + "&projectId=" + getQueryVariable("projectId");
    });

});
