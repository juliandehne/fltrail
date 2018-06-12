$(document).ready(function(){
    $("#calculateNow").on("click", function(){
        var data = [{
            StudentIdentifier: {
                projectId: 'gemeinsamForschn',
                student: 'Max*i Mustermann*ine'
            },
            Performance: {
                quizAnswers: [1,0,1,1,0,0,0,0,1],
                feedback: 'ganz feiner Student war das',
                workRating: [4,3,5,1,1,5,3]
            }
        },{
            StudentIdentifier: {
                projectId: 'gemeinsamForschn',
                student: 'John Doe'
            },
            Performance: {
                quizAnswers: [1,0,1,1,0,1,1,1,1],
                feedback: 'ganz feiner Student war das',
                workRating: [2,3,2,3,1,2,3]
            }
        },{
            StudentIdentifier: {
                projectId: 'gemeinsamForschn',
                student: 'Harri Gründler'
            },
            Performance: {
                quizAnswers: [0,0,0,0,0,0,0,0,0],
                feedback: 'ganz feiner Student war das',
                workRating: [1,1,1,1,1,1,1]
            }
        }
        ];
        $.ajax({
            url: "http://localhost:8080/gemeinsamforschen/rest/assessments/calculate",
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: data,
            success: function(data){
                alert(data);
            },
            error: function(a,b,c){
                alert('some error'+a);
            }
        })
    })
});