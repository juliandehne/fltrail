package unipotsdam.gf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unipotsdam.gf.core.management.group.GroupDAOTest;
import unipotsdam.gf.core.management.project.ProjectDAOTest;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserDAOTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GroupDAOTest.class, ProjectDAOTest.class, UserDAOTest.class})
public class CoreTests {
}
