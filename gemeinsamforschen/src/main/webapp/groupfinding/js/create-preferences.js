/**
 * Created by fides-WHK on 15.03.2018.
 */

$(document).ready(function () {
    checkCompBase(function(isCompBaseOnline){
        if (!isCompBaseOnline){
            $('#competenciesFieldSet').hide();
            $('#researchQuestionFieldSet').hide();
        }
    });

    $('#projectWrongPassword').hide();
    printTags();
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


function addInput(name) {        //creates a new input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    let i = document.getElementsByName(name).length;
    let div = document.getElementById(name);
    let newInput = document.createElement("span");
    newInput.innerHTML = "<input class='form-control' " +
        "type='text' " +
        "name='" + name + "' " +
        "id='" + name + i + "'>";
    div.appendChild(newInput);
}

function deletInput(name) {        //deletes latest input-Field with the ID 'nameX' where X is number of elements with 'name' as ID
    let i = document.getElementsByName(name).length;
    if (i > 1) {
        let lastEntry = document.getElementById(name + "" + (i - 1));
        lastEntry.parentNode.removeChild(lastEntry);
    }
}

/**
 * selects the tags from the db and prints the seleciton
 */
function printTags() {
    let url = "../../gemeinsamforschen/rest/project/tags/" + getProjectName();
    $.ajax({
        url: url,
        Accept: "application/json",
        contentType: "text/plain",
        success: function (response) {
            let tagList = response;
            for (i = 0; i < tagList.length; i++) {
                let newInput = document.createElement("label");
                newInput.innerHTML =
                    "<div class='checkbox checkbox-primary' >"
                    + "<input id='tag" + i + "' " + " class='styled' " + "name='tag'" + " type='checkbox' " + ">"
                    + "<label for='tag" + i + "' " + ">" + tagList[i] + "</label>"
                    + "</div>";
                let div = document.getElementById('tags');
                div.appendChild(newInput);
            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}


// he is added in compbase to the project
function takesPartInProject() {
    $('.cover').each(function(){
        $(this).fadeIn(100);
    });
    document.getElementById('loader').className = "loader";

    let userEmail = getUserEmail();
    let projectName = getProjectName();
    loginProject(projectName);
    checkCompBase(function(isCompBaseOnline){
        if(isCompBaseOnline){

            let allTheTags = [];
            let allTheCompetencies = [];
            let allTheResearchQuestions = [];
            for (let i = 0; i < document.getElementsByName("competencies").length; i++) {        //goes through all competencies and adds them to allTheCompetencies
                allTheCompetencies.push(document.getElementsByName("competencies")[i].value);
            }
            for (let i = 0; i < document.getElementsByName("researchQuestions").length; i++) {        //goes through all competencies and adds them to allTheResearchQuestions
                allTheResearchQuestions.push(document.getElementsByName("researchQuestions")[i].value);
            }
            for (let i = 0; i < document.getElementsByName("tag").length; i++) {   //goes through all tags and adds them to allTheTags
                if (document.getElementById("tag" + i).checked) {
                    allTheTags.push(document.getElementById("tag" + i).value);
                }
                if ($("#tag" + i).prop("checked"))
                    allTheCompetencies.push("Die Studierenden interessieren sich für " + $("#tag" + i).val());     //todo: Die Tags werden hinter der Schnittstelle noch nicht verwertet, daher diese schnelle Lösung
            }
            if (allTheTags.length > 2) {
                //alert('Sie haben zu viele Tags ausgewählt');
                $(".alert").css('background-color', 'lightcoral');
                allTheTags = [];
                document.getElementById('loader').className = "loader-inactive";
                return false;
            }
            if (allTheTags.length < 2) {
                //alert('Sie haben zu wenig Tags ausgewählt');
                $(".alert").css('background-color', 'lightcoral');
                allTheTags = [];
                document.getElementById('loader').className = "loader-inactive";
                return false;
            }
            let data = {                                            //JSON object 'data' collects everything to send
                "competences": allTheCompetencies,
                "researchQuestions": allTheResearchQuestions,
                "tagsSelected": allTheTags
            };
            let dataString = JSON.stringify(data);                     //to send correctly, data needs to be stringified
            let url = compbaseUrl + "/api2/user/" + userEmail + "/projects/" + projectName + "/preferences";
            $.ajax({
                url: url,
                type: 'PUT',
                Accept: "text/plain; charset=utf-8",
                contentType: "application/json",
                data: dataString,
                success: function (response) {
                    console.log(response);
                    document.getElementById('loader').className = "loader-inactive";
                    location.href = "../project/courses-student.jsp";
                },
                error: function (a, b, c) {
                    console.log(a);
                }
            });
        }else{
            document.getElementById('loader').className = "loader-inactive";
            location.href = "../project/courses-student.jsp";
        }
    });
}

function loginProject(projectName) {
    let password = $('#projectPassword').val();
    let url = "../../gemeinsamforschen/rest/project/login/"+projectName+"?password="+password;
    if (projectName === "") {
        return false;
    } else {
        $.ajax({
            url: url,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response === "wrong password") {            //if response !== project missing and not wrong password, its the projectName
                    $('#projectWrongPassword').show();
                }
            },
            error: function (a) {
                console.log(a);
            }
        });
    }
}

function checkCompBase(callback){
    $.ajax({
        url: '../rest/system/health',
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {
            callback(response.compBaseOnline);
        },
        error: function (a) {
            console.log(a);
        }
    });
}