$(document).ready(function () {
    let projectName = $('#projectName').html().trim();

    writeGradesToView(projectName, function () {
        modelTable();
        if (getQueryVariable("final") === "true") {
            $('#divForSaving').hide();
            $('.unsavedFinalMark').hide();
            $('#iconLegend').hide();
            getContributions(projectName);
        } else {
            $('.savedFinalMark').hide();
        }
    });
    $('#takeSuggested').on('click', function () {
        let tableEntries = $('#allGradesOfAllStudents').find('tr');
        tableEntries.each(function () {
            let userId = $(this).attr("id").substring("grades_".length);
            $('#markFor_' + userId).val($('#suggested_' + userId).find('.collapse.in').html().trim());

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
        dom: 'Bfrtip',
        select: true,
        "scrollCollapse": true,
        paging: false,
    });
    $('.dataTables_length').addClass('bs-select');
}

function fillObjectWithGrades(data) {
    let grades = data.data;
    let resultList = [];
    let fileCount = 0;
    for (let student in grades) {
        let result;
        let files = [];
        for (let i = 0; i < grades[student].files.length; i++) {
            files.push({
                fileCount: fileCount,
                filePath: grades[student].files[i].fileLocation,
                fileName: grades[student].files[i].fileName
            });
            fileCount++;
        }
        if (grades.hasOwnProperty(student)) {
            let finalMark;
            if (grades[student].finalRating === undefined) {
                finalMark = 0;
            } else {
                finalMark = grades[student].finalRating;
            }
            let productDocent = parseFloat(Number.parseFloat(grades[student].docentProductRating).toFixed(2));

            let beyondStdDeviation = "fa-check";
            if (grades[student].beyondStdDeviation != null)
                if (grades[student].beyondStdDeviation < 0) {
                    beyondStdDeviation = "fa-arrow-down";
                } else {
                    if (grades[student].beyondStdDeviation > 0)
                        beyondStdDeviation = "fa-arrow-up";
                }
            let workRating = 0;
            let suggested = parseFloat(Number.parseFloat(grades[student].suggestedRating).toFixed(2));
            if (Math.trunc(suggested) === Math.trunc(suggested + 0.3)) {
                // suggested = X.69 or less
                suggested = Math.trunc(suggested) + 0.3;
                if (Math.trunc(suggested) === Math.trunc(suggested + 0.7)) {
                    //suggested = X.29 or less
                    suggested = Math.trunc(suggested);
                }
            } else {
                // suggested = X.7 or more
                suggested = Math.trunc(suggested) + 0.7;
            }
            let cleanedSuggested = 0;
            let countValidEntries = 1; //docentProductRating always happens.

            if (grades[student].groupWorkRating !== null) {
                workRating = parseFloat(Number.parseFloat(grades[student].groupWorkRating).toFixed(2));
            }
            let cleanedWorkRating = 0;
            if (grades[student].cleanedGroupWorkRating !== null) {
                cleanedWorkRating = parseFloat(Number.parseFloat(grades[student].cleanedGroupWorkRating).toFixed(2));
                cleanedSuggested += cleanedWorkRating;
                countValidEntries++;
            }
            let levelOfAgreement = "";
            let productPeer = 0;
            if (grades[student].groupProductRating !== null) {
                productPeer = parseFloat(Number.parseFloat(grades[student].groupProductRating).toFixed(2));
                cleanedSuggested += productPeer;
                countValidEntries++;
                levelOfAgreement = "alert-success";
                if ((productPeer + 0.3 < productDocent) || (productPeer - 0.3 > productDocent)) {
                    levelOfAgreement = "alert-warning";
                }
                if ((productPeer + 0.7 < productDocent) || (productPeer - 0.7 > productDocent)) {
                    levelOfAgreement = "alert-danger";
                }
            }
            cleanedSuggested += productDocent;
            cleanedSuggested = parseFloat(Number.parseFloat(cleanedSuggested / countValidEntries).toFixed(2));
            if (Math.trunc(cleanedSuggested) === Math.trunc(cleanedSuggested + 0.3)) {
                // suggested = X.69 or less
                cleanedSuggested = Math.trunc(cleanedSuggested) + 0.3;
                if (Math.trunc(cleanedSuggested) === Math.trunc(cleanedSuggested + 0.7)) {
                    //suggested = X.29 or less
                    cleanedSuggested = Math.trunc(cleanedSuggested);
                }
            } else {
                // suggested = X.7 or more
                cleanedSuggested = Math.trunc(cleanedSuggested) + 0.7;
            }
            result = {
                groupId: grades[student].groupId,
                files: files,
                levelOfAgreement: levelOfAgreement,
                userId: grades[student].user.id,
                name: grades[student].user.name,
                userEmail: grades[student].user.email,
                cleanedWorkRating: cleanedWorkRating,
                productPeer: productPeer,
                productDocent: productDocent,
                workRating: workRating,
                beyondStdDeviation: beyondStdDeviation,
                suggested: suggested,
                cleanedSuggested: cleanedSuggested,
                finalMark: Number.parseFloat(finalMark).toFixed(2),
            };
        }
        resultList.push(result);
    }
    return resultList;
}

function saveGrades(projectName, callback) {
    let data = viewToUserPeerAssessmentData();
    if (data !== null) {
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
    } else {
        loaderStop();
    }
}

function viewToUserPeerAssessmentData() {
    let grades = [];
    let result = {};
    let notFinal = false;
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
                UserPeerAssessmentData.groupWorkRating = $(this).find('.collapse.in').html().trim();
            }
            if ($(this).attr("name") === "suggested") {
                UserPeerAssessmentData.suggestedRating = $(this).find('.collapse.in').html().trim();
            }
            if ($(this).attr("name") === "finalMark") {
                UserPeerAssessmentData.finalRating = $(this).find("input").val();
                let finalGrade = parseInt(UserPeerAssessmentData.finalRating);
                if (isNaN(finalGrade)) {
                    notFinal = true;
                } else if (parseInt(UserPeerAssessmentData.finalRating) < 1) {
                    notFinal = true;
                }
            }
        });
        UserPeerAssessmentData.user = user;
        grades.push(UserPeerAssessmentData);
    });
    result.data = grades;
    result.final = $('#finalizeGrading').prop("checked");
    if (notFinal && result.final) {
        $('#gradeMissing').attr('hidden', false);
        return null;
    }
    return result;
}

function getContributions(projectName) {
    $.ajax({
        url: "../rest/fileStorage/listOfFiles/projectName/" + projectName,
        type: 'GET',
        success: function (response) {
            let tmplObject = [];
            let count = 1;
            let fileName;
            let length = 0;
            let stringEnding;
            for (let key in response) {
                if (response.hasOwnProperty(key)) {
                    length = response[key].fileName.length;
                    stringEnding = "";
                    if (length > 20) {
                        length = 20;
                        stringEnding = "..."
                    }
                    fileName = response[key].fileName.substring(0, length) + stringEnding;
                    tmplObject.push({
                        fileCount: count,
                        fileLocation: response[key].fileLocation,
                        fileName: fileName,
                        group: response[key].group,
                        projectName: response[key].projectName,
                        userEmail: response[key].userEmail,
                        fileRole: response[key].fileRole,
                    });
                    count++;
                }
            }
            if (count === 1) {
                $('#fileManagementHeader').hide();
            }
            $('#contributionListTemplate').tmpl(tmplObject).appendTo('#contributionList');
        },
        error: function (a) {

        }
    });
}