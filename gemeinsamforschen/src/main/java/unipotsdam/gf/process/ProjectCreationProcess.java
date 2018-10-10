package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import java.io.IOException;


@Singleton
public class ProjectCreationProcess {


    @Inject
    private ConstraintsImpl constraintsImpl;

    @Inject
    private Management iManagement;

    @Inject
    private TaskDAO taskDao;

    /**
     * STEP 1
     * @param project
     * @param author
     * @throws IOException
     */
    public void createProject(Project project, User author) throws IOException {
        project.setAuthorEmail(author.getEmail());
        try {
            iManagement.create(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        taskDao.createTaskWaitForParticipants(project,author);
    }

    /**
     * STEP 2
     * @param project
     * @param user
     */
    public void studentEntersProject(Project project, User user) {
        // student enters project
        iManagement.register(user, project, null);

        // create info for student
        taskDao.createWaitingForGroupFormationTask(project, user);

        // ev. notifity teacher for new student
        // ev. send email that he is now part of project and will be notified if something happens
        Boolean groupsCanBeFormed = constraintsImpl.checkIfGroupsCanBeFormed(project);
        if (groupsCanBeFormed) {
            taskDao.persistTeacherTask(project, TaskName.EDIT_FORMED_GROUPS, Phase.GroupFormation);
        }
    }
}
