package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;

import java.util.ArrayList;
import java.util.UUID;

public class PeerFeedbackController {

    //@Override
    public void createPeer2PeerFeedback(Peer2PeerFeedback feedback) {

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        // while (existsfeedbackId(uuid)) {            //Todo: existsfeedbackId
        //     uuid = UUID.randomUUID().toString();
        //  }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO peerfeedback (`id`, `reciever`, `sender`, `text`, `category`, `filename`) VALUES (?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, feedback.getFeedbackreceiver(), feedback.getFeedbacksender(), feedback.getText(), feedback.getFeedbackcategory(), feedback.getFilename());

        // close connection
        connection.close();

        // return Response.ok().build();
        //return null;

    }

    //@Override
    public Peer2PeerFeedback getPeer2PeerFeedback(String id) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);
        System.out.print(rs);

        if (rs.next()) {

            Peer2PeerFeedback feedback = getPeerfeedbackFromResultSet(rs);

            // close connection
            connection.close();
            System.out.print(feedback);

            return feedback;
       } else {

            // close connection
            connection.close();
           System.out.print("null");
           return null;
       }

    }

    public ArrayList<Peer2PeerFeedback> getAllFeedbacks(String reciever) {

        ArrayList<Peer2PeerFeedback> feedbacks = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE reciever= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, reciever);

        while (rs.next()) {
            feedbacks.add(getPeerfeedbackFromResultSet(rs));
        }

        // close connection
        connection.close();
        System.out.print(feedbacks);
        return feedbacks;

    }

    public ArrayList<String> getUserforFeedback() {

        ArrayList<String> users = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM users WHERE isStudent= 1";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request);

        while (rs.next()) {
            users.add(getUserFromResultSet(rs));
        }

        // close connection
        connection.close();
        System.out.print(users);
        return users;

    }

    public void getTokenforFeedback(String user) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        //String request = "SELECT * FROM users WHERE isStudent= 1";
        //VereinfachtesResultSet rs = connection.issueSelectStatement(request);
        // build and execute request
        String request = "INSERT INTO peerfeedback (, , reciever, , , , ) SELECT token FROM users WHERE isStudent= 1 AND name=?";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, user);


        /**while (rs.next()) {
            token.add(getTokenFromResultSet(rs));
        }*/

        // close connection
        connection.close();
        System.out.print("token");
        //return token;

    }

    private Peer2PeerFeedback getPeerfeedbackFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String reciever = rs.getString("reciever");
        String sender = rs.getString("sender");
        String text = rs.getString("text");
        Object category = rs.getObject("category");
        String filename = rs.getString("filename");

        //return new Peer2PeerFeedback("id", 1234, Category.TITEL, "reciever", "sender", "test", "filename");
        return new Peer2PeerFeedback(id,timestamp,Category.TITEL,reciever, sender, text, filename);
    }

    private String getUserFromResultSet(VereinfachtesResultSet rs) {

        String user = rs.getString("name");
        return new String(user);
    }

    private String getTokenFromResultSet(VereinfachtesResultSet rs) {

        String token = rs.getString("token");
        return new String(token);
    }
}
