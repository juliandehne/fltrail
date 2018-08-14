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

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        while (existsSubmissionPartId(uuid)) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO submissionparts (`id`, `userId`, `fullSubmissionId`, `category`) VALUES (?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, submissionPartPostRequest.getUserId(), submissionPartPostRequest.getFullSubmissionId(), submissionPartPostRequest.getCategory().toString());

        // build and execute body requests
        String requestElement = "INSERT INTO submissionpartbodyelements (`submissionPartId`, `text`, `startCharacter`, `endCharacter`) VALUES (?,?,?,?)";
        for (SubmissionPartBodyElement element : submissionPartPostRequest.getBody()) {
            connection.issueInsertOrDeleteStatement(requestElement, uuid, element.getText(), element.getStartCharacter(), element.getEndCharacter());
        }

        // get the new submission from database
        SubmissionPart submissionPart = getSubmissionPart(uuid);

        // close connection
        connection.close();

        return submissionPart;

    }

    @Override
    public SubmissionPart getSubmissionPart(String submissionPartId) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM submissionparts LEFT JOIN submissionpartbodyelements ON id = submissionPartId WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, submissionPartId);

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
                "FROM submissionparts " +
                "LEFT JOIN submissionpartbodyelements " +
                "ON id = submissionPartId " +
                "WHERE fullSubmissionId = ? " +
                "ORDER BY id;";
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
    public boolean existsSubmissionPartId(String submissionPartId) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM submissionparts WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, submissionPartId);

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
        long timestamp = rs.getTimestamp(2).getTime();
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

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp("timestamp").getTime();
        String userId = rs.getString("userId");
        String fullSubmissionId = rs.getString("fullSubmissionId");
        Category category = Category.valueOf(rs.getString("category").toUpperCase());

        // build body and iterate over result set
        ArrayList<SubmissionPartBodyElement> body = new ArrayList<>();

        do {
            // only add it if the element is not empty
            if (!Strings.isNullOrEmpty(rs.getString("submissionPartId"))) {
                SubmissionPartBodyElement element = new SubmissionPartBodyElement(
                        rs.getString("text"),
                        rs.getInt("startCharacter"),
                        rs.getInt("endCharacter"));

                body.add(element);
            }
        } while (rs.next());

        return new SubmissionPart(id, timestamp, userId, fullSubmissionId, category, body);
    }

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
                    rs.getString("id"),
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

        System.out.println(submissionParts.toString());
        return submissionParts;
    }

}
