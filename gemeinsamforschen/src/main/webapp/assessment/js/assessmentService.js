// assert that utility is included

function startGrading(project) {
    window.console.log("starting assessment for project: "+ project);
    let requestObj = new RequestObj(1, "/assessment", "/grading/start/projects/?",[project],[], "")
    serverSide(requestObj, 'POST', function (response) {
        // yeah it worked
        location.reload(true);
    });
}

function startDocentGrading(project) {
    window.console.log("starting assessment for project: "+ project);
    let requestObj = new RequestObj(1, "/assessment", "/gradingDocent/start/projects/?",[project],[], "")
    serverSide(requestObj, 'POST', function (response) {
        // yeah it worked
        location.reload(true);
    });
}

function getAssessmentForStudent(studentEmail, callback) {
    $.ajax({
        url: "../rest/assessment/total/project/" + "gemeinsamForschen" + "/student/" + studentEmail,
        type: 'GET',
        success: function (data) {
            //alert("here is the TotalPerformance: " + data);
            callback();
        },
        error: function (a, b, c) {
            alert('some error' + a);
        }
    })
}
