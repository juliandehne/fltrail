package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.SelectedReflectionQuestion;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SelectedReflectionQuestionsDAO {

    @Inject
    private MysqlConnect connection;

    public String persist(SelectedReflectionQuestion selectedReflectionQuestion) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO selectedreflectionquestions (id, learningGoalId, question, projectName) values (?,?,?,?)";
        connection.issueInsertOrDeleteStatement(query, uuid, selectedReflectionQuestion.getLearningGoalId(), selectedReflectionQuestion.getQuestion(), selectedReflectionQuestion.getProjectName());
        connection.close();
        return uuid;
    }

    public List<SelectedReflectionQuestion> findBy(Project project) {
        connection.connect();
        String query = "SELECT * FROM selectedreflectionquestions where projectName = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName());
        List<SelectedReflectionQuestion> selectedReflectionQuestions = new ArrayList<>();
        while (resultSet.next()) {
            selectedReflectionQuestions.add(convertResultSet(resultSet));
        }
        connection.close();
        return selectedReflectionQuestions;
    }

    public List<SelectedReflectionQuestion> findBy(LearningGoal learningGoal) {
        connection.connect();
        String query = "Select * from selectedreflectionquestions where learningGoalId = ? and projectName = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, learningGoal.getId(), learningGoal.getProjectName());
        List<SelectedReflectionQuestion> selectedReflectionQuestions = new ArrayList<>();
        while (resultSet.next()) {
            selectedReflectionQuestions.add(convertResultSet(resultSet));
        }
        connection.close();
        return selectedReflectionQuestions;
    }


    private SelectedReflectionQuestion convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String learningGoalId = resultSet.getString("learningGoalId");
        String question = resultSet.getString("question");
        String projectName = resultSet.getString("projectName");
        return new SelectedReflectionQuestion(id, learningGoalId, question, projectName);
    }
}
