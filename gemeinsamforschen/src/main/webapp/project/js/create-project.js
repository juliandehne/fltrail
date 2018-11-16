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
            error: function (a) {
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
            onRemoveTag: function () {
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
        "timecreated" : time,
        "authorEmail": $('#userEmail').text().trim(),
        "phase" : "CourseCreation",
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
            sendGroupPreferences();
        },
        error: function (a) {
            console.log(a);
            // and also in this case
            return false;
        }
    });
}
