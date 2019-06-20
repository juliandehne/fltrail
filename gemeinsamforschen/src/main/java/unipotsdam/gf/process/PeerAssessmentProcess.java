package unipotsdam.gf.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static unipotsdam.gf.process.tasks.TaskName.UPLOAD_PRESENTATION;

public class PeerAssessmentProcess {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private TaskMapper taskMapper;

    @Inject
    private IPeerAssessment peer;

    @Inject
    private AssessmentDAO assessmentDAO;


    private final static Logger log = LoggerFactory.getLogger(PeerAssessmentProcess.class);

    /**
     * start THE PEER ASSESSMENT PHASE
     *
     * @param project
     */
    public void startPeerAssessmentPhase(Project project) {
        // distribute upload tasks
        taskDAO.persistTaskForAllGroups(project, UPLOAD_PRESENTATION, Phase.Assessment);
        // distribute teacher tasks
        taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Phase.Assessment);

        log.info("started peer assessment phase for project" + project.getName());
    }

    /**
     * this action is triggered by the file API if assessment relevant actions have
     * been taken
     *
     * @param fileRole
     * @param userFromSession
     * @param project
     */
    public void fileHasBeenUploaded(
            FileRole fileRole, User userFromSession, Project project) {
        switch (fileRole) {
            case PRESENTATION:
                finishPresentationUpload(userFromSession, project);
                break;
            case FINAL_REPORT:
                finishReportUpload(userFromSession, project);
                break;
            default:
        }
    }

    /**
     * this is triggered if a peer rated one of the products
     *
     * @param contributionRatings
     * @param groupId
     * @param project
     * @param user fromPeer or fromTeacher
     */
    public void postContributionRating(
            Map<FileRole, Integer> contributionRatings, String groupId, Project project, String user, Boolean
            isStudent) {
        peer.postContributionRating(project, groupId, user, contributionRatings, isStudent);
        if (isStudent) {
            // finish task for user
            taskDAO.updateForUser(
                    new Task(TaskName.GIVE_EXTERNAL_ASSESSMENT, new User(user), project, Progress.FINISHED));
            // start internal evaluation task
            taskDAO.createUserDefault(project, new User(user), TaskName.GIVE_INTERNAL_ASSESSMENT, Phase.Assessment, Progress.JUSTSTARTED);
        } else {
            if (assessmentDAO.getNextGroupToFeedbackForTeacher(project).getObjectGroup() == null) {
                // if there are no more groups to rate products for the teacher
                giveFinalGrades(project);
            }
        }
    }

    /**
     *
     * @param project
     */
    public void giveFinalGrades(Project project) {
        taskDAO.updateTeacherTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER, Progress.FINISHED);
        taskDAO.persistTeacherTask(project, TaskName.GIVE_FINAL_GRADES, Phase.GRADING);
    }

    /**
     * this is triggered if a presentation has been uploaded for a group
     *
     * @param userFromSession
     * @param project
     */
    private void finishPresentationUpload(User userFromSession, Project project) {
        // get the group the user is in the project
        Integer groupByStudent = groupDAO.getGroupByStudent(project, userFromSession);
        // update the task for all the group members
        taskDAO.updateGroupTask(
                new GroupTask(TaskName.UPLOAD_PRESENTATION, groupByStudent, Progress.FINISHED, project));
        // set new tasks
        taskDAO.persistTaskForAllGroups(project, TaskName.UPLOAD_FINAL_REPORT, Phase.Assessment);
    }

    /**
     * this is triggered if the final report has been uploaded
     *
     * @param userFromSession
     * @param project
     */
    private synchronized void finishReportUpload(User userFromSession, Project project) {
        // get the group the user is in the project
        Integer groupByStudent = groupDAO.getGroupByStudent(project, userFromSession);
        // update the task for all the group members
        taskDAO.updateGroupTask(
                new GroupTask(TaskName.UPLOAD_FINAL_REPORT, groupByStudent, Progress.FINISHED, project));

    }


    /**
     * this is called if a student rated the group work of a fellow student
     *
     * @param project
     * @param user
     * @param feedbackedUser
     * @param data
     */
    public void persistInternalAssessment(
            Project project, User user, User feedbackedUser, HashMap<String, String> data) {
        assessmentDAO.persistInternalAssessment(project, user, feedbackedUser, data);
        User nextUserToRateInternally = getNextUserToRateInternally(project, user);
        if (nextUserToRateInternally == null) {
            taskDAO.updateForUser(new Task(TaskName.GIVE_INTERNAL_ASSESSMENT, user, project, Progress.FINISHED));
            taskDAO.persist(project, user, TaskName.WAIT_FOR_GRADING, Phase.Assessment);
        }
    }

    /**
     * this produces the next user in a group to be rated for group work
     *
     * @param project
     * @param user
     * @return
     */
    public User getNextUserToRateInternally(Project project, User user) {
        return assessmentDAO.getNextGroupMemberToFeedback(user, project);
    }

    /**
     * TODO @Julian rename to start student assessments
     *
     * @param project
     */
    public void startGrading(Project project) {

        // change tasks for docent

        taskDAO.updateTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Progress.FINISHED);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_PEER_ASSESSMENTS_PHASE, Phase.Assessment);

        // set assessment tasks for students
        taskDAO.persistMemberTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT, Phase.Assessment);
        taskDAO.persistMemberTask(project, TaskName.GIVE_INTERNAL_ASSESSMENT, Phase.Assessment);

        // set assessment tasks for students
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            taskMapper.persistTaskMapping(project, user, TaskName.GIVE_EXTERNAL_ASSESSMENT);
        }

        log.info("finished uploading files for assessement for project" + project.getName());
    }

    /**
     * this starts the grading 'phase'
     * should be visualized as such in the UI but is treated here as a subphase
     *
     * @param project
     */
    public void startDocentGrading(Project project)
            throws UserDoesNotExistInRocketChatException, JsonProcessingException, WrongNumberOfParticipantsException, RocketChatDownException, JAXBException {
        // update task for docent
        taskDAO.updateTeacherTask(project, TaskName.CLOSE_PEER_ASSESSMENTS_PHASE, Progress.FINISHED);
        log.info("finished asessment process for project" + project.getName());

        // tut nicht viel gerade
        //phases.endPhase(Phase.Assessment, project);

        taskDAO.persistTeacherTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER, Phase.GRADING);
    }

}
