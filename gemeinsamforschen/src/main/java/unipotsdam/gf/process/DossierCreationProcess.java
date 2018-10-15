package unipotsdam.gf.process;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DossierCreationProcess {


    @Inject
    SubmissionController submissionController;

    @Inject
    private Management management;
    
    @Inject
    private UserDAO userDAO;

    @Inject
    private TaskDAO taskDAO;

    public void startDossierPhase(Project project) {
        taskDAO.persistMemberTask(project, TaskName.UPLOAD_DOSSIER, Phase.DossierFeedback);
    }

    public FullSubmission addSubmission(
            FullSubmissionPostRequest fullSubmissionPostRequest, User user, Project project) {
        FullSubmission fullSubmission = submissionController.addFullSubmission(fullSubmissionPostRequest);

        // this completes the upload task
        Task task = new Task(TaskName.UPLOAD_DOSSIER, user.getEmail(), project.getName(), Progress.FINISHED);
        taskDAO.updateForUser(task);

        // this triggers the annotate task
        taskDAO.persist(project, user, TaskName.ANNOTATE_DOSSIER, Phase.DossierFeedback, TaskType.LINKED);

        return fullSubmission;
    }

    /**
     *
     * @param fullSubmission
     * @param user
     */
    public void finalizeDossier(FullSubmission fullSubmission, User user) {
        // mark as final in db
        submissionController.markAsFinal(fullSubmission);

        // mark annotate task as finished in db
        Task task = new Task(TaskName.ANNOTATE_DOSSIER, user.getEmail(), fullSubmission.getProjectId(), Progress.FINISHED);
        taskDAO.updateForUser(task);
    }

/*    public void finalizeDossier(FullSubmission fullSubmission, User user) {
        TODO
    }

    public*/
}
