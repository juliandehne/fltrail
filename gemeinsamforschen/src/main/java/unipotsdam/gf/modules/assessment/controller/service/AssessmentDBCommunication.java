package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.assessment.controller.model.Contribution;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.model.cheatCheckerMethods;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.constraints.Constraints;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

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
public class AssessmentDBCommunication {

    @Inject
    private MysqlConnect connect;


    cheatCheckerMethods getAssessmentMethod(Project project) {
        cheatCheckerMethods result = cheatCheckerMethods.none;
        connect.connect();
        String mysqlRequest = "SELECT * FROM `assessmentmechanismselected` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getName());
        if (vereinfachtesResultSet.next()) {
            String resultString = vereinfachtesResultSet.getString("cheatCheckerMethod");
            result = cheatCheckerMethods.valueOf(resultString);
        }
        return result;
    }

    ArrayList<Map<String, Double>> getWorkRating(Project project, User user) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
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
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userEmail`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                        fromStudent);
        return vereinfachtesResultSet.next();
    }

    List<String> getStudents(String projectID) {
        List<String> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `projectuser` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, projectID);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userEmail"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<String> getStudentsByGroupAndProject(Integer groupId) {
        ArrayList<String> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `groupuser` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId);
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userEmail"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    ArrayList<Map<FileRole, Double>> getContributionRating(Integer groupId) {
        ArrayList<Map<FileRole, Double>> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map<FileRole, Double> contributionRating = new HashMap<>();
            for (FileRole category : FileRole.values()) {
                contributionRating.put(category, (double) vereinfachtesResultSet.getInt(category.toString()));
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

    ArrayList<Integer> getAnsweredQuizzes(Project project, User user) {
        ArrayList<Integer> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `answeredquiz` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
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
                    "INSERT INTO `answeredquiz`(`projectName`, `userEmail`, `question`, `correct`) VALUES (?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                    question, questions.get(question));
        }
        connect.close();
    }

    void writeWorkRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> workRating) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO `workrating`(`projectName`, `userEmail`, `fromPeer`, " + "`responsibility`, " + "`partOfWork`, " + "`cooperation`, " + "`communication`, " + "`autonomous`" + ") VALUES (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                fromStudent, workRating.get("responsibility"), workRating.get("partOfWork"),
                workRating.get("cooperation"), workRating.get("communication"), workRating.get("autonomous"));
        connect.close();
    }

    Integer getWhichGroupToRate(Project project, User user) {
        Integer result;
        List<Integer> groups= new ArrayList<>();
        connect.connect();
        String mysqlRequest1 = "SELECT groupId FROM `groupuser` gu JOIN groups g on " +
                "gu.groupid=g.id AND g.projectName=? WHERE `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet1 =
                connect.issueSelectStatement(mysqlRequest1, project.getName(), user.getEmail());
        vereinfachtesResultSet1.next();
        Integer groupId = vereinfachtesResultSet1.getInt("groupId");

        String mysqlRequest2 = "SELECT DISTINCT id FROM `groups` WHERE `projectName`=? ";
        VereinfachtesResultSet vereinfachtesResultSet2 =
                connect.issueSelectStatement(mysqlRequest2, project.getName());
        boolean next = vereinfachtesResultSet2.next();
        while (next) {
            groups.add(vereinfachtesResultSet2.getInt("id"));
            next = vereinfachtesResultSet2.next();
        }
        if (groups.indexOf(groupId)+1 == groups.size()){
            result = groups.get(0);
        }else{
            result = groups.get(groups.indexOf(groupId)+1);
        }
        connect.close();
        return result;
    }

    void writeContributionRatingToDB(Project project, String groupId, String fromStudent, Map<FileRole, Integer> contributionRating) {
        connect.connect();
        for (FileRole contribution : contributionRating.keySet()) {
            String mysqlRequest =
                    "INSERT INTO `contributionrating`(`projectName`, `groupId`, `fromPeer`, `contributionrole`, `rating`)" +
                            " VALUES (?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), groupId, fromStudent,
                    contribution.toString(), contributionRating.get(contribution));
        }
        connect.close();
    }

    void writeGradesToDB(Project project, Map<User, Double> grade) {
        connect.connect();
        String mysqlRequest = "INSERT INTO `grades`(`projectName`, `userEmail`, `grade`) VALUES (?,?,?)";
        for (User student : grade.keySet()) {
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), student.getEmail(),
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

    public Contribution getContribution(Project project, Integer groupId, FileRole role) {
        connect.connect();
        String sqlStatement =
                "SELECT * FROM `largefilestorage` lfs JOIN groupuser gu on gu.groupId=? AND gu.userEmail=lfs.userEmail WHERE lfs.projectName=? AND lfs.filerole=?;";
        VereinfachtesResultSet selectResultSet = connect.issueSelectStatement(sqlStatement, groupId, project.getName(), role);
        if (selectResultSet.next()){
            Contribution result = new Contribution();
            result.setPathToFile(selectResultSet.getString("filelocation"));
            result.setNameOfFile(selectResultSet.getString("filename"));
            return result;
        }
        return null;
    }

}
