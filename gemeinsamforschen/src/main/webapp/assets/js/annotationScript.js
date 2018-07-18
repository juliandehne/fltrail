// initialize userToken, userColors and targetId
var userToken = getUserTokenFromUrl();
var userColors = new Map();
var userColorsDark = new Map();
var targetId = 200;

// declare document text, start and end character
var documentText, startCharacter, endCharacter;

/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    connect("200");

    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {

            // action for 'annotation' click
            if (key == 'annotation') {
                // show modal if something is selected
                if (getSelectedText().length > 0) {
                    startCharacter = window.getSelection().getRangeAt(0).startOffset;
                    endCharacter = window.getSelection().getRangeAt(0).endOffset;

                    // display annotation create modal
                    $('#annotation-create-modal').modal("show");
                }
            }

        },
        items: {
            "annotation": {name: "Annotation", icon: "edit"}
        }
    });

    /**
     * continue button
     */
    $('#btnContinue').click(function () {
        location.href="givefeedback.jsp?token=" + getUserTokenFromUrl();
    });

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
            var title = $('#annotation-form-title').val();
            var comment = $('#annotation-form-comment').val();

            // hide and clear the modal
            $('#annotation-create-modal').modal('hide');

            // save the new annotation in db and display it
            saveNewAnnotation(title, comment, startCharacter, endCharacter);
        }
    });

    /**
     * Edit button of the annotation edit modal
     * hide modal and alter the annotation
     */
    $('#btnEdit').click(function () {
        if ($('#annotation-edit-form').valid()) {
            // get title and comment from clicked annotation card
            var id = $('#annotation-edit-modal').data('id');
            var card = $('#' + id);
            var title = card.find('.annotation-header-data-title').text();
            var comment = card.find('.annotation-body-text').text();

            // get title and comment from form
            var newTitle = $('#annotation-edit-form-title').val();
            var newComment = $('#annotation-edit-form-comment').val();

            // compare new and old card content
            if (title !== newTitle || comment !== newComment) {

                // build patch request
                var annotationPatchRequest = {
                    title: newTitle,
                    comment: newComment
                };
                // send alter request to server
                alterAnnotation(id, annotationPatchRequest, function (response) {
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
        var id = $('#annotation-edit-modal').data('id');

        // delte annotation from server and from list
        deleteAnnotation(id, function () {
            // remove annotation from list
            $('#' + id).closest('.listelement').remove()
            // remove highlighted text
            deleteHighlightedText();

            // hide and clear the modal
            $('#annotation-edit-modal').modal('hide');
        })
    });

    /**
     * Clear the title and comment input field of the create modal
     */
    $('#annotation-create-modal').on('hidden.bs.modal', function(){
        // clear title
        $('#annotation-form-title').val('');
        // clear comment
        $('#annotation-form-comment').val('')
    });

    /**
     * Clear the title and comment input field of the edit modal
     */
    $('#annotation-edit-modal').on('hidden.bs.modal', function(e){
        // clear title
        $('#annotation-edit-form-title').val('');
        // clear comment
        $('#annotation-edit-form-comment').val('')
    });

    documentText = $('#documentText').html();

    // fetch annotations from server on page start
    getAnnotations(targetId, function (response) {
        // iterate over annotations and display each
        $.each(response, function (i, annotation) {
            displayAnnotation(annotation);
        })
        // handle drop down button
        showAndHideToggleButton();
    });

});

/**
 * This will be called on page resize
 */
$( window ).resize(function() {
    // handle drop down button for every annotation
    showAndHideToggleButton();
});

/**
 * POST: Save an annotation in the database
 *
 * @param annotationPostRequest The post request
 * @param responseHandler The response handler
 */
function createAnnotation(annotationPostRequest, responseHandler) {
    var url = "../rest/annotations/";
    var json = JSON.stringify(annotationPostRequest);
    $.ajax({
        url: url,
        type: "POST",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}

/**
 * PATCH: Alter an annotation in database
 *
 * @param id The annotation id
 * @param annotationPatchRequest The patch request
 * @param responseHandler The response handler
 */
function alterAnnotation(id, annotationPatchRequest, responseHandler) {
    var url = "../rest/annotations/" + id;
    var json = JSON.stringify(annotationPatchRequest);
    $.ajax({
        url: url,
        type: "PATCH",
        data: json,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            responseHandler(response);
        }
    });
}

/**
 * DELETE: Delete an annotation from database
 *
 * @param id The annotation id
 */
function deleteAnnotation(id, responseHandler) {
    var url = "../rest/annotations/" + id;
    $.ajax({
        url: url,
        type: "DELETE",
        dataType: "json",
        success: function (response) {
            responseHandler(response)
        }
    });
}

/**
 * GET: Get all annotations from database for a specific target
 *
 *
 * @param targetId The target id
 * @param responseHandler The response handler
 */
function getAnnotations(targetId, responseHandler) {
    var url = "../rest/annotations/target/" + targetId;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (response) {
            // sort the responding annotations by timestamp (DESC)
            response.sort(function (a, b) {
                return a.timestamp - b.timestamp;
            });
            // handle the response
            responseHandler(response);
        }
    });
}

/**
 * Display annotation in the list
 *
 * @param annotation The annotation to be displayed
 */
function displayAnnotation(annotation) {
    // fetch list of annotations
    var list = $('#annotations')

    var editIcon = "fas fa-edit";
    var dateIcon = "fas fa-calendar";
    if (isTimestampToday(annotation.timestamp)) {
        dateIcon = "fas fa-clock";
    }

    // insert annotation card
    list.prepend(
        // list element
        $('<li>')
            .attr('class', 'listelement')
            .append(
                // annotation card
                $('<div>').attr('class', 'annotation-card')
                    .attr('id', annotation.id)
                    .mouseenter(function () {
                        $(this).children('.annotation-header').css('background-color', getDarkUserColor(annotation.userToken));
                    })
                    .mouseleave(function () {
                        $(this).children('.annotation-header').css('background-color', getUserColor(annotation.userToken));
                    })
                    .append(
                        // annotation header
                        $('<div>').attr('class', 'annotation-header')
                            .css('background-color', getUserColor(annotation.userToken))
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
                                                $('<span>').append(annotation.userToken)
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
                                    if (userToken == annotation.userToken) {
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
                addHighlightedText(annotation.body.startCharacter, annotation.body.endCharacter, annotation.userToken);
            })
            .mouseleave(function () {
                deleteHighlightedText();
            })
            .append(function () {
                if ($('#annotations li').filter( ".listelement" ).length > 0) {
                    return $('<div>').attr('class', 'spacing')
                }
            })
    );
}

/**
 * Add a highlighted text at specific position
 *
 * @param startCharacter The offset of the start character
 * @param endCharacter The offset of the end character
 * @param userToken The user token
 */
function addHighlightedText(startCharacter, endCharacter, userToken) {
    // create <span> tag with the annotated text
    var replacement = $('<span></span>').css('background-color', getUserColor(userToken)).html(documentText.slice(startCharacter, endCharacter));

    // wrap an <p> tag around the replacement, get its parent (the <p>) and ask for the html
    var replacementHtml = replacement.wrap('<p/>').parent().html();

    // insert the replacementHtml
    var newDocument = documentText.slice(0, startCharacter) + replacementHtml + documentText.slice(endCharacter);

    // set new document text
    $('#documentText').html(newDocument);
}

/**
 * Restore the base text
 */
function deleteHighlightedText() {
    $('#documentText').html(documentText);
}

/**
 * Get the text value of the selected text
 *
 * @returns {string} The text
 */
function getSelectedText() {
    if(window.getSelection){
        return window.getSelection().toString();
    }
    else if(document.getSelection){
        return document.getSelection();
    }
    else if(document.selection){
        return document.selection.createRange().text;
    }
}

/**
 * Get color based on user id
 *
 * @param userToken The id of the user
 * @returns {string} The user color
 */
function getUserColor(userToken) {
    // insert new color if there is no userToken key
    if (userColors.get(userToken) == null) {
        generateRandomColor(userToken);
    }
    // return the color
    return userColors.get(userToken);
}

/**
 * Get dark color based on user id
 *
 * @param userToken The token of the user
 * @returns {string} The dark user color
 */
function getDarkUserColor(userToken) {
    // insert new color if there is no userToken key
    if (userColorsDark.get(userToken) == null) {
        generateRandomColor(userToken);
    }
    // return the color
    return userColorsDark.get(userToken);
}

/**
 * Generate a random color of the format 'rgb(r, g, b)'
 *
 * @param userToken The given user token
 */
function generateRandomColor(userToken) {
    var r = Math.floor(Math.random()*56)+170;
    var g = Math.floor(Math.random()*56)+170;
    var b = Math.floor(Math.random()*56)+170;
    var r_d = r - 50;
    var g_d = g - 50;
    var b_d = b - 50;

    var color = 'rgb(' + r + ',' + g + ',' + b + ')';
    var colorDark = 'rgb(' + r_d + ',' + g_d + ',' + b_d + ')';

    userColors.set(userToken, color);
    userColorsDark.set(userToken, colorDark);
}

/**
 * Calculate and build a readable timestamp from an unix timestamp
 *
 * @param timestamp A unix timestamp
 * @returns {string} A readable timestamp
 */
function timestampToReadableTime(timestamp) {
    // build Date object from timestamp
    var annotationDate = new Date(timestamp);
    // declare response
    var responseTimestamp;

    // get hours from date
    var hours = "0" + annotationDate.getHours();
    // get minutes from date
    var minutes = "0" + annotationDate.getMinutes();

    // if annotation is from today
    if (isTimestampToday(timestamp)) {
        // build readable timestamp in format HH:mm
        responseTimestamp = hours.substr(-2) + ":" + minutes.substr(-2);
    }
    // else annotation is not from today
    else {
        // get date
        var date = "0" + annotationDate.getDate();
        // get month
        var month = "0" + annotationDate.getMonth();
        // get year
        var year = "" + annotationDate.getFullYear();

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
    var now = new Date();
    // build Date object from timestamp
    var date = new Date(timestamp);

    // return true if timestamp is today
    if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth() && now.getFullYear() == date.getFullYear()) {
        return true;
    }
    else {
        return false;
    }
}

/**
 * Toggle between the toggle button status
 *
 * @param id The id of the clicked annotation
 */
function toggleButtonHandler(id) {
    // the clicked annotation card
    var card = $('#' + id);
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
    // build annotationPostRequest
    var annotationPostRequest = {
        userToken: userToken,
        targetId: targetId,
        body: {
            title: title,
            comment: comment,
            startCharacter: startCharacter,
            endCharacter: endCharacter
        }
    };

    // send new annotation to back-end and display it in list
    createAnnotation(annotationPostRequest, function(response) {
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
    var card = $('#' + id);
    // get title and comment
    var title = card.find('.annotation-header-data-title').text();
    var comment = card.find('.annotation-body-text').text();

    // set title and comment
    $('#annotation-edit-form-title').val(title);
    $('#annotation-edit-form-comment').val(comment);

    // display annotation edit modal and pass id
    $('#annotation-edit-modal').data('id', id).modal("show");
}

/**
 * Show or hide the drop down button for every annotation card.
 * Call this on page resize and after annotations GET
 */
function showAndHideToggleButton() {
    // iterate over each annotation card
    $('#annotations').find('li').each(function () {

        // find the comment element, clone and hide it
        var comment = $(this).find('.annotation-body').children('p');
        var clone = comment.clone()
            .css({display: 'inline', width: 'auto', visibility: 'hidden'})
            .appendTo('body');
        var cloneWidth = clone.width();

        // remove the element from the page
        clone.remove();

        // show drop down button only if text was truncated
        if(cloneWidth > comment.width()) {
            $(this).find('.annotation-header-toggle').show();
        }
        else {
            $(this).find('.annotation-header-toggle').hide();
        }

    })
}
