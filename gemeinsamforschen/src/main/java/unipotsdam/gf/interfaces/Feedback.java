package unipotsdam.gf.interfaces;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.process.tasks.Task;

import java.util.List;


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
     * @param tasks
     */
    void specifyFeedbackTasks(List<Task> tasks);

    /**
     * TODO implement: Get the research report you have to give feedback to
     * @param student
     * @return
     */
    ResearchReport getFeedbackTask(User student);
}
