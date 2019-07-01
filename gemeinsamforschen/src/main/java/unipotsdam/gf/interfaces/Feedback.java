package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.tasks.Task;

import java.util.List;


/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * assigns a user to feedback every other user
     * @param tasks all feedbackTasks in a project
     */
    void specifyFeedbackTasks(List<Task> tasks);


    int getFeedBackTarget(Project project, User user);
}
