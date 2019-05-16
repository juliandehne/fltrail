package unipotsdam.gf.process;

import unipotsdam.gf.exceptions.*;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

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

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private ICommunication iCommunication;

    @Inject
    private GFContexts gfContexts;

    /**
     * STEP 1
     *
     * @param project which project is created
     * @param author  who creates the project
     */
    public void createProject(Project project, User author)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        project.setAuthorEmail(author.getEmail());
        try {
            iManagement.create(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        profileDAO.createNewSurveyProject(project);
        taskDao.createTaskWaitForParticipants(project, author);

        // create chatroom
        iCommunication.createEmptyChatRoom(project.getName(), false);
        iCommunication.addUserToChatRoom(author, project.getName());

    }

    /**
     * STEP 2
     *
     * @param project which project is entered
     * @param user    who is participates the project
     */
    public void studentEntersProject(Project project, User user)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // student enters project
        iManagement.register(user, project, null);
        updateProjCreaProcTasks(project, user);
    }

    public void createUser(User user)
            throws UserExistsInMysqlException, RocketChatDownException, UserExistsInRocketChatException {
        if (iManagement.exists(user)) {
            throw new UserExistsInMysqlException();
        }
        // create user in rocket chat
        iCommunication.registerUser(user);
        // create user in mysql
        iManagement.create(user);

    }

    public Boolean authenticateUser(User user, HttpServletRequest req)
            throws UserDoesNotExistInRocketChatException, RocketChatDownException {
        // todo implement

        RocketChatUser isLoggedIn = null;
        try {
            isLoggedIn = iCommunication.loginUser(user);
            gfContexts.updateUserSessionWithRocketChat(req, isLoggedIn);
        } catch (Exception e) {
            System.out.println("rocketchat funktioniert nicht ... mache trotzdem weiter");
        } finally {
            if (isLoggedIn != null) {
                gfContexts.updateUserWithEmail(req, isLoggedIn);
            } else {
                gfContexts.updateUserWithEmail(req, user);
            }
        }
        return iManagement.exists(user);
    }

    public void deleteUser(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        iManagement.delete(user);
        iCommunication.delete(user);
    }

    public void deleteProject(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // TODO implement
        iManagement.delete(project);
        iCommunication.deleteChatRoom(project);
    }

    /*  */

    /**
     * STEP N
     *
     * @param project the project to delete
     *//*
    public void deleteProject(Project project) {
        try {
            iManagement.delete(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        //taskDao.createTaskWaitForParticipants(project, author);
    }*/


    /**
     * the tasks are changed after user enters project
     * @param project
     * @param user
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     */
    public void updateProjCreaProcTasks(Project project, User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
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
        iCommunication.addUserToChatRoom(user, project.getName());
    }

}
