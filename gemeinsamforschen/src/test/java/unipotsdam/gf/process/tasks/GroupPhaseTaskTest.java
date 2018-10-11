package unipotsdam.gf.process.tasks;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;

import javax.inject.Inject;

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

    @Inject
    private GroupFormationProcess groupFormationProcess;


    private PodamFactory factory = new PodamFactoryImpl();

    private User teacher;

    @Before
    public void setUp() {
        /*final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());*/
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

        /*ProjectConfiguration projectConfiguration = factory.manufacturePojo(ProjectConfiguration.class);
        management.create(projectConfiguration, project);

        GroupfindingCriteria groupfindingCriteria = factory.manufacturePojo(GroupfindingCriteria.class);
        groupFinding.selectGroupfindingCriteria(groupfindingCriteria, project);*/

        taskDAO.createTaskWaitForParticipants(project, teacher);
        Task[] tasks = taskDAO.getTaskModes(teacher, project);
        assertTrue(tasks != null && tasks.length > 0);

        ArrayList<User> students = new ArrayList<>();
        for (int i = 0; i<5;i++) {
            User user = factory.manufacturePojo(User.class);
            user.setStudent(true);
            students.add(user);

            management.create(user, null);
            projectCreationProcess.studentEntersProject(project, user);
        }



        groupFormationProcess.changeGroupFormationMechanism(GroupFormationMechanism.Manual, project);
        Group group = new Group();
        for (User student : students) {
            group.addMember(student);
        }
        groupFormationProcess.finalizeGroups(project, group);

        groupFormationProcess.finish(project);

    }

}
