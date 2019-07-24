class QuillJsObject {

    constructor() {
        this.ops = [];
    }

    addArrayEntryObjects(...arrayEntryObjects) {
        for (let entry of arrayEntryObjects) {
            this.ops.push(entry);
        }

    }

    concatOpsArrays(opsArray) {
        this.ops = this.ops.concat(opsArray);
    }

    insertNewLine() {
        this.ops.push({insert: '\n'});
    }
}

