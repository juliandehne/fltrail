$(document).ready(function () {
    var ejournalFeedback = new InscrybMDE({
        element: document.getElementById("ejournalFeedback"),
        spellChecker: false,
        //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
        minHeight: "80px",
    });
    var presentationFeedback = new InscrybMDE({
        element: document.getElementById("presentationFeedback"),
        spellChecker: false,
        //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
        minHeight: "80px",
    });
    var dossierFeedback = new InscrybMDE({
        element: document.getElementById("dossierFeedback"),
        spellChecker: false,
        //toolbar: ["bold", "italic", "heading", "|", "quote", "table", "code", "|" , "side-by-side", "fullscreen"],
        minHeight: "80px",
    });


    editor.style = "min-height: 100px";


    $('#submit').on('click',function(){
        document.location="project-student.jsp?token="+getUserTokenFromUrl();
    });
});