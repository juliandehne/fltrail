package unipotsdam.gf.interfaces;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;

public class PhaseTest {

    @Inject
    private IPhases phases;

    /**
     * Utility to creaty dummy data for students
     */
    static PodamFactory factory = new PodamFactoryImpl();

    @Inject
    Management management;


    public static String projectName = "Gemainsam Forschen";

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);


        User user = factory.manufacturePojo(User.class);
        user.setStudent(true);
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = factory.manufacturePojo(Project.class);
        project.setName(projectName);
        management.create(project);
        management.register(user, project, null);
    }

    @Test
    public void phase1() {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.CourseCreation, project);
    }

    @Test
    public void phase2() {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.GroupFormation, project);
    }

    @Test
    public void phase3() {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.DossierFeedback, project);
    }

    @Test
    public void phase4() {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.Execution, project);
    }

    @Test
    public void phase5() {
        Project project = new Project();
        project.setName(projectName);
        phases.endPhase(Phase.Assessment, project);
    }
}
