// initialize userEmail, userColors
let userColors = new Map();
let userColorsDark = new Map();

// declare document text, start and end character
let startCharacter, endCharacter;

/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function () {
    let fullSubmissionId = getQueryVariable("fullSubmissionId");
    let category = getQueryVariable("category");
    getFeedbackName();
    $('#categoryHeadline').html("in der Kategorie "+category);
    let btnFinalize = $('#finalize');
    btnFinalize.hide();
    let btnBack = $('#btnBack');
    if (category === "TITEL" || category === "titel") {
        //btnBack.hide();
        btnBack.css('visibility','hidden');
    }

    let btnContinue = $('#btnContinue');
    if (category === "AUSWERTUNG") {
        btnFinalize.show();
        btnContinue.hide();

    }
    $('.close').on("click", function () {
        $('#annotation-edit-modal').hide();
        $('#annotation-create-modal').hide();
    });

    if (!category) {
        btnFinalize.hide();
        btnContinue.hide();
        btnBack.hide();
        $('#btnWholeCategory').hide();
    }
    let documentText = $('#documentText');
    // fetch full submission from database
    getFullSubmission(getQueryVariable("fullSubmissionId"), function (response) {

        // set text
        let documentText = $('#documentText');
        documentText.html(response.text);

        // fetch submission parts
        getSubmissionPart(fullSubmissionId, category, function (response) {
            let documentText = $('#documentText');
            let body;
            if (response === false) {
                body = [{
                    startCharacter: 0,
                    endCharacter: documentText.html().length,
                    text: documentText.html()
                }];
                addHighlightedSubmissionPart(body[0].startCharacter, body[0].endCharacter, 0);
            } else {
                body = response.body;
                let offset = 0;
                for (let i = 0; i < body.length; i++) {
                    addHighlightedSubmissionPart(body[i].startCharacter, body[i].endCharacter, offset);
                    // add char count of '<span class="categoryText"></span>'
                    offset += 34;
                }
            }
            documentText.data("body", body);
            // save body
            documentText.data("body", body);


            // scroll document text to first span element
            let span = $('#documentText span').first();
            documentText.scrollTo(span);
        }, function () {
            //error
        })

    }, function () {
        //error
    });

    // connect to websocket on page ready
    connect(fullSubmissionId, category);

    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function () {
            // handle annotation context click
            handleAnnotationClick()
        },
        items: {
            "annotation": {name: "Annotation", icon: "edit"}
        }
    });

    /**
     * continue button
     */
    btnContinue.click(function () {

        let submissionId = getQueryVariable("fullSubmissionId");
        let category = getQueryVariable("category");
        let nextCategory = calculateNextCategory(category);

        if (nextCategory) {
            location.href = "../annotation/annotation-document.jsp?" +
                "projectName=" + getProjectName() +
                "&fullSubmissionId=" + submissionId +
                "&category=" + nextCategory;
        }
    });

    btnFinalize.on("click", function () {
        finalize();
    });


    /**
     * back button
     */
    btnBack.click(function () {

        let submissionId = getQueryVariable("fullSubmissionId");
        let category = getQueryVariable("category");
        let nextCategory = calculateLastCategory(category);

        if (!nextCategory) {

        }
        else {
            location.href = "../annotation/annotation-document.jsp?" +
                "projectName=" + getProjectName() +
                "&fullSubmissionId=" + submissionId +
                "&category=" + nextCategory;
        }

    });

    function calculateNextCategory(current) {
        let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT", "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
        let result = false;
        for (let i = 0; i < categories.length - 1; i++) {
            if (categories[i] === current) {
                result = categories[i + 1];
            }
        }
        return result

    }

    function calculateLastCategory(current) {
        let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT", "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
        let result = false;
        for (let i = 1; i < categories.length; i++) {
            if (categories[i] === current) {
                result = categories[i - 1];
            }
        }
        return result

    }


    /**
     * validation of annotation create form inside the modal
     */
    $('#annotation-create-form').validate({
        rules: {
            title: {
                required: true,
                maxlength: 120
            },
            comment: {
                required: true,
                maxlength: 400
            }
        },
        messages: {
            title: {
                required: "Ein Titel wird benötigt",
                maxlength: "Maximal 120 Zeichen erlaubt"
            },
            comment: {
                required: "Ein Kommentar wird benötigt",
                maxlength: "Maximal 400 Zeichen erlaubt"
            }
        }
    });

    /**
     * validation of annotation edit form inside the modal
     */
    $('#annotation-edit-form').validate({
        rules: {
            title: {
                required: true,
                maxlength: 120
            },
            comment: {
                required: true,
                maxlength: 400
            }
        },
        messages: {
            title: {
                required: "Ein Titel wird benötigt",
                maxlength: "Maximal 120 Zeichen erlaubt"
            },
            comment: {
                required: "Ein Kommentar wird benötigt",
                maxlength: "Maximal 400 Zeichen erlaubt"
            }
        }
    });

    /**
     * Save button of the annotation create modal
     * hide modal and build new annotation
     */
    $('#btnSave').click(function () {
        if ($('#annotation-create-form').valid()) {
            // get title and comment from form
            let title = $('#annotation-form-title').val();
            let comment = $('#annotation-form-comment').val();

            // hide and clear the modal
            $('#annotation-create-modal').hide();

            // save the new annotation in db and display it
            saveNewAnnotation(title, comment, startCharacter, endCharacter);
        }
    });

    $('#btnWholeCategory').click(function () {
        getSubmissionPart(fullSubmissionId, category, function () {
            selectText();
            handleAnnotationClick();

        });
    });

    /**
     * Edit button of the annotation edit modal
     * hide modal and alter the annotation
     */
    $('#btnEdit').click(function () {
        if ($('#annotation-edit-form').valid()) {
            // get title and comment from clicked annotation card
            let id = $('#annotation-edit-modal').data('id');
            let card = $('#' + id);
            let title = card.find('.annotation-header-data-title').text();
            let comment = card.find('.annotation-body-text').text();

            // get title and comment from form
            let newTitle = $('#annotation-edit-form-title').val();
            let newComment = $('#annotation-edit-form-comment').val();

            // compare new and old card content
            if (title !== newTitle || comment !== newComment) {

                // build patch request
                let annotationPatchRequest = {
                    title: newTitle,
                    comment: newComment
                };
                // send alter request to server
                alterAnnotation(id, annotationPatchRequest, function () {
                    // send altered annotation to websocket
                    send("EDIT", id);

                    // alter the annotation card
                    card.find('.annotation-header-data-title').text(newTitle);
                    card.find('.annotation-body-text').text(newComment);

                    // handle drop down button
                    showAndHideToggleButton();

                    // hide and clear the modal
                    $('#annotation-edit-modal').modal('hide');
                })
            }
        }
    });

    /**
     * Delete an annotation from list and server
     */
    $('#btnDelete').click(function () {
        // get id from edit modal
        let id = $('#annotation-edit-modal').data('id');

        // delte annotation from server and from list
        deleteAnnotation(id, function () {
            // send delete request to websocket
            send("DELETE", id);
            // remove annotation from list
            $('#' + id).closest('.listelement').remove();
            // remove highlighted text
            deleteHighlightedText();

            // hide and clear the modal
            $('#annotation-edit-modal').hide();
        })
    });

    /**
     * Clear the title and comment input field of the create modal
     */
    $('#annotation-create-modal').on('hidden.bs.modal', function () {
        // clear title
        $('#annotation-form-title').val('');
        // clear comment
        $('#annotation-form-comment').val('')
    });

    /**
     * Clear the title and comment input field of the edit modal
     */
    $('#annotation-edit-modal').on('hidden.bs.modal', function () {
        // clear title
        $('#annotation-edit-form-title').val('');
        // clear comment
        $('#annotation-edit-form-comment').val('')
    });

    // fetch annotations from server on page start
    if (getQueryVariable("seeFeedback") === "true") {
        $('#btnWholeCategory').hide();
        $('#btnContinue').hide();
        $('#documentText').toggleClass('leftcontent-text').toggleClass('feedbackText');
        let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT", "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
        for (let i = 0; i < categories.length; i++) {
            getAnnotations(fullSubmissionId, categories[i], function (response) {
                // iterate over annotations and display each
                $.each(response, function (i, annotation) {
                    displayAnnotation(annotation);
                });
                let list = $('#annotations');
                list.prepend(
                    // list element
                    $('<h5>')
                        .attr('class', 'listelement')
                        .html(categories[i]));
                // handle drop down button
                showAndHideToggleButton();
            });
        }
    } else {
        getAnnotations(fullSubmissionId, category, function (response) {
            // iterate over annotations and display each
            $.each(response, function (i, annotation) {
                displayAnnotation(annotation);
            });
            // handle drop down button
            showAndHideToggleButton();
        });
    }
});

