/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    var allTheTags = [];
    var projectName = "";
    $(function () {
        $('#tagsProject').tagsInput({width: '475px',
            onAddTag: function(tag){
                allTheTags.push(tag);
            },
            onRemoveTag: function(tag){
                allTheTags.pop();           //todo: löscht noch nicht den gewählten tag sondern den letzten
            }
        });
    });
    $('#sendProject').on('click',function(){
        projectName = $("#nameProject").val();
        createNewProject(allTheTags, projectName);
    });
});

function createNewProject(allTheTags, projectName) {
    var obj = {
        "courseId": projectName,
        "printableName": projectName,
        "competences": allTheTags
    };
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api1/courses/"+$("#nameProject").val();
    var dataString = JSON.stringify(obj);
    $.ajax({
        url: url,
        contentType: 'application/json',
        type: 'PUT',
        data: dataString,
        success: function (response) {
            alert(response);
        },
        error: function (a, b, c) {
            console.log(a);
        }

    });
}