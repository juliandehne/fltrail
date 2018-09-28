
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

    $('.viewfeedback').click(function () {
        location.href="viewfeedback.jsp?token="+getUserTokenFromUrl();
    });

    new InscrybMDE({
        element: document.getElementById("editor"),
        spellChecker: false,
        forceSync: true,
    });

    var user = document.getElementById("user");
    user.setAttribute("name", document.getElementById("user").textContent);
    var cln_user = user.cloneNode(true);
    document.getElementById("journalform").appendChild(cln_user);

    var getToken = document.getElementById("user").textContent;
    //var checkFeedback = document.getElementById("user").textContent;

    //var i = document.getElementById("defaultCheck1").onclick.valueOf();
    //console.log("i:"+i);

    console.log(student);
    console.log(getToken);

    var checkFeedback = student;
    console.log(checkFeedback);

    $.ajax({
        url: "../rest/peerfeedback/save", //+ student
    }).then(function (data) {
        $('#editor').append(data.descriptionMD);

        console.log("save:"+data);

    });

    /**$.ajax({
        url: "../rest/peerfeedback/getToken/" +getToken,
    }).then(function (data) {
        console.log("getToken-js:"+data);
    });*/

    $.ajax({
        url: "../rest/peerfeedback/getUsers/" + student
    }).then(function (data) {
        console.log("getUsers:"+data);
        loadUsers(data);
    });


    $.ajax({
        url: "../rest/peerfeedback/checkFeedback/" +checkFeedback
    }).then(function (data) {
        console.log("checkFeedback:"+data);
    });

    function loadUsers(data) {


        for (var user in data) {

            var sender = [];
            var name = [];

            var pair = data[user].split("+");
            name.push(pair[0]);
            sender.push(pair[1]);
            console.log(name+sender);

            var newopt = document.createElement("OPTION");

            newopt.insertAdjacentHTML('beforeend', name);
            newopt.value = data[user];

            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentdiv = document.getElementById("reciever");
            currentdiv.appendChild(newopt);

        }

    }

    /**function getUsername(name) {
        let query = data;
        let vars = query.split("+");
        for (let i = 0; i < vars.length; i++) {
            let pair = vars[i].split("=");
            if (pair[0] === name) {
                return pair[1];
            }
        }
        return (false);
    }*/
})