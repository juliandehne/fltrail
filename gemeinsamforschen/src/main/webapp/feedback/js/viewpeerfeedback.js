
var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
var name = getQueryVariable("user");



$(document).ready(function(){
    $('#student').val(student);
    $('#project').val(project);
    //$('#name').val(name);

    /**var user = document.getElementById("user");
    user.setAttribute("name", document.getElementById("user").textContent);
    var cln_user = user.cloneNode(true);
    document.getElementById("filter-feedbacks").appendChild(cln_user);*/


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
            console.log(data);
            loadFeedback(data);
            console.log("function1");

            loadFeedbackSender(data);
            console.log("function2");

            console.log(data);
        });

    $.ajax({
        url: "../rest/peerfeedback/getUsers" //+ student
    }).then(function (data) {
        console.log("getUsers:"+data);
        //loadUsers(data);
    });
   // }
    function loadFeedback(data) {
        for (var feedback in data) {

            var newdiv = document.createElement("div");
            newdiv.className = "incoming_msg";
            var newdiv4 = document.createElement("div");
            newdiv4.className = "chat_img";
            var newdiv5 = document.createElement("img");
            newdiv5.src = "https://ptetutorials.com/images/user-profile.png";
            newdiv4.appendChild(newdiv5);

            var newdiv2 = document.createElement("div");
            newdiv2.className = "received_msg";
            var newdiv3 = document.createElement("div");
            newdiv3.className = "received_withd_msg";
            var newp = document.createElement("p");
            //newp.className = "received_withd_msg";
            newp.insertAdjacentHTML('beforeend', data[feedback].text)
            var newspan = document.createElement("span");
            newspan.insertAdjacentHTML('beforeend',timestampToDateString(data[feedback].timestamp));

            newdiv.insertBefore(newdiv4, newdiv.childNodes[0]);
            newdiv.appendChild(newdiv2);
            newdiv2.appendChild(newdiv3);
            newdiv3.appendChild(newp);
            //newdiv.appendChild(newdiv4);

            newp.appendChild(newspan);

            //newdiv.insertAdjacentHTML('beforeend',data[feedback].text);
            //newdiv.className = "feedback-container";


            // füge das neu erstellte Element und seinen Inhalt ins DOM ein
            var currentdiv = document.getElementById("msg_history");
            currentdiv.appendChild(newdiv);
        }
    }

        function loadFeedbackSender(data) {

            for (var feedback in data) {
                var newdiv = document.createElement("div");
                newdiv.className = "chat_list";
                var newdiv2 = document.createElement("div");
                newdiv2.className = "chat_people";
                var newdiv4 = document.createElement("div");
                newdiv4.className = "chat_img";
                var newdiv5 = document.createElement("img");
                newdiv5.src = "https://ptetutorials.com/images/user-profile.png";
                newdiv4.appendChild(newdiv5);
                var newdiv3 = document.createElement("div");
                newdiv3.className = "chat_ib";
                var newh = document.createElement("h5");
                newh.insertAdjacentHTML('beforeend', data[feedback].feedbacksender)
                var newspan = document.createElement("span");
                newspan.className = "chat_date";
                newspan.insertAdjacentHTML('beforeend',timestampToDateString(data[feedback].timestamp));

                newdiv.insertBefore(newdiv4, newdiv.childNodes[0]);
                newdiv.appendChild(newdiv2);
                newdiv2.appendChild(newdiv3);
                newdiv3.appendChild(newh);

                newh.appendChild(newspan);

                var currentdiv = document.getElementById("inbox_chat");
                currentdiv.appendChild(newdiv);

                var username = getUsername(data);
                console.log("username:"+username);

            }
        }

    function getUsername(data) {
        console.log("getusername:"+data);
        var query = data.split(",");
        //var vars = query.split(",");
        console.log(query);
        for (let i = 0; i < query.length; i++) {
            let pair = query[i].split("+");
            console.log(pair[i]);
            if (pair[0] === data) {
                return pair[1];
            }
        }
        return (false);
    }

        //timestampToDateString(data[feedback].timestamp)
    function timestampToDateString(timestamp) {
        var date = new Date(timestamp);
        return date.toLocaleString("de-DE");
    }


})