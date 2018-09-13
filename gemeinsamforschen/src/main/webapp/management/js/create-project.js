/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    var allTheTags = [];
    $("#nameProject").focus();
    $('#projectNameExists').hide();
    $('#projectIsMissing').hide();
    $('#exactNumberOfTags').hide();
    $('#specialChars').hide();
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
    $('#sendProject').on('click', function () {
        var activ = "1";
        createNewProject(allTheTags, activ);
    });


});


function createNewProject(allTheTags, activ) {
    $("#nameProject").focus();
    $('#projectNameExists').hide();
    $('#projectIsMissing').hide();
    $('#exactNumberOfTags').hide();
    $('#specialChars').hide();

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

    document.getElementById('loader').className = "loader";
    document.getElementById('wrapper').className = "wrapper-inactive";
    var localurl = "../../gemeinsamforschen/rest/project/create";
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
        "author": "STFHXOqQj2",
        "adminPassword": adminPassword,
        "token": "QCqGuQlYLL",
        "phase": "GroupFormation",
        "tags": allTheTags
    }

    $('#projectIsMissing').hide();
    $.ajax({                        //check local DB for existence of projectName
        url: localurl,
        contentType: 'application/json',
        activ: activ,
        type: 'PUT',
        data: JSON.stringify(project),
        success: function (response) {
            if (response === "project missing") {
                $('#projectNameExists').show();
                document.getElementById('loader').className = "loader-inactive";
                document.getElementById('wrapper').className = "wrapper";
                return true;
            } else {
                $('#projectNameExists').hide();
                if (allTheTags.length !== 5) {
                    document.getElementById('tagHelper').className = "alert alert-warning";
                    document.getElementById('loader').className = "loader-inactive";
                    document.getElementById('wrapper').className = "wrapper";
                    $('#exactNumberOfTags').show();
                    return false;
                }
                document.getElementById('tagHelper').className = "";
                var obj = {
                    "courseId": projectName,
                    "printableName": projectName,
                    "competences": allTheTags
                };
                var url = compbaseUrl + "/api1/courses/" + $("#nameProject").val();
                var dataString = JSON.stringify(obj);
                var addProjectNeo4j = $.ajax({
                    url: url,
                    contentType: 'application/json',
                    activ: activ,
                    type: 'PUT',
                    data: dataString,
                    success: function (response) {
                        console.log(response);
                        document.getElementById('loader').className = "loader-inactive";
                        document.getElementById('wrapper').className = "wrapper";
                    },
                    error: function (a, b, c) {
                        console.log(a);
                        document.getElementById('loader').className = "loader-inactive";
                        document.getElementById('wrapper').className = "wrapper";
                        return false;
                    }
                });
                $.when(addProjectNeo4j, addProjectToLocalDB(allTheTags, projectName, password, activ, adminPassword)).done(function () {
                    document.getElementById('loader').className = "loader-inactive";
                    document.getElementById('wrapper').className = "wrapper";
                    if ($('#Teilnehmer').prop("checked")) {          //if author wants to join the course, he needs to be redirected to enter-preferences.jsp
                        var url = "../../gemeinsamforschen/rest/project/token?project=" + projectName + "&password=" + document.getElementById('passwordProject').value;
                        $.ajax({
                            url: url,
                            projectName: projectName,
                            Accept: "text/plain; charset=utf-8",
                            contentType: "text/plain",
                            success: function (response) {
                                location.href = "enter-preferences.jsp?token=" + getUserTokenFromUrl() + "&projectToken=" + response;
                            },
                            error: function (a, b, c) {
                                console.log(a);
                            }
                        });
                    } else {                //if author is just author and not member, he will be directed to projects.php
                        location.href = "../project-docent.jsp?token=" + getUserTokenFromUrl();
                    }
                });
            }
        },
        error: function (a, b, c) {
            console.log(a);
            document.getElementById('loader').className = "loader-inactive";
            document.getElementById('wrapper').className = "wrapper";
            return true;
        }
    });

}

function addProjectToLocalDB(allTheTags, projectName, password, activ, adminPassword) {
    /*var tags = JSON.stringify(allTheTags);
    var author = $("#user").text().trim();
    var url = "../database/putProject.php?project=" + projectName + "&password=" + password + "&activ=" + activ + "&token=" + getUserTokenFromUrl() + "&adminpassword=" + adminPassword + "&author=" + author;
    return $.ajax({
        url: url,
        //contentType: 'application/json',
        type: 'POST',
        data: tags,
        success: function (response) {
            console.log("Tags were added to local DB");
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });*/
    // Project has been added with the exist function - maybe that was the wrong approach?
}