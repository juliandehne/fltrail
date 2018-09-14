package unipotsdam.gf.interfaces;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.core.states.model.ProjectPhase;
import javax.inject.Inject;

public class PhaseTest {

    @Inject
    private IPhases phases;

    /**
     * Utility to creaty dummy data for students
     */
    static PodamFactory factory = new PodamFactoryImpl();


    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @BeforeClass
    public static void prepareProject() {
        ManagementImpl management = new ManagementImpl();
        User user = new User("julian", "1234", "from@stuff.com", false);
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = factory.manufacturePojo(Project.class);
        management.create(project);
        management.register(user, project, null);
    }

    @Test
    public void phase1() {
        Project project = new Project();
        project.setId("Gemainsam Forschen");
        phases.endPhase(ProjectPhase.CourseCreation, project);
    }

    @Test
    public void phase2() {
        Project project = new Project();
        project.setId("Gemainsam Forschen");
        phases.endPhase(ProjectPhase.GroupFormation, project);
    }

    @Test
    public void phase3() {
        Project project = new Project();
        project.setId("Gemainsam Forschen");
        phases.endPhase(ProjectPhase.DossierFeedback, project);
    }

    @Test
    public void phase4() {
        Project project = new Project();
        project.setId("Gemainsam Forschen");
        phases.endPhase(ProjectPhase.Execution, project);
    }

    @Test
    public void phase5() {
        Project project = new Project();
        project.setId("Gemainsam Forschen");
        phases.endPhase(ProjectPhase.Assessment, project);
    }
}
