/**
 * Created by fides-WHK on 15.03.2018.
 */

$(document).ready(function () {
    checkCompBase(function (isCompBaseOnline) {
        if (!isCompBaseOnline) {
            $('#competenciesFieldSet').hide();
            $('#researchQuestionFieldSet').hide();
        }
    });

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
    let url = "../../gemeinsamforschen/rest/project/tags/" + getProjectName();
    $.ajax({
        url: url,
        Accept: "application/json",
        contentType: "text/plain",
        success: function (response) {
            let tagList = response;
            for (let i = 0; i < tagList.length; i++) {
                let newInput = document.createElement("DIV");
                newInput.innerHTML =
                    "<div class='checkbox checkbox-primary' >"
                    + "<input id='tag" + i + "' " + " class='styled' " + "name='tag'" + " type='checkbox' value='" + tagList[i] + "'>"
                    + "<label for='tag" + i + "' " + ">" + tagList[i] + "</label>"
                    + "</div>";
                let div = document.getElementById('tags');
                div.appendChild(newInput);
            }
        },
        error: function (a) {
            console.log(a);
        }
    });
}


// he is added in compbase to the project
function takesPartInProject() {
    $('.cover').each(function () {
        $(this).fadeIn(100);
    });
    document.getElementById('loader').className = "loader";

    let userEmail = getUserEmail();
    let projectName = getProjectName();
    loginProject(projectName);
    checkCompBase(function (isCompBaseOnline) {
        if (isCompBaseOnline) {
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
                return false;
            }
            if (allTheTags.length < 2) {
                $(".alert").css('background-color', 'lightcoral');
                allTheTags = [];
                document.getElementById('loader').className = "loader-inactive";
                return false;
            }
            let data = {                                            //JSON object 'data' collects everything to send
                "tags": allTheCompetencies.concat(allTheTags)       //todo: this differs from the backend interface atm
            };                                                      //todo: Julian needs to fix the backend =)
            let dataString = JSON.stringify(data);                     //to send correctly, data needs to be stringified
            let url = compbaseUrl + "/api2/user/" + userEmail + "/projects/" + projectName + "/preferences";
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
                    location.href = "../project/tasks-student.jsp?projectName="+projectName;
                },
                error: function (a) {
                    console.log(a);
                }
            });
        } else {
            document.getElementById('loader').className = "loader-inactive";
            location.href = "../project/tasks-student.jsp?projectName="+projectName;
        }
    });
}

function loginProject(projectName) {
    let password = $('#projectPassword').val();
    let url = "../../gemeinsamforschen/rest/project/login/" + projectName + "?password=" + password;
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

function checkCompBase(callback) {
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