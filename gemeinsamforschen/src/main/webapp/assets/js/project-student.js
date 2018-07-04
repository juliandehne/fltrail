$(document).ready(function(){
    /*
    var memberTable = $('#myGroupMembers');
    memberTable.hide();
    $('#nextPhase').on('click',function(){
        memberTable.show();
    });
    */
    $('.givefeedback').click(function () {
        location.href="givefeedback.jsp?token="+getUserTokenFromUrl();
    });
    $('.viewfeedback').click(function () {
        location.href="viewfeedback.jsp?token="+getUserTokenFromUrl();
    });

    $('.annotationview').click(function () {
        location.href="annotation-document.jsp?token="+getUserTokenFromUrl();
    });

    $('#btnUnstructuredUpload').click(function () {
        location.href="upload-unstructured.jsp?token="+getUserTokenFromUrl();
    })

    $('.viewprojectstudent').click(function () {
        location.href="project-student.jsp?token="+getUserTokenFromUrl();
    })
});