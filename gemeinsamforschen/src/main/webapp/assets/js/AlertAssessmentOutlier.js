$(document).ready(function () {


    $('#DiaBlende').on('click',function () {
        var ctx = document.getElementById("Diagramm").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ["Erstes Assessment", "Zweites Assessment", "Drittes Assessment", "Viertes Assessment", "Fünftes Assessment", "Sechstes Assessment"],
                datasets: [{
                    label: 'Note Student 1',
                    data: [
                        {x:1,y:2},{x:2,y:3},{x:3,y:1},{x:4,y:4}, {x:5,y:1}, {x:6,y:3}],
                    borderColor: 'rgba(255,0,3,0.2)',
                    backgroundColor: 'rgba(255,0,3,0.2)',
                    fill:false
                }
                    ,
                    {
                    label:"Note Student 2",
                    data: [{x:1,y:3},{x:2,y:2},{x:3,y:5},{x:4,y:5}, {x:5,y:5}, {x:6,y:1}]
                        ,borderColor: 'rgba(0,255,0,0.2)'
                        , backgroundColor: 'rgba(0,255,3,0.2)'
                        ,fill:false

                }]
            },
            options: {scales: {yAxes: [{ticks: {beginAtZero:true}}]},layout:{padding:{left:50,right:50,top:50, bottom:50}}}
        });
    });
});

// hier noch dynamik ergänzen, damit der Student und die Gruppe ausgelesen werden

$(document).ready(function () {
    $('#ProblemGrp1').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

        var dataP= JSON.stringify({"adressat":true,"student": {
                "projectId": getQueryVariable("projectId"),
                "studentId": "fgnxnw"
            }, "deadline":new Date(),
            "bewertender": {
                "projectId": getQueryVariable("projectId"),
                "studentId": document.getElementById("user").innerText
            },
            "projektId":getQueryVariable("projectId"),
            "bewertung":alarm
        });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp1').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});

$(document).ready(function () {
    $('#ProblemGrp1S1').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp1S1').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp1S2').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp1S2').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp2').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp2').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp2S3').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp2S3').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp2S4').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp2S4').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp3').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3S5').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp3S5').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3S6').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

            var dataP= JSON.stringify({"adressat":true,"student": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": "fgnxnw"
                }, "deadline":new Date(),
                "bewertender": {
                    "projectId": getQueryVariable("projectId"),
                    "studentId": document.getElementById("user").innerText
                },
                "projektId":getQueryVariable("projectId"),
                "bewertung":alarm
            });
            $.ajax({
                url: "../rest/assessments2/calculate2",
                type: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Cache-Control": "no-cache"
                },
                data: dataP,
                success: function (response) {
                    document.getElementById('ProblemGrp3S6').innerHTML=response;
                },
                error: function (a,b,c) {
                    alert('some error' + b);
                }
            })
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
