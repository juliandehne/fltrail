
var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
var name = getQueryVariable("user");

console.log(name);

$(document).ready(function(){
    $('#student').val(student);
    $('#project').val(project);
    //$('#name').val(name);

    var user = document.getElementById("user");
    user.setAttribute("name", document.getElementById("user").textContent);
    var cln_user = user.cloneNode(true);
    document.getElementById("filter-feedbacks").appendChild(cln_user);


    console.log(student);
    console.log(user);
    //console.log(user);
    var sender = "sender";


    //console.log(peerfeedbackID);
    //if(peerfeedbackID) {
        $.ajax({
            url: "../rest/peerfeedback/"+student              //peerfeedbackID     {id}

        }).then(function (data) {
            //$('#editor').append(data.descriptionMD);

            loadFeedback(data);
            console.log("function1");

            loadFeedbackSender(data);
            console.log("function2");

            console.log(data);
        });
   // }
    function loadFeedback(data) {
        for (var feedback in data) {

            var newdiv = document.createElement("div");
            newdiv.className = "feedback-container";
            var newp = document.createElement("p");
            newp.insertAdjacentHTML('beforeend',data[feedback].text)
            newdiv.appendChild(newp);

            //newdiv.insertAdjacentHTML('beforeend',data[feedback].text);
            //newdiv.className = "feedback-container";


            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentdiv = document.getElementById("div1");
            currentdiv.appendChild(newdiv);

        }};

        function loadFeedbackSender(data) {

            for (var feedback in data) {
            var newdiv = document.createElement("div");

            newdiv.insertAdjacentHTML('beforeend',data[feedback].feedbacksender);
            newdiv.className = "feedback-container";

            var currentdiv = document.getElementById("senderlist");
            currentdiv.appendChild(newdiv);

        }

        //timestampToDateString(data[feedback].timestamp)
    function timestampToDateString(timestamp) {
        var date = new Date(timestamp);
        return date.toLocaleString("de-DE");
    }};
})