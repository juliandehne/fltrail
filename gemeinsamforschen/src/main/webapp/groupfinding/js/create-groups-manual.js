let manipulated=false;
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
    $('#backToTasks').on('click',function(){
        location.href="../project/tasks-student.jsp?projectName="+$('#projectName').html().trim();
    });
    $('#deselectStudents').on('click',function(){
        $('.student-button.active').each(function(){
            $(this).toggleClass('active');
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
    manipulated=true;
    newGroupBtn.each(function () {
        let newGroup = $('#' + $(this).html().trim());
        let complexList = $(this).parent().parent();
        memberBtns.each(function () {
            let newLI = $(this).parent();
            $(this).toggleClass('active');
            $(newLI).append(this);
            $(complexList).append(newLI);
            $(newGroup).append(complexList);
        });
    });
    let allGroups = $('.complex-list');
    allGroups.each(function(){
        $(this).children().each(function(){
            if (this.innerText===""){
                this.remove();
            }
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
    $('#groupsInProject').children().each(function () {     //the DIVs
        if ($(this).attr("id").trim() !== "gruppenlos") {
            let members = [];
            let chatRoomId = 0;
            $(this).children().each(function () {           //the ULs
                $(this).children().each(function () {       //the LIs
                    $(this).children().each(function () {   //the buttons
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
                });
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
    //append "manipulated" to data
    let url = "../rest/group/projects/" + $('#projectName').html().trim()+"/groups/finalize";
    if (manipulated===true){
        url+= "?manipulated=true";
    }
    $.ajax({
        url: url,
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
            //alert(a);
            console.error(a);
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

function allowDrop(ev){
    ev.preventDefault();
}

function dropContent(ev){
    ev.preventDefault();
    let data = ev.dataTransfer.getData("text");
    let target = ev.target;
    if (target.nodeName === "BUTTON"){
        target=target.parentElement;
    }
    if (target.nodeName === "LI"){
        target=target.parentElement;
    }
    manipulated=true;
    $(target).append(document.getElementById(data));
}

function allowDrag(ev){
    ev.dataTransfer.setData("text", ev.target.id);
}