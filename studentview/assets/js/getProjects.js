/**
 * Created by dehne on 28.03.2018.
 */

function getProjectsOfAuthor(author, printedProjects, handleProjects) {
    var url = "../database/getProjectsOfAuthor.php?author=" + author;
    $.ajax({
        url: url,
        Accept: "text/plain; charset=utf-8",
        contentType: "text/plain",
        success: function (response) {
            var authoredProjects = JSON.parse(response);
            if (authoredProjects != null) {
                if (printedProjects != null) {
                    for (var i = 0; i < printedProjects.length; i++) {
                        authoredProjects = authoredProjects.filter(function (el) {
                            return el !== printedProjects[i];
                        });
                    }
                    handleProjects(authoredProjects, printedProjects.length);
                } else {
                    handleProjects(authoredProjects, 0);
                }

            }
        },
        error: function (a, b, c) {
            console.log(a);
        }
    });
}