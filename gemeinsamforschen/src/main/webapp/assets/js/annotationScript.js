// initialize userId, userColors and targetId
var userId = randomUserId();
var userColors = new Map();
var userColorsDark = new Map();
var targetId = 200;

// declare document text
var documentText;

/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {

            // close context menu
            window.close;

            // initialize selected body
            var body = getSelectedText();

            // if user selected something
            if (body.length > 0) {
                // annotationPostRequest
                var request = {
                    userId: userId,
                    targetId: targetId,
                    body: body,
                    startCharacter: window.getSelection().getRangeAt(0).startOffset,
                    endCharacter: window.getSelection().getRangeAt(0).endOffset
                };

                console.log(request);

                createAnnotation(request, function(response) {
                    // display the new annotation
                    displayAnnotation(response);

                });
            }

        },
        items: {
            "annotation": {name: "Annotation", icon: "edit"}
        }
    });

    /*
     *   PAGE LOADED
     */
    documentText = $('#documentText').html();

    // fetch annotations from server on page start
    getAnnotations(targetId, function (response) {
        // iterate over annotations and display each
        $.each(response, function (i, annotation) {
            displayAnnotation(annotation);
        })
    });

});

/**
 * POST: Save an annotation in the database
 *
 * @param annotationPostRequest the post request
 * @param responseHandler the response handler
 */
