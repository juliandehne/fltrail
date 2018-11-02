package unipotsdam.gf.modules.annotation.controller;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.tasks.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FeedbackImpl implements Feedback {

    @Inject
    MysqlConnect connection;

    @Override
    public void assigningMissingFeedbackTasks(Project project) {

    }

    @Override
    public void specifyFeedbackTasks(List<Task> tasks) {
        for (Task task : tasks) {
            List<String> studentsToFeedback = studentsToFeedback(tasks, task, 1);
            for (String userEmail : studentsToFeedback) {
                connection.connect();
                String request = "UPDATE `fullsubmissions` SET `feedbackUser`=? WHERE user=? AND projectName=?";
                connection.issueInsertOrDeleteStatement(request, userEmail, task.getUserEmail(), task.getProjectName());
                connection.close();
            }
        }
    }

    private List<String> studentsToFeedback(List<Task> tasks, Task task, int howMany) {
        List<String> result = new ArrayList<>();
        int position = tasks.indexOf(task);
        for (int i = 1; i <= howMany; i++) {
            result.add(tasks.get((i + position) % tasks.size()).getUserEmail());  //modulo builds a circle in users
        }
        return result;
    }

    @Override
    public ResearchReport getFeedbackTask(User student) {
        return null;
    }
}
