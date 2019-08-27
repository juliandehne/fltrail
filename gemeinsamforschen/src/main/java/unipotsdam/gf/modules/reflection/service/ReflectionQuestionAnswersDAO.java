package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.ReflectionQuestionAnswerDB;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.UUID;

@ManagedBean
@Resource
public class ReflectionQuestionAnswersDAO {

    static final String DATABASE_NAME = "reflectionquestionanswers";

    @Inject
    public MysqlConnect connection;

    public String persist(ReflectionQuestionAnswerDB question) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = String.format("INSERT IGNORE INTO %s (id,selectedReflectionQuestionId,fullSubmissionId) values" +
                " (?,?,?)", DATABASE_NAME);
        connection.issueInsertOrDeleteStatement(query, uuid, question.getSelectedReflectionQuestionId(), question.getFullSubmissionId());
        connection.close();
        return uuid;
    }


    public ReflectionQuestionAnswerDB findBy(String id) {
        connection.connect();
        String query = "SELECT * FROM reflectionquestionstoanswer WHERE id = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, id);
        ReflectionQuestionAnswerDB reflectionQuestion = null;
        if (resultSet.next()) {
            reflectionQuestion = convertResultSet(resultSet);
        }
        connection.close();
        return reflectionQuestion;
    }


    private ReflectionQuestionAnswerDB convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String selectedReflectionQuestionId = resultSet.getString("selectedReflectionQuestionId");
        String fullSubmissionId = resultSet.getString("fullSubmissionId");

        return new ReflectionQuestionAnswerDB(id, selectedReflectionQuestionId, fullSubmissionId);
    }
}
