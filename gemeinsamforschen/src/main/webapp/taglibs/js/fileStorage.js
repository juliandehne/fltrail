let projectName;
$(document).ready(function () {
    projectName = $('#projectName').html().trim();
    errorHandler(null);
    $('#uploadSubmit').on('click', function(event){
        event.preventDefault();
        uploadForm();
    });
    listFilesOfGroup();

});

function errorHandler(error) {
    switch (error) {
        case "upload":
            $('#errorUpload').show();
        break;
        case "deletion":
            $('#errorDeletion').show();
            break;
        default:
            $('#successUpload').hide();
            $('#errorUpload').hide();
            $('#fileDeleted').hide();
            $('#errorDeletion').hide();
    }
}

function uploadForm(){
    document.getElementById('loader').className = "loader";
    let data = new FormData($('#uploadForm')[0]);
    $.ajax({
        url: "../rest/fileStorage/presentation/projectName/"+projectName,
        data: data,
        dataType: "text",
        cache: false,
        contentType: false,
        processData: false,
        method: 'POST',
        type: 'POST',
        success: function(data){
            $('#successUpload').show();
            document.getElementById('loader').className = "loader-inactive";
        },
        error: function(data){
            errorHandler("upload");
            document.getElementById('loader').className = "loader-inactive";
        }
    });
}

function listFilesOfGroup(){
    $.ajax({
        url: "../rest/fileStorage/listOfFiles/projectName/"+projectName,
        type: 'GET',
        success: function(response){
            let tmplObject=[];
            let count=1;
            for (let key in response){
                if (response.hasOwnProperty(key))
                tmplObject.push({
                    fileCount: count,
                    fileLocation: key,
                    fileName: response[key]
                });
                count++;
            }
            $('#listOfFilesTemplate').tmpl(tmplObject).appendTo('#listOfFiles');
            prepareDeletion();
        },
        error: function(a){

        }
    });
}

function prepareDeletion(){
    let linksForDeletion = $('.deleteFile');
    linksForDeletion.each(function(){
        $(this).on('click', function(){
            $.ajax({
                url: "../rest/fileStorage/delete/fileLocation/"+$(this).attr('name'),
                method: 'POST',
                dataType: "text",
                type: 'POST',
                success: function(){
                    $('#fileDeleted').show();
                },
                error: function(){
                    errorHandler("deletion");
                }
            });
        });
    });
}