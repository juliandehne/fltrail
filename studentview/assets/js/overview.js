/**
 * Created by fides-WHK on 02.03.2018.
 */
$(document).ready(function () {
    getProjectOverview(document.getElementById('user').innerHTML);
    //getMembers($('#projectDropdown').innerHTML,$('#user').innerHTML);

});

function getProjectOverview(user) {
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/" + user + "/projects";
    $.ajax({
        url: url,
        user: user,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var table = document.getElementById("projectTable");
            if (data.data) {
                var i = 0;
                for (i = 0; i < data.data.length; i++) {
                    var content = document.createElement("TR");
                    var name = decodeURI(data.data[i]);                 //data.data[i] is URI encoded and holds the projects ID
                    var nameWithoutSpace = name.replace(/ /g, "_");
                    content.role = "button";
                    content.style = "cursor:pointer;";
                    content.id = encodeURI(data.data[i]);
                    content.innerHTML = '<td align="center">' +
                        '<a class="btn btn-default" id="rework' + nameWithoutSpace + '"><em class="fa fa-pencil"></em></a>' +
                        '<a class="btn btn-danger" id="delete' + nameWithoutSpace + '"><em class="fa fa-trash"></em></a>' +
                        '</td>' +
                        '<td class="hidden-xs" href="#Div_Promo_Carousel" data-slide="next">' + name + '</td>' +
                        '<td id="projectTags' + i + '" href="#Div_Promo_Carousel" data-slide="next"></td>';
                    table.appendChild(content);
                    getTags(data.data[i], i);
                    $('#' + data.data[i]).click(function () {
                        getGroups(this.id);
                    });
                    $('#delete' + nameWithoutSpace).click(function () {
                        var projectName = this.id.replace(/delete/, "").replace(/_/g, " ");
                        deleteProject(projectName);       //just deletes the first appearance of "delete"
                    });
                    $('#rework' + nameWithoutSpace).click(function () {
                        var projectName = this.id.replace(/rework/, "").replace(/_/g, " ");
                        reworkProject(projectName);       //just deletes the first appearance of "rework"
                    });
                }
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function getTags(projectName, number) {
    var url = encodeURI("../database/getTags.php?project=" + projectName);
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
                tagString += response[i].tag + " ";
            }
            table.innerHTML = tagString;
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
    var url = encodeURI("https://esb.uni-potsdam.de:8243/services/competenceBase/api2/groups/" + projectName);     //this API is used, since fleckenroller has security issues with CORS and stuff
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
                    groupMembers += data.groups[i].users[j] + " ";
                }
                groupMembers += '</td>';
                content.innerHTML = groupMembers;
                table.appendChild(content);
                $('#Gruppe' + data.groups[i].id).click(function () {
                    getDetailsOfMembers(this.id, $('#memberOf' + this.id).text());
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
    var students = studentString.split(" ");
    var innerurl = "../database/getAdresses.php?student1=" + students[0] + "&student2=" + students[1] + "&student3=" + students[2] + "&student4=" + students[3] + "&student5=" + students[4];
    $.ajax({
        student1: "" + students[0],
        student2: "" + students[1],
        student3: "" + students[2],
        student4: "" + students[3],
        student5: "" + students[4],
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
                var name = response[k].name.replace(/%/g, " ");         //project name is treated internally with "_" while it will be displayed with Spaces " "
                content.innerHTML = '<td align="center">' +
                    '<a class="btn btn-default"><em class="fa fa-pencil"></em></a>' +
                    '<a class="btn btn-danger"><em class="fa fa-trash"></em></a>' +
                    '</td>' +
                    '<td class="hidden-xs">' + name + '</td>' +
                    '<td>' + response[k].email + '</td>';
                table.appendChild(content);
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function deleteProject(projectName) {
    var url = encodeURI("https://esb.uni-potsdam.de:8243/services/competenceBase/api1/courses/" + projectName);
    var ajax1 = $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            console.log(projectName + " wurde gelöscht");
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
    var localurl = encodeURI("../database/deleteProject.php?project=" + projectName);
    var ajax2 = $.ajax({
        url: localurl,
        type: 'DELETE',
        success: function (response) {
            console.log(response);
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
    $.when(ajax1, ajax2).done(function () {
        location.reload()
    });
}

function reworkProject(projectName) {
    alert(projectName + " wird bearbeitet");
}