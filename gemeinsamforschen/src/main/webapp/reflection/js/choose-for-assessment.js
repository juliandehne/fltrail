let templateData = {};

$(document).ready(function () {
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
    let chosenAnswersPerLearningGoal = [];
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
        chosenAnswersPerLearningGoal.push({
            learningGoal: entry.learningGoal,
            learningGoalStudentResult: entry.learningGoalStudentResult,
            reflectionQuestionsWithAnswers: chosenQuestionAnswersTemp
        });
    });

}

//reimplement in backend
/*
        // learning goal
        let learningGoalHeading = new QuillArrayEntryObject();
        learningGoalHeading.insert = entry.learningGoal.text;
        learningGoalHeading.addUnderline();
        let learningGoalHeader = QuillArrayEntryObject.generateHeaderObject(1);
        quillJsObject.addArrayEntryObject(learningGoalHeading,learningGoalHeader);

        // learning goal result heading
        let learningGoalResultHeading = new QuillArrayEntryObject();
        learningGoalResultHeading.insert = "Lernziel-Ergebnis";
        learningGoalResultHeading.addUnderline();
        let learningGoalResultHeader = QuillArrayEntryObject.generateHeaderObject(2);
        quillJsObject.addArrayEntryObject(learningGoalResultHeading,learningGoalResultHeader);

        // learning goal result
        quillJsObject.addArrayEntryObject(entry.learningGoalStudentResult.text);

        if (chosenQuestionAnswersTemp.length > 0) {

            // reflection questions heading
            let reflectionQuestionHeadingText = new QuillArrayEntryObject();
            reflectionQuestionHeadingText.insert = "Reflexionsfragen";
            reflectionQuestionHeadingText.addUnderline();
            let reflectionQuestionHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);

            for (let questionAndAnswer of chosenQuestionAnswersTemp) {

            }



         */


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