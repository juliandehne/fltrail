package unipotsdam.gf;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unipotsdam.gf.core.management.group.GroupDAOTest;
import unipotsdam.gf.core.management.project.ProjectDAOTest;
import unipotsdam.gf.core.management.user.UserDAOTest;
import unipotsdam.gf.interfaces.ActivityFlowTest;
import unipotsdam.gf.interfaces.AnnotationTest;
import unipotsdam.gf.interfaces.PhaseTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ActivityFlowTest.class, AnnotationTest.class, PhaseTest.class})
public class IntegrationTests {
}
