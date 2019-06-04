package unipotsdam.gf.modules.submission.controller;

import com.google.common.base.Strings;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.ISubmission;
import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.assessment.controller.model.ContributionCategory;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.model.*;
import unipotsdam.gf.modules.submission.view.SubmissionRenderData;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.progress.HasProgress;
import unipotsdam.gf.process.progress.ProgressData;
import unipotsdam.gf.process.tasks.GroupFeedbackTaskData;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.ProjectStatus;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionController implements ISubmission, HasProgress {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SubmissionController.class);
    @Inject
    private MysqlConnect connection;
    @Inject
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;
    @Inject
    private ProjectDAO projectDAO;

    @Override
    public FullSubmission addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest) {

        connection.connect();
        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (existsFullSubmissionId(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        // build and execute request
        String request = "REPLACE INTO fullsubmissions (`id`, `groupId`, `text`, `projectName`, `contributionCategory`) VALUES (?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, fullSubmissionPostRequest.getGroupId(),
                fullSubmissionPostRequest.getText(), fullSubmissionPostRequest.getProjectName(), fullSubmissionPostRequest.getContributionCategory());

        // close connection
        connection.close();

        // get the new submission from database
        FullSubmission fullSubmission = getFullSubmission(uuid);

        return fullSubmission;

    }

    @Override
    public FullSubmission getFullSubmission(String fullSubmissionId) {

        // establish connection
        connection.connect();

        FullSubmission fullSubmission = null;

        // build and execute request
        String request = "SELECT * FROM fullsubmissions WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId);

        if (rs.next()) {
            // save submission
            fullSubmission = getFullSubmissionFromResultSet(rs);

        }

        connection.close();

        return fullSubmission;
    }

    public String getFullSubmissionId(Integer groupId, Project project) {
        // establish connection
        String fullSubmissionId = null;
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM fullsubmissions WHERE groupId = ? AND projectName= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, groupId, project.getName());

        if (rs.next()) {
            // save submission
            fullSubmissionId = rs.getString("id");
        }
        connection.close();
        return fullSubmissionId;
    }

    private boolean existsFullSubmissionId(String id) {

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM fullsubmissions WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);

        return hasRow(rs);

    }

    @Override
    public SubmissionPart addSubmissionPart(SubmissionPartPostRequest submissionPartPostRequest) {

        // establish connection
        connection.connect();

        // build and execute request
        String request =
                "INSERT IGNORE INTO submissionparts (`groupId`, `fullSubmissionId`, `category`) VALUES (?,?,?);";

        connection.issueInsertOrDeleteStatement(request, submissionPartPostRequest.getGroupId(),
                submissionPartPostRequest.getFullSubmissionId(),
                submissionPartPostRequest.getCategory().toString().toUpperCase());


        for (SubmissionPartBodyElement element : submissionPartPostRequest.getBody()) {

            // calculate how many similar elements are next to the new element

            int similarElements = numOfSimilarBodyElements(submissionPartPostRequest.getFullSubmissionId(),
                    submissionPartPostRequest.getCategory(), element.getStartCharacter(), element.getEndCharacter());


            // hotfix
            //int similarElements = 0;
            switch (similarElements) {
                // similar element on the left side
                case -1:
                    log.info("case -1");

                    String requestElement =
                            "UPDATE submissionpartbodyelements SET endCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                    connection.issueUpdateStatement(requestElement, element.getEndCharacter(),
                            submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(),
                            element.getStartCharacter());

                    break;
                // no similar element
                case 0:
                    log.info("case 0");

                    if (!hasOverlappingBoundaries(submissionPartPostRequest.getFullSubmissionId(),
                            submissionPartPostRequest.getCategory(), element)) {
                        String requestElement0 =
                                "INSERT IGNORE INTO submissionpartbodyelements (`fullSubmissionId`, `category`, `startCharacter`, `endCharacter`) VALUES (?,?,?,?);";
                        connection.issueInsertOrDeleteStatement(requestElement0,
                                submissionPartPostRequest.getFullSubmissionId(),
                                submissionPartPostRequest.getCategory().toString().toUpperCase(),
                                element.getStartCharacter(), element.getEndCharacter());
                    }

                    break;
                // similar element on the right side
                case 1:
                    log.info("case 1");

                    String requestElement1 =
                            "UPDATE submissionpartbodyelements SET startCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";

                    int startCharacter = element.getStartCharacter();
                    String fullSubmissionId = submissionPartPostRequest.getFullSubmissionId();
                    Category category = submissionPartPostRequest.getCategory();
                    int endCharacter = element.getEndCharacter();
                    connection.issueUpdateStatement(requestElement1, startCharacter, fullSubmissionId, category,
                            endCharacter);

                    break;
                // similar elements on both sides
                case 2:
                    log.info("case 2");

                    // fetch end character from right element
                    String requestElement2 =
                            "SELECT endCharacter FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";
                    VereinfachtesResultSet rs = connection
                            .issueSelectStatement(requestElement2, submissionPartPostRequest.getFullSubmissionId(),
                                    submissionPartPostRequest.getCategory(), element.getEndCharacter());
                    rs.next();
                    Integer end = rs.getInt("endCharacter");

                    // delete right element
                    String deleteElement =
                            "DELETE FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";
                    connection.issueInsertOrDeleteStatement(deleteElement,
                            submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(),
                            element.getEndCharacter());

                    // updateForUser left element
                    String updateElement =
                            "UPDATE submissionpartbodyelements SET endCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                    connection.issueUpdateStatement(updateElement, end, submissionPartPostRequest.getFullSubmissionId(),
                            submissionPartPostRequest.getCategory(), element.getStartCharacter());

            }
        }

        connection.close();

        // get the new submission from database
        SubmissionPart submissionPart = getSubmissionPart(submissionPartPostRequest.getFullSubmissionId(),
                submissionPartPostRequest.getCategory());

        return submissionPart;

    }

    @Override
    public SubmissionPart getSubmissionPart(String fullSubmissionId, Category category) {

        connection.connect();

        // result
        SubmissionPart submissionPart = null;

        // declare text
        String text;

        // build and execute request to receive text
        String requestText = "SELECT text " + "FROM fullsubmissions " + "WHERE id = ?";
        VereinfachtesResultSet rsText = connection.issueSelectStatement(requestText, fullSubmissionId);

        if (rsText.next()) {
            // save text
            text = rsText.getString("text");

            // build and execute request
            String request =
                    "SELECT * FROM submissionparts s " + "LEFT JOIN submissionpartbodyelements b " + "ON s.fullSubmissionId = b.fullSubmissionId " + "AND s.category = b.category " + "WHERE s.fullSubmissionId = ? " + "AND s.category = ?;";
            VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category);

            if (rs.next()) {
                // save submission
                submissionPart = getSubmissionPartFromResultSet(rs, text);

            }
        }
        connection.close();

        return submissionPart;

    }

    @Override
    public ArrayList<SubmissionPart> getAllSubmissionParts(String fullSubmissionId) {

        // establish connection

        connection.connect();

        // result
        ArrayList<SubmissionPart> submissionParts = new ArrayList<>();

        // declare text
        String text;

        // build and execute request to receive text
        String requestText = "SELECT text " + "FROM fullsubmissions " + "WHERE id = ?";
        VereinfachtesResultSet rsText = connection.issueSelectStatement(requestText, fullSubmissionId);

        if (rsText.next()) {
            // save text
            text = rsText.getString("text");

            // build and execute request
            String request =
                    "SELECT * " + "FROM submissionparts sp " + "LEFT JOIN submissionpartbodyelements  spbe " + "ON sp.fullSubmissionId = spbe.fullSubmissionId " + "AND sp.category = spbe.category " + "WHERE sp.fullSubmissionId = ? " + "ORDER BY sp.timestamp;";
            VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId);

            if (rs.next()) {
                // save submission
                submissionParts = getAllSubmissionPartsFromResultSet(rs, text);
            }
        }

        // close connection
        connection.close();

        return submissionParts;

    }

    @Override
    public ArrayList<SubmissionProjectRepresentation> getSubmissionPartsByProjectId(String projectName) {

        // establish connection

        connection.connect();

        // build and execute request
        String request =
                "SELECT s.userEmail, s.category, s.fullSubmissionId " + "FROM fullsubmissions f " + "LEFT JOIN submissionparts s " + "ON f.id = s.fullSubmissionId " + "WHERE f.projectName = ?";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, projectName);

        ArrayList<SubmissionProjectRepresentation> representations;

        // save submission
        representations = getAllSubmissionProjectRepresentationsFromResultSet(rs);

        // close connection
        connection.close();

        return representations;

    }

    @Override
    public boolean existsSubmissionPart(String fullSubmissionId, Category category) {

        // establish connection

        connection.connect();

        // build and execute request
        String request =
                "SELECT COUNT(*) > 0 AS `exists` FROM submissionparts WHERE fullSubmissionId = ? AND category = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category);

        return hasRow(rs);

    }

    private Boolean hasRow(VereinfachtesResultSet rs) {
        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // return true if we found the id
            if (count < 1) {
                return false;
            } else {
                return true;
            }
        }
        return null;
    }

    /**
     * Build a full submission object from a given result set
     *
     * @param rs The result set from a database query
     * @return A new full submission object
     */
    private FullSubmission getFullSubmissionFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp("timestamp").getTime();
        Integer groupId = rs.getInt("groupId");
        String text = rs.getString("text");
        String projectName = rs.getString("projectName");
        ContributionCategory contributionCategory = ContributionCategory.valueOf(rs.getString("contributionCategory"));

        return new FullSubmission(id, timestamp, groupId, text, contributionCategory, projectName);

    }

    /**
     * Build a submission part object from a given result set
     *
     * @param rs The result set from the database query
     * @return A new submission part object
     */
    private SubmissionPart getSubmissionPartFromResultSet(VereinfachtesResultSet rs, String text) {

        // declare variables
        int start, end;
        String textPart;

        // initialize variables
        long timestamp = rs.getTimestamp("timestamp").getTime();
        String userEmail = rs.getString("groupId");
        String fullSubmissionId = rs.getString("fullSubmissionId");
        Category category = Category.valueOf(rs.getString("category").toUpperCase());

        // build body and iterate over result set
        ArrayList<SubmissionPartBodyElement> body = new ArrayList<>();

        do {
            // initialize body variables
            start = rs.getInt("startCharacter");
            end = rs.getInt("endCharacter");
            textPart = text.substring(start, end);

            // build element
            SubmissionPartBodyElement element = new SubmissionPartBodyElement(textPart, start, end);

            body.add(element);
        } while (rs.next());

        return new SubmissionPart(timestamp, userEmail, fullSubmissionId, category, body);
    }

    /**
     * Build an array of submission part objects from a given result set
     *
     * @param rs The result set from the database query, holding different submission parts
     * @return An array of submission parts
     */
    private ArrayList<SubmissionPart> getAllSubmissionPartsFromResultSet(VereinfachtesResultSet rs, String text) {

        // declare variables
        int start, end;
        String textPart;

        ArrayList<SubmissionPart> submissionParts = new ArrayList<>();
        // tmp part
        SubmissionPart tmpPart = null;
        // tmp body element
        SubmissionPartBodyElement tmpElement;
        // tmp category
        String tmpCategory = "";
        do {

            if (!tmpCategory.equals(rs.getString("category").toUpperCase())) {
                // current tmp category
                tmpCategory = rs.getString("category").toUpperCase();

                // add last submission part
                if (tmpPart != null) {
                    submissionParts.add(tmpPart);
                }

                // build submission part with empty body
                tmpPart = new SubmissionPart(rs.getTimestamp("timestamp").getTime(), rs.getString("userEmail"),
                        rs.getString("fullSubmissionId"), Category.valueOf(tmpCategory),
                        new ArrayList<SubmissionPartBodyElement>());
            }

            // initialize body variables
            start = rs.getInt("startCharacter");
            end = rs.getInt("endCharacter");
            textPart = text.substring(start, end);

            tmpElement = new SubmissionPartBodyElement(textPart, start, end);

            tmpPart.getBody().add(tmpElement);

        } while (rs.next());

        // add last part
        submissionParts.add(tmpPart);

        return submissionParts;
    }

    private ArrayList<SubmissionProjectRepresentation> getAllSubmissionProjectRepresentationsFromResultSet(
            VereinfachtesResultSet rs) {

        ArrayList<SubmissionProjectRepresentation> representations = new ArrayList<>();

        while (rs.next()) {
            if (!Strings.isNullOrEmpty(rs.getString("category"))) {
                representations.add(new SubmissionProjectRepresentation(rs.getInt("groupId"),
                        Category.valueOf(rs.getString("category").toUpperCase()), rs.getString("fullSubmissionId")));
            }
        }

        return representations;

    }

    /**
     * Calculates how many similar body elements (based on start and end character) can be found in the database
     *
     * @param fullSubmissionId The id of the full submission
     * @param category         The category of the submission part
     * @param startCharacter   The start character of the new element
     * @param endCharacter     The end character of the old element
     * @return Return 0 if there are no similar elements, 2 if we found two similar elements (right and left side),
     * 1 if we found a similar element on the right side and -1 if we found a similar element on the left side.
     */
    private int numOfSimilarBodyElements(
            String fullSubmissionId, Category category, int startCharacter, int endCharacter) {

        // build and execute request
        String request =
                "SELECT COUNT(*) AS `count` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND (endCharacter = ? OR startCharacter = ?);";
        VereinfachtesResultSet rs = connection
                .issueSelectStatement(request, fullSubmissionId, category.toString().toUpperCase(), startCharacter,
                        endCharacter);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("count");

            // found one element; left or right side
            if (count == 1) {

                // build and execute request to find out on which side we found a similar body element
                String requestSide =
                        "SELECT COUNT(*) AS `side` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                VereinfachtesResultSet rsSide = connection
                        .issueSelectStatement(requestSide, fullSubmissionId, category.toString().toUpperCase(),
                                startCharacter);

                if (rsSide.next()) {
                    // save the response
                    int side = rsSide.getInt("side");

                    if (side == 1) {
                        return -1;
                    } else {
                        return 1;
                    }

                }
            } else {

                return count;
            }
        }

        return 0;

    }

    /**
     * Checks if a new body element has overlapping boundaries with an already existing element
     *
     * @param fullSubmissionId The id of the full submission
     * @param category         The category
     * @param element          The new element
     * @return Returns true if overlapping boundaries have been found
     */
    private boolean hasOverlappingBoundaries(
            String fullSubmissionId, Category category, SubmissionPartBodyElement element) {

        // initialize start and end character
        int start = element.getStartCharacter();
        int end = element.getEndCharacter();

        // build and execute request
        String request =
                "SELECT COUNT(*) > 0 AS `exists` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND (" + "(startCharacter <= ? AND ? <= endCharacter) OR " + // start character overlapping
                        "(startCharacter <= ? AND ? <= endCharacter));"; // end character overlapping
        VereinfachtesResultSet rs =
                connection.issueSelectStatement(request, fullSubmissionId, category.toString(), start, start, end, end);

        return hasRow(rs);
    }

    public SubmissionRenderData getSubmissionData(User user, Project project) {
        String query = "SELECT id FROM fullsubmissions WHERE groupId = ? AND projectName = ?";
        SubmissionRenderData data = new SubmissionRenderData();

        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, user.getEmail(), project.getName());

        if (rs.next()) {
            String id = rs.getString("id");
            data.setFullSubmissionId(id);
        } else {
            log.debug("fullSubmissionID not found");
        }

        connection.close();

        return data;
    }

    /**
     * if the submission is marked as final, the annotations cannot be updated anymore
     *
     * @param fullSubmission
     */
    public void markAsFinal(FullSubmission fullSubmission) {
        connection.connect();
        String query = "update fullsubmissions set finalized = ? where id = ?";
        connection.issueUpdateStatement(query, 1, fullSubmission.getId());
        connection.close();
    }

    /**
     * link the full submission with a user who is supposed to give feedback to it.
     * this creates a 1:1 relationship between user and submissions
     * in case gorup work is selected the relationship should be with a group instead
     *
     * @param submissionOwner
     * @param feedbackGiver
     */
    @Deprecated
    public void updateFullSubmission(User submissionOwner, User feedbackGiver) {
        connection.connect();
        String query = "update RocketChatUserName fullsubmissions set feedbackUser = ? where user = ?";
        connection.issueUpdateStatement(query, feedbackGiver.getEmail(), submissionOwner.getEmail());
        connection.close();
        // TODO implement linking submission with group
    }

    /**
     * @param target
     * @return
     */
    public GroupFeedbackTaskData getFeedbackTaskData(User target, Project project) {
        connection.connect();
        Integer targetGroupId = groupDAO.getGroupByStudent(project, target);
        String query = "SELECT * from fullsubmissions where feedbackGroup = ? and projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, targetGroupId,
                project.getName());
        return resultSetToFeedback(targetGroupId, vereinfachtesResultSet);
    }

    public GroupFeedbackTaskData getMyFeedback(User user, Project project) {
        connection.connect();
        Integer groupId = groupDAO.getGroupByStudent(project, user);
        String query = "SELECT * from fullsubmissions where groupId = ? and projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, groupId,
                project.getName());
        return resultSetToFeedback(groupId, vereinfachtesResultSet);
    }

    private GroupFeedbackTaskData resultSetToFeedback(Integer targetGroupId, VereinfachtesResultSet vereinfachtesResultSet) {
        if (vereinfachtesResultSet != null && vereinfachtesResultSet.next()) {
            String submissionId = vereinfachtesResultSet.getString("id");
            String projectName = vereinfachtesResultSet.getString("projectName");
            Category category = Category.TITEL;
            FullSubmission fullSubmission = new FullSubmission(submissionId);
            fullSubmission.setProjectName(projectName);
            connection.close();
            return new GroupFeedbackTaskData(targetGroupId, fullSubmission, category);
        } else
            return null;
    }

    public int getFinalizedDossiersCount(Project project) {
        connection.connect();

        Integer count = null;
        String query = "SELECT COUNT(*) from fullsubmissions where projectName = ? and finalized = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, project.getName(), true);
        vereinfachtesResultSet.next();
        count = vereinfachtesResultSet.getInt(1);
        connection.close();
        return count;

    }


    @Override
    public ProgressData getProgressData(Project project) {

        ProgressData progressData = new ProgressData();
        // the number of completed dossiers
        progressData.setNumberOfCompletion(getFinalizedDossiersCount(project));

        // the number of dossiers needed relative to the group or user count
        progressData.setNumberNeeded(dossiersNeeded(project));
        List<User> strugglersWithSubmission = getStrugglersWithSubmission(project);
        progressData.setUsersMissing(strugglersWithSubmission);
        progressData
                .setAlmostComplete((progressData.getNumberOfCompletion() / progressData.getNumberNeeded()) <= (1 / 10));
        return progressData;
    }

    /**
     * get how many dossiers are needed
     *
     * @param project
     * @return
     */
    public int dossiersNeeded(Project project) {
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
        Integer result = 0;
        switch (groupFormationMechanism) {
            case SingleUser:
                ProjectStatus participantCount = projectDAO.getParticipantCount(project);
                result = participantCount.getParticipants();
                break;
            case LearningGoalStrategy:
            case UserProfilStrategy:
            case Manual:
                int groupCount = groupDAO.getGroupsByProjectName(project.getName()).size();
                result = groupCount;
                break;
        }
        return result;
    }


    public List<User> getStrugglersWithSubmission(Project project) {
        ArrayList<User> struggles = new ArrayList<>();
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
        switch (groupFormationMechanism) {
            case SingleUser:
                List<User> usersInProject = userDAO.getUsersByProjectName(project.getName());
                List<User> usersHaveGivenFeedback = getAllUsersWithDossierUploaded(project);
                for (User user : usersInProject) {
                    if (!usersHaveGivenFeedback.contains(user)) {
                        struggles.add(user);
                    }
                }
                break;
            case LearningGoalStrategy:
            case Manual:
            case UserProfilStrategy:
        }
        return struggles;
    }

    public List<User> getAllUsersWithDossierUploaded(Project project) {
        List<User> result = new ArrayList<>();
        connection.connect();
        String query = "select * from fullsubmissions where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, project.getName());

        while (vereinfachtesResultSet.next()) {
            result.add(userDAO.getUserByEmail(vereinfachtesResultSet.getString("feedbackUser")));
        }
        connection.close();
        return result;
    }

    public List<User> getAllUsersWithFinalizedFeedback(Project project) {
        List<User> result = new ArrayList<>();
        connection.connect();
        String query = "select * from tasks where projectName = ? and taskName = ? and progress=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName(), TaskName.GIVE_FEEDBACK, Progress.FINISHED);

        while (vereinfachtesResultSet.next()) {
            result.add(userDAO.getUserByEmail(vereinfachtesResultSet.getString("userEmail")));
        }
        connection.close();
        return result;
    }

    public Integer getFeedbackedgroup(Project project, Integer groupId) {
        Integer feedbackedGroup;
        connection.connect();
        String query = "select groupId from fullsubmissions where projectName = ? AND feedbackGroup=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName(), groupId);
        vereinfachtesResultSet.next();
        feedbackedGroup = vereinfachtesResultSet.getInt("groupId");
        connection.close();
        return feedbackedGroup;
    }
}