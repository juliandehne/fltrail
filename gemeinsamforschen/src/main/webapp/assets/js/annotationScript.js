// set color, userId and targetId
var userId = 11;
var targetId = 200;
var color = getRandomColor();

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
 * @param annotationPostRequest
 * @param responseHandler
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
 * @param annotationPatchRequest
 * @param responseHandler
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
 * @param targetId
 * @param responseHandler
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

    // insert annotation card
    list.prepend(
        $('<li>').mouseenter(function () {
            addHighlightedText(annotation.startCharacter, annotation.endCharacter);
        }).mouseleave(function () {
            deleteHighlightedText();
        }).data(annotation).attr('class', 'listelement').attr('id', annotation.id).append(
            $('<div>').attr('class', 'card').append(
                $('<div>').attr('class', 'cardAvatar').css('background-color', color).append(
                    $('<b>').append(annotation.id.substr(0,1))
                )
            ).append(
                $('<div>').attr('class', 'cardContent').append(
                    $('<span>').append(annotation.body.substring(0, 5) + "...")
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
 */
function addHighlightedText(startCharacter, endCharacter) {
    // create <span> tag with the annotated text
    var replacement = $('<span></span>').css('background-color', color).html(documentText.slice(startCharacter, endCharacter));

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
 * @returns {*} the text
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
 * Generate a random color of the format 'rgb(r, g, b)'
 *
 * @returns {string} the new color
 */
function getRandomColor() {
    return 'rgb(' +
        (Math.floor(Math.random()*56)+170) + ', ' +
        (Math.floor(Math.random()*56)+170) + ', ' +
        (Math.floor(Math.random()*56)+170) +
        ')';
}
