package unipotsdam.gf;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.wizard.Wizard;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;
import unipotsdam.gf.process.ExecutionProcess;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Wizardtest {

    @Inject
    ExecutionProcess executionProcess;

    @Inject
    Wizard wizard;

    @Before
    public void setUp() {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);
    }

    @Test
    public void testGeneratedConcepts() throws UnsupportedEncodingException {
        ConceptImporter conceptImporter = new ConceptImporter();
        List<String> numberedConcepts = conceptImporter.getNumberedConcepts(5);
        for (String numberedConcept : numberedConcepts) {
            System.out.println(numberedConcept);
        }
    }

    @Test
    public void testCompBase() throws Exception {
        Project project = new Project("wizard16");
        User user = new User("studentwizard394294@stuff.com");
        wizard.createMockDataForCompBase(project, user);

        //Project project = new Project("wizard11");
        /*CompBaseMatcher compBaseMatcher = new CompBaseMatcher();
        List<Group> wizard10 = compBaseMatcher.calculateGroups(project);
        boolean empty = wizard10.isEmpty();
        assertFalse(empty);*/

    }

    @Test
    public void generateCorrectQuillContent() throws IOException {
        LoremIpsum loremIpsum = new LoremIpsum();
        String text = loremIpsum.getWords(500);
        String s = Wizard.convertTextToQuillJs(text);
        System.out.println(s);
    }

    @Test
    public void quickStartReflectionPhase() {
        executionProcess.start(new Project("qweqweqweqweqwqqqqq"));
    }
}
