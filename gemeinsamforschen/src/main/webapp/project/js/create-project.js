let allTheTags = [];
let projectName = "";
var gfm = "";

/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    // hide the different error messages
    errorMessages();
    // add the tagsinput lib
    initTagsInput(allTheTags);
    // add handle to the submit button
    initSendButton(allTheTags);
});

// function that creates the project in the db
function createNewProject(allTheTags, activ) {
    // again hiding the error messages
    errorMessages();
    // getting the data from the form fields
    let project = getProjectValues();
    let projectName = $("#nameProject").val().trim();
    // create the project
    if (project) {
        // create the project in local db
        let localurl = "../rest/project/create";
        $.ajax({                        //check local DB for existence of projectName
            url: localurl,
            projectName: projectName,
            contentType: 'application/json',
            activ: activ,
            type: 'POST',
            data: JSON.stringify(project),
            success: function (response) {
                if (response === "Project already exists") {
                    $('#projectNameExists').show();
                } else {
                    if (allTheTags.length !== 5) {
                        $('#exactNumberOfTags').show();
                    } else {
                        sendGroupPreferences();
                    }
                }
            },
            error: function (a, b, c) {
                console.log(a);
                return true;
            }
        });
    }
}

function errorMessages() {
    $("#nameProject").focus();
    $('#projectNameExists').hide();
    $('#projectIsMissing').hide();
    $('#exactNumberOfTags').hide();
    $('#specialChars').hide();
    $('#projectDescriptionMissing').hide();
    document.getElementById('tagHelper').className = "";
}

function initTagsInput(allTheTags) {
    $(function () {
        $('#tagsProject').tagsInput({
            width: '475px',
            onAddTag: function (tag) {
                allTheTags.push(tag);
            },
            onRemoveTag: function (tag) {
                allTheTags.pop();           //todo: löscht noch nicht den gewählten tag sondern den letzten
            }
        });
    });
}

function initSendButton(allTheTags) {
    $('#sendProject').on('click', function () {
        let activ = "1";
        createNewProject(allTheTags, activ);
    });
}

// it returns false and shows errors if input is not valid
function getProjectValues() {
    projectName = $("#nameProject").val().trim();
    let password = $("#passwordProject").val().trim();
    //allTheTags = $("#tagsProject").tagsInput('items');
    //allTheTags = $("#tagsProject").val();
    let reguexp = /^[a-zA-Z0-9äüöÄÜÖ]+$/;
    if (!reguexp.test(projectName)) {
        $('#specialChars').show();
        return false;
    }
    if (projectName === "") {           //project has no name, so abort function
        $('#projectIsMissing').show();
        return false;
    }
    let description = $('#projectDescription').val();
    if (description === ""){
        $('#projectDescriptionMissing').show();
        return false;
    }
    if (allTheTags.length !== 5) {
        document.getElementById('tagHelper').className = "alert alert-warning";
    } else {
        document.getElementById('tagHelper').className = "";
    }
    let time = new Date().getTime();

    return {
        "name" : projectName,
        "password" : password,
        "description": description,
        "active" : true,
        "phase" : "GroupFormation",
        "timecreated" : time,
        "authorEmail": $('#userEmail').text().trim(),
        "tags": allTheTags
    };
}

// creates project name in compbase where it is needed for learning goal oriented matching
function createProjectinCompbase() {
    let url = compbaseUrl + "/api1/courses/" + $("#nameProject").val();

    let obj = {
        "courseId": projectName,
        "printableName": projectName,
        "competences": allTheTags
    };
    let dataString = JSON.stringify(obj);
    $.ajax({
        url: url,
        contentType: 'application/json',
        activ: true,
        type: 'PUT',
        data: dataString,
        success: function (response) {
            console.log(response);
            // it actually worked, too
            document.location.href = "tasks-docent.jsp?projectName="+projectName;
        },
        error: function (a) {
            console.log(a);
            // and also in this case
            return false;
        }
    });
}


function sendGroupPreferences() {
    gfm = $('input[name=gfm]:checked').val();
    if (gfm == "Basierend auf Präferenzen") {
        // TODO implement preference based group selection
        gfm = "UserProfilStrategy";
    } else if (gfm == "per Hand") {
        gfm = "Manual";
    } else if (gfm == "Basierend auf Lernzielen") {
        gfm = "LearningGoalStrategy";
    } else if(gfm == "Keine Gruppen") {
        gfm = "SingleUser";
    }

    var localurl = "../../gemeinsamforschen/rest/group/gfm/create/projects/" + projectName;
    $.ajax({
        gfm: gfm,
        url: localurl,
        contentType: 'application/json',
        type: 'POST',
        data: gfm,
        success: function (a, b, c) {
            if (gfm == "LearningGoalStrategy") {
                createProjectinCompbase();
            }
            document.location.href = "tasks-docent.jsp?projectName="+projectName;
            return true;
        },
        error: function (a, b, c) {
            return false;
        }
    });
}
