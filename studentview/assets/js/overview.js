/**
 * Created by fides-WHK on 02.03.2018.
 */

$(document).ready(function () {
    getProjectOverview(document.getElementById('user').innerHTML);
    //getMembers($('#projectDropdown').innerHTML,$('#user').innerHTML);

});


function printProjects(projects, offset) {
    var table = document.getElementById("projectTable");
    var i = 0;
    if (projects != null) {
        for (i = 0; i < projects.length; i++) {
            var project = projects[i];
            var content = document.createElement("TR");
            content.role = "button";
            content.style = "cursor:pointer;";
            content.id = project;

            //'<a class="btn btn-default"><em class="fa fa-pencil" ></em></a>' +
            //'<button id="deleteButton' +i+ '" class="btn btn-danger fa fa-trash deleteButton"></button>' +
            content.innerHTML = '<td align="center">' +
                '<a href="deleteProject.php?token='+getUserTokenFromUrl()+'" class="btn btn-danger fa fa-trash"></a>' +
                '</td>' +
                '<td class="hidden-xs" href="#Div_Promo_Carousel" data-slide="next">' + projects[i] + '</td>' +
                '<td id="projectTags' + (i + offset) + '" href="#Div_Promo_Carousel" data-slide="next"></td>';
            table.appendChild(content);
            getTags(project, i + offset);
            $('#' + project).click(function () {
                getGroups(this.id);
            });
        }
    }


    //$('#deleteModal').modal('show');

}
function getProjectOverview(user) {
    var url = compbaseUrl + "/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var projects = data.data;
            printProjects(projects, 0);
            getProjectsOfAuthor(user, projects, printProjects);
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function getTags(projectName, number) {
    var url = "../database/getTags.php?project=" + projectName;
    $.ajax({
        url: url,
        Accept: "text/plain; charset=utf-8",
        contentType: "text/plain",
        success: function (response) {
            response = JSON.parse(response);
            var tagString = "";
            var i = 0;
            var table = document.getElementById("projectTags" + number);
            for (i = 0; i < response.length; i++) {
                tagString += "<label class=\"tagLabel\">" + response[i].tag + "</label>";
                //tagString += response[i].tag + " ";
            }

            table.innerHTML = tagString;
            //table.innerHTML = tagHtml;
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}




function getGroups(projectName) {
    var pathName = document.getElementsByName("pathProject");
    pathName[0].innerHTML = projectName;
    pathName[1].innerHTML = projectName;
    var url = compbaseUrl + "/api2/groups/" + projectName;     //this API is used, since fleckenroller has security
    // issues with
    // CORS and stuff
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var table = document.getElementById("groupTable");
            table.innerHTML = '';
            for (var i = 0; i < data.groups.length; i++) {
                var content = document.createElement("TR");
                content.role = "button";
                content.style = "cursor:pointer;";
                content.id = 'Gruppe' + data.groups[i].id;
                var groupMembers = '<td align="center">' +
                    '<a class="btn btn-default"><em class="fa fa-pencil"></em></a>' +
                    '<a class="btn btn-danger"><em class="fa fa-trash"></em></a>' +
                    '</td>' +
                    '<td class="hidden-xs" href="#Div_Promo_Carousel" data-slide="next">Gruppe' + data.groups[i].id + '</td>' +
                    '<td id="memberOf' + content.id + '" href="#Div_Promo_Carousel" data-slide="next">';
                for (var j = 0; j < data.groups[i].users.length; j++) {
                    groupMembers += data.groups[i].users[j] + "&nbsp;";
                }
                groupMembers += '</td>';
                content.innerHTML = groupMembers;
                table.appendChild(content);
                $('#Gruppe' + data.groups[i].id).click(function () {
                    var memberString = $('#memberOf' + this.id).html();
                    getDetailsOfMembers(this.id, memberString);
                });
            }
        },
        error: function (a, b, c) {
            console.log(a);
            var table = document.getElementById("groupTable");
            table.innerHTML = '';
            var content = document.createElement("TR");
            content.innerHTML = '<td align="center">' +
                '</td>' +
                '<td class="hidden-xs">Es liegen noch keine Gruppen vor</td>' +
                '<td></td>';
            table.appendChild(content);
        }
    });
}

function getDetailsOfMembers(group, studentString) {
    var pathName = document.getElementsByName("pathGruppe");
    pathName[0].innerHTML = group;
    studentString = studentString.substring(0, studentString.length-6);         //cuts off the last &nbsp;
    var students = studentString.split("&nbsp;");
    var innerurl = "../database/getAdresses.php";
    for (var i = 0; i < students.length; i++) {
        if (i === 0) {
            innerurl = innerurl + "?students[]=" + students[i]
        } else
            innerurl = innerurl + "&students[]=" + students[i]
    }
    $.ajax({
        url: innerurl,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            var table = document.getElementById("studentTable");
            table.innerHTML = '';
            var k;
            for (k = 0; k < response.length; k++) {
                var content = document.createElement("TR");
                content.innerHTML = '<td align="center">' +
                    '<a class="btn btn-default"><em class="fa fa-pencil"></em></a>' +
                    '<a class="btn btn-danger"><em class="fa fa-trash"></em></a>' +
                    '</td>' +
                    '<td class="hidden-xs">' + response[k].name + '</td>' +
                    '<td> <a href="mailto:' + response[k].email + '">' + response[k].email + '</a></td>';
                table.appendChild(content);
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}
