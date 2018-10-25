let allTheTags = [];
let projectName = "";

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
                        createProjectinCompbase();
                        location.href="tasks-student.jsp?projectName="+projectName;
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
    let adminPassword = $("#adminPassword").val().trim();
    if (adminPassword === "") {
        adminPassword = "1234";
    }
    //allTheTags = $("#tagsProject").tagsInput('items');
    //allTheTags = $("#tagsProject").val();
    let reguexp = /^[a-zA-Z0-9äüöÄÜÖ\ ]+$/;
    if (!reguexp.test(projectName)) {
        $('#specialChars').show();
        return false;
    }
    if (projectName === "") {           //project has no name, so abort function
        $('#projectIsMissing').show();
        return false;
    }

    if (allTheTags.length !== 5) {
        document.getElementById('tagHelper').className = "alert alert-warning";
    } else {
        document.getElementById('tagHelper').className = "";
    }
    let time = new Date().getTime();
    let project = {
        "name" : projectName,
        "password" : password,
        "active" : true,
        "timecreated" : time,
        "authorEmail": $('#userEmail').text().trim(),
        "adminPassword": adminPassword,
        "phase" : "CourseCreation",
        "tags": allTheTags
    };

   /* let project = {
        "name" : "AJ2c83Lb2x",
        "password" : "vTvaih3mK9",
        "active" : true,
        "timecreated" : 356122661234038,
        "authorEmail" : "7DoIYf4tWV",
        "adminPassword" : "bJFmgTGMdY",
        "phase" : "Execution",
        "tags" : [ "JjwWui3r2a", "J23BLwqlXa", "NOVk1tcaN0", "RTXTACSHLx", "BbMtdrXPi2" ]
    };
*/
    return project;
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
    let addProjectNeo4j = $.ajax({
        url: url,
        contentType: 'application/json',
        activ: true,
        type: 'PUT',
        data: dataString,
        success: function (response) {
            console.log(response);
            // it actually worked, too
            sendGroupPreferences();
        },
        error: function (a, b, c) {
            console.log(a);
            // and also in this case
            return false;
        }
    });
}
