package unipotsdam.gf.modules.wizard.compbase;

import java.io.UnsupportedEncodingException;

public class TomcatConceptImporter extends ConceptImporter {
    public TomcatConceptImporter() throws UnsupportedEncodingException {
    }

    @Override
    protected boolean isJUnitTest() {
        return false;
    }
}
