let isDocent = true;
$(document).ready(function () {
    let userEmail = $('#userEmail');
    $('#noGroupsYet').hide();
    $('#bisherKeineGruppen').hide();
    let projectName = $('#projectName').html().trim();
    $('#backToTasks').on('click',function(){
        if (isDocent) {
            location.href = "../project/tasks-docent.jsp?projectName=" + projectName;
        } else {
            location.href = "../project/tasks-student.jsp?projectName=" + projectName;
        }
    });
    getAllGroups(function (allGroups) {
        if(allGroups.length === 0){
            if (language==="en"){
                $('#noGroupsYet').show();
            }else{
                $('#bisherKeineGruppen').show();
            }
        }else{
            groupsToTemplate(allGroups, function (done) {
                selectableButtons(done);
            });
        }
    });


});

function getAllGroups(callback) {
    $.ajax({
        url: "../rest/group/all/projects/" + $('#projectName').html().trim(),
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            callback(response.groups);
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
    for (let i = 0; i < allGroups.length; i++) {
        for (let j = 0; j < allGroups[i].members.length; j++) {
            if (allGroups[i].members[j].email === userEmail) {
                isDocent = false;
            }
        }
    }
    $('#groupTemplate').tmpl(groupTmplObject).appendTo('#groupsInProject');
    let done = true;
    callback(done);
}

function selectableButtons(done) {
    if (done) {
        $(".student-button").click(function () {
            $(this).toggleClass('active');
        });
        $(".group-button").click(function () {
            $(".group-button.active").toggleClass('active');
            $(this).toggleClass('active');
        });
    }
}