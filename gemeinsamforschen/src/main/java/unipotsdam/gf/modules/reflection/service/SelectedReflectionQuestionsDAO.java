package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.SelectedReflectionQuestion;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SelectedReflectionQuestionsDAO {

    private final static String DATABASE_NAME = "selectedreflectionquestions";

    private static final String REFLECTION_QUESTION_QUERY =
            String.join(" ", "select srq.*, rqa.fullSubmissionId, slg.projectName,",
                    String.format("fsm.userEmail from %s srq", DATABASE_NAME),
                    String.format("left join %s rqa on srq.id = rqa.selectedReflectionQuestionId", ReflectionQuestionAnswersDAO.DATABASE_NAME),
                    String.format("left join %s slg on srq.learningGoalId = slg.id", SelectedLearningGoalsDAO.DATABASE_NAME),
                    String.format("left join %s fsm on rqa.fullSubmissionId = fsm.id", SubmissionController.DATABASE_NAME));

    @Inject
    private MysqlConnect connection;

    public String persist(SelectedReflectionQuestion selectedReflectionQuestion) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = String.format("INSERT INTO %s (id, learningGoalId, question) values (?,?,?)", DATABASE_NAME);
        connection.issueInsertOrDeleteStatement(query, uuid, selectedReflectionQuestion.getLearningGoalId(), selectedReflectionQuestion.getQuestion());
        connection.close();
        return uuid;
    }

    public List<SelectedReflectionQuestion> findBy(Project project) {
        connection.connect();
        String query = buildSelectReflectionQuestionQuery("where slg.projectName = ?");
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName());
        List<SelectedReflectionQuestion> selectedReflectionQuestions = new ArrayList<>();
        while (resultSet.next()) {
            selectedReflectionQuestions.add(convertResultSet(resultSet));
        }
        connection.close();
        return selectedReflectionQuestions;
    }

    public List<SelectedReflectionQuestion> findBy(LearningGoal learningGoal) {
        connection.connect();
        String query = buildSelectReflectionQuestionQuery("where learningGoalId = ? and projectName = ?");
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, learningGoal.getId(), learningGoal.getProjectName());
        List<SelectedReflectionQuestion> selectedReflectionQuestions = new ArrayList<>();
        while (resultSet.next()) {
            selectedReflectionQuestions.add(convertResultSet(resultSet));
        }
        connection.close();
        return selectedReflectionQuestions;
    }

    public List<ReflectionQuestion> getAnsweredQuestions(Project project) {
        String query = buildReflectionQuestionQuery("WHERE slg.projectName = ? and rqa.fullSubmissionId IS NOT NULL order by fsm.userEmail");
        return getQuestions(query, project);
    }

    public List<ReflectionQuestion> getAnsweredQuestionsFromUser(Project project, User user) {
        String query = buildReflectionQuestionQuery("where slg.projectName = ? and fsm.userEmail = ?");
        return getQuestions(query, project, user);
    }

    public List<SelectedReflectionQuestion> getUnansweredQuestions(Project project, User user, boolean onlyFirstEntry) {
        connection.connect();
        String query = String.join(" ", "select * from selectedreflectionquestions srq",
                "left join selectedlearninggoals slg on srq.learningGoalId = slg.id",
                "where srq.id not in (select srq.id from selectedreflectionquestions srq",
                "left join reflectionquestionanswers rqa on srq.id = rqa.selectedReflectionQuestionId",
                "left join selectedlearninggoals slg on srq.learningGoalId = slg.id",
                "left join fullsubmissions fsm on rqa.fullSubmissionId = fsm.id",
                "where fsm.projectName = ? and fsm.userEmail = ?) and slg.projectName = ?");

        if (onlyFirstEntry) {
            query = String.join(" ", query.trim(), "LIMIT 1");
        }

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName(), user.getEmail(), project.getName());

        ArrayList<SelectedReflectionQuestion> reflectionQuestions = new ArrayList<>();
        while (rs.next()) {
            SelectedReflectionQuestion reflectionQuestion = convertResultSet(rs);
            reflectionQuestions.add(reflectionQuestion);
        }
        connection.close();
        return reflectionQuestions;
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

    private String buildReflectionQuestionQuery(String whereClause) {
        return String.join(" ", REFLECTION_QUESTION_QUERY.trim(), whereClause.trim());
    }

    private String buildSelectReflectionQuestionQuery(String whereClause) {
        return String.join(" ", String.format("select srq.*,slg.projectName from %s srq left join %s slg " +
                        "on srq.learningGoalId = slg.id", DATABASE_NAME, SelectedLearningGoalsDAO.DATABASE_NAME).trim(),
                whereClause.trim());
    }


    private SelectedReflectionQuestion convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String learningGoalId = resultSet.getString("learningGoalId");
        String question = resultSet.getString("question");
        return new SelectedReflectionQuestion(id, learningGoalId, question);
    }

    private ReflectionQuestion convertResultSetToReflectionQuestion(VereinfachtesResultSet resultSet) {
        SelectedReflectionQuestion selected = convertResultSet(resultSet);
        String projectName = resultSet.getString("projectName");
        String fullSubMissionId = resultSet.getString("fullSubMissionId");
        return new ReflectionQuestion(selected, projectName, fullSubMissionId);
    }
}
