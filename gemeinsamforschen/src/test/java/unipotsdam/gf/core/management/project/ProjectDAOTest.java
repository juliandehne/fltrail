package unipotsdam.gf.core.management.project;

import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.core.database.InMemoryMySqlConnect;

public class ProjectDAOTest {

    private InMemoryMySqlConnect inMemoryMySqlConnect;

    @Before
    public void setUp() {
        inMemoryMySqlConnect = new InMemoryMySqlConnect();
    }

    /**
     * Test creating a user in the DB
     */
    @Test
    public void testPersist() {
        ProjectDAO projectDAO = new ProjectDAO(inMemoryMySqlConnect);
        projectDAO.persist(new Project("Gemeinsam Forschen", "1235", true, "me", "keins"));

    }
}
