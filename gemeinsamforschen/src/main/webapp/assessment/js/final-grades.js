$(document).ready(function () {
    let projectName = $('#projectName').html().trim();
    writeGradesToView(projectName, function () {
        modelTable();
    });
    if (getQueryVariable("final") === "true") {
        $('#divForSaving').hide();
    }
    $('#takeSuggested').on('click', function () {
        let tableEntries = $('#allGradesOfAllStudents').find('tr');
        tableEntries.each(function () {
            let userEmail = $(this).attr("id").substring("grades_".length);
            $('#final_' + userEmail).val($('#suggested_' + userEmail).html().trim());
        })
    })
});

function writeGradesToView(projectName, callback) {
    $.ajax({
        url: '../rest/assessment/grades/project/' + projectName,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let tmplObject = fillObjectWithGrades(response);
            $('#gradesOfOneStudentTemplate').tmpl(tmplObject).appendTo('#allGradesOfAllStudents');
            callback();
        },
        error: function () {
        }
    });
}

function modelTable() {
    $('#tableGrades').DataTable({
        "scrollY": "50vh",
        "scrollCollapse": true,
    });
    $('.dataTables_length').addClass('bs-select');
}

function fillObjectWithGrades(data) {
    let grades = data.data;
    let resultList = [];
    for (let student in grades) {
        let result;
        if (grades.hasOwnProperty(student)) {
            result = {
                name: grades[student].user.name,
                userEmail: grades[student].user.email,
                productPeer: Number.parseFloat(grades[student].groupProductRating).toFixed(2),
                productDocent: Number.parseFloat(grades[student].docentProductRating).toFixed(2),
                workRating: Number.parseFloat(grades[student].groupWorkRating).toFixed(2),
                suggested: Number.parseFloat(grades[student].suggestedRating).toFixed(2),
                finalMark: Number.parseFloat(grades[student].finalRating).toFixed(2),
            };
        }
        resultList.push(result);
    }
    return resultList;
}

function saveGrades(projectName, callback) {
    let data = viewToUserPeerAssessmentData();
    $.ajax({
        url: '../rest/assessment/grades/project/' + projectName + '/sendData',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        data: data,
        success: function () {
            callback();
        },
        error: function () {
        }
    });
}

function viewToUserPeerAssessmentData() {
    let grades = [];
    $('#allGradesOfAllStudents').children().each(function () {     //the TRs
        let UserPeerAssessmentData = {};
        let user = {};
        $(this).children().each(function () {           //the TDs
            if ($(this).attr("name") === "name") {
                user.name = $(this).html().trim();
            }
            if ($(this).attr("name") === "userEmail") {
                user.email = $(this).html().trim();
            }
            if ($(this).attr("name") === "productPeer") {
                UserPeerAssessmentData.groupProductRating = $(this).html().trim();
            }
            if ($(this).attr("name") === "productDocent") {
                UserPeerAssessmentData.docentProductRating = $(this).html().trim();
            }
            if ($(this).attr("name") === "workRating") {
                UserPeerAssessmentData.groupWorkRating = $(this).html().trim();
            }
            if ($(this).attr("name") === "workRating") {
                UserPeerAssessmentData.groupWorkRating = $(this).html().trim();
            }
        });
        if (members.length !== 0) {
            groups.push({
                chatRoomId: chatRoomId,
                id: "",
                members: members,
            })
        }
        grades.push(UserPeerAssessmentData);
    });
    callback(grades);
}