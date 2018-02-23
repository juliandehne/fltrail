/**
 * Created by fides-WHK on 09.01.2018.
 */
$(document).ready(function () {
    var allTheTags= [];
    $(function () {
        $('#Tags').tagsInput({width: '475px',
            onAddTag: function(tag){
                allTheTags.push(tag);
            },
            onRemoveTag: function(tag){
                allTheTags.pop();           //todo: löscht noch nicht den gewählten tag sondern den letzten
            }
        });
    });
    $("#studentFormSubmit").on("click", function () {   //Für die Projekte-Datei
        takesPartInProject($("#user").text(), $("#projectName").val(), allTheTags);
    });
    $("#addCompetenceButton").on("click", function () {   //Für die Projekte-Datei
        addInput("competencies");       //creates a new input-Field with the ID 'competenciesX' where X is number of elements with 'competencies' as ID
    });
    $("#addResearchQuestionButton").on("click", function () {   //Für die Projekte-Datei
        addInput("researchQuestion");   //creates a new input-Field with the ID 'researchQuestionX' where X is number of elements with 'researchQuestion' as ID
    });
});

function addInput(name){        //creates a new input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    var i = document.getElementsByName(name).length;
    var newInput=document.createElement("span");
    newInput.innerHTML = "<input class='form-control' " +
        "type='text' " +
        "name='"+name+"' " +
        "id='"+name+i+"' " +
        "style='max-width:417px;margin-left:14px;padding-top:10px;margin-top:2px;margin-bottom:13px;'>";
    var div = document.getElementById(name);
    div.appendChild(newInput);
}

function takesPartInProject(userID, projectID, allTheTags) {
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/"+userID+"/projects/"+projectID+"/preferences";
    var allTheCompetencies =[];
    var allTheResearchQuestions = [];
    for (i = 0; i < document.getElementsByName("competencies").length; i++) {        //goes through all competencies and adds them to allTheCompetencies
        allTheCompetencies.push(document.getElementsByName("competencies")[i].value);
    }
    for (i = 0; i < document.getElementsByName("researchQuestions").length; i++) {        //goes through all competencies and adds them to allTheResearchQuestions
        allTheResearchQuestions.push(document.getElementsByName("researchQuestions")[i].value);
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
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}