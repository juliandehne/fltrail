package unipotsdam.gf;

import org.junit.Test;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;

import java.util.List;

public class Wizardtest {

    @Test
    public void testGeneratedConcepts() {
        ConceptImporter conceptImporter = new ConceptImporter();
        List<String> numberedConcepts = conceptImporter.getNumberedConcepts(5);
        for (String numberedConcept : numberedConcepts) {
            System.out.println(numberedConcept);
        }
    }
}
