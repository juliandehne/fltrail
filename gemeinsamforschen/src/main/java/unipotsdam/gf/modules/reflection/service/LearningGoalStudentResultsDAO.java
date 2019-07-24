package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.UUID;

public class LearningGoalStudentResultsDAO {

    @Inject
    private MysqlConnect connection;

    public String persist(LearningGoalStudentResult studentResult) {
        connection.connect();
        String uuid = UUID.randomUUID().toString();
        String query = "INSERT INTO learninggoalstudentresults (`id`, `projectName`, `learningGoalId`, `groupId`, `userEmail`, `text`, `visibility`) VALUES (?,?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(query, uuid, studentResult.getProjectName(), studentResult.getLearningGoalId(), studentResult.getGroupId(), studentResult.getUserEmail(), studentResult.getText(), studentResult.getVisibility().name());
        connection.close();
        return uuid;
    }

    public LearningGoalStudentResult findBy(Project project, User user, LearningGoal learningGoal) {
        connection.connect();
        String query = "SELECT * FROM learninggoalstudentresults where projectName = ? and userEmail = ? and learningGoalId = ?";
        VereinfachtesResultSet resultSet = connection.issueSelectStatement(query, project.getName(), user.getEmail(), learningGoal.getId());
        LearningGoalStudentResult learningGoalStudentResult = null;
        if (resultSet.next()) {
            learningGoalStudentResult = convertResultSet(resultSet);
        }
        connection.close();
        return learningGoalStudentResult;
    }

    private LearningGoalStudentResult convertResultSet(VereinfachtesResultSet resultSet) {
        String id = resultSet.getString("id");
        String projectName = resultSet.getString("projectName");
        String learningGoalId = resultSet.getString("learningGoalId");
        long creationDate = resultSet.getTimestamp("creationDate").getTime();
        int groupId = resultSet.getInt("groupId");
        String userEmail = resultSet.getString("userEmail");
        String text = resultSet.getString("text");
        Visibility visibility = Visibility.valueOf(resultSet.getString("visibility"));
        return new LearningGoalStudentResult(id, projectName, creationDate, learningGoalId, groupId, userEmail, text, visibility);
    }
}
