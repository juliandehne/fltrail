package unipotsdam.gf.modules.tasks;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

public class GroupPhaseTaskTest {


    @Inject
    Management management;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    TaskDAO taskDAO;


    private PodamFactory factory = new PodamFactoryImpl();

    private User teacher;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void createCourse() {

        this.teacher = factory.manufacturePojo(User.class);
        teacher.setStudent(false);
        management.create(teacher, null);

        // add Titel
        Project project = factory.manufacturePojo(Project.class);
        project.setAuthorEmail(teacher.getEmail());
        management.create(project);
        management.register(teacher, project, null);

        ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        management.create(projectConfiguration, project);

        GroupfindingCriteria groupfindingCriteria = factory.manufacturePojo(GroupfindingCriteria.class);
        groupFinding.selectGroupfindingCriteria(groupfindingCriteria, project);

        taskDAO.createTaskWaitForParticipants(project, teacher);
        Task[] tasks = taskDAO.getTaskType(teacher, project);
        assertTrue(tasks != null && tasks.length > 0);


    }

}
