/**
 * Created by fides-WHK on 22.01.2018.
 */
$(document).ready(function () {
    //todo: Buttons im Eventhandler steuern und nicht auf der HTML-Seite.
    getProjects(document.getElementById('user').innerHTML);
    //getMembers($('#projectDropdown').innerHTML,$('#user').innerHTML);
});

function getProjects(user) {
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var menu = document.getElementById("dropdownOptions");          //the unordered list of buttons called by the dropdown button
            if (data.length !== 0) {
                var i = 0;
                var limit = data.data.length;
                for (i = 0; i < limit; i++) {                               //show every project a student takes part in
                    var option = document.createElement("SPAN");            //and create a span, containing a button with it
                    option.innerHTML = "<button class='dropdown-item' " +   //which carries the event onClick, the name of the group and a design
                        "onClick=" +
                        '"showProject(' + "'" + data.data[i] + "',document.getElementById('user').innerHTML);" + '"' + ">"
                        + data.data[i] +
                        "</button>";
                    menu.appendChild(option);
                }
            } else {
                document.getElementById("projectDropdown").innerHTML = "keine Projekte verfügbar";      //no projects of the students where found
            }
        }
    });
}


function getMembers(project, user) {
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/groups/" + project;     //this API is used, since fleckenroller has security issues with CORS and stuff
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.groups.length; i++) {
                for (var j = 0; j < data.groups[i].users.length; j++) {
                    if (data.groups[i].users[j] === user) {
                        $("#student2").show();
                        $("#student3").show();
                            $("#student2").text(data.groups[i].users[(j + 1) % data.groups[i].users.length]);
                            $("#student3").text(data.groups[i].users[(j + 2) % data.groups[i].users.length]);
                            if (data.groups[i].users.length > 3) {      //the fourth student is just shown if the group has at least 4 members
                                $("#student4").show();
                                $("#student4").text(data.groups[i].users[(j + 3) % data.groups[i].users.length]);
                            }
                            else $("#student4").hide();
                            if (data.groups[i].users.length > 4) {      //the fifth student is just shown if the group has 5 members
                                $("#student5").show();
                                $("#student5").text(data.groups[i].users[(j + 4) % data.groups[i].users.length]);
                            }
                            else $("#student5").hide();
                    }
                }
            }
        },
        error: function(a,b,c){
            $("#student2").text("In diesem Projekt sind noch nicht ausreichend TeilnehmerInnen vorhanden.")
            $("#student3").hide();
            $("#student4").hide();
            $("#student5").hide();
        }

    });
}

function showProject(project, user) {           //will display the chosen option in the dropdown button and show all students in a unordered list
    $("#projectDropdown").text(project);        //the dropdown button
    getMembers(project, user);                  //the students
}