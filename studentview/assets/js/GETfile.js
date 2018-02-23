/**
 * Created by fides-WHK on 09.01.2018.
 */

function loadData() {
    var competencies = $("#competencies").val();
    var researchQuestion = $("#researchQuestion").val();
    var tags = $("#Tags").val();
    /*      since jquery works async, you won't see an alert but it works going through the steps one by one
     $.getJSON("../assets/dummyGroups.json", function(data){

     alert("juhu");
     alert(data.student2.name);
     window.location.href="my groups.html";
     });
     */
    var url="https://esb.uni-potsdam.de:8243/services/competenceBase/api2/groups/12345";
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType:"json",
        beforeSend: function(){
        },
        success: function (data) {
            alert(data.groups[0].users[0]+" und "+data.groups[0].users[1]+" und auch "+data.groups[0].users[2]);
        }
    });
}
