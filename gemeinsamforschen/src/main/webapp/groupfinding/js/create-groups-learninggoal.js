/**
 * Created by fides-WHK on 22.01.2018.
 */
$(document).ready(function () {
    //todo: Buttons im Eventhandler steuern und nicht auf der HTML-Seite.
    getProjects(document.getElementById('user').innerHTML);
    //getMembers($('#projectDropdown').innerHTML,$('#user').innerHTML);
});

function printProjectDropdown(projects) {
    let menu = document.getElementById("dropdownOptions");          //the unordered list of buttons called by the dropdown button
    let limit = projects.length;
    for (let i = 0; i < limit; i++) {                               //show every project a student takes
        // part in
        let option = document.createElement("SPAN");            //and create a span, containing a button with it
        option.innerHTML = "<button class='dropdown-item' " +   //which carries the event onClick, the name of the group and a design
            "onClick=" +
            '"showProject(' + "'" + projects[i] + "',document.getElementById('user').innerHTML);" + '"' + ">"
            + projects[i] +
            "</button>";
        menu.appendChild(option);
    }
}

function getProjects(user) {
    let url = compbaseUrl + "/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            if (data.length !== 0) {
                let projects = [];
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


function printGroupTable(student1, student2, student3, student4) {
    let innerurl = "../database/getAdresses.php?student1=" + student1 + "&student2=" + student2 + "&student3=" + student3 + "&student4=" + student4;
    /*if (student4) {
        innerurl = innerurl + "&student4=" + student4;
    }*/
    $.ajax({                    //get email adresses in this ajax.
        student1: "" + student1,
        student2: "" + student2,
        student3: "" + student3,
        student4: "" + student4,
        url: innerurl,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (innerData) {
            let tableStart = '<table class="table table-striped table-bordered table-list"' +
                ' style="width: 40%;margin-top:' +
                ' 10px;"> <thead id="tableHead"> ' +
                '  <tr>' +
                '    <th class="hidden-xs">Student</th>' +
                '    <th>E-Mail</th>' +
                '  </tr>';
            let tableFinish = '</thead>' + '</table>';
            for (let k2 = 0; k2 < innerData.length; k2++) {
                if (innerData[k2].name === student1) {
                    tableStart = tableStart + ("<tr><td>" + student1 + "</td><td><a" +
                        " href='mailto:" + innerData[k2].email + "'>" + innerData[k2].email + "</a></td></tr>");
                } else if (innerData[k2].name === student2) {
                    tableStart = tableStart + ("<tr><td>" + student2 + "</td><td><a" +
                        " href='mailto:" + innerData[k2].email + "'>" + innerData[k2].email + "</a></td></tr>");
                } else if (innerData[k2].name === student3) {
                    tableStart = tableStart + ("<tr><td>" + student3 + "</td><td><a" +
                        " href='mailto:" + innerData[k2].email + "'>" + innerData[k2].email + "</a></td></tr>");
                } else if (innerData[k2].name === student4 && (student4 != null)) {
                    tableStart = tableStart + ("<tr><td>" + student4 + "</td><td><a" +
                        " href='mailto:" + innerData[k2].email + "'>" + innerData[k2].email + "</a></td></tr>");
                }
            }

            let tableString = tableStart + tableFinish;
            $("#tablesHolder").append(tableString);
        }
    });
    return innerurl;
}

function getMembers(project, user) {        //gets all Members in the chosen Project user is a part of with email adresses

    $("#tablesHolder").empty();
    let url = compbaseUrl + "/api2/groups/" + project;     //this API is used, since fleckenroller has security issues
    // with CORS
    // and stuff
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",                               //{groups: [id, users:[]] }
        success: function (data) {
            for (let i = 0; i < data.groups.length; i++) {

                let student1 = data.groups[i].users[0];
                let student2 = data.groups[i].users[1];
                let student3 = data.groups[i].users[2];
                let student4 = data.groups[i].users[3];
                printGroupTable(student1, student2, student3, student4);
            }
        },
        error: function () {
            $("#tablesHolder").append("<p>Es wurden keine Gruppen gefunden. Das Projekt muss mehr als 5 Teilnehmer haben!</p>")
        }

    });
}

function showProject(project, user) {           //will display the chosen option in the dropdown button and show all students in a unordered list
    $("#projectDropdown").text(project);        //the dropdown button
    getMembers(project, user);                  //the students
}

/**
 * Created by dehne on 28.03.2018.
 */

function getProjectsOfAuthor(author, printedProjects, handleProjects) {
    let url = "../../gemeinsamforschen/rest/project/all/author/" + getUserEmail();
    $.ajax({
        url: url,
        Accept: "application/json",
        contentType: "text/plain",
        success: function (response) {
            //  var authoredProjects = JSON.parse(response);
            let authoredProjects = response;

            if (authoredProjects != null) {
                if (printedProjects != null) {
                    for (let i = 0; i < printedProjects.length; i++) {
                        authoredProjects = authoredProjects.filter(function (el) {
                            return el !== printedProjects[i];
                        });
                    }
                    handleProjects(authoredProjects, printedProjects.length);
                } else {
                    handleProjects(authoredProjects, 0);
                }

            }
        },
        error: function (a) {
            console.log(a);
        }
    });
}