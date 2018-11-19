/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function () {
    $('#missingAnnotation').hide();
    buildAnnotationList();
    // fetch the document text of the given id
    getFullSubmission(getQueryVariable("submissionId"), function (response) {
        // set text in div
        $('#documentText').text(response.text);

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
            handleCategoryClick(key);

        },
        items: {
            "annotation": {
                name: "Annotation",
                icon: "edit",
                items: {
                    "titel": {name: "Titel"},
                    "recherche": {name: "Recherche"},
                    "literaturverzeichnis": {name: "Literaturverzeichnis"},
                    "forschungsfrage": {name: "Forschungsfrage"},
                    "untersuchungskonzept": {name: "Untersuchungskonzept"},
                    "methodik": {name: "Methodik"},
                    "durchfuehrung": {name: "Durchführung"},
                    "auswertung": {name: "Auswertung"}
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
function handleCategorySelection(category, startCharacter, endCharacter) {

    // if highlighting is possible
    if (!isAlreadyHighlighted(startCharacter, endCharacter)) {

        // check if element has 'not-added' class
        let elem = $('#' + category);
        if (elem.hasClass("not-added")) {
            elem.toggleClass("not-added added-" + category);
        }

        // add highlighted text based on selected text
        addHighlightedText(startCharacter, endCharacter, category, calculateExtraOffset(startCharacter));

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
 * Add a highlighted text at specific position
 *
 * @param startCharacter The offset of the start character
 * @param endCharacter The offset of the end character
 * @param category The category selected by user
 * @param offset The calculated extra offset depending on already highlighted text
 */
function addHighlightedText(startCharacter, endCharacter, category, offset) {

    let documentText = $('#documentText').text();
    let documentHtml = $('#documentText').html();

    // create <span> tag with the annotated text
    let replacement = $('<span></span>').attr('class', category).html(documentText.slice(startCharacter, endCharacter));

    // wrap an <p> tag around the replacement, get its parent (the <p>) and ask for the html
    let replacementHtml = replacement.wrap('<p/>').parent().html();

    // insert the replacementHtml
    let newDocument = documentHtml.slice(0, startCharacter + offset) + replacementHtml + documentHtml.slice(endCharacter + offset);

    // set new document text
    $('#documentText').html(newDocument);
}

/**
 * Check if the selected range is already highlighted
 *
 * @param startCharacter The start character of the range
 * @param endCharacter The end character of the range
 * @returns {boolean} Returns true if the selected range ist already highlighted
 */
function isAlreadyHighlighted(startCharacter, endCharacter) {
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
            if (categoriesSent.length === categories.length){
                finalizeDossier(getQueryVariable("submissionId"));
            }else{
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
    let requestObj = new RequestObj(1,"/submissions","/id/?/projects/?/finalize", [submissionId, $('#projectName').text().trim()]);
    serverSide(requestObj, "POST", function (response) {
        location.href = "../project/tasks-student.jsp?projectName=" + getQueryVariable('projectName');
    })
}

/**
 * Handle the category click and start the saving event
 *
 * @param key The selected category
 */
function handleCategoryClick(key) {

    // if saved selection's range count is > 0
    let sel = rangy.getSelection();
    if (sel.rangeCount > 0) {
        // calculate character range offset from range
        let range = sel.getRangeAt(0);
        let offsets = range.toCharacterRange($('#documentText')[0]);

        // if selected text's length is > 0
        let selectedText = getSelectedText();
        if (selectedText.length > 0) {
            // save start and end character and handle the selection
            let startCharacter = offsets.start;
            let endCharacter = offsets.end;
            handleCategorySelection(key, startCharacter, endCharacter);
        }
    }
}

function buildAnnotationList(){
    let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT",
        "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
    for (let i in categories){
        let tmplObject = {annotationType: categories[i].toLowerCase()};
        $('#annotationTemplate').tmpl(tmplObject).appendTo('#annotations');
    }
}
