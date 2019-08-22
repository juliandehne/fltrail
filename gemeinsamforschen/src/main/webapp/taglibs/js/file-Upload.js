$(document).ready(function () {
    $('#successUpload').hide();
    $('#errorUpload').hide();
    $('#divFinalContribution').hide();
    $('#uploadSubmit').on('click', function (event) {
        event.preventDefault();
        uploadForm($('#projectName').html().trim(), $('#uploadFileRole').html().trim());
    });
});
function uploadForm(projectName, fileRole){
    loaderStart();
    let data = new FormData($('#uploadForm')[0]);
    $.ajax({
        url: "../rest/fileStorage/"+fileRole+"/projectName/"+projectName,
        data: data,
        dataType: "text",
        cache: false,
        contentType: false,
        processData: false,
        method: 'POST',
        type: 'POST',
        success: function(){
            loaderStop();
            taskCompleted();
        },
        error: function(){
            $('#errorUpload').show();
            loaderStop();
        }
    });
}