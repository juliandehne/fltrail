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
    $(`#list-item-reflection-question-${reflectionQuestionIndex}`).toggleClass('active');
    templateData.data.reflectionQuestionWithAnswerList[reflectionQuestionIndex].chosen = true;
}

function clickPortfolioEntry(portfolioEntryIndex) {
    $(`#list-item-portfolio-entry-${portfolioEntryIndex}`).toggleClass('active');
    templateData.data.portfolioEntries[portfolioEntryIndex].chosen = true;
}

function save() {
    let quillJsObject = new QuillJsObject();
    let chosenQuestionsAnswers = [];
    templateData.data.reflectionQuestionWithAnswerList.forEach(function (questionAndAnswer) {
        if (questionAndAnswer.chosen) {
            chosenQuestionsAnswers.push({
                question: questionAndAnswer.question,
                answer: questionAndAnswer.answer
            });
        }
    });
    let chosenPortfolioEntries = [];
    templateData.data.portfolioEntries.forEach(function (portfolioEntry) {
        if (portfolioEntry.chosen) {
            chosenPortfolioEntries.push({
                text: portfolioEntry.text,
            });
        }
    });
    //todo: reimplement later in backend, add functions to add a header and replace .insert with function for "addText"
    if (chosenQuestionsAnswers.length > 0) {
        // reflection questions heading
        let reflectionQuestionHeadingText = new QuillArrayEntryObject();
        reflectionQuestionHeadingText.insert = "Reflexionsfragen";
        reflectionQuestionHeadingText.addUnderline();
        let reflectionQuestionHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);
        quillJsObject.addArrayEntryObjects(reflectionQuestionHeadingText, reflectionQuestionHeadingObject);
        quillJsObject.insertNewLine();
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

        if (chosenPortfolioEntries.length > 0) {
            let portfolioEntryHeadingText = new QuillArrayEntryObject();
            portfolioEntryHeadingText.insert = "Portfolio-Einträge";
            portfolioEntryHeadingText.addUnderline();
            let portfolioEntryHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);
            quillJsObject.addArrayEntryObjects(portfolioEntryHeadingText, portfolioEntryHeadingObject);
            quillJsObject.insertNewLine();
            let counter = 1;
            for (let portfolioEntry of chosenPortfolioEntries) {
                // portfolioEntry
                let portfolioEntryHeadingText = new QuillArrayEntryObject();
                portfolioEntryHeadingText.insert = `${counter}. Eintrag`;
                portfolioEntryHeadingText.addUnderline();
                quillJsObject.addArrayEntryObjects(portfolioEntryHeadingText);
                quillJsObject.insertNewLine();
                quillJsObject.concatOpsArrays(JSON.parse(portfolioEntry.text).ops);
                quillJsObject.insertNewLine();
                counter++;
            }
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