/**
 * This will be called on page resize
 */
$(window).resize(function () {
    // handle drop down button for every annotation
    showAndHideToggleButton();
});

/**
 * Display annotation in the list
 *
 * @param annotation The annotation to be displayed
 */
function displayAnnotation(annotation) {
    // fetch list of annotations
    let list = $('#annotations');

    let editIcon = "fas fa-edit";
    let dateIcon = "fas fa-calendar";
    if (isTimestampToday(annotation.timestamp)) {
        dateIcon = "fas fa-clock";
    }

    // declare variables
    let display, input, filter, user, title, comment;
    input = $('#annotation-search');
    filter = input.val().toLowerCase();

    user = annotation.userEmail.toLowerCase();
    title = annotation.body.title.toLowerCase();
    comment = annotation.body.comment.toLowerCase();

    // hiding based on user, title and comment
    if (user.indexOf(filter) > -1 ||
        title.indexOf(filter) > -1 ||
        comment.indexOf(filter) > -1) {
        display = ''
    }
    else {
        display = 'none'
    }

    // insert annotation card
    list.prepend(
        // list element
        $('<li>')
            .css('display', display)
            .attr('class', 'listelement')
            .append(
                // annotation card
                $('<div>').attr('class', 'annotation-card')
                    .attr('id', annotation.id)
                    .mouseenter(function () {
                        $(this).children('.annotation-header').css('background-color', getDarkUserColor(annotation.userEmail, annotation.targetCategory));
                    })
                    .mouseleave(function () {
                        $(this).children('.annotation-header').css('background-color', getUserColor(annotation.userEmail, annotation.targetCategory));
                    })
                    .append(
                        // annotation header
                        $('<div>').attr('class', 'annotation-header')
                            .css('background-color', getUserColor(annotation.userEmail, annotation.targetCategory))
                            .append(
                                // header data
                                $('<div>').attr('class', 'annotation-header-data')
                                    .append(
                                        // user
                                        $('<div>').attr('class', 'overflow-hidden')
                                            .append(
                                                $('<i>').attr('class', 'fas fa-user')
                                            )
                                            .append(
                                                $('<span>').attr('class', 'annotation-header-data-user').append(annotation.userEmail)
                                            )
                                    )
                                    .append(
                                        // title
                                        $('<div>').attr('class', 'overflow-hidden')
                                            .append(
                                                $('<i>').attr('class', 'fas fa-bookmark')
                                            )
                                            .append(
                                                $('<span>').attr('class', 'annotation-header-data-title').append(annotation.body.title)
                                            )
                                    )
                            )
                            .append(
                                // unfold button
                                $('<div>').attr('class', 'annotation-header-toggle')
                                    .click(function () {
                                        toggleButtonHandler(annotation.id);
                                    })
                                    .append(
                                        $('<i>').attr('class', 'fas fa-chevron-down')
                                    )
                            )
                    )
                    .append(
                        // annotation body
                        $('<div>').attr('class', 'annotation-body')
                            .append(
                                $('<p>').attr('class', 'overflow-hidden annotation-body-text').append(annotation.body.comment)
                            )
                    )
                    .append(
                        // annotation footer
                        $('<div>').attr('class', 'annotation-footer')
                            .append(
                                // edit
                                function () {
                                    if (getUserEmail() === annotation.userEmail) {
                                        return $('<div>').attr('class', 'annotation-footer-edit')
                                            .append(
                                                $('<i>').attr('class', editIcon)
                                            )
                                            .click(function () {
                                                editAnnotationHandler(annotation.id)
                                            })
                                    }
                                }
                            )
                            .append(
                                // timestamp
                                $('<div>').attr('class', 'flex-one overflow-hidden')
                                    .append(
                                        $('<i>').attr('class', dateIcon)
                                    )
                                    .append(
                                        $('<span>').append(timestampToReadableTime(annotation.timestamp))
                                    )
                            )
                    )
            )
            .data('annotation', annotation)
            .mouseenter(function () {
                addHighlightedAnnotation(annotation);

                // scroll document text to anchor element
                let documentText = $('#documentText');
                let anchor = $('#anchor');
                documentText.scrollTo(anchor);
            })
            .mouseleave(function () {
                deleteHighlightedText();
            })
            .append(
                $('<div>').attr('class', 'spacing')
            )
    );
}

