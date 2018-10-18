package unipotsdam.gf.interfaces;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.progress.HasProgress;
import unipotsdam.gf.modules.researchreport.ResearchReport;


/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * TODO implement a routine that assigns missing feedback tasks if someone drops out of a course
     * @param project
     */
    void assigningMissingFeedbackTasks(Project project);

    /**
     * TODO implement: Assigns each student in a project a feedback target
     */
    void assignFeedbackTasks(Project project);

    /**
     * TODO implement: Get the research report you have to give feedback to
     * @param student
     * @return
     */
    ResearchReport getFeedbackTask(User student);
}
