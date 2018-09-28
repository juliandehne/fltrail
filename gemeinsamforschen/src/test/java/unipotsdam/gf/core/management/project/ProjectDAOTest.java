package unipotsdam.gf.core.management.project;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ProjectDAOTest {

    @Inject
    private MysqlConnect inMemoryMySqlConnect;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;


    private Project project;

    PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        project = factory.manufacturePojo(Project.class);
        String authorEmail = project.getAuthorEmail();
        User user = factory.manufacturePojo(User.class);
        user.setEmail(authorEmail);
        userDAO.persist(user, null);

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
    public void testGetProjectById() {
        projectDAO.persist(project);
        Project projectActual = projectDAO.getProjectByName(project.getName());

        assertEquals(project.getAdminPassword(), projectActual.getAdminPassword());
        assertEquals(project.getAuthorEmail(), projectActual.getAuthorEmail());
        assertEquals(project.getName(), projectActual.getName());
        assertEquals(project.getPassword(), projectActual.getPassword());
        assertEquals(project.getPhase(), projectActual.getPhase());
        assertEquals(project.isActive(), projectActual.isActive());
    }
}
