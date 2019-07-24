class QuillJsObject {

    constructor() {
        this.ops = [];
    }

    addArrayEntryObject(...arrayEntryObject) {
        this.ops.push(arrayEntryObject);
    }
}

