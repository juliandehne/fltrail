/**
 * This function will fire when the DOM is ready
 */

let staticCategories = [];
let items = {};
$(document).ready(function () {
    let fileRole = getQueryVariable("fileRole");
    if (!fileRole) {
        fileRole = "Unbekannt";
    }
    $('#fileRole').html(fileRole);
    handleLocker("ANNOTATE_DOSSIER");
    getAnnotationCategories(function (categories) {
        buildAnnotationList(categories);
        items = contextMenuOptions(categories);
        staticCategories = categories;
    });
    $('#missingAnnotation').hide();
    // fetch the document text of the given id
    getMyGroupId(function (groupId) {
        getFullSubmissionOfGroup(groupId, 0)
    });
    // set click listener to save button
    $('#btnSave').click(function () {
        saveButtonHandler();
    });
    $('#btnReedit').click(function () {
        document.location = changeLocationTo("annotation/upload-unstructured-dossier.jsp?" +
            "projectName=" + getQueryVariable("projectName") + "&submissionId=" + getQueryVariable("submissionId") +
            "&fileRole=" + getQueryVariable("fileRole"));
    });
    $('#divSaveForReal').hide();
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
    category = category.toLowerCase();
    if (!isAlreadyHighlighted(startCharacter, endCharacter)) {
        toggleStatusbar(category);
        highlightText(category, startCharacter, endCharacter);

        // update data from category list
        addSelectionDataToList(startCharacter, endCharacter, category);
        removeSelection();
    } else {
        // show error message to user
        window.alert("Dieser Bereich wurde bereits zugeordnet.")
    }

}

function highlightText(category, startCharacter, endCharacter) {
    let color = $('#' + category).css('background-color');
    let length = endCharacter - startCharacter;
    quill.formatText(startCharacter, length, 'background', color);
}

function removeSelection() {
    quill.setSelection(null);
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

function toggleStatusbar(category) {
    let categoryTag = $('#' + category);
    if (categoryTag.hasClass('not-added')) {
        categoryTag.toggleClass("not-added");
        let r = (Math.abs(category.toUpperCase().hashCode() * 3 % 181) + 60);
        let g = (Math.abs(category.toUpperCase().hashCode() * 43 % 181) + 60);
        let b = (Math.abs(category.toUpperCase().hashCode() * 101 % 181) + 60);
        categoryTag.css("background-color", "rgb(" + r + "," + g + "," + b + ")");
        categoryTag.css("color", "#FFF");
    }
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
    } else {
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
    // declare array of promises
    let promises = [];
    let categoriesSent = [];
    $('#annotations').find('.category-card').each(function () {
        let array = $(this).data('array');
        if (array != null) {

            // initialize the post request
            let category = $(this).attr('id').toUpperCase();
            let submissionPartPostRequest = {
                groupId: groupId,
                fullSubmissionId: fullSubmissionId,
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
                console.log(`send ${response.category} 's post request to back-end`);
            }));
            categoriesSent.push(category);
        }
    });

    $.when.apply($, promises).then(function () {
        if (categoriesSent.length === staticCategories.length) {
            finalizeDossier(fullSubmissionId);
        } else {
            let missingAnnotation = $('#missingAnnotation');
            missingAnnotation.show();
            missingAnnotation.text("Sie haben noch nicht alle Kategorien markiert");
        }
    });

    // redirect user to project page after saving
    // location.href="projects-student.jsp" + getUserEmail() + "&projectName=" + getProjectIdFromUrl();
}


/**
 *
 */
function finalizeDossier(submissionId) {
    let requestObj = new RequestObj(1, "/submissions", "/id/?/projects/?/finalize", [submissionId, $('#projectName').text().trim()]);
    serverSide(requestObj, "POST", function () {
        location.href = "../project/tasks-student.jsp?projectName=" + getQueryVariable('projectName');
    })
}

/**
 * Handle the category click and start the saving event
 *
 * @param key The selected category
 */
function handleCategoryClick(key) {
    let selection = quill.getSelection();
    if (selection.length > 0) {
        let endCharacter = selection.index + selection.length;
        handleCategorySelection(key, selection.index, endCharacter);
    }
}

// do not delete! it's used in create-unstructured-annotation.jsp onClick
// noinspection JSUnusedGlobalSymbols
function deleteCategory(category) {
    let categoryLI = $('#' + category);
    let textArrays = categoryLI.data('array');
    for (let i = 0; i < textArrays.length; i++) {
        quill.formatText(textArrays[i].start, textArrays[i].end - textArrays[i].start, 'background', '#FFF');
    }
    categoryLI.data('array', null);

    categoryLI.toggleClass('not-added');
    categoryLI.css("background-color", "#FFF");
}

function contextMenuOptions(categories) {
    let result = {};
    for (let category in categories) {
        if (categories.hasOwnProperty(category))
            result[categories[category]] = {name: categories[category], icon: "edit"};
    }

    try {
        /**
         * Context menu handler
         */
        $.contextMenu({
            selector: '.context-menu-one',
            callback: function (key) {
                // handle the category click
                handleCategoryClick(key);
            },
            items: result,
        });
    } catch (e) {
        console.log('No context menu found')
    }

    return result;
}