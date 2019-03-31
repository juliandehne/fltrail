/**
 * This function will fire when the DOM is ready
 */

$(document).ready(function () {
    $('#missingAnnotation').hide();
    buildAnnotationList();
    // fetch the document text of the given id
    getFullSubmission(getQueryVariable("submissionId"), function (response) {
        // set text in div
        quill.setContents(JSON.parse(response.text));
        /*
        // get submissions parts from database
        getAllSubmissionParts(getQueryVariable("submissionId"), function (response) {

            // iterate over response
            for (let i = 0; i < response.length; i++) {
                // save current category and body
                let category = response[i].category;
                let body = response[i].body;
                // iterate over body and handle every selection
                for (let j = 0; j < body.length; j++) {
                    handleCategorySelection(category.toLowerCase(), body[j].startCharacter, body[j].endCharacter);
                }
            }

        });
         */

    }, function () {
        console.log("error occured at getting full submission");
        // jump to upload page on error
        // location.href = "upload-unstructured-dossier.jsp"
    });

    // set click listener to save button
    $('#btnSave').click(function () {
        saveButtonHandler();
    });

    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function (key, options) {

            // handle the category click
            handleCategoryClick(key,options.items.annotation.items[key].background);

        },
        items: {
            "annotation": {
                name: "Annotation",
                icon: "edit",
                items: {
                    "titel": {name: "Titel", background: "#ba68c8"},
                    "recherche": {name: "Recherche", background: "#7986cb"},
                    "literaturverzeichnis": {name: "Literaturverzeichnis", background: "#4dd0e1"},
                    "forschungsfrage": {name: "Forschungsfrage", background: "#81c784"},
                    "untersuchungskonzept": {name: "Untersuchungskonzept", background: "#dce775"},
                    "methodik": {name: "Methodik", background: "#ffd54f"},
                    "durchfuehrung": {name: "Durchführung", background: "#ff8a65"},
                    "auswertung": {name: "Auswertung", background: "#a1887f"}
                }
            }

        }
    });

});

/**
 * Handel the category selection
 *
 * @param category The chosen category
 * @param startCharacter The start character of the selected range
 * @param endCharacter The end character of the selected range
 */
function handleCategorySelection(category, color, startCharacter, endCharacter) {
    // if highlighting is possible
    if (!isAlreadyHighlighted(startCharacter, endCharacter)) {
        // TODO: change to quill subject
        // check if element has 'not-added' class
        let length = endCharacter - startCharacter;
        quill.formatText(startCharacter,length, 'background', color);

        // update data from category list
        addSelectionDataToList(startCharacter, endCharacter, category);
    }
    else {
        // show error message to user
        window.alert("Dieser Bereich wurde bereits zugeordnet.")
    }

}

/**
 * Get the text value of the selected text
 *
 * @returns {string} The text
 */
function getSelectedText() {
    if (window.getSelection) {
        return window.getSelection().toString();
    }
    else if (document.getSelection) {
        return document.getSelection();
    }
    else if (document.selection) {
        return document.selection.createRange().text;
    }
}

/**
 * Check if the selected range is already highlighted
 *
 * @param startCharacter The start character of the range
 * @param endCharacter The end character of the range
 * @returns {boolean} Returns true if the selected range ist already highlighted
 */
function isAlreadyHighlighted(startCharacter, endCharacter) {
    // TODO: refactor for quill
    let isHighlighted = false;
    $('#annotations').find('.category-card').each(function () {
        let array = $(this).data('array');
        if (array != null) {
            for (let i = 0; i < array.length; i++) {
                if ((array[i].start <= startCharacter && startCharacter <= array[i].end - 1) || // startCharacter inside highlighted range
                    (array[i].start <= endCharacter - 1 && endCharacter - 1 <= array[i].end - 1) || // endCharacter inside highlighted range
                    (startCharacter <= array[i].start && array[i].end - 1 <= endCharacter - 1)) { // new range overlaps hightlighted range
                    isHighlighted = true;
                }
            }
        }
    });
    return isHighlighted;
}

