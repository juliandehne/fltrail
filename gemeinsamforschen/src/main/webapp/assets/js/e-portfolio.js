//TODO Get student and project form context

$(document).ready(function() {
    $.ajax({
        url: "../rest/projectdescription/0"
    }).then(function(data) {
        $('.journal-description-title').append('<h2>' + data.name + '</h2>');
        $('.journal-description-text').append(data.description);
        for(var link in data.links){
            $('.journal-description-links').append('<a href=' + data.links[link] + '>' + link + '</a><br/>');
        }
        for(var g in data.group){
            $('.journal-description-group').append(data.group[g]+ '<br/>');

        }
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
            '<div class="journal-container"><div class="journal-avatar">' +
            'getBild' +
            '</div><div class="journal-date"> ' +
            timestampToDateString(data[journal].timestamp) +
            '</div><div class="journal-name">' +
            data[journal].creator + '' +
            '</div><div class="journal-category">' +
            data[journal].category +
            '</div><div class="journal-edit">' +
            '<a href="createJournal.jsp?token=test&journal=' + data[journal].id + '"><i class="fa fa-pencil" aria-hidden="true"></i></a>' +
            ' </div><div class="journal-text">' +
            data[journal].entry +
            '</div>')
    }}