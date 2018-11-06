package unipotsdam.gf.process.tasks;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.database.UpdateDB;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInMysqlException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupData;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.ProjectCreationProcess;

import javax.inject.Inject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
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

    @Inject
    private ICommunication communication;


    private PodamFactory factory = new PodamFactoryImpl();



    @Before
    public void setUp() throws IOException, SQLException, ManagedProcessException {
        UpdateDB.main(new String[0]);

        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);

    }

    @Ignore
    @Test
    public void createUser()
            throws RocketChatDownException, UserExistsInRocketChatException, UserExistsInMysqlException, UserDoesNotExistInRocketChatException {
        User teacher = factory.manufacturePojo(User.class);
        teacher.setEmail("vodka@yolo.com");
        teacher.setPassword("egal");
        teacher.setStudent(false);

        projectCreationProcess.deleteUser(teacher);
        projectCreationProcess.createUser(teacher);
        projectCreationProcess.deleteUser(teacher);
    }

    @Test
    public void createCourse()
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, UserExistsInMysqlException, UserExistsInRocketChatException, IOException {

        // create teacher
        User teacher = factory.manufacturePojo(User.class);
        teacher.setEmail("vodka@yolo.com");
        teacher.setPassword("egal");
        teacher.setStudent(false);
        projectCreationProcess.deleteUser(teacher);
        projectCreationProcess.createUser(teacher);

        Project project = factory.manufacturePojo(Project.class);
        project.setName("TEST");
        projectCreationProcess.deleteProject(project);
        projectCreationProcess.createProject(project, teacher);

        groupFormationProcess.setGroupFormationMechanism(GroupFormationMechanism.Manual, project);
        ArrayList<User> students = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = factory.manufacturePojo(User.class);
            user.setStudent(true);
            user.setRocketChatUsername("student" + i);
            user.setEmail("student" + i + "@stuff.com");
            user.setPassword("egal");
            projectCreationProcess.deleteUser(user);
            projectCreationProcess.createUser(user);
            projectCreationProcess.studentEntersProject(project, user);
            students.add(user);
        }

        //groupFormationProcess.changeGroupFormationMechanism(GroupFormationMechanism.Manual, project);
        GroupData orInitializeGroups = groupFormationProcess.getOrInitializeGroups(project);
        assertFalse(orInitializeGroups.getGroups().isEmpty());

        groupFormationProcess.finalize(project);

        ArrayList<Task> tasks = taskDAO.getTasks(teacher, project);
        assertTrue(tasks != null && tasks.size() > 0);

    /*    for (User student : students) {
            projectCreationProcess.deleteUser(student);
            projectCreationProcess.deleteProject(project);
        }*/

    }

}
