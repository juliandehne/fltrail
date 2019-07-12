package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
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
}
