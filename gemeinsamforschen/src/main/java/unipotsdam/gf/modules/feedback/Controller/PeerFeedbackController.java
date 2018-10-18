package unipotsdam.gf.modules.feedback.Controller;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.feedback.Category;
import unipotsdam.gf.modules.feedback.Model.Peer2PeerFeedback;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class PeerFeedbackController {

    @Inject
    MysqlConnect connection;



    //@Override
    public void createPeer2PeerFeedback(Peer2PeerFeedback feedback) {

        // create a new id if we found no id.
        String uuid = UUID.randomUUID().toString();
        // while (existsfeedbackId(uuid)) {            //Todo: existsfeedbackId
        //     uuid = UUID.randomUUID().toString();
        //  }

        // establish connection

        connection.connect();

        // build and execute request
        String request = "INSERT INTO peerfeedback (`id`, `reciever`, `sender`, `text`, `category`, `filename`) VALUES (?,?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, feedback.getFeedbackreceiver(), feedback.getFeedbacksender(), feedback.getText(), feedback.getFeedbackcategory(), feedback.getFilename());

        // close connection
        connection.close();

        // build response annotation
        //Annotation annotationResponse = getAnnotation(uuid);

        // return Response.ok().build();
        //return null;

    }

    //@Override
    public Peer2PeerFeedback getPeer2PeerFeedback(String id) {

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);
        System.out.print(rs);

        if (rs.next()) {

            // save annotation
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

    public ArrayList<Peer2PeerFeedback> getAllFeedbacks(String sender) {

        ArrayList<Peer2PeerFeedback> feedbacks = new ArrayList<>();

        // establish connection

        connection.connect();

        // build and execute request
        String request = "SELECT * FROM peerfeedback WHERE sender= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, sender);

        while (rs.next()) {
            feedbacks.add(getPeerfeedbackFromResultSet(rs));
        }

        // close connection
        connection.close();
        System.out.print(feedbacks);
        return feedbacks;

    }


    private Peer2PeerFeedback getPeerfeedbackFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String reciever = rs.getString("reciever");
        String sender = rs.getString("sender");
        String text = rs.getString("text");
        Object category = rs.getObject("category");
        String filename = rs.getString("filename");

        //AnnotationBody body = new AnnotationBody(title, comment, startCharacter, endCharacter);

        //return new Peer2PeerFeedback("id", 1234, Category.TITEL, "reciever", "sender", "test", "filename");
        return new Peer2PeerFeedback(id, timestamp, Category.TITEL, reciever, sender, text, filename);
    }
}