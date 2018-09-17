package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.states.model.Constraints;
import unipotsdam.gf.core.states.model.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.assessment.controller.model.Grading;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

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

    Boolean getWorkRating(StudentIdentifier student, String fromStudent) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectId`=? AND `studentId`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId(), fromStudent);
        return vereinfachtesResultSet.next();
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

    Integer getGroupByStudent(StudentIdentifier student) {
        Integer result;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT groupId FROM `groupuser` WHERE `projectId`=? AND `studentId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectId(), student.getStudentId());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("groupId");
        return result;
    }

    ArrayList<String> getStudentsByGroupAndProject(Integer groupId, String projectId) {
        ArrayList<String> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `groupuser` WHERE `groupId`=? AND `projectId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId, projectId);
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("studentId"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Map<String, Double>> getContributionRating(Integer groupId) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId);
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
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId, fromStudent);
        return vereinfachtesResultSet.next();
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
        for (String question : questions.keySet()) {
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

    Integer getWhichGroupToRate(StudentIdentifier student) {
        Integer result;
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` WHERE `projectId`=? AND `studentId`=? ";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, student.getProjectId(), student.getStudentId());
        vereinfachtesResultSet1.next();
        Integer groupId = vereinfachtesResultSet1.getInt("groupId");

        String mysqlRequest2 = "SELECT DISTINCT groupId FROM `groupuser` WHERE `projectId`=? ";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, student.getProjectId());
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
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        String mysqlRequest = "INSERT INTO `contributionrating`(" +
                "`groupId`, " +
                "`fromPeer`, " +
                "`dossier`, " +
                "`research`) " +
                "VALUES (?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest,
                groupId,
                fromStudent,
                contributionRating.get("dossier"),
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

    Map<String, Boolean> getAnswers(String projectId, String question) {
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

    Map<StudentIdentifier, ConstraintsMessages> missingAssessments(String projectId) {
        Map<StudentIdentifier, ConstraintsMessages> result = new HashMap<>();
        ArrayList<String> studentsInProject = new ArrayList<>(getStudents(projectId));
        ArrayList<StudentIdentifier> missingStudentsCauseOfWorkrating = missingWorkRatings(studentsInProject, projectId);
        if (missingStudentsCauseOfWorkrating != null)
            for (StudentIdentifier missingStudent : missingStudentsCauseOfWorkrating) {
                result.put(missingStudent, new ConstraintsMessages(Constraints.AssessmentOpen, missingStudent));
            }
        // ArrayList<StudentIdentifier> missingStudentsCauseOfQuiz <--- I can't check that atm
        ArrayList<StudentIdentifier> missingStudentsCauseOfContribution = missingContribution(studentsInProject, projectId);
        if (missingStudentsCauseOfContribution != null)
            for (StudentIdentifier missingStudent : missingStudentsCauseOfContribution) {
                result.put(missingStudent, new ConstraintsMessages(Constraints.AssessmentOpen, missingStudent));
            }
        return result;
    }

    private ArrayList<StudentIdentifier> missingWorkRatings(ArrayList<String> studentsInProject, String projectId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        ArrayList<StudentIdentifier> result = new ArrayList<>();
        String sqlSelectWorkRating = "SELECT DISTINCT fromPeer FROM `workrating` WHERE `projectId`='"+projectId+"' AND `fromPeer`=''";
        for (String studentId : studentsInProject) {
            sqlSelectWorkRating = sqlSelectWorkRating + " OR `fromPeer`='" + studentId+"'";
        }
        VereinfachtesResultSet selectWorkRatingResultSet =
                connect.issueSelectStatement(sqlSelectWorkRating);
        Boolean next = selectWorkRatingResultSet.next();
        resultSetToStudentIdentifierList(studentsInProject, projectId, result, selectWorkRatingResultSet, next);
        return result;
    }

    private ArrayList<StudentIdentifier> missingContribution(ArrayList<String> studentsInProject, String projectId) {
        MysqlConnect connect = new MysqlConnect();
        connect.connect();
        ArrayList<StudentIdentifier> result = new ArrayList<>();
        String sqlContribution = "SELECT DISTINCT cr.fromPeer FROM groupuser gu " +
                "JOIN contributionrating cr ON gu.groupId=cr.groupId WHERE gu.projectId = ?;";
        VereinfachtesResultSet selectContributionResultSet =
                connect.issueSelectStatement(sqlContribution, projectId);
        Boolean next = selectContributionResultSet.next();
        resultSetToStudentIdentifierList(studentsInProject, projectId, result, selectContributionResultSet, next);
        return result;
    }

    private void resultSetToStudentIdentifierList(ArrayList<String> studentsInProject, String projectId, ArrayList<StudentIdentifier> result, VereinfachtesResultSet selectWorkRatingResultSet, Boolean next) {
        while (next) {
            String fromPeer = selectWorkRatingResultSet.getString("fromPeer");
            if (!studentsInProject.contains(fromPeer)) {
                StudentIdentifier studentIdentifier = new StudentIdentifier(projectId, fromPeer);
                result.add(studentIdentifier);
            }
            next = selectWorkRatingResultSet.next();
        }
    }

}
