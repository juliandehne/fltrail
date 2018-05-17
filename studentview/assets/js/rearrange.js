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
    getMembers($_GET['projectId']);
    $('#currentGroups').sortable();

});

function reorderGroups(){
    alert('Kuckuk');
}

function printEmptyTable(count){
    var buttonId = "addToGruppe"+count;
    var tableStart = '<table class="table table-striped table-bordered table-list"' +
        ' style="width: 40%;margin-top:' +
        ' 10px;"> <thead id="tableHead"> ' +
        '  <tr>' +
        '    <th class="hidden-xs">Gruppe'+count+'</th>' +
        '    <th class="hidden-xs"><button class="btn btn-info" id="'+buttonId+'">einfügen</button></th>'+
        '  </tr>';
    var tableFinish = '</thead>' + '</table>';
    for (var i=0; i<4; i++){
        tableStart = tableStart + ("<tr><td></td></tr>");
    }
    var tableString = tableStart + tableFinish;
    $("#newGroups").append(tableString);
    $('#'+buttonId).on('click', function(){
        reorderGroups();
    });

}

function printGroupTable(/*student1, student2, student3, student4*/students, count) {
    var innerurl = "../database/getAdresses.php?"/*student1=" + student1 + "&student2=" + student2 + "&student3=" + student3 + "&student4=" + student4*/;
    innerurl = innerurl + "student[]="+students[0];
    for (var i=1; i<students.length; i++){
        innerurl = innerurl + "&student[]="+students[i];
    }

    $.ajax({                    //get email adresses in this ajax.
        /*student1: "" + student1,
        student2: "" + student2,
        student3: "" + student3,
        student4: "" + student4,*/
        students: students,
        url: innerurl,
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (innerData) {
            var buttonId="addToGruppe"+count;
            var tableStart = '<table class="table table-striped table-bordered table-list"' +
                ' style="width: 40%;margin-top:' +
                ' 10px;"> <thead id="tableHead"> ' +
                '  <tr align="center">' +
                '    <th class="hidden-xs">Gruppe'+count+'</th>' +
                '    <th class="hidden-xs">auswählen</th>'+
                '    <th class="hidden-xs"><button class="btn btn-info" id="'+buttonId+'">einfügen</button></th>'+
                '  </tr>';
            var tableFinish = '</thead>' + '</table>';
            for (var k2 = 0; k2 < innerData.length; k2++) {
                if (innerData[k2].name === student1) {                          //todo: jeder Student steht in "students". Ersetze alle student[Zahl] auch in PHP.
                    tableStart = tableStart + ("<tr><td>" + student1 + "</td>" +
                        "<td>" +
                        "<input class='styled' name='tag' type='checkbox' id='"+student1+"'>" +
                        "</td>" +
                        "</tr>");
                } else if (innerData[k2].name === student2) {
                    tableStart = tableStart + ("<tr><td>" + student2 + "</td>"+
                        "<td>" +
                        "<input class='styled' name='tag' type='checkbox' id='"+student2+">" +
                        "</td>"+
                        "</tr>");
                } else if (innerData[k2].name === student3) {
                    tableStart = tableStart + ("<tr><td>" + student3 + "</td>" +
                        "<td>" +
                        "<input class='styled' name='tag' type='checkbox' id='"+student3+">" +
                        "</td>" +
                        "</tr>");
                } else if (innerData[k2].name === student4 && (student4 != null)) {
                    tableStart = tableStart + ("<tr><td>" + student4 + "</td>"+
                        "<td>" +
                        "<input class='styled' name='tag' type='checkbox' id='"+student4+">" +
                        "</td>" +
                        "</tr>");
                }
            }
            var tableString = tableStart + tableFinish;
            $("#currentGroups").append(tableString);
            $('#'+buttonId).on('click', function(){
                reorderGroups();
            });
        }
    });
    return innerurl;
}

function getMembers(project) {        //gets all Members in the chosen Project user is a part of with email adresses

    $("#newGroups").empty();
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
                /*var student1 = data.groups[i].users[0];
                var student2 = data.groups[i].users[1];
                var student3 = data.groups[i].users[2];
                var student4 = data.groups[i].users[3];*/
                printGroupTable(/*student1, student2, student3, student4*/students, i);
            }
            printEmptyTable(data.groups.length);
        },
        error: function(data) {

        }

    });
}