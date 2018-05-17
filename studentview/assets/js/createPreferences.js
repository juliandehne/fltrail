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

function getProjectByToken() {
    return $('#projectNameHidden').text().trim();
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
                    + "<input id='tag" + i + "' " + " class='styled' " + "name='tag'" + "type='checkbox' " + ">"
                    + "<label for='tag" + i + "' " + ">" + tagList[i] + "</label>"
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
    var time = Date.now();

    function writeTime() {
        time = Date.now() - time;       //Timedifference from beginning to end
        time = Math.floor(time / 1000);   //time in seconds.
        var lernziele = encodeURI($('#competencies0').val().trim());
        var forschungsfrage = encodeURI($("#researchQuestion0").val().trim());
        var url = "../database/putTimetrack.php?projectID=" + projectID + "&lernziele=" + lernziele + "&forschungsfrage=" + forschungsfrage + "&dauer=" + time;
        $.ajax({
            url: url,
            async: false,
            //contentType: 'application/json',
            success: function (response) {
                console.log("this action lasted " + time + " seconds");
            },
            error: function (a, b, c) {
                console.log(a);
            }
        });
    }

    /*    setTimeout(function(){
     writeTime();
     location.href="projects.php?token="+getUserTokenFromUrl()+"&timeout=true";
     }, 10000);*/


    var userID = $("#user").text().trim();
    var projectID = $("#projectNameHidden").text().trim();

    blockScreen();

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
        if ($("#tag" + i).prop("checked")) {
            var tagValue = $("label[for=tag" + i + "]")[0].textContent;
            allTheCompetencies.push("Die Studierenden interessieren sich für " + tagValue);
        }
    }
    if (allTheTags.length > 2) {
        //alert('Sie haben zu viele Tags ausgewählt');
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        deblockScreen();
        time = 0; // das macht keinen Sinn, oder?
        return false;
    }
    if (allTheTags.length < 2) {
        //alert('Sie haben zu wenig Tags ausgewählt');
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        deblockScreen();
        time = 0; // das macht keinen Sinn oder?
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
        projectID: projectID,
        type: 'PUT',
        Accept: "text/plain; charset=utf-8",
        contentType: "application/json",
        data: dataString,
        success: function (response) {
            console.log(response);
            deblockScreen();
            writeTime();
            location.href = "projects.php?token=" +getUserTokenFromUrl() + "&timeout=false";
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}