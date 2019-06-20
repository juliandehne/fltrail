package unipotsdam.gf.modules.assessment;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.quiz.StudentIdentifier;
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

        int nEA = getNumberOfGroupsWithoutExternalAssessment(project);
        assessmentProgress.setNumberOfGroupsWithoutExternalAssessment(nEA);

        int nSIA = getNumberOfStudentsWithoutInternalAssesment(project);
        assessmentProgress.setNumberOfStudentsWithoutInternalAsssessment(nSIA);

        return assessmentProgress;
    }

    /**
     * SELECT count(*) from groupuser currentUser JOIN groupuser feedbackedUser ON currentUser.groupId = feedbackedUser.groupId JOIN groups g on currentUser.groupId = g.id JOIN users us on us.email = feedbackedUser.userEmail WHERE g.projectName = "assessmenttest3" AND currentUser.userEmail <> feedbackedUser.userEmail AND (currentUser.userEmail, feedbackedUser.userEmail) not in( SELECT wr.fromPeer, wr.userEmail from workrating wr ) ORDER BY us.id
     *
     * @param project
     * @return
     */
    private int getNumberOfStudentsWithoutInternalAssesment(Project project) {
        int result = -1;
        connect.connect();
        String query =
                "SELECT count(*) as result from groupuser currentUser" + " JOIN groupuser feedbackedUser ON currentUser.groupId = feedbackedUser.groupId " + " JOIN groups g on currentUser.groupId = g.id" + " JOIN users us on us.email = feedbackedUser.userEmail " + " WHERE g.projectName = ? " + " AND currentUser.userEmail <> feedbackedUser.userEmail" + " AND (currentUser.userEmail, feedbackedUser.userEmail)" + " not in( SELECT wr.fromPeer, wr.userEmail from workrating wr )";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("result");
        connect.close();
        return result;
    }

    /**
     * SELECT * from groups g where g.projectName = "assessmenttest3" AND (g.id, g.projectName) not in ( SELECT cr.groupId, cr.projectName from contributionrating cr )
     *
     * @param project
     * @return
     */
    private int getNumberOfGroupsWithoutExternalAssessment(Project project) {
        int result = -1;
        connect.connect();
        String query =
                "SELECT count(*) as result from groups g " + " where g.projectName = ? " + " AND (g.id, g.projectName) not in " + " (SELECT cr.groupId, cr.projectName from contributionrating cr )";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("result");
        connect.close();
        return result;
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
                "INSERT INTO mappedtasks (subjectEmail, groupObjectId, objectEmail, taskname, projectName) " + "values (?,?,?,?,?)";

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

    /**
     * at some point these might be set dynamically, not the case right now
     * @param project
     * @return
     */
    CheatCheckerMethods getAssessmentMethod(Project project) {
        CheatCheckerMethods result = CheatCheckerMethods.variance;
     /*   connect.connect();
        String mysqlRequest = "SELECT * FROM `assessmentmechanismselected` WHERE `projectName`=?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, project.getName());
        if (vereinfachtesResultSet.next()) {
            String resultString = vereinfachtesResultSet.getString("cheatCheckerMethod");
            result = CheatCheckerMethods.valueOf(resultString);
        }
        connect.close();*/
        return result;
    }

    /**
     * updated this TODO @Julian check if it works now
     * @param project
     * @param user
     * @return
     */
    public ArrayList<Map<String, Double>> getWorkRating(Project project, User user) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
        boolean next = vereinfachtesResultSet.next();
        while (next) {
            Map<String, Double> workRating = new HashMap<>();
                workRating.put(
                        vereinfachtesResultSet.getString("itemName"),
                        (double) vereinfachtesResultSet.getInt("rating"));
            result.add(workRating);
            next = vereinfachtesResultSet.next();
        }
        connect.close();
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
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, groupId);
        Boolean next = vereinfachtesResultSet.next();
        while (next) {
            result.add(vereinfachtesResultSet.getString("userEmail"));
            next = vereinfachtesResultSet.next();
        }
        return result;
    }

    public ArrayList<Map<FileRole, Double>> getContributionRating(Integer groupId, Boolean fromPeers) {
        String fromSelector = " fromTeacher is null";
        if (fromPeers) {
            fromSelector = " fromPeer is null";
        }
        ArrayList<Map<FileRole, Double>> result = new ArrayList<>();
        connect.connect();
        String mysqlRequest = "SELECT * FROM `contributionrating` WHERE `groupId`=?" + fromSelector;
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


/*    public void writeWorkRatingToDB(StudentIdentifier student, String fromStudent, Map<String, Integer> workRating) {
        connect.connect();
        String mysqlRequest =
                "INSERT INTO `workrating`(`projectName`, `userEmail`, `fromPeer`, " + "`responsibility`, " + "`partOfWork`, " + "`cooperation`, " + "`communication`, " + "`autonomous`" + ") VALUES (?,?,?,?,?,?,?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, student.getProjectName(), student.getUserEmail(),
                fromStudent, workRating.get("responsibility"), workRating.get("partOfWork"),
                workRating.get("cooperation"), workRating.get("communication"), workRating.get("autonomous"));
        connect.close();
    }*/

    void writeContributionRatingToDB(
            Project project, String groupId, String user, Map<FileRole, Integer> contributionRating,
            Boolean isStudent) {
        String columnOrigin = "`fromPeer`";
        if (!isStudent) {
            columnOrigin = "`fromTeacher`";
        }
        connect.connect();
        for (FileRole contribution : contributionRating.keySet()) {
            String mysqlRequest =
                    "INSERT INTO `contributionrating`(`projectName`, `groupId`, `filerole`, `rating`," + columnOrigin + ")" + "VALUES (?,?,?,?,?)";
            String projectName = project.getName();
            Integer groupIdParsed = null;
            if (groupId != null) {
                groupIdParsed = Integer.parseInt(groupId);
            }
            String fileRole = contribution.toString();
            Integer rating = contributionRating.get(contribution);
            connect.issueInsertOrDeleteStatement(mysqlRequest, projectName, groupIdParsed, fileRole, rating, user);
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
                "SELECT * FROM `largefilestorage` lfs " + "WHERE groupId=? AND " + "lfs.projectName=? AND lfs.filerole=?;";
        VereinfachtesResultSet selectResultSet =
                connect.issueSelectStatement(sqlStatement, groupId, project.getName(), role);
        if (selectResultSet != null && selectResultSet.next()) {
            result = new FullContribution();
            result.setRoleOfContribution(role);
            result.setPathToFile(selectResultSet.getString("filelocation"));
            result.setNameOfFile(selectResultSet.getString("filename"));

        }
        connect.close();
        return result;
    }

    /**
     * This method gets the next user that has not been given feedback yet from the group
     * needs to be tested with at least 3-4 users in a group and more then one group
     * SELECT  us.email, us.name
     * from groupuser currentUser
     * JOIN groupuser feedbackedUser
     * ON currentUser.groupId = feedbackedUser.groupId
     * JOIN groups g
     * on currentUser.groupId = g.id
     * JOIN users us
     * on us.email = feedbackedUser.userEmail
     * WHERE
     * currentUser.userEmail = "sx2@sx2.com"
     * AND g.projectName = "assessmenttest2"
     * AND currentUser.userEmail <> feedbackedUser.userEmail
     * AND (currentUser.userEmail, feedbackedUser.userEmail)
     * not in(
     * SELECT wr.fromPeer, wr.userEmail
     * from workrating wr
     * )
     * ORDER BY us.id
     *
     * @param user
     * @param project
     * @return
     */
    public User getNextGroupMemberToFeedback(User user, Project project) {
        User result = null;
        connect.connect();

        // SELF JOIN YEAH YEAH YEAH
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT  us.email, us.name  from groupuser currentUser ");
        stringBuilder.append("JOIN groupuser feedbackedUser ");
        stringBuilder.append("ON currentUser.groupId = feedbackedUser.groupId ");
        stringBuilder.append("JOIN groups g ");
        stringBuilder.append("on currentUser.groupId = g.id ");
        stringBuilder.append("JOIN users us ");
        stringBuilder.append("on us.email = feedbackedUser.userEmail ");
        stringBuilder.append("WHERE ");
        stringBuilder.append("currentUser.userEmail = ? ");
        stringBuilder.append("AND g.projectName = ? ");
        stringBuilder.append("AND currentUser.userEmail <> feedbackedUser.userEmail ");
        stringBuilder.append("AND (currentUser.userEmail, feedbackedUser.userEmail) ");
        stringBuilder.append("not in( ");
        stringBuilder.append("SELECT wr.fromPeer, wr.userEmail ");
        stringBuilder.append("from workrating wr  ");
        stringBuilder.append(")");
        stringBuilder.append("ORDER BY us.id; ");
        String query = stringBuilder.toString();
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName());
        if (vereinfachtesResultSet != null) {
            vereinfachtesResultSet.next();
            String userEmail = vereinfachtesResultSet.getString("email");
            String name = vereinfachtesResultSet.getString("name");
            result = new User(userEmail);
            result.setName(name);
        }

        connect.close();
        return result;
    }

    public void persistInternalAssessment(
            Project project, User user, User feedbackedUser, HashMap<String, String> data) {

        connect.connect();
        for (String key : data.keySet()) {

            int rating = Integer.parseInt(data.get(key));
            connect.issueInsertOrDeleteStatement(
                    "INSERT IGNORE INTO `workrating` (projectName, userEmail, fromPeer, rating, itemName)" + " values (?,?,?,?,?)",
                    project.getName(), feedbackedUser.getEmail(), user.getEmail(), rating, key);
        }
        connect.close();
    }

    /**
     * this returns the next group to rate contributions for the docent
     *
     * @param project
     * @return
     */
    public TaskMapping getNextGroupToFeedbackForTeacher(Project project) {
        TaskMapping result = new TaskMapping(null, null, null, null, project);
        connect.connect();
        String query =
                "SELECT g.id as result from groups g " + " JOIN" + " projects p on g.projectName = p.name "
                        + " where g.projectName = ? "
                        + " AND (p.name, g.id, p.author) not in "
                        + "    (SELECT cr.projectName, cr.groupId ,cr.fromTeacher from contributionrating cr " + "       WHERE cr.fromTeacher is not null" + "    )" + " LIMIT 1";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        if (vereinfachtesResultSet != null) {
            try {
                if (vereinfachtesResultSet.next()) {
                    Integer groupId = vereinfachtesResultSet.getInt("result");
                    result.setObjectGroup(new Group(groupId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        connect.close();
        return result;
    }

    /**
     * SELECT user, avg(rating2) from
     *   (SELECT gu.userEmail as user, cr.rating as rating2, cr.projectName, fromTeacher from contributionrating cr
     *   join groupuser gu on gu.groupId = cr.groupId
     * union
     * SELECT ucr.userName as user, ucr.rating as rating2, ucr.projectName, fromTeacher from contributionrating ucr
     *   WHERE ucr.groupId is null) as T
     * WHERE projectName = "assessmenttest3"
     * and fromTeacher is null
     * group by user, rating2
     */
    public HashMap<User, Double> getPeerProductRatings(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();

        String query = "SELECT user, avg(rating2) from " +
                " (SELECT gu.userEmail as user, cr.rating as rating2, cr.projectName, fromTeacher from " +
                        "contributionrating cr " +
                        "join groupuser gu on gu.groupId = cr.groupId " +
                        "union " +
                        "SELECT ucr.userName as user, ucr.rating as rating2, ucr.projectName, fromTeacher from " +
                                "contributionrating ucr " +
                        "WHERE ucr.groupId is null) as T " +
        "WHERE projectName = ? " +
        "and fromTeacher is null " +
        "group by user, rating2 ";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();

        return result;
    }

    /**
     * SELECT gu.userEmail, avg(rating) as rating2 from contributionrating cr
     *   join groupuser gu on gu.groupId = cr.groupId
     * where cr.projectName = "assessmenttest3"
     * and cr.fromPeer is null
     * group by userEmail
     * @param project
     * @return
     */
    public HashMap<User, Double> getDocentProductRatings(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();
        String query = "SELECT gu.userEmail, avg(rating) as rating2 from contributionrating cr" +
                "  join groupuser gu on gu.groupId = cr.groupId"
                + "where cr.projectName = ? "
                + "and cr.fromPeer is null"
                + "group by userEmail ";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();

        return result;
    }

    /**
     * SELECT pu.userEmail, avg(rating) as rating2 from workrating wr
     *   join projectuser pu on pu.projectName = wr.projectName
     * where wr.projectName = "assessmenttest3"
     *       and wr.userEmail = pu.userEmail
     * group by userEmail
     * @param project
     * @return
     */
    public HashMap<User, Double> getGroupRating(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();
        String query = "SELECT pu.userEmail, avg(rating) as rating2 from workrating wr" +
                "   join projectuser pu on pu.projectName = wr.projectName" +
                "  where wr.projectName = ?" +
                "   and wr.userEmail = pu.userEmail" +
                "  group by userEmail ";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();
        return result;
    }

    public void convertResultSetToUserRatingMap(Project project, HashMap<User, Double> result, String query) {
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        while (vereinfachtesResultSet.next()) {
            User user = new User(vereinfachtesResultSet.getString("user"));
            Double rating = vereinfachtesResultSet.getDouble("avg(rating2)");
            result.put(user, rating);
        }
    }
}
