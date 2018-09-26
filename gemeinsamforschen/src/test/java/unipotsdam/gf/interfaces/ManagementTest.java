package unipotsdam.gf.interfaces;

import org.junit.Before;
//import javafx.application.Application;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.util.TestHelper;

import static org.junit.Assert.assertNotNull;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dehne on 01.06.2018.
 */


public class ManagementTest  {

    /**
     * Utility to creaty dummy data for students
     */
    private PodamFactory factory = new PodamFactoryImpl();
    private ManagementImpl management;

    @Before
    public void setUp() throws Exception {
        management = TestHelper.getManagementImpl();
    }

    @Test
    public void testRegister() {
        User user = new User("julian", "1234", "from@stuff.com", true);
        management.create(user, new UserProfile());
        assert management.exists(user);

        Project project = new Project("Gemainsam Forschen", "1235", true, "me", "keins");
        management.create(project);
        management.register(user, project, null);
    }

    @Test
    public void testProjectConfiguration() {
        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        Project project = factory.manufacturePojo(Project.class);

        management.create(projectConfiguration, project);

        ProjectConfiguration projectConfiguration1 = management.getProjectConfiguration(project);
        assertNotNull(projectConfiguration1.getCriteriaSelected());
        assertNotNull(projectConfiguration1.getAssessmentMechanismSelected());
        assertNotNull(projectConfiguration1.getGroupMechanismSelected());
        assertNotNull(projectConfiguration1.getPhasesSelected());

    }
}