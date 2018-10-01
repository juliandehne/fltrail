package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.states.model.Constraints;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.model.cheatCheckerMethods;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@Resource
@Singleton
class AssessmentDBCommunication {

    @Inject
    MysqlConnect connect;


    cheatCheckerMethods getAssessmentMethod(String projectName) {
        cheatCheckerMethods result = cheatCheckerMethods.none;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `assessmentmethod` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        if (vereinfachtesResultSet.next()) {
            String resultString = vereinfachtesResultSet.getString("cheatCheckerMethod");
            result = cheatCheckerMethods.valueOf(resultString);
        }
        return result;
    }

    ArrayList<Map<String, Double>> getWorkRating(StudentIdentifier student) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userName`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
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

    Boolean getWorkRating(StudentIdentifier student, String fromStudent) {

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userName`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                        fromStudent);
        return vereinfachtesResultSet.next();
    }

    List<String> getStudents(String projectID) {
        List<String> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `projectuser` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectID);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userID"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    Integer getGroupByStudent(StudentIdentifier student) {
        Integer result;
        connect.connect();
        String mysqlRequest = "SELECT groupId FROM `groupuser` WHERE `projectName`=? AND `userName`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("groupId");
        return result;
    }

    ArrayList<String> getStudentsByGroupAndProject(Integer groupId, String projectName) {
        ArrayList<String> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `groupuser` WHERE `groupId`=? AND `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId, projectName);
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userName"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Map<String, Double>> getContributionRating(Integer groupId) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
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

    Boolean getContributionRating(Integer groupId, String fromStudent) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId, fromStudent);
        return vereinfachtesResultSet.next();
    }

    Integer getQuizCount(String projectName) {
        Integer result = 0;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `quiz` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectName);
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            result++;
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Integer> getAnsweredQuizzes(StudentIdentifier student) {
        ArrayList<Integer> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `answeredquiz` WHERE `projectName`=? AND `userName`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getInt("correct"));
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    void writeAnsweredQuiz(StudentIdentifier student, Map<String, Boolean> questions) {
        connect.connect();
        for (String question : questions.keySet()) {
            String mysqlRequest =
                    "INSERT INTO `answeredquiz`(`projectName`, `userName`, `question`, `correct`) VALUES (?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                    question, questions.get(question));
        }
        connect.close();
    }

    void writeWorkRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> workRating) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO `workrating`(`projectName`, `userName`, `fromPeer`, " + "`responsibility`, " + "`partOfWork`, " + "`cooperation`, " + "`communication`, " + "`autonomous`" + ") VALUES (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                fromStudent, workRating.get("responsibility"), workRating.get("partOfWork"),
                workRating.get("cooperation"), workRating.get("communication"), workRating.get("autonomous"));
        connect.close();
    }

    Integer getWhichGroupToRate(StudentIdentifier student) {
        Integer result;
        connect.connect();
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` WHERE `projectName`=? AND `userName`=? ";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet1.next();
        Integer groupId = vereinfachtesResultSet1.getInt("groupId");

        String mysqlRequest2 = "SELECT DISTINCT groupId FROM `groupuser` WHERE `projectName`=? ";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, student.getProjectName());
        Boolean next = vereinfachtesResultSet2.next();
        result = vereinfachtesResultSet2.getInt("groupId");
        while (next) {
            if (vereinfachtesResultSet2.getInt("groupId") == groupId) {
                next = vereinfachtesResultSet2.next();
                if (next) {
                    result = vereinfachtesResultSet2.getInt("groupId");
                }
            } else {
                next = vereinfachtesResultSet2.next();
            }

        }
        connect.close();
        return result;
    }

    void writeContributionRatingToDB(String groupId, String fromStudent, Map<String, Integer> contributionRating) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO `contributionrating`(" + "`groupId`, " + "`fromPeer`, " + "`dossier`, " + "`research`) " + "VALUES (?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, groupId, fromStudent, contributionRating.get("dossier"),
                contributionRating.get("research"));
        connect.close();
    }

    void writeGradesToDB(Map<StudentIdentifier, Double> grade) {
        connect.connect();
        String mysqlRequest = "INSERT INTO `grades`(`projectName`, `userName`, `grade`) VALUES (?,?,?)";
        for (StudentIdentifier student : grade.keySet()) {
            connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                    grade.get(student));
        }
        connect.close();
    }

    Double getGradesFromDB(StudentIdentifier student) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `grades` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet.next();
        return vereinfachtesResultSet.getDouble("grade");
    }

    Map<String, Boolean> getAnswers(String projectName, String question) {
        connect.connect();
        Map<String, Boolean> result = new HashMap<>();
        String mysqlRequest = "SELECT * FROM `quiz` WHERE `projectName`=? AND `question`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, projectName, question);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.put(vereinfachtesResultSet.getString("answer"), vereinfachtesResultSet.getBoolean("correct"));
            next = vereinfachtesResultSet.next();
        }
        connect.close();
        return result;
    }

    Map<StudentIdentifier, ConstraintsMessages> missingAssessments(String projectName) {
        Map<StudentIdentifier, ConstraintsMessages> result = new HashMap<>();
        ArrayList<String> studentsInProject = new ArrayList<>(getStudents(projectName));
        ArrayList<StudentIdentifier> missingStudentsCauseOfWorkrating =
                missingWorkRatings(studentsInProject, projectName);
        if (missingStudentsCauseOfWorkrating != null)
            for (StudentIdentifier missingStudent : missingStudentsCauseOfWorkrating) {
                result.put(missingStudent, new ConstraintsMessages(Constraints.AssessmentOpen, missingStudent));
            }
        // ArrayList<StudentIdentifier> missingStudentsCauseOfQuiz <--- I can't check that atm
        ArrayList<StudentIdentifier> missingStudentsCauseOfContribution =
                missingContribution(studentsInProject, projectName);
        if (missingStudentsCauseOfContribution != null)
            for (StudentIdentifier missingStudent : missingStudentsCauseOfContribution) {
                result.put(missingStudent, new ConstraintsMessages(Constraints.AssessmentOpen, missingStudent));
            }
        return result;
    }

    private ArrayList<StudentIdentifier> missingWorkRatings(ArrayList<String> studentsInProject, String projectName) {
        connect.connect();
        ArrayList<StudentIdentifier> result = new ArrayList<>();
        String sqlSelectWorkRating =
                "SELECT DISTINCT fromPeer FROM `workrating` WHERE `projectName`='" + projectName + "' AND `fromPeer`=''";
        for (String userName : studentsInProject) {
            sqlSelectWorkRating = sqlSelectWorkRating + " OR `fromPeer`='" + userName + "'";
        }
        VereinfachtesResultSet selectWorkRatingResultSet = connect.issueSelectStatement(sqlSelectWorkRating);
        Boolean next = selectWorkRatingResultSet.next();
        resultSetToStudentIdentifierList(studentsInProject, projectName, result, selectWorkRatingResultSet, next);
        return result;
    }

    private ArrayList<StudentIdentifier> missingContribution(ArrayList<String> studentsInProject, String projectName) {
        connect.connect();
        ArrayList<StudentIdentifier> result = new ArrayList<>();
        String sqlContribution =
                "SELECT DISTINCT cr.fromPeer FROM groupuser gu " + "JOIN contributionrating cr ON gu.groupId=cr.groupId WHERE gu.projectName = ?;";
        VereinfachtesResultSet selectContributionResultSet = connect.issueSelectStatement(sqlContribution, projectName);
        Boolean next = selectContributionResultSet.next();
        resultSetToStudentIdentifierList(studentsInProject, projectName, result, selectContributionResultSet, next);
        return result;
    }

    private void resultSetToStudentIdentifierList(
            ArrayList<String> studentsInProject, String projectName, ArrayList<StudentIdentifier> result,
            VereinfachtesResultSet selectWorkRatingResultSet, Boolean next) {
        while (next) {
            String fromPeer = selectWorkRatingResultSet.getString("fromPeer");
            if (!studentsInProject.contains(fromPeer)) {
                StudentIdentifier userNameentifier = new StudentIdentifier(projectName, fromPeer);
                result.add(userNameentifier);
            }
            next = selectWorkRatingResultSet.next();
        }
    }

}
