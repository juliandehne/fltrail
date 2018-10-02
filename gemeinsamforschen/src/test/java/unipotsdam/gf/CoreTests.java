package unipotsdam.gf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unipotsdam.gf.core.context.group.GroupDAOTest;
import unipotsdam.gf.core.context.project.ProjectDAOTest;
import unipotsdam.gf.core.context.user.UserDAOTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({GroupDAOTest.class, ProjectDAOTest.class, UserDAOTest.class})
public class CoreTests {
}
