package unipotsdam.gf.interfaces;
import unipotsdam.gf.modules.peer2peerfeedback.peer2peerfeedback;
import java.*;


/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * create Peer2PeerFeedback Object
     *
     * @param FeedbackUser: The student who is creating the feedback
     * @param SelectedStudent: The student who receive the feedback
     * @param Document: The selected document to give feedback about
     * @return Returns the Peer2PeerFeedback Object
     */

    Peer2PeerFeedback createPeer2PeerFeedbackmask(User feedbackuser, User selectedstudent, File document);

    /**
     * give Peer2PeerFeedback
     *
     * @param Peer2PeerFeedback: The Peer2PeerFeedback as an Object
     * @param Document: The selected document
     * @return Send feedback with doc and return true, if the feedback is successfully sended
     */

    Boolean giveFeedback(Peer2PeerFeedback feedback, File document);

    /**
     * show Feedbackhistory
     *
     * @param student
     * @return List of Feedbacks with Docs
     */

    ArrayList <Peer2PeerFeedback> showFeedback(User student);

    /**
     * count Feedback
     *
     * @param student The Student, that have given Feedback
     * @return Number of given Feedback
     */

    int countFeedback(User student);

}
