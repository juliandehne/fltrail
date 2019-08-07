/**
 * Created by fides-WHK on 15.03.2018.
 */

$(document).ready(function () {
    $('#projectWrongPassword').hide();
    printTags();
    let competencies = $("#competencies0");
    competencies.tagsInput();
    competencies.focus();
    $("#studentFormSubmit").on("click", function () {
        takesPartInProject();
    });
});

/**
 * selects the tags from the db and prints the seleciton
 */
function printTags() {
    let url = "../rest/project/tags/" + getProjectName();
    $.ajax({
        url: url,
        Accept: "application/json",
        contentType: "text/plain",
        success: function (response) {
            let tagList = response;
            for (let i = 0; i < tagList.length; i++) {
                let tmplObject = {
                    tagCount: i,
                    tagText: tagList[i]
                };
                $('#tagTemplate').tmpl(tmplObject).appendTo('#tags');
            }
        },
        error: function (a) {
            console.log(a);
        }
    });
}


// he is added in compbase to the project
function takesPartInProject() {
    loaderStart();
    let cover = $('.cover');
    cover.each(function () {
        $(this).fadeIn(100);
    });
    document.getElementById('loader').className = "loader";

    let userEmail = getUserEmail();
    let projectName = getProjectName();
    //checkCompBase(function (isCompBaseOnline) {
    //if (1) {
    let allTheTags = [];
    let allTheCompetencies;
    allTheCompetencies = $('#competencies0').val().split(",");
    for (let i = 0; i < document.getElementsByName("tag").length; i++) {   //goes through all tags and adds them to allTheTags
        if (document.getElementById("tag" + i).checked) {
            allTheTags.push(document.getElementById("tag" + i).value);
        }
    }
    if (allTheTags.length > 2) {
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        document.getElementById('loader').className = "loader-inactive";
        cover.each(function () {
            $(this).fadeOut(100);
        });
        return false;
    }
    if (allTheTags.length < 2) {
        $(".alert").css('background-color', 'lightcoral');
        allTheTags = [];
        document.getElementById('loader').className = "loader-inactive";
        cover.each(function () {
            $(this).fadeOut(100);
        });
        return false;
    }
    let dataTags = allTheCompetencies.concat(allTheTags);
    for (let i = 0; i < dataTags.length; i++) {
        dataTags[i] = "Studierende interessieren sich für " + dataTags[i];
    }

    let data = {
        "competences": [],
        "researchQuestions": [],
        //JSON object 'data' collects everything to send
        "tagsSelected": dataTags
    };
    loginProject(projectName);

    let dataString = JSON.stringify(data);                     //to send correctly, data needs to be stringified
    //let url = compbaseUrl + "/api2/user/" + userEmail + "/projects/" + projectName + "/preferences";
    let url = "../rest/group/user/" + userEmail + "/projects/" + projectName + "/preferences";
    $.ajax({
        url: url,
        type: 'PUT',
        Accept: "text/plain; charset=utf-8",
        contentType: "application/json",
        projectName: projectName,
        data: dataString,
        success: function (response) {
            console.log(response);
            document.getElementById('loader').className = "loader-inactive";
            loaderStop();
            location.href = "../project/tasks-student.jsp?projectName=" + projectName;
        },
        error: function (a) {
            console.log(a);
            loaderStop();
        }
    });
    /*    } else {
            document.getElementById('loader').className = "loader-inactive";
            location.href = "../project/tasks-student.jsp?projectName="+projectName;
        }
    });*/
}

function loginProject(projectName) {
    //let password = $('#projectPassword').val();
    let url = "../rest/project/login/" + projectName;// + "?password=" + password;
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