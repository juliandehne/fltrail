package unipotsdam.gf.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.assessment.UserAssessmentDataHolder;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
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

    @Inject
    private ProjectDAO projectDAO;


    private final static Logger log = LoggerFactory.getLogger(PeerAssessmentProcess.class);

    /**
     * start THE PEER ASSESSMENT PHASE
     *
     * @param project of interest
     */
    public void startPeerAssessmentPhase(Project project) {
        // distribute upload tasks
        taskDAO.persistTaskForAllGroups(project, UPLOAD_PRESENTATION, Phase.Assessment);
        // distribute teacher tasks
        taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Phase.Assessment);

        log.debug("started peer assessment phase for project" + project.getName());
    }

    /**
     * this action is triggered by the file API if assessment relevant actions have
     * been taken
     *
     * @param fileRole role of file like DOSSIER, PRESENTATION or so
     * @param userFromSession currently logged in user
     * @param project of interest
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
     * @param contributionRatings is a map which connects the file, with an integer rating
     * @param groupId that receives a rating
     * @param project of interest
     * @param user fromPeer or fromTeacher
     */
    public void postContributionRating(
            Map<FileRole, Integer> contributionRatings, String groupId, Project project, String user, Boolean
            isStudent) throws Exception {
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
     * this is triggered if a presentation has been uploaded for a group
     *
     * @param userFromSession this user uploaded a report for his / her group
     * @param project of interest
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
     * @param userFromSession this user uploaded a report for his / her group
     * @param project of interest
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
     * @param project of interest
     * @param user the user who rates another
     * @param feedbackedUser the user who is rated here
     * @param data a map which connects values (e.g. "compatibility", "punctual") with integer values
     */
    public void persistInternalAssessment(
            Project project, User user, User feedbackedUser, HashMap<String, Integer> data) throws Exception {
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
     * @param project of interest
     * @param user who will rate the next user
     * @return the next user, who will receive a rating
     */
    public User getNextUserToRateInternally(Project project, User user) {
        return assessmentDAO.getNextGroupMemberToFeedback(user, project);
    }

    /**
     * @param project of interest
     */
    public void startStudentAssessments(Project project) throws Exception {

        // change tasks for docent

        taskDAO.updateTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Progress.FINISHED);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_PEER_ASSESSMENTS_PHASE, Phase.Assessment);

        // set assessment tasks for students
        taskDAO.persistMemberTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT, Phase.Assessment);
        taskDAO.persistMemberTask(project, TaskName.GIVE_INTERNAL_ASSESSMENT, Phase.Assessment);

        // set assessment tasks for students
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            Integer groupId = groupDAO.getMyGroupId(user, project);
            taskMapper.persistTaskMapping(project, user, groupId, TaskName.GIVE_EXTERNAL_ASSESSMENT);
        }

        log.debug("finished uploading files for assessement for project" + project.getName());
    }

    /**
     * this starts the grading 'phase'
     * should be visualized as such in the UI but is treated here as a subphase
     *
     * @param project of interest
     */
    public void startDocentGrading(Project project) throws Exception {
        Task task = new Task(TaskName.GIVE_EXTERNAL_ASSESSMENT, null, project, Progress.FINISHED);
        taskDAO.updateForAll(task);
        Task task1 = new Task(TaskName.GIVE_INTERNAL_ASSESSMENT, null, project, Progress.FINISHED);
        taskDAO.updateForAll(task1);

        // update task for docent
        taskDAO.updateTeacherTask(project, TaskName.CLOSE_PEER_ASSESSMENTS_PHASE, Progress.FINISHED);
        log.info("finished asessment process for project" + project.getName());

        // tut nicht viel gerade
        //phases.endPhase(Phase.Assessment, project);

        taskDAO.persistTeacherTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER, Phase.GRADING);
    }

    /**
     *
     * @param project of interest
     */
    private void giveFinalGrades(Project project) throws Exception {
        taskDAO.updateTeacherTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT_TEACHER, Progress.FINISHED);
        taskDAO.persistTeacherTask(project, TaskName.GIVE_FINAL_GRADES, Phase.GRADING);
    }

    /**
     *
     * @param project of interest
     * @param userAssessmentDataHolder  holds all ratings, including the final mark. If marked as final, project is done
     */
    public void saveGrades(Project project, UserAssessmentDataHolder userAssessmentDataHolder) throws Exception {
        assessmentDAO.saveGrades(project, userAssessmentDataHolder);
        if (userAssessmentDataHolder.getFinal()) {
            taskDAO.updateTeacherTask(project, TaskName.GIVE_FINAL_GRADES, Progress.FINISHED);
            taskDAO.persistTeacherTask(project, TaskName.END_DOCENT, Phase.GRADING);
            taskDAO.persistMemberTask(project, TaskName.END_STUDENT, Phase.GRADING);
            taskDAO.persistTeacherTask(project, TaskName.EVALUATION_TECHNISCH, Phase.GRADING);
        }
    }
}
