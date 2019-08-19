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
public class SelectedLearningGoalsDAO {

    @Inject
    private MysqlConnect connection;

    public String persist(LearningGoal learningGoal) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO selectedlearninggoals (id,text,projectName) VALUES (?,?,?)";
        connection.issueInsertOrDeleteStatement(query, uuid, learningGoal.getText(), learningGoal.getProjectName());
        connection.close();
        return uuid;
    }

    public List<LearningGoal> getLearningGoals(Project project) {
        connection.connect();
        String query = "Select * from selectedlearninggoals where projectName = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName());
        List<LearningGoal> learningGoals = new ArrayList<>();
        while (resultSet.next()) {
            learningGoals.add(convertResultSet(resultSet));
        }
        return learningGoals;
    }

    private LearningGoal convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String text = resultSet.getString("text");
        String projectName = resultSet.getString("projectName");
        return new LearningGoal(id, text, projectName);
    }


}
