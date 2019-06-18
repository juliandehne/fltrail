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
     * this function is only used to show the peer assessment phase before previous phases are ready
     *
     * @param project
     */
    public void startPeerAssessmentPhase(Project project) {
        // distribute upload tasks
        taskDAO.persistTaskForAllGroups(project, UPLOAD_PRESENTATION, Phase.Assessment);
        // distribute teacher tasks
        taskDAO.persistTeacherTask(project, TaskName.WAIT_FOR_UPLOAD, Phase.Assessment);
    }

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
    private void finishPresentationUpload(User userFromSession, Project project) {
        // get the group the user is in the project
        Integer groupByStudent = groupDAO.getGroupByStudent(project, userFromSession);
        // update the task for all the group members
        taskDAO.updateGroupTask(
                new GroupTask(TaskName.UPLOAD_PRESENTATION, groupByStudent, Progress.FINISHED, project));
        // set new tasks
        taskDAO.persistTaskForAllGroups(project, TaskName.UPLOAD_FINAL_REPORT, Phase.Assessment);
    }

    private synchronized void finishReportUpload(User userFromSession, Project project) {
        // get the group the user is in the project
        Integer groupByStudent = groupDAO.getGroupByStudent(project, userFromSession);
        // update the task for all the group members
        taskDAO.updateGroupTask(
                new GroupTask(TaskName.UPLOAD_FINAL_REPORT, groupByStudent, Progress.FINISHED, project));
        // set new tasks

    }

    /**
     *
     * @param project
     */
    public void startGrading(Project project) {
        // TODO implement
        //System.out.println("grading tihs project now:" + project);

        // set assessment tasks for students
        taskDAO.persistMemberTask(project, TaskName.GIVE_EXTERNAL_ASSESSMENT, Phase.Assessment);
        taskDAO.persistMemberTask(project, TaskName.GIVE_INTERNAL_ASSESSMENT, Phase.Assessment);

        // set assessment tasks for students
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            taskMapper.persistTaskMapping(project, user, TaskName.GIVE_EXTERNAL_ASSESSMENT);
        }
        // set final grading tasks
    }

    public void persistInternalAssessment(
            Project project, User user, User feedbackedUser, HashMap<String, String> data) {
         assessmentDAO.persistInternalAssessment(project, user, feedbackedUser, data);
    }

    public User getNextUserToRateInternally(Project project, User user) {
        return assessmentDAO.getNextGroupMemberToFeedback(user, project);
    }


    /**
     * IF WE GET TO THE PEER ASSESSMENT PHASE NATURALLY (NOT VIA DIRECT LINK AFTER GROUP FORMATION)
     * COMMENT THE FUNCTIONS IN THAT ARE NEEDED
     */
/*    public void start(Project project) {
        Task task = new Task(TaskName.CLOSE_EXECUTION_PHASE, project.getAuthorEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);

        // create a task, telling the docent to wait for students upload of dossiers
        taskDAO.persist(project, new User(project.getAuthorEmail()), TaskName.WAIT_FOR_PEER_ASSESSMENTS, Phase
                .Assessment, TaskType.INFO);
        taskDAO.persistMemberTask(project, TaskName.GIVE_ASSESSMENT, Phase.Assessment);
    }

    public void finalizeAssessment(User user, Project project) {
        // mark assessment as final in db
        //submissionController.markAsFinal(fullSubmission);

        // mark assessment task as finished in db
        Task task = new Task(TaskName.GIVE_ASSESSMENT, user.getEmail(), project.getName(),
                Progress.FINISHED);
        taskDAO.updateForUser(task);
    }

    public void createCloseAssessmentPhaseTask(Project project) {
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_ASSESSMENT_PHASE, Phase.Assessment);
    }

    public void finishPhase(Project project) {

        User user = userDAO.getUserByEmail(project.getAuthorEmail());
        Task task = new Task();
        task.setUserEmail(user.getEmail());
        task.setProjectName(project.getName());
        task.setProgress(Progress.FINISHED);
        task.setTaskName(TaskName.CLOSE_ASSESSMENT_PHASE);
        taskDAO.updateForUser(task);
        taskDAO.persist(taskDAO.createUserDefault(project, user, TaskName.END, Phase.Projectfinished));
        //todo: implement communication stuff
        *//*   if (tasks.size() > 0) {
         iCommunication.informAboutMissingTasks(tasks, project);
         } else {
         // send a message to the users informing them about the start of the new phase
         iCommunication.sendMessageToUsers(project, Messages.NewFeedbackTask(project));
         saveState(project, changeToPhase);
         }*//*
    }

    public void createSeeAssessmentTask(Project project, User user) {
        taskDAO.persist(project, user, TaskName.SEE_ASSESSMENT, Phase.Assessment);
    }*/

}
