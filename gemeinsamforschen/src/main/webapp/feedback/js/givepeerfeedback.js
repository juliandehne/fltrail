
var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
var name = getQueryVariable("user");
var email = getQueryVariable("email");

var sender = getQueryVariable("sender");
var reciever = getQueryVariable("reciever");
var filename = getQueryVariable("filename");
var category = getQueryVariable("category");
var timetamp = getQueryVariable("timestamp");
//var project = getQueryVariable("projectId");

console.log(student);

function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}

$(document).ready(function() {
    $('#student').val(student);
    $('#project').val(project);
    $('#name').val(name);

    new InscrybMDE({
        element: document.getElementById("editor"),
        spellChecker: false,
        forceSync: true,
    });

    var user = document.getElementById("user");
    user.setAttribute("name", document.getElementById("user").textContent);
    var cln_user = user.cloneNode(true);
    document.getElementById("journalform").appendChild(cln_user);

    console.log(student);
    console.log(user);

    $.ajax({
        url: "../rest/peerfeedback/save" //+ student
    }).then(function (data) {
        $('#editor').append(data.descriptionMD);

       /** //TODO preselet in select tags
        new InscrybMDE({
            element: document.getElementById("editor"),
            spellChecker: false,
            forceSync: true,
        });*/

        console.log(data);
       //location.href="give-feedback.jsp?token=" + getUserTokenFromUrl();
       //alert("Feedback wurde gesendet!");
    });

    $.ajax({
        url: "../rest/peerfeedback/getUsers" //+ student
    }).then(function (data) {
        console.log(data);
        loadUsers(data);
    });

    $.ajax({
        url: "../rest/peerfeedback/getToken" + user //+ student
    }).then(function (data) {
        console.log(data);
        //loadUsers(data);
    });

    function loadUsers(data) {

        for (var user in data) {
            var newopt = document.createElement("OPTION");

            newopt.insertAdjacentHTML('beforeend', data[user]);
            newopt.value = data[user];

            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentdiv = document.getElementById("reciever");
            currentdiv.appendChild(newopt);

        }

    }
})