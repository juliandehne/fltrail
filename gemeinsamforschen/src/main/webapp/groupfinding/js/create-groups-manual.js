$(document).ready(function () {
    let userEmail = $('#userEmail').html().trim();
    let projectName = $('#projectName').html().trim();
    getAllGroups(function (allGroups) {
        groupsToTemplate(allGroups, function (done) {
            selectableButtons(done);
        });
    });
    $('#btnRelocate').click(function () {
        relocateMember(function (done) {
            selectableButtons(done);
        });
        relocateMember(function (done) {
            selectableButtons(done);
        });
    });
    $('#btnSave').click(function () {
        viewToGroup(function (groups) {

        })
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
    let studentTmplObject = [];
    let groupTmplObject = [];
    for (let group = 0; group < allGroups.length; group++) {
        /*for (let groupMember = 0; groupMember < allGroups[group].members.length; groupMember++) {
            studentTmplObject.push({
                studentName: allGroups[group].members[groupMember].name
            });
        }*/
        groupTmplObject.push({
            groupName: "group" + group,
            groupMember: allGroups[group].members,
        });
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

function relocateMember(callback) {
    let memberBtns = $('.student-button.active');
    let newGroupBtn = $('.group-button.active');
    newGroupBtn.each(function () {
        let newGroup = $('#' + $(this).html().trim());
        memberBtns.each(function () {
            $(this).toggleClass('active');
            $(newGroup).append($(this));
        });
    });
    let done = true;
    callback(done);
}

function viewToGroup(callback) {
    if ($('#gruppenlos').children().length > 1) {
        return null;
    }
    let groups = [];
    $('#groupsInProject').children().each(function () {
        if ($(this).attr("id").trim() !== "gruppenlos") {
            let members = [];
            $(this).children().each(function () {
                if ($(this).attr("name") === "student")
                    members.push({
                        email: "", //todo:
                        name: $(this).html().trim(),
                        rocketChatPersonalAccessToken: "", //todo:
                        rocketChatUserId: "", //todo:
                        rocketChatUsername: "", //todo:
                        student: true
                    });
            });
            groups.push({
                chatRoomId: "1", //todo:
                id: "1",        //todo:
                members: members,
            })
        }
    });
    callback(groups);
}

function saveNewGroups(groups) {
    $.ajax({
        url: "group/projects/" + $('#projectName').html().trim(),
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (response) {

        },
        error: function (a) {
            alert(a);
        }
    });
}