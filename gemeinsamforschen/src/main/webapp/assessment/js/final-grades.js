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
            $('#markFor_' + userEmail).val($('#suggested_' + userEmail).html().trim());
        })
    });
    $('#btnSave').on('click', function () {
        loaderStart();
        saveGrades(projectName, function () {
            loaderStop();
            taskCompleted();
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
            //gradesData = response;
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
            let finalMark;
            if (grades[student].finalRating === null) {
                finalMark = 0;
            } else {
                finalMark = grades[student].finalRating;
            }
            result = {
                name: grades[student].user.name,
                userEmail: grades[student].user.email,
                productPeer: Number.parseFloat(grades[student].groupProductRating).toFixed(2),
                productDocent: Number.parseFloat(grades[student].docentProductRating).toFixed(2),
                workRating: Number.parseFloat(grades[student].groupWorkRating).toFixed(2),
                suggested: Number.parseFloat(grades[student].suggestedRating).toFixed(2),
                finalMark: Number.parseFloat(finalMark).toFixed(2),
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
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(data),
        success: function () {
            callback();
        },
        error: function () {
        }
    });
}

function viewToUserPeerAssessmentData() {
    let grades = [];
    let result = {};
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
            if ($(this).attr("name") === "suggested") {
                UserPeerAssessmentData.suggestedRating = $(this).html().trim();
            }
            if ($(this).attr("name") === "finalMark") {
                UserPeerAssessmentData.finalRating = $(this).find("input").val();
            }
        });
        UserPeerAssessmentData.user = user;
        grades.push(UserPeerAssessmentData);
    });
    result.data = grades;
    result.final = $('#finalizeGrading').prop("checked");
    return result;
}