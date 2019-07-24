class QuillArrayEntryObject {
    constructor() {
        this.insert = "";
    }

    static generateHeaderObject(headerLevel) {
        let headerObject = new QuillArrayEntryObject();
        headerObject.insert = '\n';
        headerObject.attributes = {};
        headerObject.attributes.header = headerLevel;
        return headerObject;
    }

    addUnderline() {
        if (!this.attributes) {
            this.attributes = {};
        }
        this.attributes.underline = true;
        return this;
    }
}