package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.assessment.controller.model.*;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@Resource
@Singleton
class AssessmentDBCommunication {
    ArrayList<Map<String, Double>> getWorkRating(StudentIdentifier student) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map<String, Double> workRating = new HashMap<>();
            for (String category : Categories.workRatingCategories) {
                workRating.put(category, (double) vereinfachtesResultSet.getInt(category));
            }
            result.add(workRating);
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    List<String> getStudents(String projectID) {
        List<String> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `projectuser` WHERE `projectId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectID);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userID"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Map<String, Double>> getContributionRating(StudentIdentifier student) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map<String, Double> contributionRating = new HashMap<>();
            for (String category : Categories.contributionRatingCategories) {
                contributionRating.put(category, (double) vereinfachtesResultSet.getInt(category));
            }
            result.add(contributionRating);
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    ArrayList<Integer> getAnsweredQuizzes(StudentIdentifier student) {
        ArrayList<Integer> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `answeredquiz` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getInt("correct"));
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    void writeAnsweredQuiz(StudentIdentifier student, Map<String, Boolean> questions) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        for (String question: questions.keySet()){
            String mysqlRequest = "INSERT INTO `answeredquiz`(`projectId`, `studentId`, `question`, `correct`) VALUES (?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest,
                    student.getProjectId(),
                    student.getStudentId(),
                    question,
                    questions.get(question)
            );
        }
        connect.close();
    }

    void writeWorkRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> workRating) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO `workrating`(`projectId`, `studentId`, `fromPeer`, " +
                "`responsibility`, " +
                "`partOfWork`, " +
                "`cooperation`, " +
                "`communication`, " +
                "`autonomous`" +
                ") VALUES (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectId(), student.getStudentId(), fromStudent,
                workRating.get("responsibility"),
                workRating.get("partOfWork"),
                workRating.get("cooperation"),
                workRating.get("communication"),
                workRating.get("autonomous")
        );
        connect.close();
    }

    void writeContributionRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> contributionRating) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO `contributionrating`(`studentID`, `projectID`, `fromStudentID`, " +
                "`Dossier`, " +
                "`eJournal`, " +
                "`research`" +
                ") VALUES (?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectId(), student.getStudentId(), fromStudent,
                contributionRating.get("Dossier"),
                contributionRating.get("eJournal"),
                contributionRating.get("research")
        );
        connect.close();
    }

    void writeGradesToDB(Grading grade) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO `grades`(`projectId`, `studentId`, `grade`) VALUES (?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest,
                grade.getStudentIdentifier().getProjectId(),
                grade.getStudentIdentifier().getStudentId(),
                grade.getGrade()
        );
        connect.close();
    }

    public Map<String, Boolean> getAnswers(String projectId, String question) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        Map<String, Boolean> result = new HashMap<>();
        String mysqlRequest = "SELECT * FROM `quiz` WHERE `projectId`=? AND `question`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectId, question);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.put(vereinfachtesResultSet.getString("answer"), vereinfachtesResultSet.getBoolean("correct"));
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

}
