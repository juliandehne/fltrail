var student = getQueryVariable("token");
var project = getQueryVariable("projectName");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);


    var peerfeedbackID = getQueryVariable("Peerfeedback");
    console.log(peerfeedbackID);
    var peerfeedbackID = "a3cef66d-e1b7-4030-8fcd-1413d6e77ba0";
    var sender = "sender";
    console.log(peerfeedbackID);
    //if(peerfeedbackID) {
    $.ajax({
        url: "../rest/peerfeedback/" + sender              //peerfeedbackID     {id}

    }).then(function (data) {
        //$('#editor').append(data.descriptionMD);
        console.log("function1");
        loadFeedback(data);
        console.log("function2");

        //document.getElementById("Peerfeedback").innerHTML = data.text +"text";
        //document.write(data);
        //console.log(data);
        /**
         var newDiv = document.createElement("div");
         var newContent = document.createTextNode(data.text);
         newDiv.appendChild(newContent); // füge den Textknoten zum neu erstellten div hinzu.

         // füge das neu erstellte Element und seinen Inhalt ins DOM ein
         var currentDiv = document.getElementById("div1");
         currentDiv.appendChild(newDiv);
         //document.body.insertBefore(newDiv, currentDiv);
         */
        //$('#peerfeedbackID').val(peerfeedbackID);
        console.log(data);
    });

    // }
    function loadFeedback(data) {
        for (var feedback in data) {
            /**var feedbackString = '<div class="pf-container">' +
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
             '</div><br><br>';*/

            var newdiv = document.createElement("div");


            //newdiv.innerHTML = data[feedback].text;
            //newdiv.append(data[feedback].text);
            newdiv.insertAdjacentHTML('beforeend', data[feedback].text);
            newdiv.className = "feedback-container";
            //var text = convertMarkdownToHtml(data[feedback].text);
            //var newcontent = document.createTextNode(data[feedback].text);
            //newdiv.appendChild(newcontent); // füge den Textknoten zum neu erstellten div hinzu.

            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentdiv = document.getElementById("div1");
            currentdiv.appendChild(newdiv);
            //document.body.insertBefore(newDiv, currentDiv);
            //document.getElementById("div").innerHTML = data[feedback].text;

            //$('.Peerfeedback').append(feedbackString)
        }
    };
})