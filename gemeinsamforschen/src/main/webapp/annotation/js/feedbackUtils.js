function prepareFeedbackMenu(category) {
    let startCharacter;
    let endCharacter;
// fetch full submission from database
    getFullSubmission(getQueryVariable("fullSubmissionId"), function (response) {

        // set text if student looks at peer review
        quill.setContents(JSON.parse(response.text));
        setHeader(response.header);
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
    let categoryColor = colorOfCategory(category);
    let length = endIndex - startIndex;
    quill.formatText(startIndex, length, 'background', categoryColor);
}

function colorOfCategory(category) {
    let r = (Math.abs(category.hashCode() * 3 % 200) + 55);
    let g = (Math.abs(category.hashCode() * 43 % 200) + 55);
    let b = (Math.abs(category.hashCode() * 101 % 200) + 55);
    return 'rgb(' + r + ',' + g + ',' + b + ')';
}