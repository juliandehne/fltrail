package unipotsdam.gf.session;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@ManagedBean
public class AnnotationAlreadyDoneFilter extends BackwardNavigationFilter {

    @Inject
    TaskDAO taskDAO;

    @Inject
    SubmissionController submissionController;

    @Inject
    ProjectDAO projectDAO;

    public AnnotationAlreadyDoneFilter() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    @Override
    protected boolean contraintNotFullFilled(ServletRequest request, Object userEmail, ServletResponse response) {

        Boolean result = false;
        String submissionId = request.getParameter("submissionId");
        if (submissionId == null) {
            return false;
        }
        FullSubmission fullSubmission = submissionController.getFullSubmission(submissionId);
        Project project = null;
        try {
            project = projectDAO.getProjectByName(fullSubmission.getProjectName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Task userTask = taskDAO.getTasksWithTaskName(fullSubmission.getGroupId(), project, TaskName.ANNOTATE_DOSSIER);
        if (userTask != null && userTask.getProgress().name().equals(Progress.FINISHED.name())) {
            result = true;
        }
        Boolean isStudent = !project.getAuthorEmail().equals(userEmail);
        if (result) {
            redirectToTasks(project, isStudent, request, response);
        }
        return result;
    }
}
