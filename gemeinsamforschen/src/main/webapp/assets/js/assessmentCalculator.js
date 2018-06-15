$(document).ready(function(){
    $("#giveItBack").on("click", function(){
        $.ajax({
            url: "http://localhost:8080/gemeinsamforschen/rest/assessments/total/project/"+"gemeinsamForschen"+"/student/"+"Bela",
            type: 'GET',
            success: function(data){
                alert("here is the TotalPerformance: "+data);
            },
            error: function(a,b,c){
                alert('some error'+a);
            }
        })
    });
    $("#calculateNow").on("click", function(){
        var data = {
            "studentIdentifier":[{
                "projectId":"gemeinsamForschen",
                "studentId":"Haralf"
            },
                {
                    "projectId":"gemeinsamForschen",
                    "studentId":"Regine"
                }
                ],
            "performances":[{
                "quizAnswer":[1,0,1,0,0,0,1],
                "feedback":"toller dude",
                "workRating":[5,4,3,2,1]
            },{
                "quizAnswer":[0,1,0,1,1,1,0],
                "feedback":"tolle dudine",
                "workRating":[1,2,3,4,5]}
                ]
        };

        var datacorrect = {                   //todo: write it in maps instead of arrays
            "studentIdentifier":[ {
                "projectId": 'gemeinsamForschn',
                "student": 'Max*i Mustermann*ine'
            },
                {
                "projectId": 'gemeinsamForschn',
                "student": 'John Doe'
            },
            {
                "projectId": 'gemeinsamForschn',
                "student": 'Harri Gründler'
            }
        ],
            "performance":[
                {
                    "quizAnswers": [1,0,1,1,0,0,0,0,1],
                    "feedback": 'ganz feiner Student war das',
                    "workRating": [4,3,5,1,1,5,3]
                },
                {
                    "quizAnswers": [1,0,1,1,0,1,1,1,1],
                    "feedback": 'ganz feiner Student war das',
                    "workRating": [2,3,2,3,1,2,3]
                },
                {
                    "quizAnswers": [0,0,0,0,0,0,0,0,0],
                    "feedback": 'ganz feiner Student war das',
                    "workRating": [1,1,1,1,1,1,1]
                }
            ]
        };


        $.ajax({
            url: "http://localhost:8080/gemeinsamforschen/rest/assessments/calculate",
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: data,
            success: function(response){
                alert(response);
            },
            error: function(a){
                alert('some error'+a);
            }
        })
    })
});