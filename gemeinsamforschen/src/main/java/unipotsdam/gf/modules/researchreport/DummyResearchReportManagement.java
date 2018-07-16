package unipotsdam.gf.modules.researchreport;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
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
            feedback.assignFeedbackTasks();
        }
        return factory.manufacturePojo(ResearchReport.class).getId();
    }

    @Override
    public boolean updateResearchReport(ResearchReport researchReport) {
        return false;
    }

    @Override
    public boolean deleteReport(ResearchReport researchReport) {
        return false;
    }

    @Override
    public File getResearchReport(ResearchReport researchReport) {
        return null;
    }

    @Override
    public void createFinalResearchReport(
            ResearchReport researchReport, Project project, User student) {

    }

    @Override
    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
