/**
 * Created by fides-WHK on 19.02.2018.
 */
$(document).ready(function () {
    var allTheTags = [];
    var projectName = "";
    var password = "";
    var activ = "1";
    document.getElementById('loader').className = "loader-inactive";
    document.getElementById('wrapper').className = "wrapper";
    $("#nameProject").focus();
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
        courseExists(projectName, allTheTags);
        createNewProject(allTheTags, projectName, password, activ);
    });
});

function courseExists(projectName) {
    var localurl = "../database/getProjects.php?project=" + projectName;
    if (projectName === "") {
        $('#projectIsMissing').show();
        return false;
    } else {
        $('#projectIsMissing').hide();
        $.ajax({
            url: localurl,
            projectName: projectName,
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response !== "project missing") {
                    $('#projectNameExists').show();
                    if (allTheTags.length !== 5) {
                        document.getElementById('tagHelper').className = "alert alert-warning";
                    }
                    document.getElementById('loader').className = "loader-inactive";
                    document.getElementById('wrapper').className = "wrapper";
                    return true;
                } else {
                    $('#projectNameExists').hide();
                    createNewProject(allTheTags, projectName, password, activ);
                    document.getElementById('loader').className = "loader-inactive";
                    document.getElementById('wrapper').className = "wrapper";
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

function createNewProject(allTheTags, activ) {
    var projectName = $("#nameProject").val();
    var password = $("#passwordProject").val();
    document.getElementById('loader').className = "loader";
    document.getElementById('wrapper').className = "wrapper-inactive";
    var localurl = "../database/getProjects.php?project=" + projectName + "&token="+getUserTokenFromUrl();
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
            Accept: "text/plain; charset=utf-8",
            contentType: "text/plain",
            success: function (response) {
                if (response !== "project missing") {
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
                        return false;
                    }
                    document.getElementById('tagHelper').className = "";
                    var obj = {
                        "courseId": projectName,
                        "printableName": projectName,
                        "competences": allTheTags
                    };
                    var url = "https://esb.uni-potsdam.de:8243/services/competenceBase/api1/courses/" + $("#nameProject").val();
                    var dataString = JSON.stringify(obj);
                    $.ajax({
                        url: url,
                        contentType: 'application/json',
                        type: 'PUT',
                        data: dataString,
                        success: function (response) {
                            alert(response);
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
                    if (addTagsToProject(allTheTags, projectName, password)) {
                        var parts = window.location.search.substr(1).split("&");
                        var $_GET = {};
                        for (var i = 0; i < parts.length; i++) {
                            var temp = parts[i].split("=");
                            $_GET[decodeURIComponent(temp[0])] = decodeURIComponent(temp[1]);
                        }
                        location.href = "overview.php?token=" + $_GET['token'];
                    } else {
                        document.getElementById('loader').className = "loader-inactive";
                        document.getElementById('wrapper').className = "wrapper";
                        return false;
                    }
                    document.getElementById('loader').className = "loader-inactive";
                    document.getElementById('wrapper').className = "wrapper";
                    return false;
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
}

function addTagsToProject(allTheTags, projectName, password, activ) {
    var tags = JSON.stringify(allTheTags);
    var url = "../database/putTagsAndPW.php?project=" + projectName + "&password=" + password + "&activ=" + activ;
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