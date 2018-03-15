/**
 * Created by fides-WHK on 09.01.2018.
 */
$(document).ready(function () {
    $("#projectWrongPassword").hide();
    $("#projectIsMissing").hide();
    $("#toggleArea").toggle();

    $("#studentFormSubmit").on("click", function () {
        takesPartInProject($("#user").text(), $("#projectName").val());
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
    $("#projectName").keypress(function (e) {
        if (e.which == 13) {
            getTags();
            document.getElementById("projectPassword").focus();
        }
    });
    $("#projectPassword").keypress(function (e) {
        if (e.which == 13) {
            seeProject($('#projectName').val());
            document.getElementById("competencies0").focus();
        }
    });
    $("#seeProject").on('click', function () {
        seeProject($('#projectName').val());
    });
});

function seeProject(projectName) {
    var url = "../database/getProjects.php?project=" + projectName + "&password=" + document.getElementById('projectPassword').value;
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: url,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response === "project missing") {
                    $("#projectIsMissing").show();
                    getTags(projectName);
                } else {
                    if (response === "correct password") {
                        $("#projectIsMissing").hide();
                        getTags(projectName);
                        $("#toggleArea").toggle();
                        $("#seeProject").hide();
                        $('#projectWrongPassword').hide();
                    } else {
                        $("#projectIsMissing").hide();
                        $('#projectWrongPassword').show();
                    }
                }
            },
            error: function (a, b, c) {
                console.log(a);
            }
        });
    }
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
                newInput.innerHTML = tagList[i] + "<input style='margin-right:10px;' " +
                    "type='checkbox' " +
                    "name='tag' " +
                    "id='tag" + i + "' " +
                    "value=" + tagList[i] + ">";
                var div = document.getElementById('tags');
                div.appendChild(newInput);
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function takesPartInProject(userID, projectID) {
    document.getElementById('loader').className = "loader";
    document.getElementById('wrapper').className = "wrapper inactive";

    var allTheTags = [];
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/" + userID + "/projects/" + projectID + "/preferences";
    var allTheCompetencies = [];
    var allTheResearchQuestions = [];
    for (i = 0; i < document.getElementsByName("competencies").length; i++) {        //goes through all competencies and adds them to allTheCompetencies
        allTheCompetencies.push(document.getElementsByName("competencies")[i].value);
    }
    for (i = 0; i < document.getElementsByName("researchQuestions").length; i++) {        //goes through all competencies and adds them to allTheResearchQuestions
        allTheResearchQuestions.push(document.getElementsByName("researchQuestions")[i].value);
    }
    for (i = 0; i < document.getElementsByName("tag").length; i++) {        //goes through all tags and adds them to allTheTags
        if (document.getElementById("tag" + i).checked === true) {
            allTheTags.push(document.getElementById("tag" + i).value);
        }
        allTheCompetencies.push("Die Studierenden interessieren sich für " + document.getElementById("tag" + i).value);     //todo: Die Tags werden hinter der Schnittstelle noch nicht verwertet, daher diese schnelle Lösung
        if (allTheTags.length > 2) {
            alert('Sie haben zu viele Tags ausgewählt');
            allTheTags = [];
            document.getElementById('loader').className = "loader inactive";
            document.getElementById('wrapper').className = "wrapper";
            return false;
        }
        if (allTheTags.length < 2) {
            alert('Sie haben zu wenig Tags ausgewählt');
            allTheTags = [];
            document.getElementById('loader').className = "loader inactive";
            document.getElementById('wrapper').className = "wrapper";
            return false;
        }
    }
    var data = {                                            //JSON object 'data' collects everything to send
        "competences": allTheCompetencies,
        "researchQuestions": allTheResearchQuestions,
        "tagsSelected": allTheTags
    };
    var dataString = JSON.stringify(data);                     //to send correctly, data needs to be stringified
    $.ajax({
        url: url,
        type: 'PUT',
        Accept: "text/plain; charset=utf-8",
        contentType: "application/json",
        data: dataString,
        success: function (response) {
            console.log(response);
            document.getElementById('loader').className = "loader inactive";
            document.getElementById('wrapper').className = "wrapper";

        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}