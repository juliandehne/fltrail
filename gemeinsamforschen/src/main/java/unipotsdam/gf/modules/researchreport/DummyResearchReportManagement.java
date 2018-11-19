package unipotsdam.gf.modules.researchreport;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.interfaces.Feedback;

import javax.inject.Inject;
import java.io.File;

public class DummyResearchReportManagement implements ResearchReportManagement {



    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();


    @Inject
    Feedback feedback;

    @Override
    public String createResearchReport(
            ResearchReport researchReport, Project project, User student) {

        // real implementation should check if all the constraints are ok before starting with feedbacks
        // this assumes uploading and giving feedback is in the same phase (no teacher decision to go from
        // uploading dossiers to feedback
        if (DummyResearchReportCounter.feedbackTasksNotAssigned) {
            DummyResearchReportCounter.feedbackTasksNotAssigned = false;
            //feedback.specifyFeedbackTasks(project);
        }
        return factory.manufacturePojo(ResearchReport.class).getId();
    }

    @Override
    public boolean updateResearchReport(ResearchReport researchReport)  {
        NotImplementedLogger.logAssignment(Assignee.QUARK, ResearchReportManagement.class, null, "updateResearchReport");
        return false;
    }

    @Override
    public boolean deleteReport(ResearchReport researchReport)  {
        NotImplementedLogger.logAssignment(Assignee.QUARK, ResearchReportManagement.class);
        return false;
    }

    @Override
    public File getResearchReport(ResearchReport researchReport) {
        NotImplementedLogger.logAssignment(Assignee.QUARK, ResearchReportManagement.class);
        return null;
    }

    @Override
    public void createFinalResearchReport(
            ResearchReport researchReport, Project project, User student) {
        NotImplementedLogger.logAssignment(Assignee.QUARK, ResearchReportManagement.class);
    }

    @Override
    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
