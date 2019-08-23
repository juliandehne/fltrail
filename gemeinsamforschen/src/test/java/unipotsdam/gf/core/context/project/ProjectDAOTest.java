package unipotsdam.gf.core.context.project;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.project.ProjectDAO;

import javax.inject.Inject;

import java.io.IOException;
import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ProjectDAOTest {

    @Inject
    private MysqlConnect inMemoryMySqlConnect;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;


    @Inject
    private Management management;

    private Project project;

    PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() throws IOException, SQLException, ManagedProcessException {

        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);

        project = factory.manufacturePojo(Project.class);
        String authorEmail = project.getAuthorEmail();
        User user = factory.manufacturePojo(User.class);
        user.setEmail(authorEmail);
        userDAO.persist(user);

    }

    @Test
    public void testExists() {
        projectDAO.persist(project);
        assertThat(projectDAO.exists(project), is(true));
    }

    @Test
    public void testDelete() {
        projectDAO.persist(project);
        assertThat(projectDAO.exists(project), is(true));

        projectDAO.delete(project);
        assertThat(projectDAO.exists(project), is(false));
    }

    @Test
    public void testGetProjectById() throws Exception {
        projectDAO.persist(project);
        Project projectActual = projectDAO.getProjectByName(project.getName());

        assertEquals(project.getAuthorEmail(), projectActual.getAuthorEmail());
        assertEquals(project.getName(), projectActual.getName());
        assertEquals(project.getPassword(), projectActual.getPassword());
        assertEquals(project.getPhase(), projectActual.getPhase());
        assertEquals(project.isActive(), projectActual.isActive());
    }

    @Test
    public void testRegister() {
        User user = factory.manufacturePojo(User.class);
        user.setStudent(true);
        userDAO.persist(user);

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
