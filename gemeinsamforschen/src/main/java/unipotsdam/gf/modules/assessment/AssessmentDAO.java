package unipotsdam.gf.modules.assessment;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.constraints.Constraints;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
import unipotsdam.gf.process.tasks.TaskMapping;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentDAO {

    PodamFactoryImpl podamFactory = new PodamFactoryImpl();

    @Inject
    private MysqlConnect connect;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    /**
     * get the progress for the docent so he can assess if he wants to proceed to grading
     *
     * @param project
     * @return
     */
    public AssessmentProgress getProgress(Project project) {
        AssessmentProgress assessmentProgress = new AssessmentProgress();
        //assessmentProgress.setNumberOfGroupsWithoutPresentation(3);

        int numberOfGroups = groupDAO.getGroupsByProjectName(project.getName()).size();

        // get number of submitted Presentation Files
        String fileRole = FileRole.PRESENTATION.name();
        int presentationCount = getNumberOfSubmittedFiles(project, fileRole);
        assessmentProgress.setNumberOfGroupsWithoutPresentation(numberOfGroups - presentationCount);

        // get number of submitted final reports
        String fileRole2 = FileRole.FINAL_REPORT.name();
        int reportCount = getNumberOfSubmittedFiles(project, fileRole2);
        assessmentProgress.setNumberOfGroupReportsMissing(numberOfGroups - reportCount);

        //
        assessmentProgress.setNumberOfGroupsWithoutExternalAssessment(numberOfGroups);
        // Todo IMPLEMENT

        assessmentProgress
                .setNumberOfStudentsWithoutInternalAsssessment(userDAO.getUsersByProjectName(project.getName()).size());
        // TODO IMPLEMENT

        return assessmentProgress;
    }

    public int getNumberOfSubmittedFiles(Project project, String fileRole) {
        int presentationCount;
        connect.connect();
        String query = "SELECT COUNT(*) from largefilestorage where projectName = ? and fileRole = '" + fileRole + "'";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        if (vereinfachtesResultSet == null) {
            return 0;
        }
        vereinfachtesResultSet.next();
        presentationCount = vereinfachtesResultSet.getInt("COUNT(*)");
        connect.close();
        return presentationCount;
    }

    /**
     * TODO implement
     *
     * @param mappedTask
     */
    public void persistMapping(TaskMapping mappedTask) {

        connect.connect();
        String query =
                "INSERT INTO mappedtasks (subjectEmail, groupObjectId, objectEmail, taskname, projectName) "
                        + "values (?,?,?,?,?)";

        Object groupValue = null;
        if (mappedTask.getObjectGroup() != null) {
            groupValue = mappedTask.getObjectGroup().getId();
        }
        String objectUser = null;
        if (mappedTask.getObjectUser() != null) {
            objectUser = mappedTask.getObjectUser().getEmail();
        }

        connect.issueInsertOrDeleteStatement(query, mappedTask.getSubject().getEmail(), groupValue, objectUser,
                mappedTask.getTaskName().name(), mappedTask.getProject().getName());

        connect.close();
    }

    public TaskMapping getTargetGroupForAssessment(User subject) {
        TaskMapping result;
        connect.connect();

        String query = "SELECT * from mappedtasks where subjectEmail = ? and taskname = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, subject.getEmail(), TaskName.GIVE_EXTERNAL_ASSESSMENT.name());
        if (vereinfachtesResultSet != null) {
            boolean next = vereinfachtesResultSet.next();
            int groupObjectId = vereinfachtesResultSet.getInt("groupObjectId");
            String subjectEmail = vereinfachtesResultSet.getString("subjectEmail");
            String objectEmail = vereinfachtesResultSet.getString("objectEmail");
            String taskName = vereinfachtesResultSet.getString("taskname");
            String projectName = vereinfachtesResultSet.getString("projectName");
            result = new TaskMapping(new User(subjectEmail), new Group(groupObjectId), new User(objectEmail),
                    TaskName.valueOf(taskName), new Project(projectName));
        } else {
            return null;
        }
        connect.close();
        return result;
    }

    public cheatCheckerMethods getAssessmentMethod(Project project) {
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

    public ArrayList<Map<String, Double>> getWorkRating(Project project, User user) {
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

    public Boolean getWorkRating(StudentIdentifier student, String fromStudent) {

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userEmail`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                        fromStudent);
        return vereinfachtesResultSet.next();
    }

    public List<String> getStudents(String projectID) {
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

    public ArrayList<String> getStudentsByGroupAndProject(Integer groupId) {
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

    public ArrayList<Map<FileRole, Double>> getContributionRating(Integer groupId) {
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

    public Boolean getContributionRating(Integer groupId, String fromStudent) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=? AND `fromPeer`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, groupId, fromStudent);
        return vereinfachtesResultSet.next();
    }



    public void writeWorkRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> workRating) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO `workrating`(`projectName`, `userEmail`, `fromPeer`, " + "`responsibility`, " + "`partOfWork`, " + "`cooperation`, " + "`communication`, " + "`autonomous`" + ") VALUES (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                fromStudent, workRating.get("responsibility"), workRating.get("partOfWork"),
                workRating.get("cooperation"), workRating.get("communication"), workRating.get("autonomous"));
        connect.close();
    }

    public void writeContributionRatingToDB(
            Project project, String groupId, String fromStudent, Map<FileRole, Integer> contributionRating) {
        connect.connect();
        for (FileRole contribution : contributionRating.keySet()) {
            String mysqlRequest =
                    "INSERT INTO `contributionrating`(`projectName`, `groupId`, `fromPeer`, `filerole`, `rating`)" +
                            " VALUES (?,?,?,?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), groupId, fromStudent,
                    contribution.toString(), contributionRating.get(contribution));
        }
        connect.close();
    }

    public void writeGradesToDB(Project project, Map<User, Double> grade) {
        connect.connect();
        String mysqlRequest = "INSERT INTO `grades`(`projectName`, `userEmail`, `grade`) VALUES (?,?,?)";
        for (User student : grade.keySet()) {
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), student.getEmail(),
                    grade.get(student));
        }
        connect.close();
    }

    public Double getGradesFromDB(StudentIdentifier student) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `grades` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, student.getProjectName(), student.getUserEmail());
        vereinfachtesResultSet.next();
        return vereinfachtesResultSet.getDouble("grade");
    }

    public Map<String, Boolean> getAnswers(String projectName, String question) {
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

    public Map<StudentIdentifier, ConstraintsMessages> missingAssessments(String projectName) {
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

    FullContribution getContribution(Project project, Integer groupId, FileRole role) {
        FullContribution result = null;
        connect.connect();
        String sqlStatement =
                "SELECT * FROM `largefilestorage` lfs " +
                        "WHERE groupId=? AND " +
                        "lfs.projectName=? AND lfs.filerole=?;";
        VereinfachtesResultSet selectResultSet = connect.issueSelectStatement(sqlStatement, groupId, project.getName(), role);
        if (selectResultSet != null && selectResultSet.next()){
            result = new FullContribution();
            result.setRoleOfContribution(role);
            result.setPathToFile(selectResultSet.getString("filelocation"));
            result.setNameOfFile(selectResultSet.getString("filename"));

        }
        connect.close();
        return result;
    }


}
