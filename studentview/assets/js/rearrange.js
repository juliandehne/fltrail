/**
 * Created by fides-WHK on 17.05.2018.
 */

$(document).ready(function () {
    var parts = window.location.search.substr(1).split("&");
    var $_GET = {};
    for (var i = 0; i < parts.length; i++) {
        var temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    initialGroupPrint($_GET['projectId']);
    printEmptyTable();
    var maxGroup = document.getElementById('Groups').childElementCount;
    for (var j = 0; j < maxGroup; j++) {
        $('#addToGruppe' + j).on('click', {group: 'Gruppe' + j, maxGroup: maxGroup}, reorderGroups);
    }
    $('#saveArrangement').on('click',{projectId: $_GET['projectId']}, saveArrangement);
    $('#automaticArrangement').on('click',{projectId: $_GET['projectId']}, automaticArrangement);
});

function initialGroupPrint(projectId){
    i=0;
    var pStudents = $('#students'+i);
    if (pStudents.length !== 0){
        while (pStudents.length !== 0){
            var students=JSON.parse(pStudents.text());
            printGroupTable(students, i);
            i++;
            pStudents = $('#students'+i);
        }
    }else
        automaticArrangement(projectId);
}

function saveArrangement(event){
    var projectId = event.data.projectId;
    var maxGroups = document.getElementById('Groups').childElementCount;
    var groupSetup = JSON.stringify(collectStudents(maxGroups, {}));
    var url = "../database/createGroups.php?projectId=" + projectId + "&token=" + getUserTokenFromUrl();
    return $.ajax({
        url: url,
        type: 'POST',
        data: groupSetup,
        success: function (response) {
            console.log("groups were written to DB");
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function automaticArrangement(event){
    $("#Groups").empty();
    var project;
    if (event['data']){
        project = event.data.projectId;
    }else project = event;
    var url = compbaseUrl + "/api2/groups/" + project;      //this API is used, since fleckenroller has security issues
                                                            // with CORS
                                                            // and stuff
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",                               //{groups: [id, users:[]] }
        success: function (data) {
            for (var i = 0; i < data.groups.length; i++) {
                var students = data.groups[i];
                printGroupTable(students.users, i);
            }
            for (var j = 0; j < data.groups.length; j++) {
                $('#addToGruppe' + j).on('click', {group: 'Gruppe' + j, maxGroup: data.groups.length}, reorderGroups);
            }
        },
        error: function (data) {
        }
    });
    var obj = {data: {projectId: event}};       //for some unexplainable reason event is not an obj anymore down here but the projectId
    saveArrangement(obj);
}

function collectStudents(numberOfGroups, opts){
    var students = [];
    for (var i = 0; i < numberOfGroups; i++) {                   //gets all checked students (relocatedStudents) in all tables and deletes them
        $('#tableGruppe' + i + ' tr').each(function () {
            if (this.id !== null)
                if (opts['checked']===true){
                    if (document.getElementById('check' + this.id).checked) {
                        students.push(this.id);
                        $(this).fadeOut(function () {
                            $(this).remove();
                        });
                    }
                }else{
                    students.push({student: this.id, group: 'Gruppe'+i});
                }
        });
    }
    return students;
}

function reorderGroups(event) {
    var group = event.data.group;
    var maxGroup = event.data.maxGroup;
    var relocatedStudents = collectStudents(maxGroup, {checked: true});
    for (var student = 0; student < relocatedStudents.length; student++) {
        var studentCell = "<tr id='" + relocatedStudents[student] + "'><td>" + relocatedStudents[student] + "</td>" +
            "<td>" +
            "<input class='styled' name='tag' type='checkbox' id='check" + relocatedStudents[student] + "'>" +
            "</td>" +
            "</tr>";
        $('#table' + group).append(studentCell);
    }

}

function printGroupTable(students, count) {
    var buttonId = "addToGruppe" + count;
    var tableStart = '<table class="table table-striped table-bordered table-list"' +
        ' style="width: 40%;margin-top:' +
        ' 10px;"> <thead> ' +
        '  <tr align="center">' +
        '    <th class="hidden-xs">Gruppe' + count + '</th>' +
        '    <th class="hidden-xs">auswählen</th>' +
        '    <th class="hidden-xs"><button class="btn btn-info" id="' + buttonId + '">einfügen</button></th>' +
        '  </tr>' +
        '</thead>' +
        '<tbody id="tableGruppe' + count + '">';
    var tableFinish = '</tbody>' + '</table>';
    for (var k2 = 0; k2 < students.length; k2++) {
        tableStart = tableStart + ("<tr id='" + students[k2] + "'><td>" + students[k2] + "</td>" +
            "<td>" +
            "<input class='styled' name='tag' type='checkbox' id='check" + students[k2] + "'>" +
            "</td>" +
            "</tr>");
    }
    var tableString = tableStart + tableFinish;
    $("#Groups").append(tableString);
}

function printEmptyTable() {
    var count = document.getElementById('Groups').childElementCount;
    var buttonId = "addToGruppe" + count;
    var tableStart = '<table class="table table-striped table-bordered table-list"' +
        ' style="width: 40%;margin-top:' +
        ' 10px;"> <thead> ' +
        '  <tr>' +
        '    <th class="hidden-xs">Gruppe' + count + '</th>' +
        '    <th class="hidden-xs"><button class="btn btn-info" id="' + buttonId + '">einfügen</button></th>' +
        '  </tr>' +
        '</thead>' +
        '<tbody id="tableGruppe' + count + '">';
    var tableFinish = '</tbody>' + '</table>';

    var tableString = tableStart + tableFinish;
    $("#Groups").append(tableString);
}
