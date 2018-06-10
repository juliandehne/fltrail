package unipotsdam.gf.modules.peer2peerfeedback;

import unipotsdam.gf.core.management.user.User;
import java.io.File;

/**
 Peer2PeerFeedback example
 created by Katharina
 */

public class ExampleFeedback {

    User feebackrec = new User();
    User feedbacksend = new User();


    public Peer2PeerFeedback examplePeerFeedback (String feedbacktopic, String feedbacktype, Category feedbackcategory, File document, User feedbacksender, User feedbackreceiver){

        Peer2PeerFeedback example = new Peer2PeerFeedback(feedbacktopic, feedbacktype, feedbackcategory, document, feedbacksender, feedbackreceiver);
        example.setFeedbacktopic("Vorschlag zur Methodik");
        example.setDocument(new File("C:/exampleMethodik.txt"));
        example.setFeedbackcategory(Category.METHODIK);
        example.setFeedbacktype("Text");
        example.setFeedbacksender(feedbacksend);
        example.setFeedbackreceiver(feebackrec);

        return example;
    }
}
