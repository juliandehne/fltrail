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
$(document).ready(function () {
    $('#ProblemGrp1').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1"||alarm=="2"||alarm=="3"||alarm=="4"||alarm=="5"||alarm=="6") {

        var dataP= JSON.stringify({"adressat":true,"student": {
                "projectId": "projekt2",
                "studentId": "fgnxnw"
            }, "deadline":new Date(),
            "bewertender": {
                "projectId": "projekt2",
                "studentId": "christians"
            },
            "projektId":"projekt2",
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
            document.getElementById("ProblemGrp1S1").innerHTML = alarm;
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
            document.getElementById("ProblemGrp1S2").innerHTML = alarm;
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
            document.getElementById("ProblemGrp2").innerHTML = alarm;
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
            document.getElementById("ProblemGrp2S3").innerHTML = alarm;
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp2S4').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "2") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "3") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "4") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "5") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "6") {
            document.getElementById("demo").innerHTML = alarm;
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "2") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "3") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "4") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "5") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "6") {
            document.getElementById("demo").innerHTML = alarm;
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3S5').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "2") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "3") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "4") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "5") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "6") {
            document.getElementById("demo").innerHTML = alarm;
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
$(document).ready(function () {
    $('#ProblemGrp3S6').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        if (alarm === "1") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "2") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "3") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "4") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "5") {
            document.getElementById("demo").innerHTML = alarm;
        } else if (alarm === "6") {
            document.getElementById("demo").innerHTML = alarm;
        }
        else{
            window.alert("Bitte eine Zahl zwischen eins und 6 angeben")
        }
    });
});
