package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class PeerFeedbackController {

    //@Override
    public String createPeer2PeerFeedback(Peer2PeerFeedback feedback) {

        String uuid = UUID.randomUUID().toString();

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        //MysqlConnect connection1 = new MysqlConnect();
        //connection1.connect();

        String request = "INSERT INTO peerfeedback (`id`, `reciever`, `sender`, `text`, `category`, `filename`) VALUES (?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, feedback.getFeedbackreceiver(), feedback.getFeedbacksender(), feedback.getText(), feedback.getFeedbackcategory(), feedback.getFilename());
        System.out.print("FEEDBACKCREATE");
        //String req = "UPDATE peerfeedback SET reciever =(SELECT token FROM users WHERE name=?) WHERE reciever=?";
        //connection1.issueUpdateStatement(req, feedback.getFeedbackreceiver(), feedback.getFeedbackreceiver());
        //System.out.print("FEEDBACKUPDATE");
        // close connection
        connection.close();
        //connection1.close();

        String pair = feedback.getFeedbacksender();
        String[] pp = pair.split("'+'");
        System.out.print("pair" + pp[0]);
        String ur = "../give-feedback.jsp?token="+pp[0];
        return ("wurde gesendet!"+ur);

    }

    public ArrayList<Peer2PeerFeedback> getsendedPeerfedback(String sender) {

        ArrayList<Peer2PeerFeedback> feedbacksbysender = new ArrayList<>();

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        System.out.print("SENDER"+sender);

        String request = "SELECT * FROM peerfeedback WHERE sender= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, sender);
        System.out.print("rs:"+rs);

        while (rs.next()) {
            feedbacksbysender.add(getPeerfeedbackFromResultSet(rs));
        }

        connection.close();
        System.out.print("FEEDBACKS"+feedbacksbysender);
        return feedbacksbysender;
    }

    public ArrayList<Peer2PeerFeedback> getRecievedPeerfeedback(String reciever) {

        ArrayList<Peer2PeerFeedback> rf = new ArrayList<>();

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        System.out.print("RECIEVER"+reciever);

        String request = "SELECT * FROM peerfeedback WHERE reciever= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, reciever);
        System.out.print("rs:"+rs);

        while (rs.next()) {
            rf.add(getPeerfeedbackFromResultSet(rs));
        }

        connection.close();
        System.out.print("FEEDBACKS"+rf);
        return rf;
    }

    public ArrayList<Peer2PeerFeedback> getFeedbacksBySender(String reciever, String sender) {

        ArrayList<Peer2PeerFeedback> feedbacks = new ArrayList<>();

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        String request = "SELECT * FROM peerfeedback WHERE reciever= ? AND sender= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, reciever, sender);
        System.out.print("rsfb:"+rs);
        while (rs.next()) {
            feedbacks.add(getPeerfeedbackFromResultSet(rs));
        }

        connection.close();
        System.out.print(feedbacks);
        return feedbacks;
    }

    public ArrayList<String> getSender(String token){

        ArrayList<String> username = new ArrayList<>();
        ArrayList<String> tok = new ArrayList<>();

        MysqlConnect connection1 = new MysqlConnect();
        connection1.connect();

            String[] pair = token.split(",");
            System.out.print("TOKEN,,"+pair[0]+pair.length);
            System.out.print(pair[0].substring(token.indexOf("+")+1));

            for(int j=0; j < pair.length; j++){
                pair[j] = pair[j].substring(token.indexOf("+")+1);
                System.out.print("PAIR2 "+pair[j]);
            }
            System.out.print("pair");
            for(int i = 0; i < pair.length; i++) {
                String request1 = "SELECT * FROM users WHERE token= ?;";
                VereinfachtesResultSet rs1 = connection1.issueSelectStatement(request1, pair[i]);

                while (rs1.next()) {
                    username.add(getNameFromResultSet(rs1));
                }
            }
        System.out.print("getSender:"+username);
        return username;
    }



    public ArrayList<String> getUserforFeedback(String token) {

        System.out.print("IN");

        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> email = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> groupid = new ArrayList<>();

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        String request1 = "SELECT * FROM users WHERE token=?";
        VereinfachtesResultSet rs1 = connection.issueSelectStatement(request1, token);
        while (rs1.next()) {
            email.add(getMailFromResultSet(rs1));
        }
        System.out.print("rs1:"+email);
        String el = email.get(0);
        System.out.print("email"+el);

        MysqlConnect connection1 = new MysqlConnect();
        connection1.connect();

        String request2 = "SELECT * FROM groupuser WHERE userEmail=?";
        VereinfachtesResultSet rs2 = connection1.issueSelectStatement(request2, el);

        while (rs2.next()) {
            groupid.add(getGroupIDFromResultSet(rs2));
        }
        String us = groupid.get(0);
        System.out.print("groupid"+us);

        MysqlConnect connection2 = new MysqlConnect();
        connection2.connect();

        String request3 = "SELECT * FROM groupuser WHERE groupId=?";
        VereinfachtesResultSet rs3 = connection2.issueSelectStatement(request3, us);

        while (rs3.next()) {
            emails.add(getEmailFromResultSet(rs3));
        }
        String ems = emails.get(0);
        System.out.print("emails:"+emails);
        String[] e = ems.split(",");
        System.out.print("emails:"+e);

        MysqlConnect connection3 = new MysqlConnect();
        connection3.connect();

        for (int i = 0; i < emails.size(); i++) {
            String pair = emails.get(i);
            System.out.print("pair" + pair);
            String request4 = "SELECT * FROM users WHERE email=? AND NOT token=?";
            VereinfachtesResultSet rs4 = connection3.issueSelectStatement(request4, pair, token);

            while (rs4.next()) {
                users.add(getNameFromResultSet(rs4));
            }

        }

        System.out.print("rs4:" + users);
        connection.close();
        connection1.close();
        connection2.close();
        connection3.close();
        System.out.print("userscontroller:"+users);
        return users;

    }


    public boolean checkFeedback(String checkFeedback) {

        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        String request = "SELECT * FROM peerfeedback WHERE sender = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, checkFeedback);
        System.out.print(rs);

        if (rs!=null) {
            System.out.print("true");
            connection.close();

            return true;

        } else {
            System.out.print("false");
            connection.close();

            return false;
        }

    }


    private Peer2PeerFeedback getPeerfeedbackFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        Timestamp timestamp = rs.getTimestamp(2);
        String category = rs.getString("category");
        String rec = rs.getString("text");
        String sender = rs.getString("sender");
        String txt = rs.getString("reciever");
        String filename = rs.getString("filename");

        return new Peer2PeerFeedback(id,timestamp,Category.valueOf(category),rec, sender, txt, filename);
    }

    private String getGroupIDFromResultSet(VereinfachtesResultSet rs) {

        String grID = rs.getString("groupId");
        return new String(grID);
    }

    private String getEmailFromResultSet(VereinfachtesResultSet rs) {

        String mail = rs.getString("userEmail");
        return new String(mail);
    }

    private String getNameFromResultSet(VereinfachtesResultSet rs) {

        String name = rs.getString("name");
        String token = rs.getString("token");
        return new String(name+"+"+token);
    }

    private String getMailFromResultSet(VereinfachtesResultSet rs) {

        String mail = rs.getString("email");
        return new String(mail);
    }

    private String getTokenFromResultSet(VereinfachtesResultSet rs) {

        String token = rs.getString("sender");
        return new String(token);
    }

}
