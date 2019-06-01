const categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT", "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];

/*
    TODO
        create feedback page:
            - implement how btnContinue works
            - implement how btnBack works
            - implement how btnFinalize works
        show feedback page (could be this page):
            - color feedback in user color
            - color on mouseover with user color
            -
        not one page!:
            - you need to hide half of the page, just to have one page: einzige gemeinsamkeit -> editor und das reicht nicht
        - Websocket fixen (vllt)
        - bearbeiten druecken laesst die karte nicht verschwinden (was wollte ich damit sagen?)
 */

let contributionFeedback = undefined;

/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function () {
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    getFeedbackedGroup(function (response) {
        $('#feedBackTarget').html(response.id);
    });

    addExistingContributionFeedback(fullSubmissionId, category);

    $('#categoryHeadline').html("Kategorie: " + category);
    let btnFinalize = $('#finalize');
    btnFinalize.hide();
    btnFinalize.on("click", function () {
        saveContributionFeedback();
        finalize();
    });

    let btnBack = $('#btnBack');
    btnBack.click(handleBackButtonClick)
    if (category.toUpperCase() === "TITEL") {
        //btnBack.css('visibility', 'hidden');
        btnBack.hide()
    }

    let btnContinue = $('#btnContinue');
    btnContinue.click(handleContinueButtonClick);
    if (category.toUpperCase() === "AUSWERTUNG") {
        btnFinalize.show();
        btnContinue.hide();
    }

    $('#backToTasks').on('click', function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
    });

    let startCharacter;
    let endCharacter;
    // fetch full submission from database
    getFullSubmission(getQueryVariable("fullSubmissionId"), function (response) {

        // set text if student looks at peer review
        quill.setContents(JSON.parse(response.text));

        // fetch submission parts
        getSubmissionPart(fullSubmissionId, category, function (response) {
            let body = response ? response.body : [{startCharacter: 0, endCharacter: quillTemp.getLength()}];
            for (let i = 0; i < body.length; i++) {
                startCharacter = body[i].startCharacter;
                endCharacter = body[i].endCharacter;
                highlightQuillText(body[i].startCharacter, body[i].endCharacter, category);
            }
            let editor = $('#editor');
            editor.data("body", body);
        }, function () {
            //error
        })

    }, function () {
        //error
    });

    // connect to websocket on page ready
    connect(fullSubmissionId, category);
});

function handleContinueButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateNextCategory(category);

    if (nextCategory) {
        saveContributionFeedback();
        location.href = "../annotation/give-feedback.jsp?" +
            "projectName=" + getProjectName() +
            "&fullSubmissionId=" + submissionId +
            "&category=" + nextCategory;
    }
}

function addExistingContributionFeedback(fullSubmissionId, category) {
    getMyGroupId(function (groupId) {
        getContributionFeedback(fullSubmissionId, category, groupId, function (response) {
            contributionFeedback = response;
            quillFeedback.setContents(contributionFeedback.text);
        });
    });
}

function handleBackButtonClick() {
    let submissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    let nextCategory = calculateLastCategory(category);

    if (!nextCategory) {

    } else {
        location.href = "../annotation/give-feedback.jsp?" +
            "projectName=" + getProjectName() +
            "&fullSubmissionId=" + submissionId +
            "&category=" + nextCategory;
    }
}

function calculateNextCategory(current) {
    let result = false;
    for (let i = 0; i < categories.length - 1; i++) {
        if (categories[i] === current) {
            result = categories[i + 1];
        }
    }

    return result

}

function calculateLastCategory(current) {
    let result = false;
    for (let i = 1; i < categories.length; i++) {
        if (categories[i] === current) {
            result = categories[i - 1];
        }
    }
    return result

}

function highlightQuillText(startIndex, endIndex, category) {
    let categoryTag = $('#categoryColor');
    let lowercaseCategory = category.toLowerCase();
    if (!categoryTag.hasClass('added-') + lowercaseCategory) {
        categoryTag.toggleClass('added-' + lowercaseCategory)
    }
    let categoryColor = categoryTag.css('background-color');
    let length = endIndex - startIndex;
    quill.formatText(startIndex, length, 'background', categoryColor);
}


function saveContributionFeedback() {

    // initialize target
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let fullSubmissionPartCategory = getQueryVariable("category").toUpperCase();
    let content = quillFeedback.getContents();

    if (contributionFeedback) {
        let contributionFeedbackRequest = contributionFeedback;
        contributionFeedbackRequest.text = content;
        updateContributionFeedback(contributionFeedbackRequest.id, contributionFeedbackRequest, function () {
            console.log(response);
        });
    } else {
        getMyGroupId(function (groupId) {
            let contributionFeedbackRequest = {
                groupId: groupId,
                fullSubmissionId: fullSubmissionId,
                fullSubmissionPartCategory: fullSubmissionPartCategory,
                text: JSON.stringify(content),
            };

            // send new annotation to back-end and display it in list
            createContributionFeedback(contributionFeedbackRequest, function (response) {
                // send new annotation to websocket
                //send("CREATE", response.id);
                console.log(response);
            });
        });
    }
}

/**
 * Change title and comment from annotation by given annotation
 *
 * @param annotation The given altered annotation
 */
function editAnnotationValues(annotation) {
    // find annotation
    let annotationElement = $('#' + annotation.id);

    // set title and comment
    annotationElement.find('.annotation-header-data-title').text(annotation.body.title);
    annotationElement.find('.annotation-body-text').text(annotation.body.comment);

    // handle drop down button
    showAndHideToggleButtonById(annotation.id);
}

/**
 * Show or hide the drop down button for a given annotation card.
 *
 * @param id The id of the annotation
 */
function showAndHideToggleButtonById(id) {
    // find annotation
    let annotationElement = $('#' + id);
    // find the comment element, clone and hide it
    let comment = annotationElement.find('.annotation-body').children('p');
    let clone = comment.clone()
        .css({display: 'inline', width: 'auto', visibility: 'hidden'})
        .appendTo('body');
    let cloneWidth = clone.width();

    // remove the element from the page
    clone.remove();

    // show drop down button only if text was truncated
    if (cloneWidth > comment.width()) {
        annotationElement.find('.annotation-header-toggle').show();
        annotationElement.find('.annotation-header-data').css('width', 'calc(100% - 40px)');
    } else {
        annotationElement.find('.annotation-header-toggle').hide();
        annotationElement.find('.annotation-header-data').css('width', '100%');
    }
}


String.prototype.hashCode = function () {
    let hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr = this.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};