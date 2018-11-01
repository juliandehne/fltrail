package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.HashMap;

import static unipotsdam.gf.modules.group.GroupFormationMechanism.SingleUser;


@Singleton
public class ProjectCreationProcess {


    @Inject
    private ConstraintsImpl constraintsImpl;

    @Inject
    private Management iManagement;

    @Inject
    private TaskDAO taskDao;

    @Inject
    private GroupDAO groupDAO;


    /**
     * STEP 1
     *
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
        taskDao.createTaskWaitForParticipants(project, author);
        HashMap<Phase, Boolean> phase = new HashMap<>();
        phase.put(project.getPhase(), true);
        HashMap<Category, Boolean> category = new HashMap<>();
        category.put(Category.TITEL, true);
        ProjectConfiguration projectConfiguration = new ProjectConfiguration(
                phase,
                category,
                AssessmentMechanism.PEER_ASSESSMENT,   //todo: its just a default by now. correct this
                GroupFormationMechanism.SingleUser);   //todo: its just a default by now. fix it
        iManagement.create(projectConfiguration, project);
    }

    /**
     * STEP 2
     *
     * @param project
     * @param user
     */
    public void studentEntersProject(Project project, User user) {
        // student enters project
        iManagement.register(user, project, null);

        // create info for student
        Task task = taskDao.createWaitingForGroupFormationTask(project, user);

        // ev. notifity teacher for new student
        // ev. send email that he is now part of project and will be notified if something happens

        Boolean groupsCanBeFormed = constraintsImpl.checkIfGroupsCanBeFormed(project);
        if (groupsCanBeFormed) {
            GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
            if (!groupFormationMechanism.equals(SingleUser) && !groupFormationMechanism
                    .equals(GroupFormationMechanism.Manual)) {
                taskDao.persistTeacherTask(project, TaskName.EDIT_FORMED_GROUPS, Phase.GroupFormation);
            } else {
                taskDao.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
                taskDao.updateForAll(task);
                //phases.endPhase(Phase.GroupFormation, project);
            }
        }
    }

    /**
     * STEP N
     *
     * @param project
     * @throws IOException
     */
    public void deleteProject(Project project) throws IOException {
        try {
            iManagement.delete(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        //taskDao.createTaskWaitForParticipants(project, author);
    }
}
