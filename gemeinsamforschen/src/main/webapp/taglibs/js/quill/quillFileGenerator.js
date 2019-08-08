async function generatePortfolioEntryFile(projectName) {
    getChosenGroupEntries(projectName, function (response) {
        let sortedByGroupId = {};
        response.forEach(element => {
            if (!sortedByGroupId[element.groupId]) {
                sortedByGroupId[element.groupId] = [];
            }
            sortedByGroupId[element.groupId].push(element);
        });
        Object.keys(sortedByGroupId).forEach(async groupId => {
            let quillJsObject = new QuillJsObject();
            let portfolioEntryHeadingText = new QuillArrayEntryObject();
            portfolioEntryHeadingText.insert = "Portfolio-Einträge";
            portfolioEntryHeadingText.addUnderline();
            let portfolioHeadingObject = QuillArrayEntryObject.generateHeaderObject(2);
            quillJsObject.addArrayEntryObjects(portfolioEntryHeadingText, portfolioHeadingObject);
            quillJsObject.insertNewLine();
            let counter = 1;
            sortedByGroupId[groupId].forEach(entry => {
                let entryHeading = new QuillArrayEntryObject();
                entryHeading.insert = `${counter}. Eintrag`;
                entryHeading.addUnderline();
                quillJsObject.addArrayEntryObjects(entryHeading);
                quillJsObject.insertNewLine();
                quillJsObject.concatOpsArrays(JSON.parse(entry.text).ops);
                quillJsObject.insertNewLine();
                counter++
            });
            quill.setContents(quillJsObject);
            let html = quill.root.innerHTML;
            await saveGroupSelection(projectName, groupId, html);
        });
    });
}