/**
 * Add a highlighted text at specific position
 *
 * @param annotation
 */
function addHighlightedAnnotation(annotation) {
    let startCharacter = annotation.body.startCharacter;
    let endCharacter = annotation.body.endCharacter;
    let userEmail = annotation.userEmail;
    let offset = calculateExtraOffset(startCharacter);

    //initialize variables
    let docText = $('#documentText');
    let documentText = docText.text();
    let documentHtml = docText.html();

    //create <span> tag with the annotated text
    let replacement = $('<span></span>')
        .attr('id', 'anchor')
        .css('background-color', getUserColor(userEmail, annotation.targetCategory))
        .html(documentText.slice(startCharacter, endCharacter));

    //wrap an <p> tag around the replacement, get its parent (the <p>) and ask for the html
    let replacementHtml = replacement.wrap('<p/>').parent().html();

    //insert the replacementHtml
    let newDocument = documentHtml.slice(0, startCharacter + offset) + replacementHtml + documentHtml.slice(endCharacter + offset);

    // set new document text
    docText.html(newDocument);
}

/**
 * Add a highlighted text at specific position
 *
 * @param startCharacter The offset of the start character
 * @param endCharacter The offset of the end character
 * @param offset The calculated extra offset depending on already highlighted text
 */
function addHighlightedSubmissionPart(startCharacter, endCharacter, offset) {

    let docText = $('#documentText');
    let documentText = docText.text();
    let documentHtml = docText.html();

    // create <span> tag with the annotated text
    let replacement = $('<span></span>').attr('class', 'categoryText').html(documentText.slice(startCharacter, endCharacter));

    // wrap an <p> tag around the replacement, get its parent (the <p>) and ask for the html
    let replacementHtml = replacement.wrap('<p/>').parent().html();

    // insert the replacementHtml
    let newDocument = documentHtml.slice(0, startCharacter + offset) + replacementHtml + documentHtml.slice(endCharacter + offset);

    // set new document text
    docText.html(newDocument);
    if (getQueryVariable("seeFeedback") === "true"){
        let deleteMe=document.getElementsByClassName('categoryText');
        deleteMe[0].className='feedbackText';
    }
}

