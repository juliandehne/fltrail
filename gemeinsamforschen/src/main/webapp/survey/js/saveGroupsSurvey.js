$(document).ready(function () {
    $('#eMailVerified').hide();
    let context = getQueryVariable("context");
    $('#btnBuildGroups').on('click', function () {
        let password = $('#password').val().trim();
        //if (true){
        let encodedPass = context.hashCode();
        if (encodedPass == password || true) {
            $('#wrongAuthentication').hide();
            $('#authenticationPanel').hide();
            $('#eMailVerified').show();

            getProjectNameByContext(context, function (projectName) {
                initializeOrGetGroups(projectName, function (groups) {
                    if (groups.length == 0) {
                        $('#groupsInProject').hide();
                        $('.groups-manual').hide();
                        $('#NoParticipantsInfo').show();
                    } else {
                        groupsToTemplate(groups, function (done) {
                            $('#Gruppeneinteilung').show();
                            $('#groupsHeadline').hide();
                            $('#noGroupsYet').hide();
                            $('#bisherKeineGruppen').hide();
                            $('#groupsInProject').show();
                            $('.groups-manual').show();
                            $('#NoParticipantsInfo').hide();
                        });
                    }
                });

                // es wäre besser, wenn dies nicht in dem gruppen template enthalten wäre


            });
        } else {
            $('#wrongAuthentication').show();
        }
        /*getProjectNameByContext(context, function(projectName){

        });*/
    });
});

String.prototype.hashCode = function () {
    let hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};


function getProjectNameByContext(context, callback) {
    $.ajax({
        url: "../rest/survey/project/name/" + context,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (projectName) {
            callback(projectName.name);
        },
        error: function (a) {
        }
    });
}

// fetches the initialized groups from the backend
function initializeOrGetGroups(projectName, callback) {
    $.ajax({
        url: "../rest/survey/projects/" + projectName + "/buildGroups",
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (groups) {
            callback(groups);
        },
        error: function (a) {
        }
    });
}

function groupsToTemplate(allGroups, callback) {
    let groupTmplObject = [];
    for (let group = 0; group < allGroups.length; group++) {
        groupTmplObject.push({
            groupName: "group" + group,
            groupMember: allGroups[group].members,
            chatRoomId: allGroups[group].chatRoomId,
        });
    }
    $('#groupTemplate').tmpl(groupTmplObject).appendTo('#groupsInProject');
    let done = true;
    callback(done);
}