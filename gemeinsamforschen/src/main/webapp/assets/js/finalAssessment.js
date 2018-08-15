$(document).ready(function() {
    $('#notAllRated').hide();
    $(".carousel").carousel({
        interval: false
    });
    $('#assessThePeer').on("click", function () {
        assessPeer();
    });
});

function assessPeer(){
    ///////initialize variables///////
    var dataP = [];
    var workRating = {};
    var rateThis = ['responsibility','partOfWork','cooperation','communication','autonomous'];

    ///////read values from html///////
    var peerStudents =$('.peerStudent');
    for (var peer=0; peer< peerStudents.length; peer++){
        var peerRating = {
            "fromPeer": $('#user').html().trim(),
            "toPeer": peerStudents[peer].id,
            "workRating": {}
        };
        for (var rate=0; rate<rateThis.length; rate++ ){
            var category = rateThis[rate];
            workRating[category]=($('input[name='+rateThis[rate]+peerStudents[peer].id+']:checked').val());
        }
        for (var i=0; i<workRating.length; i++){
            if(workRating[i]===undefined){
                $('#notAllRated').show();
                return;
            }
        }
        peerRating.workRating = workRating;
        workRating=[];
        //////write values in Post-Variable
        dataP.push(peerRating);
    }
    var projectId=$('#projectId').html().trim();
    $.ajax({
        url:'../rest/assessments/peerRating/project/'+projectId,
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function(){
            location.href="takeQuiz.jsp?token="+getUserTokenFromUrl()+"&projectId="+$('#projectId').html().trim();
        },
        error: function(a,b,c){

        }
    });
}
