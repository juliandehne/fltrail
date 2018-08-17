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
    var peerStudents =$('.peerStudent');
    ///////initialize variables///////
    var dataP = new Array(peerStudents.size());
    var rateThis = ['responsibility','partOfWork','cooperation','communication','autonomous'];

    ///////read values from html///////
    for (var peer=0; peer< peerStudents.length; peer++){
        var workRating = {};
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
        //////write values in Post-Variable
        dataP[peer]=peerRating;
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
