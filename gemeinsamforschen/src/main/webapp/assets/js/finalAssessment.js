$(document).ready(function() {
    $(".carousel").carousel({
        interval: false
    });
    $('#assessThePeer').on("click", function () {
        assessPeer();
    });
});
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
        url:'http://localhost:8080/gemeinsamforschen/rest/assessments/peer/project/1/group/1',
        type: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache"
        },
        data: JSON.stringify(dataP),
        success: function(){

        },
        error: function(a,b,c){

        }
    });
}
