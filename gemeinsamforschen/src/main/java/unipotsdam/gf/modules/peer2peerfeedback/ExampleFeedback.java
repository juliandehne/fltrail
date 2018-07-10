package unipotsdam.gf.modules.peer2peerfeedback;

import unipotsdam.gf.core.management.user.User;

import java.io.File;

/**
 Peer2PeerFeedback example
 created by Katharina
 */

public class ExampleFeedback {

    String feedbacktopic = "Vorschlag zur Methodik";
    String feedbacktype = "Text";
    Category feedbackcategory = Category.METHODIK;
    User feedbackrec = new User();
    User feedbacksend = new User();
    File file = new File("src/main/resources/example.txt"); //gibt erstmal nur einen Pfad an
    //String path = file.getAbsolutePath();


    public Peer2PeerFeedback examplePeerFeedback (){

        Peer2PeerFeedback example = new Peer2PeerFeedback(feedbacktopic, feedbacktype,
                feedbackcategory, file, feedbacksend, feedbackrec);

        return example;
    }
}
