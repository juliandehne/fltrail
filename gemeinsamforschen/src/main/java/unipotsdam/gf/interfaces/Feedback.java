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
     * @param project which project needs feedbacks to be assigned
     */
    void assigningMissingFeedbackTasks(Project project);

    /**
     * assigns a user to feedback every other user
     * @param tasks all feedbackTasks in a project
     */
    void specifyFeedbackTasks(List<Task> tasks);

    /**
     * TODO implement: Get the research report you have to give feedback to
     * @param student which student to give feedback to
     * @return The research Report that was written by the student
     */
    ResearchReport getFeedbackTask(User student);

    String getFeedBackTarget(Project project,User user);
}
