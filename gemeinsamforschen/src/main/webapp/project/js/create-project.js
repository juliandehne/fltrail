let allTheTags = [];
let projectName = "";
let gfm = "";
let groupSize = 3;
/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    checkCompBase(function (isCompBaseOnline, isGroupAlOnline) {
        if (!isCompBaseOnline) {
            $('#lgLI').hide();
        }
        if (!isGroupAlOnline) {
            $('#bpLI').hide();
        }
    });

    // hide the different error messages
    errorMessages();
    // add the tagsinput lib
    initTagsInput(allTheTags);
    // add handle to the submit button
    initSendButton(allTheTags);
    $('#tagsProject').importTags('');

    let userCount = $('#userCount');
    userCount.change(function () {
        groupSize = parseInt(userCount.val());
        let minSize = (groupSize * (groupSize + 1)) - 2 * groupSize;
        $('#groupSize').html(minSize);
    });

    getAnnotationCategories(function (response) {
        let tmplObject = [];
        for (let b in response) {
            if (response.hasOwnProperty(b)) {
                tmplObject.push({
                    categoryName: response[b][0] + response[b].substring(1).toLowerCase(),
                    category: response[b]
                });
            }
        }
        $('#categoryTemplate').tmpl(tmplObject).appendTo('#categoryList');

    });


});

// function that creates the project in the db
function createNewProject(allTheTags) {
    let gfm = $('input[name=gfm]:checked').val();
    if (typeof  gfm === "undefined") {
        $('#groupMechanismMissing').show();
        return false;
    }
    loaderStart();
    // again hiding the error messages
    errorMessages();
    // getting the data from the form fields
    let project = getProjectValues();
    // create the project
    if (allTheTags.length !== 5 && gfm === "Basierend auf Lernzielen") {
        loaderStop();
        document.getElementById('tagHelper').className = "alert alert-warning";
        $('#exactNumberOfTags').show();
    } else {
        if (project) {
            // create the project in local db
            let localurl = "../rest/project/create?groupSize=" + groupSize;

            $.ajax({                        //check local DB for existence of projectName
                url: localurl,
                contentType: 'application/json',
                type: 'POST',
                data: JSON.stringify(project),
                success: function () {
                    sendGroupPreferences();
                    loaderStop();
                },
                error: function (xhr) {
                    if (xhr.status === 409) {
                        $('#projectNameExists').show();
                    } else {
                        console.log("Error: " + xhr.status);
                    }
                    loaderStop();
                    return true;
                }
            });
        }
    }
}

function errorMessages() {
    $("#nameProject").focus();
    $('#projectNameExists').hide();
    $('#projectIsMissing').hide();
    $('#groupMechanismMissing').hide();
    $('#exactNumberOfTags').hide();
    $('#specialChars').hide();
    $('#projectDescriptionMissing').hide();
    document.getElementById('tagHelper').className = "";
}

function initTagsInput(allTheTags) {
    $(function () {
        $('#tagsProject').tagsInput({
            width: '400px',
            onAddTag: function (tag) {
                allTheTags.push(tag);
            },
            onRemoveTag: function () {
                allTheTags.pop();
                //allTheTags = $(this).val().split(",");
            }
        });
    });
}

function initSendButton(allTheTags) {
    $('#sendProject').on('click', function () {
        createNewProject(allTheTags);
    });
}

// it returns false and shows errors if input is not valid
function getProjectValues() {
    let interruptFunction = false;
    projectName = $("#nameProject").val().trim();
    let password = $("#passwordProject").val().trim();
    //allTheTags = $("#tagsProject").tagsInput('items');
    //allTheTags = $("#tagsProject").val();
    let reguexp = /^[a-zA-Z0-9äüöÄÜÖß ]+$/;
    if (!reguexp.test(projectName)) {
        $('#specialChars').show();
        loaderStop();
        interruptFunction = true;
    }
    if (projectName === "") {           //project has no name, so abort function
        $('#projectIsMissing').show();
        loaderStop();
        interruptFunction = true;
    }
    let description = $('#projectDescription').val();
    if (description === "") {
        $('#projectDescriptionMissing').show();
        loaderStop();
        interruptFunction = true;
    }
    let time = new Date().getTime();
    let selectedCategories = [];
    $("input:checked[class*='category']").each(function () {
        selectedCategories.push($(this).val());
    });
    $(".LIOwnCategory").each(function () {
        let nodeOfInterest = $(this).find('input:checked');
        if (nodeOfInterest.length > 0) {
            let ownCategory = $(this).find('input[type*=text]').val();
            if (ownCategory.match(/^[A-Za-z]+$/)) {
                selectedCategories.push(ownCategory);
            } else {
                $('#noSpecialCharacters').attr('hidden', false);
                loaderStop();
                interruptFunction = true;
            }
        }
    });
    if (selectedCategories.length === 0) {
        stop();
    }
    if (interruptFunction) {
        return false;
    }

    return {
        "name": projectName,
        "password": password,
        "description": description,
        "active": true,
        "phase": "GroupFormation",
        "timecreated": time,
        "authorEmail": $('#userEmail').text().trim(),
        "tags": allTheTags,
        "categories": selectedCategories,
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
            document.location.href = "tasks-docent.jsp?projectName=" + projectName;
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
    if (gfm === "Basierend auf Präferenzen") {
        gfm = "UserProfilStrategy";
    } else if (gfm === "per Hand") {
        gfm = "Manual";
    } else if (gfm === "Basierend auf Lernzielen") {
        gfm = "LearningGoalStrategy";
    } else if (gfm === "Keine Gruppen") {
        gfm = "SingleUser";
    } else {
        $('#groupMechanismMissing').show();
        loaderStop();
        return false;
    }

    let localurl = "../rest/group/gfm/create/projects/" + projectName;
    $.ajax({
        gfm: gfm,
        url: localurl,
        contentType: 'application/json',
        type: 'POST',
        data: gfm,
        success: function () {
            if (gfm === "LearningGoalStrategy") {
                createProjectinCompbase();
            }
            document.location.href = "tasks-docent.jsp?projectName=" + projectName;
            return true;
        },
        error: function () {
            return false;
        }
    });
}

function checkCompBase(callback) {
    $.ajax({
        url: '../rest/system/health',
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {
            callback(response.compBaseOnline, response.groupAlOnline);
        },
        error: function (a) {
            console.log(a);
        }
    });
}
