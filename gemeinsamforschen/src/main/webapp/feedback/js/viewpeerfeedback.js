
var student = getQueryVariable("token");
var project = getQueryVariable("projectId");
var name = getQueryVariable("user");


$(document).ready(function(){
    $('#student').val(student);
    $('#project').val(project);
    $('#name').val(name);


    console.log(student);
    var nme = document.getElementById("user").innerHTML;
    var zsm = nme +"+"+ student;
    $('#zsm').val(zsm);
    console.log(zsm);
    //var sender = "sender";

    $('#sended').click(function () {

        $.ajax({
            url: "../rest/peerfeedback/"+zsm              //peerfeedbackID     {id}

        }).then(function (data) {
            //$('#editor').append(data.descriptionMD);
            console.log(data);
            //loadFeedback(data);
            console.log("function1");
            var list = [];
            for(var id in data){
                if(!list.includes(data[id].feedbacksender)){
                    list.push(data[id].feedbacksender);
                    console.log(data[id].feedbacksender);
                }
            }
            console.log(list);


            $.ajax({
                url: "../rest/peerfeedback/getSender/" + list
            }).then(function (data) {
                console.log("getSender:"+data);
                console.log(list);
                var s = data[0];
                loadFeedbackSender(data);
                //loadFeedbackReciever(data);
                //loadUsers(data);
            });
            console.log("function2");

            /**var listr = [];
            for(var id in data){
                if(!listr.includes(data[id].feedbackreceiver)){
                    listr.push(data[id].feedbackreceiver);
                    console.log(data[id].feedbackreceiver);
                }
            }
            console.log(listr);

            $.ajax({
                url: "../rest/peerfeedback/getSender/" + listr
            }).then(function (data) {
                console.log("getReciever:"+data);
                console.log(listr);
                var s = data[0];
                //loadFeedbackReciever(data);
                //loadUsers(data);
            });
            console.log("function3");*/

            console.log(data);
        });
    });

    $('#recieved').click(function () {
        //location.href="../feedback/view-feedback.jsp?token="+getUserTokenFromUrl();
        $.ajax({
            url: "../rest/peerfeedback/"+zsm              //peerfeedbackID     {id}

        }).then(function (data) {
            //$('#editor').append(data.descriptionMD);
            console.log(data);
            //loadFeedback(data);
            console.log("function1");
            var list = [];
            for(var id in data){
                if(!list.includes(data[id].feedbacksender)){
                    list.push(data[id].feedbacksender);
                    console.log(data[id].feedbacksender);
                }
            }
            console.log(list);


            $.ajax({
                url: "../rest/peerfeedback/getSender/" + list
            }).then(function (data) {
                console.log("getSender:"+data);
                console.log(list);
                var s = data[0];
                //loadFeedbackSender(data);
                loadFeedbackReciever(data);
                //loadUsers(data);
            });
            console.log("function2");

           /**var listr = [];
            for(var id in data){
                if(!listr.includes(data[id].feedbacksender)){
                    listr.push(data[id].feedbackreceiver);
                    console.log(data[id].feedbackreceiver);
                }
            }
            console.log(listr);

            $.ajax({
                url: "../rest/peerfeedback/getSender/" + listr
            }).then(function (data) {
                console.log("getReciever:"+data);
                console.log(listr);
                var s = data[0];
                //loadFeedbackSender(data);
                loadFeedbackReciever(data);
                //loadUsers(data);
            });
            console.log("function3");*/

            console.log(data);
        });
    });

    function loadFeedback(data) {

        $("#msg_history").empty();

        console.log(data);

        for (var feedback in data) {

            console.log(data[feedback]);

            var newdiv = document.createElement("div");
            newdiv.className = "incoming_msg";
            var newdiv4 = document.createElement("div");
            newdiv4.className = "chat_img";
            var newdiv5 = document.createElement("img");
            newdiv5.src = "../libs/img/noImg.png";
            newdiv5.alt="Avatar";
            newdiv5.className = "img-reciever";

            var newdiv2 = document.createElement("div");
            newdiv2.className = "received_msg";
            var newdiv3 = document.createElement("div");
            newdiv3.className = "received_withd_msg";
            newdiv3.appendChild(newdiv5);
            var newp = document.createElement("p");
            //newp.className = "received_withd_msg";
            newp.insertAdjacentHTML('beforeend', data[feedback].text)
            var newspan = document.createElement("span");
            newspan.className = "chat_date";
            newspan.insertAdjacentHTML('beforeend',timestampToDateString(data[feedback].timestamp));
            var newhr =document.createElement("hr");

            newdiv2.insertBefore(newdiv4, newdiv.childNodes[0]);
            newdiv.appendChild(newdiv2);
            newdiv2.appendChild(newdiv3);
            newdiv3.appendChild(newp);
            newp.appendChild(newspan);
            var currentdiv = document.getElementById("msg_history");
            currentdiv.appendChild(newdiv);
            currentdiv.appendChild(newhr);

        }
    }

        function loadFeedbackSender(data) {

            $("#inbox_chat").empty();

            console.log(student);
            console.log(data);

            for (var feedback in data) {

                console.log(data[feedback]);
                var sender = [];
                var name = [];

                var pair = data[feedback].split("+");
                name.push(pair[0]);
                sender.push(pair[1]);
                console.log(name+sender);


                var newdiv = document.createElement("button");
                newdiv.className = "chat_list";
                newdiv.id = sender;
                //newdiv.value = sender;
                //newdiv.onclick = new function(){alert('clicked');};

                //newdiv.setAttribute("onclick","myFunction(student, sender)");

                newdiv.onclick = function () {
                    var h = this.id;
                    //console.log(document.getElementById(sender.toString()));
                        console.log(h);
                        myFunction(student, h);

                }


                    var ddiv = document.createElement("div");
                    ddiv.id = "ddiv";

                    var newdiv2 = document.createElement("div");
                    newdiv2.className = "chat_people";
                    var newdiv4 = document.createElement("div");
                    newdiv4.className = "chat_img";
                    var newdiv5 = document.createElement("img");
                    newdiv5.src = "../libs/img/noImg.png";
                    newdiv5.alt = "Avatar";
                    newdiv5.className = "img-sender";
                    newdiv4.appendChild(newdiv5);
                    var newdiv3 = document.createElement("div");
                    newdiv3.className = "chat_ib";
                    var newh = document.createElement("h5");
                    newh.insertAdjacentHTML('beforeend', name)
                    var newspan = document.createElement("span");
                    newspan.className = "chat_date";
                    //newspan.insertAdjacentHTML('beforeend',timestampToDateString(data[feedback].timestamp));

                    ddiv.appendChild(newdiv);
                    newdiv.insertBefore(newdiv4, newdiv.childNodes[0]);
                    newdiv.appendChild(newdiv2);
                    newdiv2.appendChild(newdiv3);
                    newdiv3.appendChild(newh);

                    newh.appendChild(newspan);

                    var currentdiv = document.getElementById("inbox_chat");
                    currentdiv.appendChild(ddiv);

                    //var username = getUsername(data[feedback]);
                    //console.log("username:"+username);

            }
            //newdiv.disabled =true;
        }

    function loadFeedbackReciever(data) {

        $("#inbox_chat").empty();

        console.log(student);
        console.log(data);

        for (var feedback in data) {

            console.log(data[feedback]);
            var sender = [];
            var name = [];

            var pair = data[feedback].split("+");
            name.push(pair[0]);
            sender.push(pair[1]);
            console.log(name+sender);


            var newdiv = document.createElement("button");
            newdiv.className = "chat_list";
            newdiv.id = sender;
            //newdiv.value = sender;
            //newdiv.onclick = new function(){alert('clicked');};

            //newdiv.setAttribute("onclick","myFunction(student, sender)");

            newdiv.onclick = function () {
                var h = this.id;
                //console.log(document.getElementById(sender.toString()));
                console.log(h);
                myFunction(h, student);

            }


            var ddiv = document.createElement("div");
            ddiv.id = "ddiv";

            var newdiv2 = document.createElement("div");
            newdiv2.className = "chat_people";
            var newdiv4 = document.createElement("div");
            newdiv4.className = "chat_img";
            var newdiv5 = document.createElement("img");
            newdiv5.src = "../libs/img/noImg.png";
            newdiv5.alt = "Avatar";
            newdiv5.className = "img-sender";
            newdiv4.appendChild(newdiv5);
            var newdiv3 = document.createElement("div");
            newdiv3.className = "chat_ib";
            var newh = document.createElement("h5");
            newh.insertAdjacentHTML('beforeend', name)
            var newspan = document.createElement("span");
            newspan.className = "chat_date";
            //newspan.insertAdjacentHTML('beforeend',timestampToDateString(data[feedback].timestamp));

            ddiv.appendChild(newdiv);
            newdiv.insertBefore(newdiv4, newdiv.childNodes[0]);
            newdiv.appendChild(newdiv2);
            newdiv2.appendChild(newdiv3);
            newdiv3.appendChild(newh);

            newh.appendChild(newspan);

            var currentdiv = document.getElementById("inbox_chat");
            currentdiv.appendChild(ddiv);

            //var username = getUsername(data[feedback]);
            //console.log("username:"+username);

        }
        //newdiv.disabled =true;
    }


        //timestampToDateString(data[feedback].timestamp)
    function timestampToDateString(timestamp) {
        var date = new Date(timestamp);
        return date.toLocaleString("de-DE");
    }

    function myFunction(student, sender) {
        console.log("YOU CLICKED ME!");
        console.log(student);
        console.log(sender);

        $.ajax({
            url: "../rest/peerfeedback/getfeedbackbysender/" + student + "/" + sender
        }).then(function (data) {
            console.log("pffürsender:"+data);
            loadFeedback(data);
            //loadUsers(data);
        });
        console.log("pffürsender");
    }


})