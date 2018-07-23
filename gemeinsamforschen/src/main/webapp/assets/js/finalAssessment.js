$(document).ready(function() {
    $(".carousel").carousel({
        interval: false
    });
    $('#assessThePeer').on("click", function () {
        assessPeer();
    });
});

function getUser(){//todo: you can see what you need to do
    return "dummy";
}

function assessPeer(){
    var peerRating = {
        "fromPeer": getUser(),
        "toPeer": "",
        "workRating": []
    };
    var dataP = [];
    var peerStudents =$('.peerStudent');
    for (var i=0; i< peerStudents.length; i++){
        peerRating.toPeer = peerStudents[i].id;
        peerRating.workRating = [5,4,3,2]
    }
    dataP.push(peerRating);
    $.ajax({
        url:'../rest/assessments/peer/project/1/group/1',
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
