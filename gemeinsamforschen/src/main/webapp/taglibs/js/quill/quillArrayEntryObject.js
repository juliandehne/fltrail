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
            this.atrributes = {};
        }
        this.atrributes.underline = true;
        return this;
    }
}