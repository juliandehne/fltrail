
var gfm = "";

$(document).ready(function () {
    $("#selectGFM").click(function () {
        gfm = $('input[name=gfm]:checked').val();
        if (gfm == "Basierend auf Präferenzen") {
            // TODO implement preference based group selection
            gfm = "UserProfilStrategy";
        } else if(gfm == "per Hand") {
            gfm = "Manual";
        } else if (gfm == "Basierend auf Lernzielen") {
            gfm = "LearningGoalStrategy";
        }

        var projectToken = getProjectName();
        var localurl = "../../gemeinsamforschen/rest/group/settings/projects/"+getProjectName();
        $.ajax({
            gfm: gfm,
            url: localurl,
            contentType: 'application/json',
            type: 'POST',
            data: gfm,
            success: function (a,b,c) {
                if (gfm == "Manual") {
                    document.location.href = "../groupfinding/create-groups-manual.jsp"+"&projectName="+projectToken;
                }
                if (gfm == "UserProfilStrategy"){
                    document.location.href = "../groupfinding/create-groups-preferences.jsp"+"&projectName="+projectToken;
                }
                else {
                    document.location.href = "../groupfinding/create-groups-learninggoal.js.jsp"+"&projectName="+projectToken;
                }
                return true;
            },
            error: function (a,b,c) {
                return false;
            }
        });
    });
});