function prepareFeedbackMenu(category) {

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
        if (category !== undefined) {
            getSubmissionPart(getQueryVariable("fullSubmissionId"), category, function (response) {
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
        }

    }, function () {
        //error
    });
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

