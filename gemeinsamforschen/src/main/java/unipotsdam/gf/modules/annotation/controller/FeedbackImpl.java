package unipotsdam.gf.modules.annotation.controller;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskMapping;

import javax.inject.Inject;
import java.util.List;

public class FeedbackImpl implements Feedback {

    @Inject
    MysqlConnect connection;

    @Inject
    GroupDAO groupDAO;

    @Inject
    TaskMapping taskMapping;

    @Override
    public void assigningMissingFeedbackTasks(Project project) {

    }

    @Override
    public void specifyFeedbackTasks(List<Task> tasks) {
        for (Task task : tasks) {
            List<Integer> groupToFeedback = taskMapping.groupToFeedback(tasks, task, 1);
            for (Integer groupId : groupToFeedback) {
                connection.connect();
                String request = "UPDATE `fullsubmissions` SET `feedbackGroup`=? WHERE groupId=? AND projectName=?";
                connection.issueInsertOrDeleteStatement(request, groupId, task.getGroupTask(), task.getProjectName());
                connection.close();
            }
        }
    }


    @Override
    public ResearchReport getFeedbackTask(User student) {
        return null;
    }

    public int getFeedBackTarget(Project project, User user) {
        connection.connect();
        Integer groupId = groupDAO.getMyGroupId(user, project);
        int feedbackTarget = 0;
        String request = "SELECT * FROM `fullsubmissions` WHERE feedbackGroup=? AND projectName=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(request, groupId, project.getName());
        if (vereinfachtesResultSet.next()){
            feedbackTarget = vereinfachtesResultSet.getInt("groupId");
        }
        connection.close();
        return feedbackTarget;
    }
}
