package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.inject.Inject;

public class ReflectionQuestionsStoreDAO {

    private MysqlConnect connection;

    @Inject
    public ReflectionQuestionsStoreDAO(MysqlConnect connection) {
        this.connection = connection;
    }

    public void persist(ReflectionQuestionsStoreItem item) {
        connection.connect();
        String query = "INSERT INTO reflectionquestionsstore(id,question,learningGoal) VALUES (?,?,?)";
        connection.issueInsertOrDeleteStatement(query, item.getId(), item.getQuestion(), item.getLearningGoal());
        connection.close();
    }
}
