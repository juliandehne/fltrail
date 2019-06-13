package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import javax.inject.Inject;
import java.util.Map;

public class ExecutionProcess implements IExecutionProcess {

    @Inject
    private IJournal iJournal;

    @Inject
    private EmailService emailService;

    @Override
    public void start(Project project) {
        // TODO @Martin
    }

    @Override
    public Boolean isPhaseCompleted(Project project) {
        Map<StudentIdentifier, ConstraintsMessages> tasks;

        tasks = iJournal.getPortfoliosForEvaluationPrepared(project);
        if (tasks.size() < 1) {
            // inform users about the end of the phase
            emailService.sendMessageToUsers(project, Messages.AssessmentPhaseStarted(project));
        } else {
            emailService.informAboutMissingTasks(tasks, project);
        }
        // TODO @Martin
        return null;
    }

    @Override
    public void finishPhase(Project project) {
        // TODO @Martin
    }
}
