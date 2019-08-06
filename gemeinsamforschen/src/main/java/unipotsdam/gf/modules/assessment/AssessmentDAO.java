package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.assessment.controller.model.Contribution;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.progress.ProgressData;
import unipotsdam.gf.process.tasks.TaskMapping;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentDAO {

    @Inject
    private MysqlConnect connect;

    @Inject
    private GroupDAO groupDAO;


    /**
     * get the progress for the docent so he can assess if he wants to proceed to grading.
     * Progress includes number of final reports, final presentations, internal and external gradings
     *
     * @param project of interest
     * @return information about all missing subjects
     */
    public AssessmentProgress getProgress(Project project) {
        AssessmentProgress assessmentProgress = new AssessmentProgress();
        //assessmentProgress.setNumberOfGroupsWithoutPresentation(3);

        List<Group> groups = groupDAO.getGroupsByProjectName(project.getName());
        //check how many groups just have one member
        /* if necessary for anything
        int numberOfSingletons = 0;
        for (Group group: groups){
            if (group.getMembers().size()==1){
                numberOfSingletons++;
            }
        }*/
        int numberOfGroups = groups.size();

        // get number of submitted Presentation Files
        String fileRole = FileRole.PRESENTATION.name();
        int presentationCount = getNumberOfSubmittedFiles(project, fileRole);
        assessmentProgress.setNumberOfGroupsWithoutPresentation(numberOfGroups - presentationCount);

        // get number of submitted final reports
        String fileRole2 = FileRole.FINAL_REPORT.name();
        int reportCount = getNumberOfSubmittedFiles(project, fileRole2);
        assessmentProgress.setNumberOfGroupReportsMissing(numberOfGroups - reportCount);

        int nEA = getNumberOfGroupsWithoutExternalAssessment(project);
        if (numberOfGroups > 1) {
            assessmentProgress.setNumberOfGroupsWithoutExternalAssessment(nEA);
        } else {
            assessmentProgress.setNumberOfGroupsWithoutExternalAssessment(0);
        }

        int nSIA = getNumberOfStudentsWithoutInternalAssessment(project);
        assessmentProgress.setNumberOfStudentsWithoutInternalAsssessment(nSIA);

        return assessmentProgress;
    }

    /**
     * SELECT count(*) from groupuser currentUser JOIN groupuser feedbackedUser ON currentUser.groupId = feedbackedUser.groupId JOIN groups g on currentUser.groupId = g.id JOIN users us on us.email = feedbackedUser.userEmail WHERE g.projectName = "assessmenttest3" AND currentUser.userEmail <> feedbackedUser.userEmail AND (currentUser.userEmail, feedbackedUser.userEmail) not in( SELECT wr.fromPeer, wr.userEmail from workrating wr ) ORDER BY us.id
     *
     * @param project of interest
     * @return number of students, that did not receive a rating by their groupMembers yet
     */
    private int getNumberOfStudentsWithoutInternalAssessment(Project project) {
        int result;
        connect.connect();
        String query =
                "SELECT count(*) as result from groupuser currentUser" +
                        " JOIN groupuser feedbackedUser ON currentUser.groupId = feedbackedUser.groupId " +
                        " JOIN groups g on currentUser.groupId = g.id" +
                        " JOIN users us on us.email = feedbackedUser.userEmail " +
                        " WHERE g.projectName = ? " +
                        " AND currentUser.userEmail <> feedbackedUser.userEmail" +
                        " AND (currentUser.userEmail, feedbackedUser.userEmail)" +
                        " NOT IN( SELECT wr.fromPeer, wr.userEmail from workrating wr WHERE projectName= ? )";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, project.getName(), project.getName());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("result");
        connect.close();
        return result;
    }

    /**
     * SELECT * from groups g where g.projectName = "assessmenttest3" AND (g.id, g.projectName) not in ( SELECT cr.groupId, cr.projectName from contributionrating cr )
     *
     * @param project of interest
     * @return number of groups, that did not receive a rating by other groupmembers
     */
    private int getNumberOfGroupsWithoutExternalAssessment(Project project) {
        int result;
        connect.connect();
        String query =
                "SELECT count(*) as result from groups g " + " where g.projectName = ? " + " AND (g.id, g.projectName) not in " + " (SELECT cr.groupId, cr.projectName from contributionrating cr )";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        result = vereinfachtesResultSet.getInt("result");
        connect.close();
        return result;
    }

    private int getNumberOfSubmittedFiles(Project project, String fileRole) {
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
     * persists information in DB who is about to assess whom
     *
     * @param mappedTask defines which target is to be rated by whom
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

    public TaskMapping getTargetGroupForAssessment(User subject, Project project) {
        TaskMapping result;
        connect.connect();

        String query = "SELECT * from mappedtasks where subjectEmail = ? and taskname = ? and projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, subject.getEmail(), TaskName.GIVE_EXTERNAL_ASSESSMENT.name(),
                        project.getName());
        if (vereinfachtesResultSet.next()) {
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
     * gets all given ratings of groupMembers for one user in a project
     *
     * @param project of interest
     * @param user of interest
     * @return a list of maps, which connect a value (for example "compatibility" or "punctual")
     * with a value between 1 and 5
     */
    ArrayList<Map<String, Double>> getWorkRating(Project project, User user) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `workrating` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
        boolean next = vereinfachtesResultSet.next();
        String feedbackStudent = "";
        if (next)
            feedbackStudent = vereinfachtesResultSet.getString("fromPeer");
        Map<String, Double> workRating = new HashMap<>();
        while (next) {
            Map<String, Double> workRatingNew = new HashMap<>();
            workRatingNew.put(vereinfachtesResultSet.getString("itemName"),
                    (double) vereinfachtesResultSet.getInt("rating"));
            if (!feedbackStudent.equals(vereinfachtesResultSet.getString("fromPeer"))) {
                feedbackStudent = vereinfachtesResultSet.getString("fromPeer");
                result.add(workRating);
                workRating = workRatingNew;
            } else {
                workRating.put(vereinfachtesResultSet.getString("itemName"),
                        (double) vereinfachtesResultSet.getInt("rating"));
            }
            next = vereinfachtesResultSet.next();
        }
        if (workRating.size() > 0)
            result.add(workRating);
        connect.close();
        return result;
    }

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
                    "INSERT INTO `contributionrating`(`projectName`, `groupId`, `filerole`, `rating`," +
                            columnOrigin + ")" + "VALUES (?,?,?,?,?)";
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

    public Double getGradesFromDB(Project project, User user) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM `grades` WHERE `projectName`=? AND `userEmail`=?";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, project.getName(), user.getEmail());
        vereinfachtesResultSet.next();
        return vereinfachtesResultSet.getDouble("grade");
    }

    Contribution getContribution(Project project, Integer groupId, FileRole role) {
        Contribution result = null;
        connect.connect();
        String sqlStatement =
                "SELECT * FROM `largefilestorage` lfs WHERE groupId=? AND " + "lfs.projectName=? AND lfs.filerole=?;";
        VereinfachtesResultSet selectResultSet =
                connect.issueSelectStatement(sqlStatement, groupId, project.getName(), role);
        if (selectResultSet != null && selectResultSet.next()) {
            result = new Contribution(
                    selectResultSet.getString("filelocation"),
                    selectResultSet.getString("filename"),
                    role);
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
     * @param user who is about to rate someone
     * @param project of interest
     * @return the user that will be rated
     */
    public User getNextGroupMemberToFeedback(User user, Project project) {
        User result = null;
        connect.connect();

        String query =
                "SELECT  us.email, us.name  from groupuser currentUser " +
                        "JOIN groupuser feedbackedUser " +
                        "ON currentUser.groupId = feedbackedUser.groupId " +
                        "JOIN groups g " +
                        "on currentUser.groupId = g.id " +
                        "JOIN users us " +
                        "on us.email = feedbackedUser.userEmail " +
                        "WHERE " + "currentUser.userEmail = ? " +
                        "AND g.projectName = ? " +
                        "AND currentUser.userEmail <> feedbackedUser.userEmail " +
                        "AND (currentUser.userEmail, feedbackedUser.userEmail) " +
                        "not in( " + "SELECT wr.fromPeer, wr.userEmail " + "from workrating wr  " +
                        ")" + "ORDER BY us.id; ";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName());
        if (vereinfachtesResultSet.next()) {
            String userEmail = vereinfachtesResultSet.getString("email");
            String name = vereinfachtesResultSet.getString("name");
            result = new User(userEmail);
            result.setName(name);
        }

        connect.close();
        return result;
    }

    public void persistInternalAssessment(
            Project project, User user, User feedbackedUser, HashMap<String, Integer> data) {

        connect.connect();
        for (String key : data.keySet()) {

            int rating = data.get(key);
            connect.issueInsertOrDeleteStatement(
                    "INSERT IGNORE INTO `workrating` (projectName, userEmail, fromPeer, rating, itemName)" + " values (?,?,?,?,?)",
                    project.getName(), feedbackedUser.getEmail(), user.getEmail(), rating, key);
        }
        connect.close();
    }

    /**
     * this returns the next group to rate contributions for the docent
     *
     * @param project of interest
     * @return a map which connects a task to the next group to rate
     */
    public TaskMapping getNextGroupToFeedbackForTeacher(Project project) {
        TaskMapping result = new TaskMapping(null, null, null, null, project);
        connect.connect();
        String query =
                "SELECT g.id as result from groups g " + " JOIN" + " projects p on g.projectName = p.name " + " where g.projectName = ? " + " AND (p.name, g.id, p.author) not in " + "    (SELECT cr.projectName, cr.groupId ,cr.fromTeacher from contributionrating cr " + "       WHERE cr.fromTeacher is not null" + "    )" + " LIMIT 1";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        if (vereinfachtesResultSet != null) {
            try {
                if (vereinfachtesResultSet.next()) {
                    int groupId = vereinfachtesResultSet.getInt("result");
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
     * (SELECT gu.userEmail as user, cr.rating as rating2, cr.projectName, fromTeacher from contributionrating cr
     * join groupuser gu on gu.groupId = cr.groupId
     * union
     * SELECT ucr.userName as user, ucr.rating as rating2, ucr.projectName, fromTeacher from contributionrating ucr
     * WHERE ucr.groupId is null) as T
     * WHERE projectName = "assessmenttest3"
     * and fromTeacher is null
     * group by user, rating2
     */
    HashMap<User, Double> getPeerProductRatings(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();

        String query =
                "SELECT * FROM (SELECT groupId, avg(rating) as avgGrade FROM `contributionrating` " +
                        "WHERE projectName=? AND fromTeacher IS NULL group By groupId) as T " +
                        "JOIN groupuser gu on T.groupId = gu.groupId";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();

        return result;
    }

    /**
     * SELECT gu.userEmail, avg(rating) as rating2 from contributionrating cr
     * join groupuser gu on gu.groupId = cr.groupId
     * where cr.projectName = "assessmenttest3"
     * and cr.fromPeer is null
     * group by userEmail
     *
     * @param project of interest
     * @return A map which connects projectMembers with the mean value of docents rating for their contributions
     */
    HashMap<User, Double> getDocentProductRatings(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();
        String query =
                "SELECT gu.userEmail, avg(rating) as avgGrade from contributionrating cr " + "join groupuser gu on gu.groupId = cr.groupId " + "where cr.projectName = ? " + "and cr.fromPeer is null " + "group by userEmail;";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();

        return result;
    }

    /**
     * SELECT pu.userEmail, avg(rating) as rating2 from workrating wr
     * join projectuser pu on pu.projectName = wr.projectName
     * where wr.projectName = "assessmenttest3"
     * and wr.userEmail = pu.userEmail
     * group by userEmail
     *
     * @param project of interest
     * @return A map which connects every projectMember with their avg grade regarding their contributions given by peers
     */
    HashMap<User, Double> getGroupRating(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();
        String query =
                "SELECT pu.userEmail, avg(rating) as avgGrade from workrating wr" + "   join projectuser pu on pu.projectName = wr.projectName" + "  where wr.projectName = ?" + "   and wr.userEmail = pu.userEmail" + "  group by userEmail ";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();
        return result;
    }

    HashMap<User, Double> getFinalRating(Project project) {
        HashMap<User, Double> result = new HashMap<>();
        connect.connect();
        String query = "SELECT userEmail, grade as avgGrade from grades g WHERE projectName = ?";
        convertResultSetToUserRatingMap(project, result, query);
        connect.close();
        return result;
    }

    private void convertResultSetToUserRatingMap(Project project, HashMap<User, Double> result, String query) {
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        while (vereinfachtesResultSet.next()) {
            User user = new User(vereinfachtesResultSet.getString("userEmail"));
            Double rating = vereinfachtesResultSet.getDouble("avgGrade");
            result.put(user, rating);
        }
    }

    /**
     * save grades in db
     *
     * @param project of interest
     * @param userAssessmentDataHolder holds all Data of the assessment
     */
    public void saveGrades(Project project, UserAssessmentDataHolder userAssessmentDataHolder) {
        // delete old grades in project
        connect.connect();
        connect.issueInsertOrDeleteStatement("DELETE from grades where projectName = ?", project.getName());
        // persist new ones
        List<UserPeerAssessmentData> data = userAssessmentDataHolder.getData();
        for (UserPeerAssessmentData datum : data) {
            connect.issueInsertOrDeleteStatement(
                    "INSERT IGNORE INTO `grades` (projectName, userEmail, grade)" +
                            "values (?,?,?)", project.getName(),
                    datum.getUser().getEmail(), datum.getFinalRating());
        }
        connect.close();
    }

    /**
     * select count(*) as result from groupuser gu1 join groupuser gu2 on gu1.groupId = gu2.groupId join groups g on gu1.groupId = g.id where gu1.userEmail = "a8@a8.com" and g.projectName = "ljhlkjhl" and gu1.userEmail <> gu2.userEmail
     *
     * @param project of interest
     * @param user who needs to assess his groupMembers
     * @return a int representing the number of missing ratings from user
     */
    public InternalPeerAssessmentProgress getInternalPeerAssessmentProgress(Project project, User user) {
        connect.connect();
        String query =
                "select count(gu1.userEmail) as result from groupuser gu1"
                        + " join groupuser gu2 on gu1.groupId = gu2.groupId "
                        + " join groups g on gu1.groupId = g.id"
                        + " where gu1.userEmail = ? and g.projectName = ?"
                        + " and gu1.userEmail <> gu2.userEmail and (gu1.userEmail, gu2.userEmail) not in"
                        + " (SELECT wr.fromPeer, wr.userEmail from workrating wr where wr.projectName = g.projectName)";
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(query, user.getEmail(), project.getName());
        vereinfachtesResultSet.next();
        int result = vereinfachtesResultSet.getInt("result");
        connect.close();
        return new InternalPeerAssessmentProgress(result);
    }

    public ProgessAndTaskMapping getTaskMappingAndProgress(Project project) {
        TaskMapping taskMapping = getNextGroupToFeedbackForTeacher(project);
        ProgressData progressData = getTeacherGroupFeedbackProgress(project);
        return new ProgessAndTaskMapping(taskMapping, progressData);
    }

    private ProgressData getTeacherGroupFeedbackProgress(Project project) {
        connect.connect();
        String query =
                "select count(g.id) as result from groups g" + " where g.id not in " + "(SELECT cr.groupId from " + "contributionrating cr" + " join projects p on cr.projectName = p.name " + " and cr.fromTeacher = p.author " + " group by cr.groupId) " + "and g.projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        vereinfachtesResultSet.next();
        int result = vereinfachtesResultSet.getInt("result");
        connect.close();
        ProgressData progressData = new ProgressData();
        progressData.setNumberNeeded(result);
        return progressData;
    }
}
