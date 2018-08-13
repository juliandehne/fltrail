var documentText;

/**
 * This function will fire when the DOM is ready
 */
$(document).ready(function() {

    // fetch the document text of the given id
    getFullSubmission(getSubmissionIdFromUrl(), function (response) {
        // set text in div
        $('#documentText').html(response.text);

    }, function () {
        // jump to upload page on error
        location.href="unstructured-upload.jsp?token="+getUserTokenFromUrl();
    });



    /**
     * Context menu handler
     */
    $.contextMenu({
        selector: '.context-menu-one',
        callback: function(key, options) {

            // TODO - show and handle more options
            if (key === "titel" ||
                key === "recherche" ||
                key === "literaturverzeichnis" ||
                key === "forschungsfrage" ||
                key === "untersuchungskonzept" ||
                key === "methodik" ||
                key === "durchfuehrung" ||
                key === "auswertung") {
                if (getSelectedText().length > 0) {
                    let startCharacter = window.getSelection().getRangeAt(0).startOffset;
                    let endCharacter = window.getSelection().getRangeAt(0).endOffset;
                    let selectedText = getSelectedText();

                    handleCategorySelection(selectedText, key, startCharacter, endCharacter);

                }
            }

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


function handleCategorySelection(text, category, startCharacter, endCharacter) {
    var elem = $('#' + category);
    elem.toggleClass("not-added added");

    addHighlightedText(startCharacter, endCharacter);

    isAlreadyHighlighted(startCharacter, endCharacter);

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
 * Add a highlighted text at specific position
 *
 * @param startCharacter The offset of the start character
 * @param endCharacter The offset of the end character
 */
function addHighlightedText(startCharacter, endCharacter) {

    var documentText = $('#documentText').text();
    var documentHtml = $('#documentText').html();

    // create <span> tag with the annotated text
    var replacement = $('<span></span>').css('background-color', 'lightgreen').html(documentText.slice(startCharacter, endCharacter));

    // wrap an <p> tag around the replacement, get its parent (the <p>) and ask for the html
    var replacementHtml = replacement.wrap('<p/>').parent().html();

    // insert the replacementHtml
    var newDocument = documentText.slice(0, startCharacter) + replacementHtml + documentText.slice(endCharacter);

    // set new document text
    $('#documentText').html(newDocument);
}

function isAlreadyHighlighted(startCharacter, endCharacter) {
    $('#annotations').each(function () {
        if ($(this).find(".category-card").attr("added")) {
            console.log("hi")
        }
    });
    return false;
}
