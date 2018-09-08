
var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function() {
    $('#student').val(student);
    $('#project').val(project);


    var peerfeedbackID = getQueryVariable("Peerfeedback");
    console.log(peerfeedbackID);
    var peerfeedbackID = "a3cef66d-e1b7-4030-8fcd-1413d6e77ba0";
    console.log(peerfeedbackID);
    //if(peerfeedbackID) {
        $.ajax({
            url: "../rest/peerfeedback/"+peerfeedbackID     //{id}

        }).then(function (data) {
            //$('#editor').append(data.descriptionMD);
            console.log("function1");
            loadFeedback(data);
            console.log("function2");

            document.getElementById("Peerfeedback").innerHTML = data;
            //document.write(data);
            //console.log(data);

            var newDiv = document.createElement("div");
            var newContent = document.createTextNode(data.text);
            newDiv.appendChild(newContent); // füge den Textknoten zum neu erstellten div hinzu.

            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentDiv = document.getElementById("div1");
            document.body.insertBefore(newDiv, currentDiv);

            //$('#peerfeedbackID').val(peerfeedbackID);
            console.log(data);
        });
   // }
    function loadFeedback(data) {
        for (var feedback in data) {
            var feedbackString = '<div class="pf-container">' +
                '<div class="journal-date"> ' +
                data[feedback].timestamp +
                '</div>' +
                '<div class="journal-name">' +
                // TODO id to name
                data[feedback].text +
                '</div>' +
                '<div class="journal-category">' +
                data[feedback].id +
                '</div>' +
                '<div class="journal-edit" align="right">';

            feedbackString = feedbackString + '</div>' +
                '<div class="journal-text">' +
                data[feedback].entryHTML +
                '</div>' +
                '</div><br><br>';

            $('.Peerfeedback').append(feedbackString)
        }};
})