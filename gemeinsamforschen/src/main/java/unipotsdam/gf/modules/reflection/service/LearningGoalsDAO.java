package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedBean
@Resource
public class LearningGoalsDAO {

    @Inject
    private MysqlConnect connection;

    public String persist(LearningGoal learningGoal) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO learninggoals (id,text,projectName) VALUES (?,?,?)";
        connection.issueInsertOrDeleteStatement(query, uuid, learningGoal.getText(), learningGoal.getProjectName());
        connection.close();
        return uuid;
    }

    public LearningGoal getNextUnfinishedLearningGoal(Project project) {
        connection.connect();
        String query = "SELECT * FROM learninggoals where projectName = ? and finished = false LIMIT 1";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName());
        LearningGoal learningGoal = null;
        if (resultSet.next()) {
            learningGoal = convertResultSet(resultSet);
        }
        connection.close();
        return learningGoal;
    }

    public List<LearningGoal> getLearningGoals(Project project) {
        connection.connect();
        String query = "Select * from learningGoals where projectName = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName());
        List<LearningGoal> learningGoals = new ArrayList<>();
        while (resultSet.next()) {
            learningGoals.add(convertResultSet(resultSet));
        }
        return learningGoals;
    }

    public void finishLearningGoal(LearningGoal learningGoal) {
        connection.connect();
        String query = "UPDATE learninggoals SET `finished` = true WHERE id = ?";
        connection.issueUpdateStatement(query, learningGoal.getId());
        connection.close();
    }

    private LearningGoal convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String text = resultSet.getString("text");
        String projectName = resultSet.getString("projectName");
        boolean finished = resultSet.getBoolean("finished");
        return new LearningGoal(id, text, projectName, finished);
    }


}
