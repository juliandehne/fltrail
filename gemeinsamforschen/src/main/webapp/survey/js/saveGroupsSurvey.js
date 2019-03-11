$(document).ready(function () {
    $('#eMailVerified').hide();
    let context = getQueryVariable("context");
    $('#btnBuildGroups').on('click', function(){
        let password = $('#password').val().trim();
        if (true){
            //if (context.hashCode() == password){
            getProjectNameByContext(context,function(projectName){
                initializeOrGetGroups(projectName, function(groups){
                    groupsToTemplate(groups, function (done) {});
                });
                $('#eMailVerified').show();
            });
        }
        /*getProjectNameByContext(context, function(projectName){

        });*/
    });
});

String.prototype.hashCode = function() {
    let hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr   = this.charCodeAt(i);
        hash  = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};


function getProjectNameByContext(context, callback){
    $.ajax({
        url: "../rest/survey/project/name/"+context,
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

function initializeOrGetGroups(projectName, callback){
    $.ajax({
        url: "../rest/survey/projects/"+projectName+"/buildGroups",
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