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
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.process.tasks.TaskType;

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

        taskDAO.persist(project, user, TaskName.FINALIZE_DOSSIER, Phase.DossierFeedback, TaskType.LINKED);

        return fullSubmission;
    }
}
