$(document).ready(function () {
    $('#eMailVerified').hide();
    $('#btnBuildGroups').on('click', function(){
        let password = $('#password').val().trim();
        let context = getQueryVariable("context");
        getProjectNameByContext(context, function(projectName){

        });
    });
});


function getProjectNameByContext(context, callback){
    $.ajax({
        url: "../rest/survey/project/name/"+context,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (projectName) {
            callback(projectName);
        },
        error: function (a) {
        }
    });
}