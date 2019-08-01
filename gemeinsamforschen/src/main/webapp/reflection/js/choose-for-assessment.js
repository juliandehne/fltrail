let templateData = {};

$(document).ready(function () {
    //todo: add portfolio-entries
    getMaterialForAssessment(function (response) {
        templateData.data = response;
        templateData.extraData = {scriptBegin: "<script>", scriptEnd: "</script>"};

        let tmpl = $.templates("#assessmentTemplate");
        let html = tmpl.render(templateData);
        $("#assessmentTemplateResult").html(html);
    })
});

function skip() {
    chooseAssessmentMaterial(function () {
        location.href = "../project/tasks-student.jsp?projectName=" + $('#projectName').html().trim();
    })
}

function clickReflectionQuestion(reflectionQuestionIndex) {
    $(`#list-item-${reflectionQuestionIndex}`).toggleClass('active');
    templateData.data[reflectionQuestionIndex].chosen = true;
}

function save() {
    let quillJsObject = new QuillJsObject();
    let chosenQuestionsAnswers = [];
    templateData.data.forEach(function (questionAndAnswer) {
        if (questionAndAnswer.chosen) {
            chosenQuestionsAnswers.push({
                question: questionAndAnswer.question,
                answer: questionAndAnswer.answer
            });
        }
    });
    //todo: reimplement later in backend
    if (chosenQuestionsAnswers.length > 0) {
        // reflection questions heading
        let reflectionQuestionHeadingText = new QuillArrayEntryObject();
        reflectionQuestionHeadingText.insert = "Reflexionsfragen";
        reflectionQuestionHeadingText.addUnderline();
        let reflectionQuestionHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);
        quillJsObject.addArrayEntryObjects(reflectionQuestionHeadingText, reflectionQuestionHeadingObject);
        let counter = 1;
        for (let questionAndAnswer of chosenQuestionsAnswers) {
            // reflecion question
            let reflectionQuestion = new QuillArrayEntryObject();
            reflectionQuestion.insert = `${counter}. ${questionAndAnswer.question.question}`;
            //reflectionQuestion.addUnderline();
            let reflectionQuestionHeader = QuillArrayEntryObject.generateHeaderObject(3);
            quillJsObject.addArrayEntryObjects(reflectionQuestion, reflectionQuestionHeader);

            // reflecion question answer
            quillJsObject.concatOpsArrays(JSON.parse(questionAndAnswer.answer.text).ops);
            quillJsObject.insertNewLine();
            counter++;
        }
        quill.setContents(quillJsObject);
        let html = quill.root.innerHTML;
        chooseAssessmentMaterial(html, function () {
            changeLocation();
        });
    } else {
        alert('Sie müssen mindestens eine Reflexionsfrage zur Bewertung abgeben.')
    }


}


/*
{
    "ops": [
        {
            "attributes": {
                "underline": true
            },
            "insert": "reflection question überschrift"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "reflection question"
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "reflection q answer\n\n"
        }
    ]
}
 */