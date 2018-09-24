package unipotsdam.gf.core.management.project;

import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ProjectDAOTest {

    private InMemoryMySqlConnect inMemoryMySqlConnect;
    private ProjectDAO projectDAO;
    private Project project;

    static PodamFactory factory = new PodamFactoryImpl();

    @Before
    public void setUp() {
        inMemoryMySqlConnect = new InMemoryMySqlConnect();
        projectDAO = new ProjectDAO(inMemoryMySqlConnect);
        project = factory.manufacturePojo(Project.class);
    }

    @Test
    public void testPersist() {
        projectDAO.persist(project);
        inMemoryMySqlConnect.connect();
        String mysqlRequest = "SELECT * FROM projects where id = ? and adminPassword = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                inMemoryMySqlConnect.issueSelectStatement(mysqlRequest, project.getId(), project.getAdminPassword());
        boolean result = vereinfachtesResultSet.next();
        assertThat(result, is(true));
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
        Project projectActual = projectDAO.getProjectById(project.getId());

        assertEquals(project.getAdminPassword(), projectActual.getAdminPassword());
        assertEquals(project.getAuthor(), projectActual.getAuthor());
        assertEquals(project.getId(), projectActual.getId());
        assertEquals(project.getPassword(), projectActual.getPassword());
        assertEquals(project.getPhase(), projectActual.getPhase());
        assertEquals(project.isActive(), projectActual.isActive());
    }
}
