package unipotsdam.gf.modules.annotation.controller;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.Task;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FeedbackImpl implements Feedback {

    @Inject
    MysqlConnect connection;

    @Inject
    GroupDAO groupDAO;

    @Override
    public void assigningMissingFeedbackTasks(Project project) {

    }

    @Override
    public void specifyFeedbackTasks(List<Task> tasks) {
        for (Task task : tasks) {
            List<Integer> groupToFeedback = groupToFeedback(tasks, task, 1);
            for (Integer groupId : groupToFeedback) {
                connection.connect();
                String request = "UPDATE `fullsubmissions` SET `feedbackGroup`=? WHERE groupId=? AND projectName=?";
                connection.issueInsertOrDeleteStatement(request, groupId, task.getGroupTask(), task.getProjectName());
                connection.close();
            }
        }
    }

    private List<Integer> groupToFeedback(List<Task> tasks, Task task, int howMany) {
        List<Integer> result = new ArrayList<>();
        int position = tasks.indexOf(task);
        for (int i = 1; i <= howMany; i++) {
            result.add(tasks.get((i + position) % tasks.size()).getGroupTask());  //modulo builds a circle in users
        }
        return result;
    }

    @Override
    public ResearchReport getFeedbackTask(User student) {
        return null;
    }

    public String getFeedBackTarget(Project project,User user){
        connection.connect();
        Integer groupId = groupDAO.getMyGroupId(user, project);
        String feedbackTarget="";
        String request = "SELECT * FROM `fullsubmissions` WHERE feedbackGroup=? AND projectName=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(request, groupId, project.getName());
        if (vereinfachtesResultSet.next()){
            feedbackTarget = vereinfachtesResultSet.getString("groupId");
        }
        connection.close();
        return feedbackTarget;
    }
}
