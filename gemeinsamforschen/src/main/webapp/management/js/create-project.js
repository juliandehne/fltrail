var allTheTags = [];
var projectToken = "";

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
    errorMessages()
    // getting the data from the form fields
    var project = getProjectValues();
    // create the project
    if (project) {
        // create the project in local db
        var localurl = "../../gemeinsamforschen/rest/project/create";
        $.ajax({                        //check local DB for existence of projectName
            url: localurl,
            contentType: 'application/json',
            activ: activ,
            type: 'PUT',
            data: JSON.stringify(project),
            success: function (response) {
                if (response === "project exists") {
                    $('#projectNameExists').show();
                } else {
                    if (allTheTags.length !== 5) {
                        $('#exactNumberOfTags').show();
                    } else {
                        // it actually worked
                        projectToken = response;
                        createProjectinCompbase(project.id);

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
        var activ = "1";
        createNewProject(allTheTags, activ);
    });
}

// it returns false and shows errors if input is not valid
function getProjectValues() {
    var projectName = $("#nameProject").val().trim();
    var password = $("#passwordProject").val().trim();
    var adminPassword = $("#adminPassword").val().trim();
    if (adminPassword == "") {
        adminPassword = "1234";
    }
    var reguexp = /^[a-zA-Z0-9äüöÄÜÖ\ ]+$/;
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

    // TODO find out author
    var project = {
        "id": projectName,
        "password": password,
        "active": true,
        "timecreated": null,
        "author": getUserTokenFromUrl(),
        "adminPassword": adminPassword,
        "token": "",
        "phase": "GroupFormation",
        "tags": allTheTags
    }
    return project;
}

// creates project name in compbase where it is needed for learning goal oriented matching
function createProjectinCompbase(projectName) {
    var url = compbaseUrl + "/api1/courses/" + $("#nameProject").val();

    var obj = {
        "courseId": projectName,
        "printableName": projectName,
        "competences": allTheTags
    };
    var dataString = JSON.stringify(obj);
    var addProjectNeo4j = $.ajax({
        url: url,
        contentType: 'application/json',
        activ: true,
        type: 'PUT',
        data: dataString,
        success: function (response) {
            console.log(response);
            // it actually worked, too
            document.location.href = "edit-project.jsp?token="+getUserTokenFromUrl()+"&projectToken="+projectToken;
        },
        error: function (a, b, c) {
            console.log(a);
            // and also in this case
            return false;
        }
    });
}
