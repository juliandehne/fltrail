//TODO Get student and project form context

$(document).ready(function() {
    $.ajax({
        url: "../rest/projectdescription/0"
    }).then(function(data) {
        $('.journal-description-title').append('<h2>' + data.name + '</h2>');
        $('.journal-description-text').append(data.descriptionHTML);
        for(var link in data.links){
            $('.journal-description-links').append('<button class="btn btn-default btn-xs" onclick=\'linkLoeschen("'+link+'")\'> <i class="fa fa-trash" aria-hidden="true" ></i></button><a href=\' + data.links[link] + \'>' + link + '</a> <br/>');
        }
        $('.journal-description-links').append('<button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#addLinkModal"><i class="fa fa-plus" aria-hidden="true"></i></button>');

        for(var g in data.group){
            $('.journal-description-group').append(data.group[g]+ '<br/>');

        }
        console.log(data);
    });

    $.ajax({
        url: "../rest/journal//journals/0/0"
    }).then(function(data) {
        loadJournals(data);
        console.log(data);
    });

});

function timestampToDateString(timestamp) {
    var date = new Date(timestamp);
    return date.toLocaleString("de-DE");
}

function filterJournals() {
    var filter = $( '#journalfilter option:selected' ).val();

    $('.journal').empty();

    $.ajax({
        url: "../rest/journal//journals/0/0/"+filter
    }).then(function(data) {
        loadJournals(data);
        console.log(data);

    });

}

function loadJournals(data) {
    for (var journal in data) {
        $('.journal').append(
            '<div class="journal-container">' +
                '<div class="journal-avatar">' +
                  'getBild' +
                '</div>' +
                '<div class="journal-date"> ' +
                     timestampToDateString(data[journal].timestamp) +
                '</div>' +
                '<div class="journal-name">' +
                    data[journal].creator +
                '</div>' +
                '<div class="journal-category">' +
                    data[journal].category +
                '</div>' +
                '<div class="journal-edit" align="right">' +
                    '<a class="btn btn-default btn-sm" href="createJournal.jsp?journal=' + data[journal].id + '"><i class="fa fa-pencil"></i> Bearbeiten</a>' +
                    '<a class="btn btn-default btn-sm" data-toggle="modal" data-target="#closeJournalModal"><i class="fa fa-check-square" aria-hidden="true"></i>Abschlie&szlig;en</a>' +
                '</div>' +
                '<div class="journal-text">' +
                    data[journal].entryHTML +
                '</div>' +
            '</div><br><br>')
    }};


function linkLoeschen(name) {
    console.log("löschen" + name);
    $.ajax({
        type: "POST",
        url: "../rest/projectdescription/deleteLink",
        data: JSON.stringify(name),
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data, status, jqXHR) {

            alert(success);
        }
    });

}

function closeJournal(journal) {
    console.log("löschen" + journal);
    $.ajax({
        type: "POST",
        url: "../rest/journal/close",
        data: JSON.stringify(journal),
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data, status, jqXHR) {

            alert(success);
        }
    });

}

function closeJournal(description) {
    console.log("löschen" + description);
    $.ajax({
        type: "POST",
        url: "../rest/projectdescription/close",
        data: JSON.stringify(description),
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json",
        success: function (data, status, jqXHR) {

            alert(success);
        }
    });

}