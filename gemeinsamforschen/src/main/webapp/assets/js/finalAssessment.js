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
    let peerStudents =$('.peerStudent');
    ///////initialize variables///////
    let dataP = new Array(peerStudents.size());
    let rateThis = ['responsibility','partOfWork','cooperation','communication','autonomous'];

    ///////read values from html///////
    for (let peer=0; peer< peerStudents.length; peer++){
        let workRating = {};
        let peerRating = {
            "fromPeer": $('#user').html().trim(),
            "toPeer": peerStudents[peer].id,
            "workRating": {}
        };
        for (let rate=0; rate<rateThis.length; rate++ ){
            let category = rateThis[rate];
            workRating[category]=($('input[name='+rateThis[rate]+peerStudents[peer].id+']:checked').val());
        }

        peerRating.workRating = workRating;
        //////write values in Post-Variable
        dataP[peer]=peerRating;
    }
    for (let peer=0; peer< dataP.length; peer++){
        for (let workRating=0; workRating<rateThis.length;workRating++){
            if(dataP[peer].workRating[rateThis[workRating]]===undefined){
                $('#notAllRated').show();
                return;
            }
        }
    }
    let projectId=$('#projectId').html().trim();
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
