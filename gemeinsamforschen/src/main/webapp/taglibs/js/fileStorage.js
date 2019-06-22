$(document).ready(function () {
    $('#fileDeleted').hide();
    $('#errorDeletion').hide();
    listFilesOfGroup($('#projectName').html().trim());

});

function listFilesOfGroup(projectName){
    $.ajax({
        url: "../rest/fileStorage/listOfFiles/projectName/"+projectName,
        type: 'GET',
        success: function(response){
            let tmplObject=[];
            let count=1;
            let fileName;
            let length = 0;
            let stringEnding;
            for (let key in response){
                if (response.hasOwnProperty(key))
                    length = response[key].length;
                    stringEnding = "";
                    if (length > 15){
                        length = 15;
                        stringEnding = "..."
                    }
                    fileName = response[key].substring(0, length)+stringEnding;
                tmplObject.push({
                    fileCount: count,
                    fileLocation: key,
                    fileName: fileName
                });
                count++;
            }
            if (count === 1) {
                $('#fileManagementHeader').hide();
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
                dataType: "text",
                type: 'DELETE',
                success: function(){
                    $('#fileDeleted').show();
                },
                error: function(){
                    $('#errorDeletion').show();
                }
            });
        });
    });
}