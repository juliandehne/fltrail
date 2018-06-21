$(document).ready(function () {     //todo: paths should be relative
    $("#giveItBack").on("click", function () {
        $.ajax({
            url: "../rest/assessments/total/project/" + "gemeinsamForschen" + "/student/" + "Bela",
            type: 'GET',
            success: function (data) {
                alert("here is the TotalPerformance: " + data);

                location.href="../pages/project-student.jsp?token="+getUserTokenFromUrl();
            },
            error: function (a, b, c) {
                alert('some error' + a);
            }
        })
    });
    $("#calculateNow").on("click", function () {
        var dataP = [
            {
                "studentIdentifier": {
                    "projectId": "projekt",
                    "studentId": "Keiler"
                },
                "quizAnswer": [
                    1,
                    0,
                    1,
                    1,
                    1,
                    0
                ],
                "feedback": "toller dude",
                "workRating": [
                    5,
                    4,
                    3,
                    2,
                    1,
                    0
                ]
            },
            {
                "studentIdentifier": {
                    "projectId": "projekt",
                    "studentId": "Glucke"
                },
                "quizAnswer": [
                    1,
                    1,
                    1,
                    1,
                    1,
                    0
                ],
                "feedback": "super",
                "workRating": [
                    1,
                    1,
                    1,
                    2,
                    3,
                    2
                ]
            }
        ];

        $.ajax({
            url: "../rest/assessments/calculate",
            type: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Cache-Control": "no-cache"
            },
            data: JSON.stringify(dataP),
            success: function (response) {
                alert(response[0].studentIdentifier.studentId + " got " +response[0].grade*100+"% /n"+
                    response[1].studentIdentifier.studentId + " got " +response[1].grade*100+"% /n");
            },
            error: function (a) {
                alert('some error' + a);
            }
        })
    })
});