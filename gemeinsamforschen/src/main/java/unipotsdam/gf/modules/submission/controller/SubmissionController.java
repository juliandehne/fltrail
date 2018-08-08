package unipotsdam.gf.modules.submission.controller;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.interfaces.ISubmission;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;

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

    /**
     * Build an full submission object from a given result set
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
}
