/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    // fetch the document text of the given id
    getFullSubmission(getSubmissionIdFromUrl(), function (response) {
        // set text in div
        $('#documentText').html(response.text);
    }, function () {
        // jump to previous page on error
        location.href="unstructured-upload.jsp?token="+getUserTokenFromUrl();
    });

    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {

            // TODO - show and handle more options

            // action for 'annotation' click
            if (key == 'annotation') {
            }

        },
        items: {
            "annotation": {name: "Annotation", icon: "edit"}
        }
    });

});

/**
 * Get the id of a full submission from url
 *
 * @returns The id of the full submission
 */
function getSubmissionIdFromUrl() {
    var parts = window.location.search.substr(1).split("&");
    var $_GET = {};
    for (var i = 0; i < parts.length; i++) {
        var temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    return $_GET['submission'];

}


