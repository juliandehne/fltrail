package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Resource
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

    public List<ReflectionQuestionsStoreItem> getAllReflectionQuestions() {
        connection.connect();
        String query = "SELECT * FROM reflectionquestionsstore";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query);
        ArrayList<ReflectionQuestionsStoreItem> items = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        while (resultSet.next()) {
            ReflectionQuestionsStoreItem item = convertResultSet(resultSet);
            if (questions.contains(item.getQuestion())) {
                continue;
            }
            items.add(convertResultSet(resultSet));
            questions.add(item.getQuestion());
        }
        connection.close();
        return items;
    }

    public List<ReflectionQuestionsStoreItem> getLearningGoalSpecificQuestions(String learningGoal) {
        String query = "SELECT * FROM reflectionquestionsstore WHERE learningGoal = ?";
        connection.connect();
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, learningGoal);
        ArrayList<ReflectionQuestionsStoreItem> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(convertResultSet(resultSet));
        }
        connection.close();
        return items;
    }

    private ReflectionQuestionsStoreItem convertResultSet(VereinfachtesResultSet resultSet) {
        String question = resultSet.getString("question");
        String learningGoal = resultSet.getString("learningGoal");
        return new ReflectionQuestionsStoreItem(question, learningGoal);
    }
}
