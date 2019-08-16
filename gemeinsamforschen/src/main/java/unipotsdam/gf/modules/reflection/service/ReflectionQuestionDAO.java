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
import java.util.UUID;

@ManagedBean
@Resource
public class ReflectionQuestionDAO {

    @Inject
    public MysqlConnect connection;

    public String persist(ReflectionQuestion question) {
        ReflectionQuestion reflectionQuestion = existReflectionQuestion(question.getProjectName(), question.getUserEmail(), question.getQuestion());
        if (reflectionQuestion != null) {
            return reflectionQuestion.getId();
        }
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO reflectionquestions (id, learningGoalId, question, userEmail, fullSubmissionId,projectName) values(?,?,?,?,?,?)";
        connection.issueInsertOrDeleteStatement(query, uuid, question.getLearningGoalId(), question.getQuestion(), question.getUserEmail(), question.getFullSubmissionId(), question.getProjectName());
        connection.close();
        return uuid;
    }

    public List<ReflectionQuestion> getReflectionQuestions(Project project, User user) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ?";
        return getQuestions(query, project, user);
    }

    public List<ReflectionQuestion> getAnsweredQuestions(Project project) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and fullSubmissionId IS NOT NULL";

        return getQuestions(query, project);
    }

    public List<ReflectionQuestion> getAnsweredQuestionsFromUser(Project project, User user) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ? and fullSubmissionId IS NOT NULL";
        return getQuestions(query, project, user);
    }


    public List<ReflectionQuestion> getUnansweredQuestions(Project project, User user, boolean onlyFirstEntry) {
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ? and fullSubmissionId IS NULL";
        if (onlyFirstEntry) {
            query = String.format("%s LIMIT 1", query.trim());
        }
        return getQuestions(query, project, user);
    }

    public void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) {
        connection.connect();
        String query = "UPDATE reflectionquestions set fullSubmissionId = ? where id = ?";
        connection.issueUpdateStatement(query, fullSubmission.getId(), reflectionQuestion.getId());
        connection.close();
    }

    public ReflectionQuestion findBy(String id) {
        connection.connect();
        String query = "SELECT * FROM reflectionquestions WHERE id = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, id);
        ReflectionQuestion reflectionQuestion = null;
        if (resultSet.next()) {
            reflectionQuestion = convertResultSet(resultSet);
        }
        connection.close();
        return reflectionQuestion;
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

    private List<ReflectionQuestion> getQuestions(String query, Project project) {
        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName());

        ArrayList<ReflectionQuestion> reflectionQuestions = new ArrayList<>();
        while (rs.next()) {
            ReflectionQuestion reflectionQuestion = convertResultSet(rs);
            reflectionQuestions.add(reflectionQuestion);
        }
        connection.close();
        return reflectionQuestions;
    }

    private List<ReflectionQuestion> getQuestions(String query, Project project, User user) {
        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName(), user.getEmail());

        ArrayList<ReflectionQuestion> reflectionQuestions = new ArrayList<>();
        while (rs.next()) {
            ReflectionQuestion reflectionQuestion = convertResultSet(rs);
            reflectionQuestions.add(reflectionQuestion);
        }
        connection.close();
        return reflectionQuestions;
    }

    private ReflectionQuestion existReflectionQuestion(String projectName, String userEmail, String question) {
        connection.connect();
        String query = "SELECT * FROM reflectionquestions WHERE projectName = ? and userEmail = ? and question = ?";
        VereinfachtesResultSet rs = connection.issueSelectStatement(query, projectName, userEmail, question);
        ReflectionQuestion reflectionQuestion = null;
        if (rs.next()) {
            reflectionQuestion = convertResultSet(rs);
        }
        connection.close();
        return reflectionQuestion;
    }

}
