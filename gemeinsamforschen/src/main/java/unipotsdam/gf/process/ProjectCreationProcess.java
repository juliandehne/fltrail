package unipotsdam.gf.process;

import unipotsdam.gf.exceptions.*;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;


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

    @Inject
    private ICommunication iCommunication;

    @Inject
    private GFContexts gfContexts;

    /**
     * STEP 1
     *
     * @param project
     * @param author
     * @throws IOException
     */
    public void createProject(Project project, User author)
            throws IOException, RocketChatDownException, UserDoesNotExistInRocketChatException {
        project.setAuthorEmail(author.getEmail());
        try {
            iManagement.create(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        taskDao.createTaskWaitForParticipants(project, author);

        // create chatromm
        iCommunication.createEmptyChatRoom(project.getName(), false);

    }

    /**
     * STEP 2
     *
     * @param project
     * @param user
     */
    public void studentEntersProject(Project project, User user)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // student enters project
        iManagement.register(user, project, null);

        // create info for student
        Task task = taskDao.createWaitingForGroupFormationTask(project, user);

        // ev. notifity teacher for new student
        // ev. send email that he is now part of project and will be notified if something happens

        Boolean groupsCanBeFormed = constraintsImpl.checkIfGroupsCanBeFormed(project);
        if (groupsCanBeFormed) {
            GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
            if (!groupFormationMechanism.equals(GroupFormationMechanism.SingleUser) && !groupFormationMechanism
                    .equals(GroupFormationMechanism.Manual)) {
                taskDao.persistTeacherTask(project, TaskName.EDIT_FORMED_GROUPS, Phase.GroupFormation);
            } else {
                taskDao.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
                taskDao.updateForAll(task);
                //phases.endPhase(Phase.GroupFormation, project);
            }
        }
        iCommunication.addUserToChatRoom(user, project.getName());
    }

    public void createUser(User user)
            throws UserExistsInMysqlException, RocketChatDownException, UserExistsInRocketChatException {
        // todo implement
        if(iManagement.exists(user)) {
            throw new UserExistsInMysqlException();
        }
        // create user in rocket chat
        iCommunication.registerUser(user);
        // create user in mysql
        iManagement.create(user, null);

    }

    public Boolean authenticateUser(User user, HttpServletRequest req)
            throws UserDoesNotExistInRocketChatException, RocketChatDownException {
        // todo implement

        User isLoggedIn = iCommunication.loginUser(user);
        gfContexts.updateUserSessionWithRocketChat(req, isLoggedIn);
        gfContexts.updateUserWithEmail(req, isLoggedIn);
        return iManagement.exists(user);
    }

}
