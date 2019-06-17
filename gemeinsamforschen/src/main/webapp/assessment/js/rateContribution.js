let groupId;

$(document).ready(function () {
    $('#missingFeedback').hide();
    $('#done').hide();


    groupId=getQueryVariable("groupId");
    $('#groupId').append(groupId);
    prepareContributionRating();

    //editor.style = "min-height: 100px";

    $('#submit').on('click', function () {
        safeContributionRating();
    });
});

/*function whichGroupToRate(callback) {
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/assessment/groupRate/project/' + projectName,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (groupId) {
            $('#groupId').html(groupId);
            callback();
        },
        error: function () {

        }
    })
}*/

function safeContributionRating() {
    let contributions = $('.contributionRating');
    ///////initialize variables///////
    let dataP = {};

    ///////read values from html///////
    for (let contribution = 0; contribution < contributions.length; contribution++) {
        let checkbox = $("#" + contributions[contribution].id + " input:checked");
        dataP[checkbox.attr('name')] = checkbox.val();
    }
    if (contributions.length>$("input:checked").length){
        $('#missingFeedback').show();
        return false;
    }
    let fromPeer = $('#userEmail').html().trim();
    let groupId = getQueryVariable("groupId");
    let projectName = $('#projectName').html().trim();
    $.ajax({
        url: '../rest/assessment/contributionRating/project/'+projectName+'/group/' + groupId + '/fromPeer/' + fromPeer,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function () {
            $('#done').show();
            setTimeout(function () {
                document.location.href = "../project/tasks-docent.jsp?projectName=" + $('#projectName').html().trim();
            }, 1000);
        },
        error: function (a, b, c) {

        }
    });
}

function prepareContributionRating() {
    $.ajax({
        url: '../rest/assessment/contributions/project/' + getProjectName(),
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            for (let contribution in response) {
                let tmplObject = getTmplObject(response[contribution]);
                $('#contributionTemplate').tmpl(tmplObject).appendTo('#listOfContributions');
                if(response.hasOwnProperty(contribution)) {
                    if (response[contribution].textOfContribution != null){
                        let editor = new Quill('#editor'+response[contribution].roleOfContribution,
                            {
                                readOnly: true
                            });
                        editor.setContent(response[contribution].textOfContribution);
                    }
                }
            }
        },
        error: function (a) {

        }
    });

}

function getTmplObject(contribution) {
    let result = {
        contributionRole: contribution.roleOfContribution,
        contributionText: contribution.textOfContribution,
        contributionFileName: contribution.nameOfFile,
        contributionFilePath: "../rest/fileStorage/download/fileLocation/"+contribution.pathToFile,
    };
    return result;
}