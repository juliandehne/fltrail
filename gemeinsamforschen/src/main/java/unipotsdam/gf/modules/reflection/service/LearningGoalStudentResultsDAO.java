package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.mysql.MysqlConnect;

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
}