/**
 * Iterate over all data arrays and calculate the offset for a given start character
 *
 * @param startCharacter The given start character
 * @returns {number} The offset
 */
function calculateExtraOffset(startCharacter) {
    // get submission part body
    let body = $('#documentText').data("body");
    let extraOffset = 0;

    for (let i = 0; i < body.length; i++) {
        if (body[i].startCharacter <= startCharacter) {
            extraOffset += 27;
        }
        if (body[i].endCharacter <= startCharacter) {
            extraOffset += 7;
        }
    }

    return extraOffset;
}

/**
 * Restore the base text
 */
function deleteHighlightedText() {

    let documentText = $('#documentText');
    let highlight = documentText.find('#anchor');
    let text = highlight.text();
    highlight.replaceWith(text);

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
 * Get color based on user id
 *
 * @param userEmail The id of the user
 * @param category
 * @returns {string} The user color
 */
function getUserColor(userEmail, category) {
    // insert new color if there is no userEmail key
    if (getQueryVariable("seeFeedback")) {
        generateCategoryBasedColor(userEmail, category);
    }
    if (userColors.get(userEmail) == null) {
        generateRandomColor(userEmail);
    }
    // return the color
    return userColors.get(userEmail);
}

/**
 * Get dark color based on user id
 *
 * @param userEmail The token of the user
 * @param category
 * @returns {string} The dark user color
 */
function getDarkUserColor(userEmail, category) {
    // insert new color if there is no userEmail key
    if (getQueryVariable("seeFeedback")) {
        generateCategoryBasedColor(userEmail, category);
    }
    if (userColorsDark.get(userEmail) == null) {
        generateRandomColor(userEmail);
    }
    // return the color
    return userColorsDark.get(userEmail);
}

/**
 * Generate a random color of the format 'rgb(r, g, b)'
 *
 * @param userEmail The given user token
 */
function generateRandomColor(userEmail) {
    let r = Math.floor(Math.random() * 56) + 170;
    let g = Math.floor(Math.random() * 56) + 170;
    let b = Math.floor(Math.random() * 56) + 170;
    let r_d = r - 50;
    let g_d = g - 50;
    let b_d = b - 50;

    let color = 'rgb(' + r + ',' + g + ',' + b + ')';
    let colorDark = 'rgb(' + r_d + ',' + g_d + ',' + b_d + ')';

    userColors.set(userEmail, color);
    userColorsDark.set(userEmail, colorDark);
}

function generateCategoryBasedColor(userEmail, category) {
    let category_r, category_g, category_b = 0;
    let categories = ["TITEL", "RECHERCHE", "LITERATURVERZEICHNIS", "FORSCHUNGSFRAGE", "UNTERSUCHUNGSKONZEPT", "METHODIK", "DURCHFUEHRUNG", "AUSWERTUNG"];
    for (let i = 0; i < categories.length; i++) {
        if (category === categories[i]) {
            category_r = i * 203 % 255; //mod 255 ensures to create valid colors. times any big
            category_g = i * 101 % 255; //number enlarges variation of usually pretty close numbers
            category_b = i * 181 % 255;
        }
    }
    let r = category_r + (userEmail.hashCode() * userEmail.hashCode() * userEmail.hashCode()) % 71;
    let g = category_g + (userEmail.hashCode() * userEmail.hashCode()) % 71;
    let b = category_b + userEmail.hashCode() % 71;
    let r_d = r - 50;
    let g_d = g - 50;
    let b_d = b - 50;

    let color = 'rgb(' + r + ',' + g + ',' + b + ')';
    let colorDark = 'rgb(' + r_d + ',' + g_d + ',' + b_d + ')';

    userColors.set(userEmail, color);
    userColorsDark.set(userEmail, colorDark);

}

/**
 * Calculate and build a readable timestamp from an unix timestamp
 *
 * @param timestamp A unix timestamp
 * @returns {string} A readable timestamp
 */
function timestampToReadableTime(timestamp) {
    // build Date object from timestamp
    let annotationDate = new Date(timestamp);
    // declare response
    let responseTimestamp;

    // get hours from date
    let hours = "0" + annotationDate.getHours();
    // get minutes from date
    let minutes = "0" + annotationDate.getMinutes();

    // if annotation is from today
    if (isTimestampToday(timestamp)) {
        // build readable timestamp in format HH:mm
        responseTimestamp = hours.substr(-2) + ":" + minutes.substr(-2);
    }
    // else annotation is not from today
    else {
        // get date
        let date = "0" + annotationDate.getDate();
        // get month
        let month = "0" + annotationDate.getMonth();
        // get year
        let year = "" + annotationDate.getFullYear();

        // build readable timestamp dd.MM.yy HH:mm
        responseTimestamp = date.substr(-2) + "." + month.substr(-2) + "." + year.substr(-2) + " " + hours.substr(-2) + ":" + minutes.substr(-2);
    }

    return responseTimestamp;
}

/**
 * Check if given timestamp is from today
 *
 * @param timestamp The given timestamp in milliseconds
 * @returns {boolean} Returns true if the timestamp is from today
 */
function isTimestampToday(timestamp) {
    // now
    let now = new Date();
    // build Date object from timestamp
    let date = new Date(timestamp);

    // return true if timestamp is today
    return (now.getDate() === date.getDate() && now.getMonth() === date.getMonth() && now.getFullYear() === date.getFullYear());
}

/**
 * Toggle between the toggle button status
 *
 * @param id The id of the clicked annotation
 */
function toggleButtonHandler(id) {
    // the clicked annotation card
    let card = $('#' + id);
    // open and close annotation text
    card.find(".annotation-body").children("p").toggleClass("overflow-hidden");
    // toggle between up and down button
    card.find('.annotation-header-toggle').children("i").toggleClass("fa-chevron-down fa-chevron-up")
}

/**
 * Save a new annotation in database and list
 *
 * @param title The title of the new annotation
 * @param comment The comment of the new annotation
 * @param startCharacter The startCharacter based on the annotated text
 * @param endCharacter The endCharacter based on the annotated text
 */
function saveNewAnnotation(title, comment, startCharacter, endCharacter) {

    // initialize target
    let targetId = getQueryVariable("fullSubmissionId");
    let targetCategory = getQueryVariable("category").toUpperCase();
    let userEmail = getUserEmail();

    // build annotationPostRequest
    let annotationPostRequest = {
        userEmail: userEmail,
        targetId: targetId,
        targetCategory: targetCategory,
        body: {
            title: title,
            comment: comment,
            startCharacter: startCharacter,
            endCharacter: endCharacter
        }
    };

    // send new annotation to back-end and display it in list
    createAnnotation(annotationPostRequest, function (response) {
        // send new annotation to websocket
        send("CREATE", response.id);
        // display the new annotation
        displayAnnotation(response);

    });
}

/**
 * Open edit modal with title and comment from given card
 *
 * @param id The id of the clicked annotation
 */
function editAnnotationHandler(id) {
    // the clicked annotation card
    let card = $('#' + id);
    // get title and comment
    let title = card.find('.annotation-header-data-title').text();
    let comment = card.find('.annotation-body-text').text();

    // set title and comment
    $('#annotation-edit-form-title').val(title);
    $('#annotation-edit-form-comment').val(comment);

    // display annotation edit modal and pass id
    $('#annotation-edit-modal').data('id', id).show();
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
 * Show or hide the drop down button for every annotation card.
 * Call this on page resize and after annotations GET
 */
function showAndHideToggleButton() {
    // iterate over each annotation card
    $('#annotations').find('li').each(function () {

        // find the comment element, clone and hide it
        let comment = $(this).find('.annotation-body').children('p');
        let clone = comment.clone()
            .css({display: 'inline', width: 'auto', visibility: 'hidden'})
            .appendTo('body');
        let cloneWidth = clone.width();

        // remove the element from the page
        clone.remove();

        // show drop down button only if text was truncated
        if (cloneWidth > comment.width()) {
            $(this).find('.annotation-header-toggle').show();
            $(this).find('.annotation-header-data').css('width', 'calc(100% - 40px)');
        }
        else {
            $(this).find('.annotation-header-toggle').hide();
            $(this).find('.annotation-header-data').css('width', '100%');
        }

    })
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
    }
    else {
        annotationElement.find('.annotation-header-toggle').hide();
        annotationElement.find('.annotation-header-data').css('width', '100%');
    }
}

/**
 * Handle the annotation click and show the modal
 *
 */
function handleAnnotationClick() {

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
            startCharacter = offsets.start;
            endCharacter = offsets.end;

            if (isAnnotationInRange(startCharacter, endCharacter)) {
                // display annotation create modal
                $('#annotation-create-modal').show();
            }
            else {
                window.alert("Annotationen sind nur in vorgehobenen Bereichen möglich")
            }
        } else {
            $('#annotation-create-modal').show();
        }
    } else {
        $('#annotation-create-modal').show();
    }
}


