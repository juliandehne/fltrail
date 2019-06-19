function resizeGroup() {
    $.ajax({
        url: '../rest/project/update/project/' + $('#projectName').html().trim() + '/groupSize/' + $('#userCount').val().trim(),
        headers: {
            "Cache-Control": "no-cache"
        },
        type: 'POST',
        success: function (response) {
            location.reload();
        }
    });
}

function updateGroupSizeView() {
    let userCount = parseInt($('#userCount').val().trim());
    $('#groupSize').html(userCount * (userCount - 1));
}

function closePhase(phase, projectName) {
    loaderStart();
    let innerurl = '../rest/phases/' + phase + '/projects/' + projectName + '/end';
    $.ajax({
        url: innerurl,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function () {
            loaderStop();
            location.reload();
        },
        error: function () {
            loaderStop();
        }
    })
}

function initializeGroups(projectName) {
    let projq = new RequestObj(1, "/group", "/all/projects/?", [projectName], []);
    serverSide(projq, "GET", function (response) {
        redirect("../groupfinding/create-groups-manual.jsp?projectName=" + projectName);
    });
}


function redirect(url) {
    location.href = url;
}
