$(document).ready(function () {
    if(!$("#Diagramm").hidden){
        $("#Diagramm").hide();
    };

    document.getElementById("Diagramm").style.visibility="invisible";
    $('#DiaBlende').on('click',function () {
        $("#Diagramm").show();
        document.getElementById("demo").innerHTML="verlauf";
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
