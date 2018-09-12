/**
 * Created by fides-WHK on 15.03.2018.
 */


$(document).ready(function () {
var projectName = getProjectByToken();
    getTags(projectName);
    $("#competencies0").focus();
    $("#studentFormSubmit").on("click", function () {
        takesPartInProject();
    });
    $("#addCompetenceButton").on("click", function () {
        addInput("competencies");       //creates a new input-Field with the ID 'competenciesX' where X is number of elements with 'competencies' as ID
    });
    $("#subtractCompetenceButton").on("click", function () {
        deletInput("competencies");     //deletes latest input-Field with the ID 'competenciesX' where X is number of elements with 'competencies' as ID
    });
    $("#addResearchQuestionButton").on("click", function () {
        addInput("researchQuestion");   //creates a new input-Field with the ID 'researchQuestionX' where X is number of elements with 'researchQuestion' as ID
    });
    $("#subtractCResearchQuestionButton").on("click", function () {
        deletInput("researchQuestion");    //deletes the latest input-Field with ID 'researchQuestionX' where X is number of elements with 'researchQuestion' as ID
    });

});

function getProjectByToken(){
    return $('#projectName').text().trim();
}

function addInput(name) {        //creates a new input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    var i = document.getElementsByName(name).length;
    var newInput = document.createElement("span");
    newInput.innerHTML = "<input class='form-control' " +
        "type='text' " +
        "name='" + name + "' " +
        "id='" + name + i + "' " +
        "style='max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;'>";
    var div = document.getElementById(name);
    div.appendChild(newInput);
}

function deletInput(name) {        //deletes latest input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    var i = document.getElementsByName(name).length;
    if (i > 1) {
        var lastEntry = document.getElementById(name + "" + (i - 1));
        lastEntry.parentNode.removeChild(lastEntry);
    }
}

function getTags(projectName) {
    var url = "../database/getTags.php?project=" + projectName;
    $.ajax({
        url: url,
        Accept: "text/plain; charset=utf-8",
        contentType: "text/plain",
        success: function (response) {
            response = JSON.parse(response);
            var tagList = [];
            var i = 0;
            for (i = 0; i < response.length; i++)
                tagList.push(response[i].tag);
            for (i = 0; i < tagList.length; i++) {
                var newInput = document.createElement("label");
                newInput.innerHTML =
                    "<div class='checkbox checkbox-primary' >"
                        + "<input id='tag" + i + "' "  + " class='styled' " + "name='tag'" + "type='checkbox' " + ">"
                        + "<label for='tag" + i + "' "+ ">" + tagList[i] + "</label>"
                    + "</div>";
                var div = document.getElementById('tags');
                div.appendChild(newInput);
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function takesPartInProject() {
    var userID = $("#user").text().trim();
    var projectID = $("#projectName").text().trim();
    document.getElementById('loader').className = "loader";
    document.getElementById('wrapper').className = "wrapper-inactive";

    var allTheTags = [];
    var allTheCompetencies = [];
    var allTheResearchQuestions = [];
    for (i = 0; i < document.getElementsByName("competencies").length; i++) {        //goes through all competencies and adds them to allTheCompetencies
        allTheCompetencies.push(document.getElementsByName("competencies")[i].value);
    }
    for (i = 0; i < document.getElementsByName("researchQuestions").length; i++) {        //goes through all competencies and adds them to allTheResearchQuestions
        allTheResearchQuestions.push(document.getElementsByName("researchQuestions")[i].value);
    }
    for (i = 0; i < document.getElementsByName("tag").length; i++) {        //goes through all tags and adds them to allTheTags
        if (document.getElementById("tag" + i).checked) {
            allTheTags.push(document.getElementById("tag" + i).value);
        }
        if ($("#tag"+i).prop("checked"))
            allTheCompetencies.push("Die Studierenden interessieren sich für " + $("#tag" + i).val());     //todo: Die Tags werden hinter der Schnittstelle noch nicht verwertet, daher diese schnelle Lösung
    }
    if (allTheTags.length > 2) {
        //alert('Sie haben zu viele Tags ausgewählt');
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        document.getElementById('loader').className = "loader-inactive";
        document.getElementById('wrapper').className = "wrapper";
        return false;
    }
    if (allTheTags.length < 2) {
        //alert('Sie haben zu wenig Tags ausgewählt');
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        document.getElementById('loader').className = "loader-inactive";
        document.getElementById('wrapper').className = "wrapper";
        return false;
    }
    var data = {                                            //JSON object 'data' collects everything to send
        "competences": allTheCompetencies,
        "researchQuestions": allTheResearchQuestions,
        "tagsSelected": allTheTags
    };
    var dataString = JSON.stringify(data);                     //to send correctly, data needs to be stringified
    var url = compbaseUrl + "/api2/user/" + userID + "/projects/" + projectID + "/preferences";
    $.ajax({
        url: url,
        type: 'PUT',
        Accept: "text/plain; charset=utf-8",
        contentType: "application/json",
        data: dataString,
        success: function (response) {
            console.log(response);
            document.getElementById('loader').className = "loader-inactive";
            document.getElementById('wrapper').className = "wrapper";
            var parts = window.location.search.substr(1).split("&");
            var $_GET = {};
            for (var i = 0; i < parts.length; i++) {
                var temp = parts[i].split("=");
                $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
            }
            location.href = "projects.php?token=" + $_GET['token'];
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}