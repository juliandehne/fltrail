/**
 * Created by fides-WHK on 22.01.2018.
 */
$(document).ready(function () {
    //todo: Buttons im Eventhandler steuern und nicht auf der HTML-Seite.
    getProjects(document.getElementById('user').innerHTML);
    $('#rearrangeButton').hide();
    $('#passwordDiv').hide();
    $('#wrongPasswordDiv').hide();
});

function checkAuthor() {
    var password = document.getElementById('adminPassword').value;
    var project = document.getElementById('projectDropdown').innerHTML;
    $.ajax({
        url: '../database/checkAuthorPassword.php?project='+project+'&password='+password,
        dataType: 'json',
        contentType: 'json',
        project: project,
        success: function(data){
            if (data){
                location.href = "rearrangeGroups.php?token=" + getUserTokenFromUrl() + "&projectId=" + project;
            }
            else {
                $('#wrongPasswordDiv').show();
            }
        },
        error: function(a,b,c){
            alert('wrong password');
        }
    });
}

function printProjectDropdown(projects, numberOfProjectsPrinted) {
    var menu = document.getElementById("dropdownOptions");          //the unordered list of buttons called by the dropdown button
    var limit = projects.length;
    for (var i = 0; i < limit; i++) {                               //show every project a student takes
        // part in
        var option = document.createElement("SPAN");            //and create a span, containing a button with it
        option.innerHTML = "<button class='dropdown-item' " +   //which carries the event onClick, the name of the group and a design
            "onClick=" +
            '"showProject(' + "'" + projects[i] + "');" + '"' + ">"
            + projects[i] +
            "</button>";
        menu.appendChild(option);
    }
}
function getProjects(user) {
    var url = compbaseUrl + "/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if (data.length !== 0) {
                var projects = [];
                if (data.data != null) {
                    projects = data.data;
                    printProjectDropdown(projects, 0);
                }
                getProjectsOfAuthor(user, projects, printProjectDropdown)
            } else {
                document.getElementById("projectDropdown").innerHTML = "keine Projekte verfügbar";      //no projects of the students where found
            }
        }
    });
}


function printGroupTable(students) {
    var innerurl = "../database/getAdresses.php";
    for (var i = 0; i < students.length; i++) {
        if (i === 0) {
            innerurl = innerurl + "?students[]=" + students[i]
        } else
            innerurl = innerurl + "&students[]=" + students[i]
    }
    $.ajax({                    //get email adresses in this ajax.
        students: students,
        url: innerurl,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (innerData) {
            var tableStart = '<table class="table table-striped table-bordered table-list"' +
                ' style="width: 40%;margin-top:' +
                ' 10px;"> <thead id="tableHead"> ' +
                '  <tr>' +
                '    <th class="hidden-xs">Student</th>' +
                '    <th>E-Mail</th>' +
                '  </tr>';
            var tableFinish = '</thead>' + '</table>';
            for (var k2 = 0; k2 < innerData.length; k2++) {
                tableStart = tableStart + ("<tr><td>" + innerData[k2].name + "</td><td><a" +
                    " href='mailto:" + innerData[k2].email + "'>" + innerData[k2].email + "</a></td></tr>");
            }
            var tableString = tableStart + tableFinish;
            $("#tablesHolder").append(tableString);

        }
    });
    return innerurl;
}


function getMembers(project) {        //gets all Members in the chosen Project user is a part of with email adresses
    $("#tablesHolder").empty();
    var url = "../database/getGroups.php?projectId=" + project;
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",                               //{groups: [id, users:[]] }
        success: function (data) {
            if (data.length < 1) {
                var innerurl = compbaseUrl + "/api2/groups/" + project;      //this API is used, since fleckenroller has security issues
                // with CORS
                // and stuff
                $.ajax({
                    url: innerurl,
                    type: 'GET',
                    contentType: "application/json",
                    dataType: "json",                               //{groups: [id, users:[]] }
                    success: function (data) {
                        var students = [];
                        for (var i = 0; i < data.groups.length; i++) {
                            for (var j = 0; j < data.groups[i].users.length; j++) {
                                students.push(data.groups[i].users[j]);
                            }
                            printGroupTable(students);
                            students = [];
                        }
                        $('#rearrangeButton').show();
                    },
                    error: function (data) {
                        $("#tablesHolder").append("<p>Es wurden keine Gruppen gefunden. Das Projekt muss mehr als 5 Teilnehmer haben!</p>")
                    }

                });
            } else {
                var students = [];
                for (var i = 0; i < data.length; i++) { //data.length is the count of students in the project
                    for (var j = 0; j < data.length; j++) {
                        if (data[j].student.groupId === 'Gruppe' + i) {
                            students.push(data[j].student.student);
                        }
                    }
                    printGroupTable(students);
                    students = [];
                }
                $('#rearrangeButton').show();
            }
        }
        ,
        error: function (data) {
            $("#tablesHolder").append("<p>Es wurden keine Gruppen gefunden. Das Projekt muss mehr als 5 Teilnehmer haben!</p>")
        }

    });
}

function showProject(project) {           //will display the chosen option in the dropdown button and show all students in a unordered list
    $("#projectDropdown").text(project);        //the dropdown button
    getMembers(project);                  //the students
}