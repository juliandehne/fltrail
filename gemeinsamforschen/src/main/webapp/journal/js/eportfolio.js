var student = getQueryVariable("token");
var project = getQueryVariable("projectName");
var description = 0;

$(document).ready(function () {
    $.ajax({
        url: "../rest/projectdescription/" + project + "/" + student
    }).then(function (data) {
        console.log("desc: " + data);
        description = data.id;
        $('#projectdescriptionId').val(description);

        if (!data.open) {
            $("#description-edit").remove();
        }
        $('.journal-description-text').append(data.descriptionHTML);
        for (var ii in data.links) {
            console.log(data.links[ii])
            $('.journal-description-links').append('<button class="btn btn-default btn-xs" onclick=\'linkLoeschen("' + data.links[ii].id + '")\'> <i class="fa fa-trash" aria-hidden="true" ></i></button><a href=' + data.links[ii].link + '>' + data.links[ii].name + '</a> <br/>');
        }
        $('.journal-description-links').append('<button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#addLinkModal"><i class="fa fa-plus" aria-hidden="true"></i></button>');

        for (var g in data.group) {
            $('.journal-description-group').append(data.group[g] + '<br/>');

        }


        console.log(data);
    });


    $.ajax({
        url: "../rest/journal/journals/" + student + "/" + project + "/ALL"
    }).then(function (data) {
        loadJournals(data);
        console.log(data);
    });

    $('#editDescriptionLink').on('click', function () {
        location.href = "edit-description.jsp";
    });

    $('#createJournalLink').on('click', function () {
        location.href = "create-journal.jsp";
    });

});

$(document).on("click", ".open-CloseJournalDialog", function () {
    var journalID = $(this).data('id');
    console.log("on:" + $(this).data('id'));
    $('#journalID-input').val(journalID);
});


function timestampToDateString(timestamp) {
    var date = new Date(timestamp);
    return date.toLocaleString("de-DE");
}

function filterJournals() {
    var filter = $('#journalfilter option:selected').val();
    project = getQueryVariable("projectName");
    $('.journal').empty();

    $.ajax({
        url: "../rest/journal/journals/" + student + "/" + project + "/" + filter
    }).then(function (data) {
        loadJournals(data);
        console.log(data);

    });

}

function loadJournals(data) {
    for (let journal in data) {
        let journalString = '<div class="journal-container">' +
            '<div class="journal-avatar">' +
            'getBild' +
            '</div>' +
            '<div class="journal-date"> ' +
            timestampToDateString(data[journal].timestamp) +
            '</div>' +
            '<div class="journal-name">' +
            // TODO id to name
            data[journal].userNameentifier.userName +
            '</div>' +
            '<div class="journal-category">' +
            data[journal].category +
            '</div>' +
            '<div class="journal-edit" align="right">';

        //TODO userEmail...
        if (data[journal].userNameentifier.userName == student && data[journal].open) {
            journalString = journalString +
                '<a class="btn btn-default btn-sm" href="create-journal.jsp&journal=' + data[journal].id + '"><i class="fa fa-pencil"></i> Bearbeiten</a>' +
                '<a class="open-CloseJournalDialog btn btn-default btn-sm" data-toggle="modal" data-id ='
                + data[journal].id +
                ' data-target ="#closeJournalModal" > <i class="fa fa-check-square" aria-hidden = "true" ></i> Abschlie&szlig;en</a> '
        }

        journalString = journalString + '</div>' +
            '<div class="journal-text">' +
            data[journal].entryHTML +
            '</div>' +
            '</div><br><br>';

        $('.journal').append(journalString)
    }
};

function linkLoeschen(id) {
    console.log("löschen" + id);
    $.ajax({
        type: "POST",
        url: "../rest/projectdescription/deleteLink",
        data: id,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data, status, jqXHR) {

        }
    });
    location.reload();
}

function closeJournal() {
    //TODO reload when modal close
    var journalID = $('#journalID-input').val();
    console.log("schließe=" + journalID);

    $.ajax({
        type: "POST",
        url: "../rest/journal/close",
        data: journalID,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "text",
        success: function (data, status, jqXHR) {
            console.log("succ");
            filterJournals();

        }
    });
}

function closeDescription() {
    console.log("schließe=" + description);

    $.ajax({
        type: "POST",
        url: "../rest/projectdescription/close",
        data: description,
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "text",
        success: function (data, status, jqXHR) {
            console.log("succ");
            location.reload();
        }
    });

}