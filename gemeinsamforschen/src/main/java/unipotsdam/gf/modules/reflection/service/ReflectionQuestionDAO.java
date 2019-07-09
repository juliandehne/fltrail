package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@Resource
public class ReflectionQuestionDAO {

    @Inject
    public MysqlConnect connection;

    private List<ReflectionQuestion> getQuestions(String query, Project project, User user, String learningGoalId) {
        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName(), user.getEmail(), learningGoalId);

        ArrayList<ReflectionQuestion> reflectionQuestions = new ArrayList<>();
        while (rs.next()) {
            ReflectionQuestion reflectionQuestion = convertResultSet(rs);
            reflectionQuestions.add(reflectionQuestion);
        }
        connection.close();
        return reflectionQuestions;
    }

    public List<ReflectionQuestion> getReflectionQuestions(Project project, User user, String learningGoalId) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ? and learningGoalId = ?";
        return getQuestions(query, project, user, learningGoalId);
    }

    public List<ReflectionQuestion> getUnansweredQuestions(Project project, User user, String learningGoalId, boolean onlyFirstEntry) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ? and learningGoalId = ? and fullSubmissionId IS NULL";
        if (onlyFirstEntry) {
            query = String.format("%s LIMIT 1", query.trim());
        }
        return getQuestions(query, project, user, learningGoalId);
    }

    public void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) {
        connection.connect();
        String query = "UPDATE reflectionquestions set fullSubmissionId = ? where id = ?";
        connection.issueUpdateStatement(query, fullSubmission.getId(), reflectionQuestion.getId());
        connection.close();
    }


    private ReflectionQuestion convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String learningGoalId = resultSet.getString("learningGoalId");
        String question = resultSet.getString("question");
        String userEmail = resultSet.getString("userEmail");
        String fullSubmissionId = resultSet.getString("fullSubmissionId");
        String projectName = resultSet.getString("projectName");

        return new ReflectionQuestion(id, learningGoalId, question, fullSubmissionId, userEmail, projectName);

    }

}
