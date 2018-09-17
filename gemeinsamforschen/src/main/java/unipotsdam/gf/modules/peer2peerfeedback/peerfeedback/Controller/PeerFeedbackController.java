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
        // while (existsfeedbackId(uuid)) {
        //     uuid = UUID.randomUUID().toString();
        //  }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        MysqlConnect connection1 = new MysqlConnect();
        connection1.connect();

        // build and execute request
        String request = "INSERT INTO peerfeedback (`id`, `reciever`, `sender`, `text`, `category`, `filename`) VALUES (?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, feedback.getFeedbackreceiver(), feedback.getFeedbacksender(), feedback.getText(), feedback.getFeedbackcategory(), feedback.getFilename());

        String req = "UPDATE peerfeedback SET reciever =(SELECT token FROM users WHERE name=?) WHERE reciever=?";
        connection1.issueUpdateStatement(req, feedback.getFeedbackreceiver(), feedback.getFeedbackreceiver());
        // close connection
        connection.close();
        connection1.close();

        // return Response.ok().build();
        //return null;

    }

    public ArrayList<Peer2PeerFeedback> getAllFeedbacks(String reciever) {

        ArrayList<Peer2PeerFeedback> feedbacks = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE reciever= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, reciever);
        System.out.print("rs:"+rs);

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
            users.add(getUserFromResultSet(rs)+"+"+getTokenFromResultSet(rs));
        }

        // close connection
        connection.close();
        System.out.print("userscontroller:"+users);
        return users;

    }

    public boolean getTokenforFeedback(String user) {

        System.out.print("getTokencontroller:"+user);

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        /**String request = "UPDATE\n" +
                "  peerfeedback\n" +
                "SET\n" +
                "  reciever = (\n" +
                "    SELECT\n" +
                "      token\n" +
                "    FROM\n" +
                "      users\n" +
                "    WHERE\n" +
                "      name = ?\n" +
                "  ) WHERE reciever = ?";*/
        String request = "UPDATE peerfeedback SET reciever =(SELECT token FROM users WHERE name=?) WHERE reciever=?";
        //VereinfachtesResultSet rs = connection.issueSelectStatement(request, user); (SELECT token FROM users WHERE name=?)
        //connection.issueInsertOrDeleteStatement(request, user);
        connection.issueUpdateStatement(request, user, user);

        // close connection
        System.out.print("token");
        connection.close();

        return true;

    }

    public boolean checkFeedback(String checkFeedback) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE sender = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, checkFeedback);
        System.out.print(rs);

        if (rs!=null) {
            System.out.print("true");
            // close connection
            connection.close();

            return true;

        } else {
            System.out.print("false");
            // close connection
            connection.close();

            return false;
        }

    }


    private Peer2PeerFeedback getPeerfeedbackFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String category = rs.getString("category");
        String rec = rs.getString("text");
        String sender = rs.getString("sender");
        String txt = rs.getString("reciever");
        String filename = rs.getString("filename");

        //return new Peer2PeerFeedback("id", 1234, Category.TITEL, "reciever", "sender", "test", "filename");
        return new Peer2PeerFeedback(id,timestamp,Category.valueOf(category),rec, sender, txt, filename);
    }

    private String getUserFromResultSet(VereinfachtesResultSet rs) {

        String user = rs.getString("name");
        return new String(user);
    }

    private String getTokenFromResultSet(VereinfachtesResultSet rs) {

        String token = rs.getString("token");
        return new String(token);
    }

    private String getCheckFromResultSet(VereinfachtesResultSet rs) {

        String check = rs.getString("token");
        return new String(check);
    }
}
