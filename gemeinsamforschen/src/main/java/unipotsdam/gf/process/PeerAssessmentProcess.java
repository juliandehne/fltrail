package unipotsdam.gf.process;

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
import javax.ws.rs.PathParam;

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

    /**
     * start THE PEER ASSESSMENT PHASE
     * @param project
     */
    public void startPeerAssessmentPhase(Project project) {
        // distribute upload tasks
        taskDAO.persistTaskForAllGroups(project, UPLOAD_PRESENTATION, Phase.Assessment);
        // distribute teacher tasks
        taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Phase.Assessment);
    }

    /**
     * this action is triggered by the file API if assessment relevant actions have
     * been taken
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
     * @param contributionRatings
     * @param groupId
     * @param projectName
     * @param fromPeer
     */
    public void postContributionRating(Map<FileRole, Integer> contributionRatings,
                                       String groupId,
                                        String projectName,
                                      String fromPeer) {
        peer.postContributionRating(new Project(projectName), groupId, fromPeer, contributionRatings);
        // finish task for user
        taskDAO.updateForUser(new Task(TaskName.GIVE_EXTERNAL_ASSESSMENT, fromPeer, projectName, Progress.FINISHED));
        // start internal evaluation task
        taskDAO.persist(new Task(TaskName.GIVE_INTERNAL_ASSESSMENT, fromPeer, projectName, Progress.JUSTSTARTED));

    }

    /**
     * this is triggered if a presentation has been uploaded for a group
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
     * @param userFromSession
     * @param project
     */
    private synchronized void finishReportUpload(User userFromSession, Project project) {
        // get the group the user is in the project
        Integer groupByStudent = groupDAO.getGroupByStudent(project, userFromSession);
        // update the task for all the group members
        taskDAO.updateGroupTask(
                new GroupTask(TaskName.UPLOAD_FINAL_REPORT, groupByStudent, Progress.FINISHED, project));
        // set new tasks

    }


    /**
     * TODO @Julian rename to start student assessments
     * @param project
     */
    public void startGrading(Project project) {

        // set assessment tasks for students
        taskDAO.persistMemberTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT, Phase.Assessment);
        taskDAO.persistMemberTask(project, TaskName.GIVE_INTERNAL_ASSESSMENT, Phase.Assessment);

        // set assessment tasks for students
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            taskMapper.persistTaskMapping(project, user, TaskName.GIVE_EXTERNAL_ASSESSMENT);
        }
    }

    /**
     * this is called if a student rated the group work of a fellow student
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
             taskDAO.updateForUser(new Task(TaskName.GIVE_INTERNAL_ASSESSMENT, project, Progress.FINISHED));
             taskDAO.persist(project, user, TaskName.WAIT_FOR_GRADING, Phase.Assessment);
         }
    }

    /**
     * this produces the next user in a group to be rated for group work
     * @param project
     * @param user
     * @return
     */
    public User getNextUserToRateInternally(Project project, User user) {
        return assessmentDAO.getNextGroupMemberToFeedback(user, project);
    }

    /**
     * this starts the grading 'phase'
     * should be visualized as such in the UI but is treated here as a subphase
     * @param project
     */
    public void startDocentGrading(Project project){
        // update task for docent
        taskDAO.updateForUser(new Task(TaskName.WAIT_FOR_UPLOAD, project, Progress.FINISHED));
    }

}
