package unipotsdam.gf.process;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.UserExistsInMysqlException;
import unipotsdam.gf.exceptions.UserExistsInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.communication.model.RocketChatUser;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.modules.performance.PerformanceCandidates;
import unipotsdam.gf.modules.performance.PerformanceUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;

public class ProjectCreationProcess {


    @Inject
    private Management iManagement;

    @Inject
    private TaskDAO taskDao;


    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private ICommunication iCommunication;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private AnnotationController annotationController;

    /**
     * STEP 1
     *
     * @param project which project is created
     * @param author  who creates the project
     */
    public void createProject(Project project, User author, Integer groupSize)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        project.setAuthorEmail(author.getEmail());
        try {
            iManagement.create(project, groupSize);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
        annotationController.setAnnotationCategories(project);
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
    public synchronized void studentEntersProject(Project project, User user)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        PerformanceUtil.start(PerformanceCandidates.STUDENT_ENTERS_PROJECT);
        // student enters project
        iManagement.register(user, project, null);
        updateUserCreationTask(project, user);
        PerformanceUtil.stop(PerformanceCandidates.STUDENT_ENTERS_PROJECT);
    }

    public synchronized void createUser(User user)
            throws UserExistsInMysqlException, RocketChatDownException, UserExistsInRocketChatException {
        PerformanceUtil.start(PerformanceCandidates.CREATE_USER);
        if (iManagement.exists(user)) {
            throw new UserExistsInMysqlException();
        }
        // create user in rocket chat
        iCommunication.registerUser(user);
        // create user in mysql
        iManagement.create(user);
        PerformanceUtil.stop(PerformanceCandidates.CREATE_USER);
    }

    public Boolean authenticateUser(User user, HttpServletRequest req) {
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

    public synchronized void deleteUser(User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        PerformanceUtil.start(PerformanceCandidates.DELETE_USER);
        iManagement.delete(user);
        iCommunication.delete(user);
        PerformanceUtil.stop(PerformanceCandidates.DELETE_USER);
    }

    public void deleteProject(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        iManagement.delete(project);
        iCommunication.deleteChatRoom(project);
    }

    /**
     * the tasks are changed after user enters project
     *
     * @param project
     * @param user
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     */
    public void updateUserCreationTask(Project project, User user) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // create info for student
        taskDao.createWaitingForGroupFormationTask(project, user);

        iCommunication.addUserToChatRoom(user, project.getName());
    }

}
