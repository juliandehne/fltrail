var student = getQueryVariable("token");
var project = getQueryVariable("projectName");


/**function getQueryVariable(variable) {
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) {
            return pair[1];
        }
    }
    return (false);
}*/

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);
    //$('#name').val(name);
    var name = document.getElementById("user").innerHTML;
    var zsm = name+"+"+student;
    console.log(name)
    $('#zsm').val(zsm);
    console.log(zsm);


    $('#viewfeedback').click(function () {
        location.href="../feedback/view-feedback.jsp?token="+getUserTokenFromUrl();
    });

    $('#backlink').click(function () {
        window.history.back();
    });

    new InscrybMDE({
        element: document.getElementById("editor"),
        spellChecker: false,
        forceSync: true,
    });


    $('#sub').click(function () {

        $.ajax({
            url: "../rest/peerfeedback/save"
        }).then(function (data) {
            //console.log("save:"+data);
            return location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();
        });
        location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();
    });


    $.ajax({
        url: "../rest/peerfeedback/getUsers/" + student
    }).then(function (data) {
        console.log("getUsers:"+data);
        loadUsers(data);
    });


    $.ajax({
        url: "../rest/peerfeedback/checkFeedback/" +student
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

            var currentdiv = document.getElementById("reciever");
            currentdiv.appendChild(newopt);
        }

    }

})