package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

@ManagedBean
public class LearningGoalStoreDAO {

    private MysqlConnect connection;

    @Inject
    public LearningGoalStoreDAO(MysqlConnect connection) {
        this.connection = connection;
    }

    public void persist(LearningGoalStoreItem learningGoal) {
        connection.connect();
        String query = "INSERT INTO learninggoalstore(text) VALUES (?)";
        connection.issueInsertOrDeleteStatement(query, learningGoal.getText());
        connection.close();
    }
}
