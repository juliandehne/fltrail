package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Resource
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

    public List<LearningGoalStoreItem> getAllStoreGoals() {
        connection.connect();
        String query = "SELECT * FROM learninggoalstore";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query);
        ArrayList<LearningGoalStoreItem> learningGoals = new ArrayList<>();
        while (resultSet.next()) {
            learningGoals.add(convertResultSet(resultSet));
        }
        connection.close();
        return learningGoals;
    }

    private LearningGoalStoreItem convertResultSet(VereinfachtesResultSet resultSet) {
        String text = resultSet.getString("text");
        return new LearningGoalStoreItem(text);
    }
}
