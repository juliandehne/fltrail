var student = getQueryVariable("token");
var project = getQueryVariable("projectId");


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

    /**console.log(student);
    var nme = document.getElementById("user").innerHTML;
    var zsm = nme +"+"+ student;*/



    var checkFeedback = student;
    console.log(checkFeedback);

    $('#sub').click(function () {
        //event.preventDefault();

        $.ajax({
            url: "../rest/peerfeedback/save" //+ student
        }).then(function (data) {
            //$('#editor').append(data.descriptionMD);
            //location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();
            console.log("save:"+data);
            location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();

        });
        //$("#journalform").submit();
        //event.preventDefault();
        return location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();
    });

    /**$.ajax({
        url: "../rest/peerfeedback/save", //+ student
    }).then(function (data) {
        //$('#editor').append(data.descriptionMD);
        //location.href="../feedback/give-feedback.jsp?="+getUserTokenFromUrl();
        console.log("save:"+data);

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

})