function createAnnotation(annotationPostRequest, responseHandler) {
    var url = "http://localhost:8080/rest/annotations/";
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
 * PATCH: Alter an annotation
 *
 * @param id the annotation id
 * @param annotationPatchRequest the patch request
 * @param responseHandler the response handler
 */
function alterAnnotation(id, annotationPatchRequest, responseHandler) {
    var url = "http://localhost:8080/rest/annotations/" + id;
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
 * GET: Get all annotations for a specific target
 *
 *
 * @param targetId the target id
 * @param responseHandler the response handler
 */
function getAnnotations(targetId, responseHandler) {
    var url = "http://localhost:8080/rest/annotations/target/" + targetId;
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
 * @param annotation the annotation to be displayed
 */
function displayAnnotation(annotation) {
    // fetch list of annotations
    var list = $('#annotations')

    // insert spacing if we need one
    if ($('#annotations li').filter( ".listelement" ).length > 0) {
        list.prepend(
            $('<li>').attr('class', 'spacing')
        )
    }

    var dateIcon = "fas fa-calendar";
    if (timestampIsToday(annotation.timestamp)) {
        dateIcon = "fas fa-clock";
    }

    // insert annotation card
    list.prepend(
        $('<li>')
            .mouseenter(function () {
                addHighlightedText(annotation.startCharacter, annotation.endCharacter, annotation.userId);
            })
            .mouseleave(function () {
                deleteHighlightedText();
            })
            .data('annotation', annotation)
            .attr('class', 'listelement')
            .append(
                $('<div>').attr('class', 'annotation-card')
                    .mouseenter(function () {
                        $(this).children('.annotation-header').css('background-color', getDarkUserColor(annotation.userId));
                    })
                    .mouseleave(function () {
                        $(this).children('.annotation-header').css('background-color', getUserColor(annotation.userId));
                    })
                    .append(
                        $('<div>').attr('class', 'annotation-header')
                            .css('background-color', getUserColor(annotation.userId))
                            .append(
                                $('<div>').attr('class', 'annotation-header-title')
                                    .append(
                                        $('<div>').attr('class', 'overflow-hidden')
                                            .append(
                                                $('<i>').attr('class', 'fas fa-user')
                                            )
                                            .append(
                                                $('<span>').append(annotation.userId)
                                            )
                                    )
                                    .append(
                                        $('<div>').attr('class', 'overflow-hidden')
                                            .append(
                                                $('<i>').attr('class', 'fas fa-bookmark')
                                            )
                                            .append(
                                                $('<span>').append('title' + annotation.userId)
                                            )
                                    )
                            )
                            .append(
                                $('<div>').attr('class', 'annotation-header-toggle')
                                    .click(function () {
                                        toggleButtonHandler($(this));
                                    })
                                    .append(
                                        $('<i>').attr('class', 'fas fa-chevron-down')
                                    )
                            )
                    )
                    .append(
                        $('<div>').attr('class', 'annotation-body')
                            .append(
                                $('<p>').attr('class', 'overflow-hidden').append(annotation.body)
                            )
                    )
                    .append(
                        $('<div>').attr('class', 'annotation-footer')
                            .append(
                                $('<i>').attr('class', dateIcon)
                            )
                            .append(
                                $('<span>').append(timestampToReadableTime(annotation.timestamp))
                            )
                    )
            )

    );
}

/**
 * Add a highlighted text at specific position
 *
 * @param startCharacter the offset of the start character
 * @param endCharacter the offset of the end character
 * @param userId the user id
 */
function addHighlightedText(startCharacter, endCharacter, userId) {
    // create <span> tag with the annotated text
    var replacement = $('<span></span>').css('background-color', getUserColor(userId)).html(documentText.slice(startCharacter, endCharacter));

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
 * @returns {string} the text
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
 * @param userId the id of the user
 * @returns {string} the user color
 */
function getUserColor(userId) {
    // insert new color if there is no userId key
    if (userColors.get(userId) == null) {
        generateRandomColor(userId);
    }
    // return the color
    return userColors.get(userId);
}

/**
 * Get dark color based on user id
 *
 * @param userId the id of the user
 * @returns {string} the dark user color
 */
function getDarkUserColor(userId) {
    // insert new color if there is no userId key
    if (userColorsDark.get(userId) == null) {
        generateRandomColor(userId);
    }
    // return the color
    return userColorsDark.get(userId);
}

/**
 * Generate a random color of the format 'rgb(r, g, b)'
 *
 * @param userId the given user id
 */
function generateRandomColor(userId) {
    var r = Math.floor(Math.random()*56)+170;
    var g = Math.floor(Math.random()*56)+170;
    var b = Math.floor(Math.random()*56)+170;
    var r_d = r - 50;
    var g_d = g - 50;
    var b_d = b - 50;

    var color = 'rgb(' + r + ',' + g + ',' + b + ')';
    var colorDark = 'rgb(' + r_d + ',' + g_d + ',' + b_d + ')';

    userColors.set(userId, color);
    userColorsDark.set(userId, colorDark);
}

/**
 * Calculate and build a readable timestamp from an unix timestamp
 *
 * @param timestamp a unix timestamp
 * @returns {string} a readable timestamp
 */
function timestampToReadableTime(timestamp) {
    // build Date object from timestamp
    var annotationDate = new Date(timestamp);
    // declare response
    var responseTimestamp;

    // if annotation is from today
    if (timestampIsToday(timestamp)) {
        // get hours from date
        var hours = annotationDate.getHours();
        // get minutes from date
        var minutes = "0" + annotationDate.getMinutes();
        // get seconds from date
        // var seconds = "0" + annotationDate.getSeconds();

        // build readable timestamp
        responseTimestamp = hours + ":" + minutes.substr(-2);
    }
    // else annotation is not from today
    else {
        // get date
        var date = annotationDate.getDate();
        // get month
        var month = annotationDate.getMonth();
        // get year
        var year = annotationDate.getFullYear();

        // build readable timestamp
        responseTimestamp = date + "." + month + "." + year;
    }

    return responseTimestamp;
}

/**
 * Check if given timestamp is from today
 *
 * @param timestamp the given timestamp in milliseconds
 * @returns {boolean} returns true if the timestamp is from today
 */
function timestampIsToday(timestamp) {
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
 * @param element the given toggle button
 */
function toggleButtonHandler(element) {
    // open and close annotation text
    element.parent().siblings(".annotation-body").children("p").toggleClass("overflow-hidden");
    // toggle between up and down button
    element.children("i").toggleClass("fa-chevron-down fa-chevron-up")
}

/*
    MOCKUP FUNCTIONS
 */
function randomUserId() {
    return Math.floor((Math.random() * 10) + 1);;
}

