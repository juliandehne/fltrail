$(document).ready(function () {
    let projectName = $('#projectName').html().trim();
    writeGradesToView(projectName, function () {
        modelTable();
        if (getQueryVariable("final") === "true") {
            $('#divForSaving').hide();
        }
    });

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