/**
 * Iterate over all data arrays and calculate the offset for a given start character
 *
 * @param startCharacter The given start character
 * @returns {number} The offset
 */
function calculateExtraOffset(startCharacter) {
    let extraOffset = 0;
    $('#annotations').find('.category-card').each(function () {
        let array = $(this).data('array');
        if (array != null) {
            for (let i = 0; i < array.length; i++) {
                if (array[i].end <= startCharacter) {
                    extraOffset += 22 + $(this).attr('id').length;
                }
            }
        }
    });
    return extraOffset;
}

/**
 * Save start and end character to the data array of a category
 *
 * @param startCharacter The start character of the body element
 * @param endCharacter The end character of the body element
 * @param category The chosen category
 */
function addSelectionDataToList(startCharacter, endCharacter, category) {
    // TODO: change for quillJs
    let elem = $('#' + category);
    let array = elem.data('array');

    if (array != null) {
        // define new object
        let newElement = {
            start: startCharacter,
            end: endCharacter
        };
        // update array
        array.push(newElement);
    }
    else {
        // store first element in array
        array = [
            {
                "start": startCharacter,
                "end": endCharacter
            }
        ]
    }

    // sort array
    array.sort(function (a, b) {
        return a.start - b.start;
    });
    // store updated array
    elem.data('array', array);
}

/**
 * Handel the save button click
 * Iterate over the category cards and send each post request to the back-end
 */
function saveButtonHandler() {
    // show alert message
    if (window.confirm("Möchten Sie wirklich ihre Annotationen speichern?")) {
        // declare array of promises
        let promises = [];
        let categoriesSent = [];
        $('#annotations').find('.category-card').each(function () {
            let array = $(this).data('array');
            if (array != null) {

                // initialize the post request
                let category = $(this).attr('id').toUpperCase();
                let submissionPartPostRequest = {
                    userEmail: getUserEmail(),
                    fullSubmissionId: getQueryVariable("submissionId"),
                    category: category,
                    body: []
                };

                // iterate over the array
                for (let i = 0; i < array.length; i++) {

                    // initialize a body element
                    let submissionPartBodyElement = {
                        text: $('#documentText').text().slice(array[i].start, array[i].end),
                        startCharacter: array[i].start,
                        endCharacter: array[i].end
                    };

                    // store the body element in the post request
                    submissionPartPostRequest.body.push(submissionPartBodyElement);
                }

                // send the post request to the back-end and save promise
                promises.push(createSubmissionPart(submissionPartPostRequest, function (response) {
                    console.log("send " + response.category + "'s post request to back-end")
                }));
                categoriesSent.push(category);
            }
        });

        $.when.apply($, promises).then(function () {
            let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT",
                "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
            if (categoriesSent.length === categories.length) {
                finalizeDossier(getQueryVariable("submissionId"));
            } else {
                let missingAnnotation = $('#missingAnnotation');
                missingAnnotation.show();
                missingAnnotation.text("Sie haben noch nicht alle Kategorien markiert");
            }
        });

        // redirect user to project page after saving
        // location.href="projects-student.jsp" + getUserEmail() + "&projectName=" + getProjectIdFromUrl();
    }
}


/**
 *
 */
function finalizeDossier(submissionId) {
    let requestObj = new RequestObj(1, "/submissions", "/id/?/projects/?/finalize", [submissionId, $('#projectName').text().trim()]);
    serverSide(requestObj, "POST", function (response) {
        location.href = "../project/tasks-student.jsp?projectName=" + getQueryVariable('projectName');
    })
}

/**
 * Handle the category click and start the saving event
 *
 * @param key The selected category
 */
function handleCategoryClick(key, color) {
    let selection = quill.getSelection();
    if (selection.length > 0) {
        let endCharacter = selection.index + selection.length;
        handleCategorySelection(key, color, selection.index, endCharacter);
    }
}

function buildAnnotationList() {
    let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT",
        "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
    for (let i in categories) {
        let tmplObject = {annotationType: categories[i].toLowerCase()};
        $('#annotationTemplate').tmpl(tmplObject).appendTo('#annotations');
    }
}
