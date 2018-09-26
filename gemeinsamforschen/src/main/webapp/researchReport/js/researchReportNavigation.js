var student = getQueryVariable("token");
var project = getQueryVariable("projectId");

$(document).ready(function () {
    $('#student').val(student);
    $('#project').val(project);

    let locationLink;
    switch ($('#category')) {
        case "TITEL":
            locationLink = "create-overview.jsp?token=" + student + "&projectId=" + project;
            break;
        case "RECHERCHE":
            locationLink = "create-title.jsp?token=" + student + "&projectId=" + project;
            break;
        case "LITERATURVERZEICHNIS":
            locationLink = "create-research.jsp?token=" + student + "&projectId=" + project;
            break;
        case "FORSCHUNGSFRAGE":
            locationLink = "create-bibliography.jsp?token=" + student + "&projectId=" + project;
            break;
        case "UNTERSUCHUNGSKONZEPT":
            locationLink = "create-question.jsp?token=" + student + "&projectId=" + project;
            break;
        case "METHODIK":
            locationLink = "create-concept.jsp?token=" + student + "&projectId=" + project;
            break;
        case "DURCHFUEHRUNG":
            locationLink = "create-method.jsp?token=" + student + "&projectId=" + project;
            break;
        case "AUSWERTUNG":
            locationLink = "create-process-description.jsp?token=" + student + "&projectId=" + project;
            break;
    }

    $('#backLink').on('click', function () {
        location.href = locationLink
    });

});
