$(document).ready(function () {
    $('#ProblemGrp1').on('click',function () {
        var alarm = prompt("hier gibt es Unstimmigeiten","Vorgeschlagene Note:")
        document.getElementById("demo").innerHTML = "Wurde übernommen";
    });
});

