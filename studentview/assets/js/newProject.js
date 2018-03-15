/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    var allTheTags = [];
    var projectName = "";
    var password = "";
    var activ = "1";
    $('#projectNameExists').hide();
    $('#projectIsMissing').hide();
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
        createNewProject(allTheTags, projectName, password, activ);
        if (document.getElementById("Teilnehmer").checked===true){
            takePart(allTheTags);
        }
    });
});



function createNewProject(allTheTags, activ) {
    var userID = $("#user").text().trim();
    var projectName = $("#nameProject").val().trim();
    var password = $("#passwordProject").val().trim();
    var localurl = encodeURI("../database/getProjects.php?project=" + projectName);
    if (allTheTags.length !== 5) {
        document.getElementById('tagHelper').className = "alert alert-warning";
    } else {
        document.getElementById('tagHelper').className = "";
    }
    if (projectName === "") {           //project has no name, so abort function
        $('#projectIsMissing').show();
        return false;
    } else {
        $('#projectIsMissing').hide();
        $.ajax({                        //check local DB for existence of projectName
            url: localurl,
            projectName: projectName,
            userID: userID,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response !== "project missing") {
                    $('#projectNameExists').show();
                    return true;
                } else {
                    $('#projectNameExists').hide();
                    if (allTheTags.length !== 5) {
                        document.getElementById('tagHelper').className = "alert alert-warning";
                        return false;
                    }
                    document.getElementById('tagHelper').className = "";
                    var obj = {
                        "courseId": projectName,
                        "printableName": projectName,
                        "competences": allTheTags
                    };
                    var url = encodeURI("https://esb.uni-potsdam.de:8243/services/competenceBase/api1/courses/" + projectName);
                    var dataString = JSON.stringify(obj);
                    $.ajax({
                        url: url,
                        contentType: 'application/json',
                        type: 'PUT',
                        data: dataString,
                        success: function (response) {
                            alert(response);
                        },
                        error: function (a, b, c) {
                            console.log(a);
                            return false;
                        }
                    });
                    if (putProjectToLocalDB(allTheTags, projectName, password)) {
                        var parts = window.location.search.substr(1).split("&");
                        var $_GET = {};
                        for (var i = 0; i < parts.length; i++) {
                            var temp = parts[i].split("=");
                            $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
                        }
                        location.href = "projects.php?token=" + $_GET['token'];
                    } else return false;

                    return false;
                }
            },
            error: function (a, b, c) {
                console.log(a);
                return true;
            }
        });
    }
}

function takePart(allTheTags){          //will just be called upon creating a project and user wants to be member of it. allTheTags is an array
    var userID = $("#user").text().trim();
    var projectID = $("#nameProject").val().trim();
    var data = {                                            //JSON object 'data' collects everything to send
        "competences": [],
        "researchQuestions": [],
        "tagsSelected": allTheTags
    };
    var dataString = JSON.stringify(data);
    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api2/user/" + userID + "/projects/" + projectID + "/preferences";
    $.ajax({
        url: url,
        type: 'PUT',
        Accept: "text/plain; charset=utf-8",
        contentType: "application/json",
        data: dataString,
        success: function (response) {
            console.log(response);
            document.getElementById('loader').className = "loader inactive";
            document.getElementById('wrapper').className = "wrapper";
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}

function putProjectToLocalDB(allTheTags, projectName, password, activ) {
    var userID = $("#user").text().trim();
    var adminpassword = $("#adminPassword").val().trim();
    var tags = JSON.stringify(allTheTags);
    var userToken = getUserTokenFromUrl();
    var url = "../database/putProject.php?project=" + projectName + "&password=" + password + "&activ=" + activ+"&author="+userID+ "&adminpassword="+adminpassword+"&token="+userToken;
    $.ajax({
        url: url,
        //contentType: 'application/json',
        type: 'POST',
        data: tags,
        success: function (response) {
            console.log("Tags were added to local DB");
            return true;
        },
        error: function (a, b, c) {
            console.log(a);
            return false;
        }
    });
}

function getUserTokenFromUrl() {
    var $_GET = {};
    for (var i = 0; i < parts.length; i++) {
        var temp = parts[i].split("=");
        $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
    }
    return $_GET['token'];
}