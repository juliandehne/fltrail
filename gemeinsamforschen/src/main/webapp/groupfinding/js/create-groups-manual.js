$(document).ready(function () {
    $('#studentsWithoutGroup').hide();
    $('#done').hide();
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
            selectableButtons(done);    //i have no clue why this needs to be called twice, but it seems necessary
        });
    });
    $('#btnSave').click(function () {
        viewToGroup(function (groups) {
            saveNewGroups(groups);
        })
    });
    $('#openNewGroup').click(function () {
        openNewGroup(function (done) {
            selectableButtons(done);
        });
        selectableButtons(true);  //i have no clue why this needs to be called twice, but it seems necessary
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
        $('#studentsWithoutGroup').show();
        return null;
    }
    let groups = [];
    $('#groupsInProject').children().each(function () {
        if ($(this).attr("id").trim() !== "gruppenlos") {
            let members = [];
            let chatRoomId = 0;
            $(this).children().each(function () {
                if ($(this).attr("name") === "student") {
                    let entries = $(this).children();
                    let email = entries[1].innerText;
                    let name = entries[0].innerText;
                    members.push({
                        email: email,
                        name: name,
                        rocketChatPersonalAccessToken: "",
                        rocketChatUserId: "",
                        rocketChatUsername: "",
                        student: true
                    });
                }
                if ($(this).attr("name") === "chatRoomId") {
                    chatRoomId = $(this).html().trim();
                }
            });
            if (members.length !== 0) {
                groups.push({
                    chatRoomId: chatRoomId,
                    id: "",
                    members: members,
                })
            }
        }
    });
    callback(groups);
}

function saveNewGroups(groups) {
    let data = JSON.stringify(groups);
    $.ajax({
        url: "../rest/group/projects/" + $('#projectName').html().trim(),
        data: data,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (response) {
            $('#done').show();
            setTimeout(function () {
                document.location.href = "../project/tasks-docent.jsp?projectName=" + $('#projectName').html().trim();
            }, 1000);
        },
        error: function (a) {
            alert(a);
        }
    });
}

function openNewGroup(callback) {
    let groupTmplObject = [];
    let nextGroup = $('#groupsInProject').children().length;
    groupTmplObject.push({
        groupName: "group" + nextGroup,
        groupMember: [],
        chatRoomId: "",
    });
    $('#groupTemplate').tmpl(groupTmplObject).appendTo('#groupsInProject');
    let done = true;
    callback(done);
}