package unipotsdam.gf;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.wizard.Wizard;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class Wizardtest {

    @Inject
    Wizard wizard;

    @Before
    public void setUp() {
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @Test
    public void testGeneratedConcepts() {
        ConceptImporter conceptImporter = new ConceptImporter();
        List<String> numberedConcepts = conceptImporter.getNumberedConcepts(5);
        for (String numberedConcept : numberedConcepts) {
            System.out.println(numberedConcept);
        }
    }

    @Test
    public void testCompBase() throws Exception {
        Project project = new Project("wizard11");
        User user = new User("studentwizard394294@stuff.com");
        wizard.createMockDataForCompBase(project, user);

        //Project project = new Project("wizard11");
        /*CompBaseMatcher compBaseMatcher = new CompBaseMatcher();
        List<Group> wizard10 = compBaseMatcher.calculateGroups(project);
        boolean empty = wizard10.isEmpty();
        assertFalse(empty);*/

    }
}
