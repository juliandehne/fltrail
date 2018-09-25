$(document).ready(function () {     //todo: paths should be relative
    $("#giveItBack").on("click", function () {
        $.ajax({
            url: "../rest/assessments/total/project/" + "gemeinsamForschen" + "/student/" + "Bela",
            type: 'GET',
            success: function (data) {
                alert("here is the TotalPerformance: " + data);

                location.href="../pages/project-student.jsp?token="+getUserEmail();
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
                    "studentId": "student"
                },
                "quizAnswer": [
                    1,
                    0,
                    1,
                    1,
                    1,
                    0
                ],
                "feedback": "ein toller typ",
                "workRating": {
                    "responsibility": 1,
                    "partOfWork": 1,
                    "autonomous": 1,
                    "communication": 1,
                    "cooperation": 1
                }
            },
            {
                "studentIdentifier": {
                    "projectId": "projekt",
                    "studentId": "student"
                },
                "quizAnswer": [
                    1,
                    0,
                    1,
                    1,
                    1,
                    0
                ],
                "feedback": "feini feini",
                "workRating": {
                    "responsibility": 3,
                    "partOfWork": 4,
                    "autonomous": 4,
                    "communication": 3,
                    "cooperation": 5
                }
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