/**
 * Checks if user selected area is inside submission part range
 *
 * @param start The start character of the selection
 * @param end The end character of the selection
 * @returns {boolean} Returns true if the selection is in range
 */
function isAnnotationInRange(start, end) {
    let body = $('#documentText').data("body");
    for (let i = 0; i < body.length; i++) {
        if (body[i].startCharacter <= start && end <= body[i].endCharacter) {
            return true;
        }
    }
    return false;
}

/**
 * Display or hide annotations based on filter string
 */
function searchAnnotation() {
    // declare variables
    let input, filter;
    input = $('#annotation-search');
    filter = input.val().toLowerCase();

    // iterate over annotation card and hide those who don't match the search query
    $('#annotations').find('li').each(function () {
        let user, title, comment;
        user = $(this).find('.annotation-header-data-user').text().toLowerCase();
        title = $(this).find('.annotation-header-data-title').text().toLowerCase();
        comment = $(this).find('.annotation-body-text').text().toLowerCase();

        // hiding based on user, title and comment
        if (user.indexOf(filter) > -1 ||
            title.indexOf(filter) > -1 ||
            comment.indexOf(filter) > -1) {
            $(this).css('display', '')
        }
        else {
            $(this).css('display', 'none')
        }
    });
}

function selectText() {
    let text = document.getElementsByClassName('categoryText')[0];
    if (window.getSelection()) {
        let selection = window.getSelection();
        let range = document.createRange();
        range.selectNodeContents(text);
        selection.removeAllRanges();
        selection.addRange(range);
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

function getFeedbackName(){
    $.ajax({
        url: "../rest/annotations/feedbackTarget/projectName/" + $('#projectName').html().trim(),
        headers: {
            "Content-Type":"application/json",
            "Cache-Control": "no-cache"
        },
        type: 'GET',
        success: function (response) {
            $('#feedBackTarget').html(response.name + " schrieb folgendes");
        }
    });
}