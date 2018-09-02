package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Controller;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;

import java.util.UUID;

public class PeerFeedbackController{

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
        connection.issueInsertOrDeleteStatement(request, uuid, feedback.getFeedbackreceiver(), feedback.getFeedbacksender(), feedback.getText(), feedback.getFeedbackcategory(),feedback.getFilename());

        // close connection
        connection.close();

        // build response annotation
       //Annotation annotationResponse = getAnnotation(uuid);

       // return Response.ok().build();
        //return null;

    }



}
