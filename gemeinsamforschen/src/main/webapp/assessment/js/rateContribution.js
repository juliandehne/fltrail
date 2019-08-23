let groupId;

$(document).ready(function () {
    Survey
        .StylesManager
        .applyTheme("default");

    $('#missingFeedback').hide();
    $('#done').hide();
    groupId = getQueryVariable("groupId");
    $.ajax({
        url: "../rest/group/groupId/" + groupId,
        type: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        success: function (group) {
            let groupName = group.groups[0].name;
            $('#groupName').append(groupName);
        }
    });

    prepareContributionRating();

    //editor.style = "min-height: 100px";

    $('#submit').on('click', function () {
        safeContributionRating();
    });
});

function safeContributionRating(survey) {
    let dataP = survey.data;
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
            taskCompleted();
        },
        error: function (a, b, c) {

        }
    });
}

function prepareContributionRating() {
    $.ajax({
        url: '../rest/assessment/contributions/project/' + getProjectName()+ "/groupId/"+groupId,
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            let surveyJSON ={
                completedHtml: "<h3>Vielen Dank für die Bewertung</h3>",
                pages: [{
                    name:"Internal Assessment",
                    questions:[]
                }],
                title: "Abgaben",
            };
            for (let contribution in response) {
                surveyJSON.pages[0].questions.push({
                    isRequired: true,
                    maxRateDescription: {
                        de: "schlecht",
                        en: "bad"
                    },
                    minRateDescription: {
                        de: "sehr gut",
                        en:"great"
                    },
                    name: response[contribution].roleOfContribution,
                    title: {
                        de: response[contribution].roleOfContribution,
                        en: response[contribution].roleOfContribution
                    },
                    type: "rating"
                });
                surveyJSON.pages[0].questions.push({
                    type: "html",
                    html: "<a href='../rest/fileStorage/download/fileLocation/"+response[contribution].pathToFile+"'>" +
                        "<i class='fa fa-paperclip'></i> download " +response[contribution].roleOfContribution+
                        "</a>",
                    name: "info",
                })
            }
            let survey = new Survey.Model(surveyJSON);
            survey.locale = "de";
            $("#surveyContainer").Survey({
                model: survey,
                onComplete: safeContributionRating,
            });
        },
        error: function (a) {

        }
    });

}