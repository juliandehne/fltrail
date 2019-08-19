package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionToAnswer;
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
public class ReflectionQuestionsToAnswerDAO {

    private static final String STANDARD_SELECT_QUERY = "SELECT rq.*, sq.question FROM reflectionquestionstoanswer rq JOIN selectedreflectionquestions sq on sq.id = rq.selectedReflectionQuestionId";

    @Inject
    public MysqlConnect connection;

    public String persist(ReflectionQuestionToAnswer question) {
        ReflectionQuestionToAnswer reflectionQuestion = existReflectionQuestion(question.getProjectName(), question.getUserEmail(), question.getSelectedReflectionQuestionId());
        if (reflectionQuestion != null) {
            return reflectionQuestion.getId();
        }
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO reflectionquestionstoanswer (id, learningGoalId, selectedReflectionQuestionId, userEmail, fullSubmissionId,projectName) values(?,?,?,?,?,?)";
        connection.issueInsertOrDeleteStatement(query, uuid, question.getLearningGoalId(),
                question.getSelectedReflectionQuestionId(), question.getUserEmail(), question.getFullSubmissionId(),
                question.getProjectName());
        connection.close();
        return uuid;
    }

    public List<ReflectionQuestion> getAnsweredQuestions(Project project) {
        String query = buildQuery("WHERE rq.projectName = ? and rq.fullSubmissionId IS NOT NULL");

        return getQuestions(query, project);
    }

    public List<ReflectionQuestion> getAnsweredQuestionsFromUser(Project project, User user) {
        String query = buildQuery("WHERE rq.projectName = ? and rq.userEmail = ? and rq.fullSubmissionId IS NOT NULL");
        return getQuestions(query, project, user);
    }


    public List<ReflectionQuestion> getUnansweredQuestions(Project project, User user, boolean onlyFirstEntry) {
        String query = buildQuery("WHERE rq.projectName = ? and rq.userEmail = ? and rq.fullSubmissionId IS NULL");
        if (onlyFirstEntry) {
            query = String.format("%s LIMIT 1", query.trim());
        }
        return getQuestions(query, project, user);
    }

    public void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestionToAnswer reflectionQuestion) {
        connection.connect();
        String query = "UPDATE reflectionquestionstoanswer set fullSubmissionId = ? where id = ?";
        connection.issueUpdateStatement(query, fullSubmission.getId(), reflectionQuestion.getId());
        connection.close();
    }

    public ReflectionQuestionToAnswer findBy(String id) {
        connection.connect();
        String query = "SELECT * FROM reflectionquestionstoanswer WHERE id = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, id);
        ReflectionQuestionToAnswer reflectionQuestion = null;
        if (resultSet.next()) {
            reflectionQuestion = convertResultSet(resultSet);
        }
        connection.close();
        return reflectionQuestion;
    }


    private ReflectionQuestionToAnswer convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String learningGoalId = resultSet.getString("learningGoalId");
        String selectedReflectionQuestionId = resultSet.getString("selectedReflectionQuestionId");
        String userEmail = resultSet.getString("userEmail");
        String fullSubmissionId = resultSet.getString("fullSubmissionId");
        String projectName = resultSet.getString("projectName");

        return new ReflectionQuestionToAnswer(id, learningGoalId, selectedReflectionQuestionId, fullSubmissionId, userEmail, projectName);
    }

    private ReflectionQuestion convertResultSetToReflectionQuestion(VereinfachtesResultSet resultSet) {
        ReflectionQuestionToAnswer reflectionQuestionToAnswer = convertResultSet(resultSet);
        String question = resultSet.getString("question");
        return new ReflectionQuestion(reflectionQuestionToAnswer, question);
    }

    private List<ReflectionQuestion> getQuestions(String query, Project project) {
        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName());

        ArrayList<ReflectionQuestion> reflectionQuestions = new ArrayList<>();
        while (rs.next()) {
            ReflectionQuestion reflectionQuestion = convertResultSetToReflectionQuestion(rs);
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
            ReflectionQuestion reflectionQuestion = convertResultSetToReflectionQuestion(rs);
            reflectionQuestions.add(reflectionQuestion);
        }
        connection.close();
        return reflectionQuestions;
    }

    private ReflectionQuestionToAnswer existReflectionQuestion(String projectName, String userEmail,
                                                               String selectedReflectionQuestionId) {
        connection.connect();
        String query = "SELECT * FROM reflectionquestionstoanswer WHERE projectName = ? and userEmail = ? and selectedReflectionQuestionId = ?";
        VereinfachtesResultSet rs = connection.issueSelectStatement(query, projectName, userEmail, selectedReflectionQuestionId);
        ReflectionQuestionToAnswer reflectionQuestion = null;
        if (rs.next()) {
            reflectionQuestion = convertResultSet(rs);
        }
        connection.close();
        return reflectionQuestion;
    }

    private String buildQuery(String whereClause) {
        return String.join(" ", STANDARD_SELECT_QUERY.trim(), whereClause.trim());
    }

}
