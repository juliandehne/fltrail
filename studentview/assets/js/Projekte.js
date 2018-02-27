/**
 * Created by fides-WHK on 09.01.2018.
 */
$(document).ready(function () {
    $("#toggleArea").toggle();

    $("#studentFormSubmit").on("click", function () {
        takesPartInProject($("#user").text(), $("#projectName").val());
    });
    $("#addCompetenceButton").on("click", function () {
        addInput("competencies");       //creates a new input-Field with the ID 'competenciesX' where X is number of elements with 'competencies' as ID
    });
    $("#subtractCompetenceButton").on("click",function() {
        deletInput("competencies");     //deletes latest input-Field with the ID 'competenciesX' where X is number of elements with 'competencies' as ID
    });
    $("#addResearchQuestionButton").on("click", function () {
        addInput("researchQuestion");   //creates a new input-Field with the ID 'researchQuestionX' where X is number of elements with 'researchQuestion' as ID
    });
    $("#subtractCResearchQuestionButton").on("click",function() {
        deletInput("researchQuestion");    //deletes the latest input-Field with ID 'researchQuestionX' where X is number of elements with 'researchQuestion' as ID
    });
    $("#projectName").keypress(function(e){
        if (e.which == 13){
            getTags();
            document.getElementById("projectPassword").focus();
        }
    });
    $("#projectPassword").keypress(function(e){
        if (e.which == 13){
            document.getElementById("competencies0").focus();
        }
    });
    $("#seeProject").on('click', function (){
        seeProject($('#projectName').val());
    });
});

function seeProject(projectName){
    var url = "getProjects.php?project="+projectName;
    if (projectName===""){
        return false;
    }else{
        $.ajax({
            url: url,
            type: 'GET',
            Accept: "text/plain; charset=utf-8",
            contentType: "application/json",
            success: function (response) {
                console.log(JSON.parse(response).project);
                console.log(JSON.parse(response).password);
            },
            error: function (a, b, c) {
                console.log(a);
            }
        });
    }
    //$("#toggleArea").toggle();
    //$("#seeProject").hide();
}

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

function deletInput(name){        //deletes latest input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    var i = document.getElementsByName(name).length;
    if (i>1){
        var lastEntry = document.getElementById(name+""+(i-1));
        lastEntry.parentNode.removeChild(lastEntry);
    }
}

function getTags(){
    var i=0;
    var tagList=["eins","zwei","drei","vier","fünf"];
    for (i=0 ; i< tagList.length; i++){
        var newInput=document.createElement("label");
        newInput.innerHTML = tagList[i]+"<input style='margin-right:10px;' " +
            "type='checkbox' " +
            "name='tag' " +
            "id='tag"+i+"' " +
            "value="+tagList[i]+">";
        var div = document.getElementById('tags');
        div.appendChild(newInput);
    }
}

function takesPartInProject(userID, projectID) {
    document.getElementById('loader').className="loader";
    document.getElementById('wrapper').className="wrapper inactive";

    var allTheTags=[];
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/"+userID+"/projects/"+projectID+"/preferences";
    var allTheCompetencies =[];
    var allTheResearchQuestions = [];
    for (i = 0; i < document.getElementsByName("competencies").length; i++) {        //goes through all competencies and adds them to allTheCompetencies
        allTheCompetencies.push(document.getElementsByName("competencies")[i].value);
    }
    for (i = 0; i < document.getElementsByName("researchQuestions").length; i++) {        //goes through all competencies and adds them to allTheResearchQuestions
        allTheResearchQuestions.push(document.getElementsByName("researchQuestions")[i].value);
    }
    for (i = 0; i < document.getElementsByName("tag").length; i++) {        //goes through all tags and adds them to allTheTags
        if (document.getElementById("tag"+i).checked === true){
            allTheTags.push(document.getElementById("tag"+i).value);
        }
        if (allTheTags.length > 2){
            alert('Sie haben zu viele Tags ausgewählt');
            allTheTags=[];
            document.getElementById('loader').className="loader inactive";
            document.getElementById('wrapper').className="wrapper";
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
            document.getElementById('loader').className="loader inactive";
            document.getElementById('wrapper').className="wrapper";

        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}