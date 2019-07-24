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

function clickReflectionQuestion(learningGoalIndex, reflectionQuestionIndex) {
    $(`#list-item-${learningGoalIndex}-${reflectionQuestionIndex}`).toggleClass('active');
    templateData.data[learningGoalIndex].reflectionQuestionWithAnswers[reflectionQuestionIndex].chosen = true;
}

function save() {
    let quillJsObject = new QuillJsObject();
    templateData.data.forEach(function (entry) {
        let chosenQuestionAnswersTemp = [];
        entry.reflectionQuestionWithAnswers.forEach(function (questionAndAnswer) {
            if (questionAndAnswer.chosen) {
                chosenQuestionAnswersTemp.push({
                    question: questionAndAnswer.question,
                    answer: questionAndAnswer.answer
                });
            }
        });

        //todo: reimplement later in backend

        // learning goal
        let learningGoalHeading = new QuillArrayEntryObject();
        learningGoalHeading.insert = entry.learningGoal.text;
        learningGoalHeading.addUnderline();
        let learningGoalHeader = QuillArrayEntryObject.generateHeaderObject(1);
        quillJsObject.addArrayEntryObjects(learningGoalHeading, learningGoalHeader);

        // learning goal result heading
        let learningGoalResultHeading = new QuillArrayEntryObject();
        learningGoalResultHeading.insert = "Lernziel-Ergebnis";
        learningGoalResultHeading.addUnderline();
        let learningGoalResultHeader = QuillArrayEntryObject.generateHeaderObject(2);
        quillJsObject.addArrayEntryObjects(learningGoalResultHeading, learningGoalResultHeader);

        // learning goal result
        quillJsObject.concatOpsArrays(JSON.parse(entry.learningGoalStudentResult.text).ops);

        if (chosenQuestionAnswersTemp.length > 0) {
            // reflection questions heading
            let reflectionQuestionHeadingText = new QuillArrayEntryObject();
            reflectionQuestionHeadingText.insert = "Reflexionsfragen";
            reflectionQuestionHeadingText.addUnderline();
            let reflectionQuestionHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);
            quillJsObject.addArrayEntryObjects(reflectionQuestionHeadingText, reflectionQuestionHeadingObject);
            let counter = 1;
            for (let questionAndAnswer of chosenQuestionAnswersTemp) {
                // reflecion question
                let reflectionQuestion = new QuillArrayEntryObject();
                reflectionQuestion.insert = `${counter}. ${questionAndAnswer.question.question}`;
                //reflectionQuestion.addUnderline();
                let reflectionQuestionHeader = QuillArrayEntryObject.generateHeaderObject(3);
                quillJsObject.addArrayEntryObjects(reflectionQuestion, reflectionQuestionHeader);

                // reflecion question answer
                quillJsObject.concatOpsArrays(JSON.parse(questionAndAnswer.answer.text).ops);
                counter++;
            }
        }

        quillJsObject.insertNewLine();

    });
    quill.setContents(quillJsObject);
    let html = quill.root.innerHTML;
    chooseAssessmentMaterial(html, function () {
        changeLocation();
    });

}


/*
{
    "ops": [
        {
            "attributes": {
                "underline": true
            },
            "insert": "Learning Goal"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "attributes": {
                "underline": true
            },
            "insert": "result überschrift"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "result\n"
        },
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