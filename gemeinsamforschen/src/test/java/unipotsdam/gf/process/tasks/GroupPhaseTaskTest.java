package unipotsdam.gf.process.tasks;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.ProjectCreationProcess;

import javax.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GroupPhaseTaskTest {


    @Inject
    Management management;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    TaskDAO taskDAO;

    @Inject
    private ProjectCreationProcess projectCreationProcess;


    private PodamFactory factory = new PodamFactoryImpl();

    private User teacher;

    @Before
    public void setUp() {
        /*final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());*/
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void createCourseWithLearningGoalStrategy() throws IOException {

        this.teacher = factory.manufacturePojo(User.class);
        teacher.setStudent(false);
        management.create(teacher, null);

        // add Titel
        Project project = factory.manufacturePojo(Project.class);
        project.setAuthorEmail(teacher.getEmail());
        management.create(project);

        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        projectConfiguration.setGroupMechanismSelected(GroupFormationMechanism.LearningGoalStrategy);
        projectCreationProcess.createProject(project, teacher);

        Task[] tasks = taskDAO.getTasks(teacher, project);
        assertTrue(tasks != null && tasks.length > 0);

        ArrayList<User> students = new ArrayList<>();
        for (int i = 0; i<5;i++) {
            User user = factory.manufacturePojo(User.class);
            user.setStudent(true);
            students.add(user);

            management.create(user, null);
            projectCreationProcess.studentEntersProject(project, user);
        }

    }

}
