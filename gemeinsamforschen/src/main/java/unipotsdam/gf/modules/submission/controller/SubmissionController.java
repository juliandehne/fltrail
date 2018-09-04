package unipotsdam.gf.modules.submission.controller;

import com.google.common.base.Strings;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.interfaces.ISubmission;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.submission.model.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class SubmissionController implements ISubmission {
    @Override
    public FullSubmission addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest) {

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (existsFullSubmissionId(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO fullsubmissions (`id`, `user`, `text`) VALUES (?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, fullSubmissionPostRequest.getUser(), fullSubmissionPostRequest.getText());

        // get the new submission from database
        FullSubmission fullSubmission = getFullSubmission(uuid);

        // close connection
        connection.close();

        return fullSubmission;

    }

    @Override
    public FullSubmission getFullSubmission(String fullSubmissionId) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM fullsubmissions WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId);

        if (rs.next()) {
            // save submission
            FullSubmission fullSubmission = getFullSubmissionFromResultSet(rs);

            // close connection
            connection.close();

            return fullSubmission;
        }
        else {

            // close connection
            connection.close();

            return null;
        }

    }

    @Override
    public boolean existsFullSubmissionId(String id) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM fullsubmissions WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // close connection
            connection.close();

            // return true if we found the id
            if (count < 1) {
                return false;
            }
            else {
                return true;
            }
        }

        // something happened
        return true;

    }

    @Override
    public SubmissionPart addSubmissionPart(SubmissionPartPostRequest submissionPartPostRequest) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT IGNORE INTO submissionparts (`userId`, `fullSubmissionId`, `category`) VALUES (?,?,?);";
        connection.issueInsertOrDeleteStatement(request, submissionPartPostRequest.getUserId(), submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory().toString().toUpperCase());

        // declare request string
        String requestElement;
        for (SubmissionPartBodyElement element : submissionPartPostRequest.getBody()) {

            // calculate how many similar elements are next to the new element
            int similarElements = numOfSimilarBodyElements(submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getStartCharacter(), element.getEndCharacter());

            switch (similarElements) {
                // similar element on the left side
                case -1:
                    requestElement = "UPDATE submissionpartbodyelements SET endCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                    connection.issueUpdateStatement(requestElement, element.getEndCharacter(), submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getStartCharacter());
                    break;
                // no similar element
                case 0:
                    if (!hasOverlappingBoundaries(submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element)) {
                        requestElement = "INSERT IGNORE INTO submissionpartbodyelements (`fullSubmissionId`, `category`, `text`, `startCharacter`, `endCharacter`) VALUES (?,?,?,?,?);";
                        connection.issueInsertOrDeleteStatement(requestElement, submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory().toString().toUpperCase(), element.getText(), element.getStartCharacter(), element.getEndCharacter());
                    }
                    break;
                // similar element on the right side
                case 1:
                    requestElement = "UPDATE submissionpartbodyelements SET startCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";
                    connection.issueUpdateStatement(requestElement, element.getStartCharacter(), submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getEndCharacter());
                    break;
                // similar elements on both sides
                case 2:
                    // fetch end character from right element
                    requestElement = "SELECT endCharacter FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";
                    VereinfachtesResultSet rs = connection.issueSelectStatement(requestElement, submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getEndCharacter());

                    // delete right element
                    String deleteElement = "DELETE FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND startCharacter = ?;";
                    connection.issueInsertOrDeleteStatement(deleteElement, submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getEndCharacter());

                    if (rs.next()) {
                        int end = rs.getInt("endCharacter");

                        // update left element
                        String updateElement = "UPDATE submissionpartbodyelements SET endCharacter = ? WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                        connection.issueUpdateStatement(updateElement, end, submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory(), element.getStartCharacter());

                    }

            }

        }

        // get the new submission from database
        SubmissionPart submissionPart = getSubmissionPart(submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory());

        // close connection
        connection.close();

        return submissionPart;

    }

    @Override
    public SubmissionPart getSubmissionPart(String fullSubmissionId, Category category) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM submissionparts s " +
                "LEFT JOIN submissionpartbodyelements b " +
                "ON s.fullSubmissionId = b.fullSubmissionId " +
                "AND s.category = b.category " +
                "WHERE s.fullSubmissionId = ? " +
                "AND s.category = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category);

        if (rs.next()) {
            // save submission
            SubmissionPart submissionPart = getSubmissionPartFromResultSet(rs);

            // close connection
            connection.close();

            return submissionPart;
        }
        else {
            // close connection
            connection.close();

            return null;
        }

    }

    @Override
    public ArrayList<SubmissionPart> getAllSubmissionParts(String fullSubmissionId) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * " +
                "FROM submissionparts sp " +
                "LEFT JOIN submissionpartbodyelements  spbe " +
                "ON sp.fullSubmissionId = spbe.fullSubmissionId " +
                "AND sp.category = spbe.category " +
                "WHERE sp.fullSubmissionId = ? " +
                "ORDER BY sp.timestamp;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId);

        ArrayList<SubmissionPart> submissionParts = new ArrayList<>();

        if (rs.next()) {
            // save submission
            submissionParts = getAllSubmissionPartsFromResultSet(rs);
        }

        // close connection
        connection.close();

        return submissionParts;

    }

    @Override
    public boolean existsSubmissionPart(String fullSubmissionId, Category category) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM submissionparts WHERE fullSubmissionId = ? AND category = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // close connection
            connection.close();

            // return true if we found the id
            if (count < 1) {
                return false;
            }
            else {
                return true;
            }
        }

        // something happened
        return true;

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
        String user = rs.getString("user");
        String text = rs.getString("text");

        return new FullSubmission(id, timestamp, user, text);

    }

    /**
     * Build a submission part object from a given result set
     *
     * @param rs The result set from the database query
     * @return A new submission part object
     */
    private SubmissionPart getSubmissionPartFromResultSet(VereinfachtesResultSet rs) {

        long timestamp = rs.getTimestamp("timestamp").getTime();
        String userId = rs.getString("userId");
        String fullSubmissionId = rs.getString("fullSubmissionId");
        Category category = Category.valueOf(rs.getString("category").toUpperCase());

        // build body and iterate over result set
        ArrayList<SubmissionPartBodyElement> body = new ArrayList<>();

        do {
            // only add it if the element is not empty
            if (!Strings.isNullOrEmpty(rs.getString("text"))) {
                SubmissionPartBodyElement element = new SubmissionPartBodyElement(
                        rs.getString("text"),
                        rs.getInt("startCharacter"),
                        rs.getInt("endCharacter"));

                body.add(element);
            }
        } while (rs.next());

        return new SubmissionPart(timestamp, userId, fullSubmissionId, category, body);
    }

    /**
     * Build an array of submission part objects from a given result set
     *
     * @param rs The result set from the database query, holding different submission parts
     * @return An array of submission parts
     */
    private ArrayList<SubmissionPart> getAllSubmissionPartsFromResultSet(VereinfachtesResultSet rs) {

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
                tmpPart = new SubmissionPart(
                    rs.getTimestamp("timestamp").getTime(),
                    rs.getString("userId"),
                    rs.getString("fullSubmissionId"),
                    Category.valueOf(tmpCategory),
                    new ArrayList<SubmissionPartBodyElement>()
                );
            }

            tmpElement = new SubmissionPartBodyElement(
                    rs.getString("text"),
                    rs.getInt("startCharacter"),
                    rs.getInt("endCharacter")
            );

            tmpPart.getBody().add(tmpElement);

        } while (rs.next());

        // add last part
        submissionParts.add(tmpPart);

        return submissionParts;
    }

    /**
     * Calculates how many similar body elements (based on start and end character) can be found in the database
     *
     * @param fullSubmissionId The id of the full submission
     * @param category The category of the submission part
     * @param startCharacter The start character of the new element
     * @param endCharacter The end character of the old element
     * @return Return 0 if there are no similar elements, 2 if we found two similar elements (right and left side),
     * 1 if we found a similar element on the right side and -1 if we found a similar element on the left side.
     */
    private int numOfSimilarBodyElements(String fullSubmissionId, Category category, int startCharacter, int endCharacter) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) AS `count` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND (endCharacter = ? OR startCharacter = ?);";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category.toString().toUpperCase(), startCharacter, endCharacter);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("count");

            // found one element; left or right side
            if (count == 1) {

                // build and execute request to find out on which side we found a similar body element
                String requestSide = "SELECT COUNT(*) AS `side` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND endCharacter = ?;";
                VereinfachtesResultSet rsSide = connection.issueSelectStatement(requestSide, fullSubmissionId, category.toString().toUpperCase(), startCharacter);

                if (rsSide.next()) {
                    // save the response
                    int side = rsSide.getInt("side");

                    // close connection
                    connection.close();

                    if (side == 1) {
                        return -1;
                    }
                    else {
                        return 1;
                    }

                }
            }
            else {
                // close connection
                connection.close();

                return count;
            }
        }

        return 0;

    }

    /**
     * Checks if a new body element has overlapping boundaries with an already existing element
     *
     * @param fullSubmissionId The id of the full submission
     * @param category The category
     * @param element The new element
     * @return Returns true if overlapping boundaries have been found
     */
    private boolean hasOverlappingBoundaries(String fullSubmissionId, Category category, SubmissionPartBodyElement element) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // initialize start and end character
        int start = element.getStartCharacter();
        int end = element.getEndCharacter();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM submissionpartbodyelements WHERE fullSubmissionId = ? AND category = ? AND (" +
                "(startCharacter <= ? AND ? <= endCharacter) OR " + // start character overlapping
                "(startCharacter <= ? AND ? <= endCharacter));"; // end character overlapping
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, fullSubmissionId, category.toString(), start, start, end, end);

        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // close connection
            connection.close();

            // return true if we found the id
            if (count < 1) {
                return false;
            }
            else {
                return true;
            }
        }

        // something happened
        return true;

    }

}
