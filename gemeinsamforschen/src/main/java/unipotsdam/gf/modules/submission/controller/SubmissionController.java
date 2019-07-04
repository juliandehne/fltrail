package unipotsdam.gf.modules.submission.controller;

import com.google.common.base.Strings;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.ISubmission;
import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionPart;
import unipotsdam.gf.modules.submission.model.SubmissionPartBodyElement;
import unipotsdam.gf.modules.submission.model.SubmissionPartPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionProjectRepresentation;
import unipotsdam.gf.modules.submission.model.Visibility;
import unipotsdam.gf.modules.submission.view.SubmissionRenderData;
import unipotsdam.gf.modules.user.User;
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
import java.util.Objects;
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
    private GroupDAO groupDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Override
    public FullSubmission addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest) {
        return addFullSubmission(fullSubmissionPostRequest, 0);
    }

    @Override
    public FullSubmission addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest, Integer version) {
        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        String requestCommand = "INSERT INTO";

        if (!Strings.isNullOrEmpty(fullSubmissionPostRequest.getId())) {
            uuid = fullSubmissionPostRequest.getId();
            requestCommand = "REPLACE INTO";
        }

        String request = String.join(" ", requestCommand.trim(),
                "fullsubmissions (`id`, `version`, `groupId`, `text`, `projectName`, `fileRole`, `userEmail`, `visibility`) VALUES (?,?,?,?,?,?,?,?);");

        connection.connect();
        // build and execute request
        connection.issueInsertOrDeleteStatement(request, uuid, version,
                fullSubmissionPostRequest.getGroupId(),
                fullSubmissionPostRequest.getText(),
                fullSubmissionPostRequest.getProjectName(),
                fullSubmissionPostRequest.getFileRole().toString(),
                fullSubmissionPostRequest.getUserEMail(),
                fullSubmissionPostRequest.getVisibility().toString()
        );

        // close connection
        connection.close();

        // get the new submission from database
        return getFullSubmission(uuid, version);

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

    @Override
    public FullSubmission getFullSubmission(String fullSubmissionId, Integer version) {

        // establish connection
        connection.connect();

        FullSubmission fullSubmission = null;

        // build and execute request
        String request = "SELECT * FROM fullsubmissions WHERE id = ? AND version = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, version);
        if (rs.next()) {
            // save submission
            fullSubmission = getFullSubmissionFromResultSet(rs);

        }

        connection.close();

        return fullSubmission;
    }

    @Override
    public void updateFullSubmissionTextAndVisibility(FullSubmissionPostRequest fullSubmission) {
        connection.connect();
        String request = "UPDATE fullsubmissions set text = ?, visibility = ? where id = ?";
        connection.issueUpdateStatement(request, fullSubmission.getText(), fullSubmission.getVisibility().name(), fullSubmission.getId());
        connection.close();
    }

    public List<FullSubmission> getPersonalSubmissions(User user, Project project, FileRole fileRole) {
        List<FullSubmission> fullSubmissionList = new ArrayList<>();
        connection.connect();

        String request = "SELECT * FROM fullsubmissions WHERE userEmail = ? AND projectName= ? AND fileRole = ? ORDER BY timestamp DESC;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, user.getEmail(), project.getName(), fileRole);

        while (rs.next()) {
            fullSubmissionList.add(getFullSubmissionFromResultSet(rs));
        }
        connection.close();
        return fullSubmissionList;
    }

    public FullSubmission getFullSubmissionBy(int groupId, Project project, FileRole fileRole) {
        FullSubmission fullSubmission = null;
        connection.connect();

        String request = "SELECT * FROM fullsubmissions WHERE groupId = ? AND projectName= ? AND fileRole = ? AND userEmail = null;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, groupId, project.getName(), fileRole);

        if (rs.next()) {
            fullSubmission = getFullSubmissionFromResultSet(rs);
        }
        connection.close();
        return fullSubmission;
    }

    public FullSubmission getFullSubmissionBy(int groupId, Project project, FileRole fileRole, Integer version) {

        FullSubmission fullSubmission = null;
        connection.connect();

        String request = "SELECT * FROM fullsubmissions WHERE groupId = ? AND projectName= ? AND fileRole = ? AND version = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, groupId, project.getName(), fileRole, version);

        if (rs.next()) {
            fullSubmission = getFullSubmissionFromResultSet(rs);
        }
        connection.close();
        return fullSubmission;
    }

    public List<FullSubmission> getGroupSubmissions(Project project, int groupId, FileRole fileRole, Visibility visibility) {
        List<FullSubmission> fullSubmissionList = new ArrayList<>();
        connection.connect();
        String query = "SELECT * FROM fullsubmissions WHERE groupId = ? AND projectName= ? AND fileRole = ? and visibility = ? ORDER BY timestamp DESC;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(query, groupId, project.getName(), fileRole.name(), visibility.name());
        while (rs.next()) {
            fullSubmissionList.add(getFullSubmissionFromResultSet(rs));
        }

        return fullSubmissionList;
    }

    public List<FullSubmission> getProjectSubmissions(Project project, FileRole fileRole, Visibility visibility) {
        List<FullSubmission> fullSubmissionList = new ArrayList<>();
        connection.connect();
        String query = "SELECT * FROM fullsubmissions WHERE projectName= ? AND fileRole = ? and visibility = ? ORDER BY timestamp DESC;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(query, project.getName(), fileRole.name(), visibility);
        while (rs.next()) {
            fullSubmissionList.add(getFullSubmissionFromResultSet(rs));
        }

        return fullSubmissionList;
    }

    public String getFullSubmissionId(Integer groupId, Project project, FileRole fileRole) {
        return getFullSubmissionId(groupId, project, fileRole, 0);
    }

    public String getFullSubmissionId(Integer groupId, Project project, FileRole fileRole, Integer version) {
        FullSubmission fullSubmission = getFullSubmissionBy(groupId, project, fileRole, version);
        String fullSubmissionId = null;
        if (!Objects.isNull(fullSubmission)) {
            fullSubmissionId = fullSubmission.getId();
        }
        return fullSubmissionId;
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

        return getSubmissionPart(submissionPartPostRequest.getFullSubmissionId(),
                submissionPartPostRequest.getCategory());

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

        Boolean result = hasRow(rs);
        connection.close();

        return result;

    }

    private Boolean hasRow(VereinfachtesResultSet rs) {
        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // return true if we found the id
            return count >= 1;
        }
        return false;
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
        String userEmail = rs.getString("userEmail");
        String text = rs.getString("text");
        String projectName = rs.getString("projectName");
        FileRole fileRole = FileRole.valueOf(rs.getString("fileRole"));
        Visibility visibility = Visibility.valueOf(rs.getString("visibility"));

        return new FullSubmission(id, timestamp, groupId, userEmail, text, fileRole, projectName, visibility);

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

    public SubmissionRenderData getSubmissionData(Integer groupId, Project project) {
        String query = "SELECT id FROM fullsubmissions WHERE groupId = ? AND projectName = ?";
        SubmissionRenderData data = new SubmissionRenderData();

        connection.connect();

        VereinfachtesResultSet rs = connection.issueSelectStatement(query, groupId, project.getName());

        if (rs.next()) {
            String id = rs.getString("id");
            data.setFullSubmissionId(id);
        } else {
            log.debug("fullSubmissionID not found");
        }

        connection.close();

        return data;
    }

    public SubmissionRenderData getSubmissionData(User user, Project project) {
        Integer groupId = groupDAO.getGroupByStudent(project, user);
        return getSubmissionData(groupId, project);
    }

    /**
     * if the submission is marked as final, the annotations cannot be updated anymore
     *
     * @param fullSubmission
     */
    public void markAsFinal(FullSubmission fullSubmission, Boolean finalized) {
        connection.connect();
        String query = "update fullsubmissions set finalized = ? where id = ?";
        connection.issueUpdateStatement(query, finalized, fullSubmission.getId());
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
        Integer targetGroupId = groupDAO.getGroupByStudent(project, target);
        return getFeedbackTaskData(targetGroupId, project);
    }

    public GroupFeedbackTaskData getFeedbackTaskData(Integer groupId, Project project) {
        connection.connect();
        String query = "SELECT * from fullsubmissions where feedbackGroup = ? and projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, groupId,
                project.getName());
        return resultSetToFeedback(groupId, vereinfachtesResultSet);
    }

    public GroupFeedbackTaskData getMyFeedback(User user, Project project) {
        Integer groupId = groupDAO.getGroupByStudent(project, user);
        return getMyFeedback(groupId, project);
    }

    public GroupFeedbackTaskData getMyFeedback(Integer groupId, Project project) {
        connection.connect();
        String query = "SELECT * from fullsubmissions where groupId = ? and projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, groupId,
                project.getName());
        return resultSetToFeedback(groupId, vereinfachtesResultSet);
    }

    private GroupFeedbackTaskData resultSetToFeedback(Integer targetGroupId, VereinfachtesResultSet vereinfachtesResultSet) {
        if (vereinfachtesResultSet != null && vereinfachtesResultSet.next()) {
            String submissionId = vereinfachtesResultSet.getString("id");
            String projectName = vereinfachtesResultSet.getString("projectName");
            FullSubmission fullSubmission = new FullSubmission(submissionId);
            fullSubmission.setProjectName(projectName);
            connection.close();
            Category category = Category.valueOf(getAnnotationCategories(new Project(projectName)).get(0));
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
        List<Group> strugglersWithSubmission = getStrugglersWithSubmission(project);
        progressData.setGroupsMissing(strugglersWithSubmission);
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


    public List<Group> getStrugglersWithSubmission(Project project) {
        ArrayList<Group> struggles = new ArrayList<>();
        List<Group> groupsInProject = groupDAO.getGroupsByProjectName(project.getName());
        List<Group> groupsHaveGivenFeedback = getAllGroupsWithDossierUploaded(project);
        for (Group group : groupsInProject) {
            if (!groupsHaveGivenFeedback.contains(group)) {
                struggles.add(group);
            }
        }
        return struggles;
    }

    public List<Group> getAllGroupsWithDossierUploaded(Project project) {
        List<Group> result = new ArrayList<>();
        connection.connect();
        String query = "select * from fullsubmissions where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query, project.getName());

        while (vereinfachtesResultSet.next()) {
            result.add(groupDAO.getGroupByGroupId(vereinfachtesResultSet.getInt("feedbackGroup")));
        }
        connection.close();
        return result;
    }

    public List<Group> getAllGroupsWithFinalizedFeedback(Project project) {
        List<Group> result = new ArrayList<>();
        connection.connect();
        String query = "select * from tasks where projectName = ? and taskName = ? and progress=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName(), TaskName.GIVE_FEEDBACK, Progress.FINISHED);

        while (vereinfachtesResultSet.next()) {
            Integer groupId = vereinfachtesResultSet.getInt("groupTask");
            result.add(groupDAO.getGroupByGroupId(groupId));
        }
        connection.close();
        return result;
    }

    public List<Group> getAllGroupsWithFinalizedDossier(Project project) {
        List<Group> result = new ArrayList<>();
        connection.connect();
        String query = "select * from fullSubmissions where projectName = ? and version = ? and finalized=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName(), 1, 1);

        while (vereinfachtesResultSet.next()) {
            Integer groupId = vereinfachtesResultSet.getInt("groupId");
            result.add(groupDAO.getGroupByGroupId(groupId));
        }
        connection.close();
        return result;
    }

    public int getFeedbackedgroup(Project project, Integer groupId) {
        int feedbackedGroup = 0;
        connection.connect();
        String query = "select groupId from fullsubmissions where projectName = ? AND feedbackGroup=?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName(), groupId);
        if (vereinfachtesResultSet.next()) {
            feedbackedGroup = vereinfachtesResultSet.getInt("groupId");
        }
        connection.close();
        return feedbackedGroup;
    }

    public List<String> getAnnotationCategories(Project project) {
        List<String> result = new ArrayList<>();
        connection.connect();
        String query = "select * from categoriesselected where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connection.issueSelectStatement(query,
                project.getName());
        while (vereinfachtesResultSet.next()) {

            result.add(vereinfachtesResultSet.getString("categorySelected"));
        }
        connection.close();
        return result;
    }
}