// assert that utility is included

function startGrading(project) {
    window.console.log("starting assessment for project: "+ project);
    let requestObj = new RequestObj(1, "/assessment", "/grading/start/projects/?",[project],[], "")
    serverSide(requestObj, 'POST', function (response) {
        // yeah it worked
    });
}

function startDocentGrading(project) {
    // TODO implement
}