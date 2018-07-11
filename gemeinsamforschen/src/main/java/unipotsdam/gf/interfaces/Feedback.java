package unipotsdam.gf.interfaces;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.peer2peerfeedback.Peer2PeerFeedback;
import java.*;
import java.io.File;
import java.util.ArrayList;


/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * create Peer2PeerFeedback Object
     *
     * @param feedbackuser: The student who is creating the feedback
     * @param selectedstudent: The student who receive the feedback
     * @param document: The selected document to give feedback about
     * @return Returns the Peer2PeerFeedback Object
     */

    Peer2PeerFeedback createPeer2PeerFeedbackmask(User feedbackuser, User selectedstudent, File document);

    /**
     * give Peer2PeerFeedback
     *
     * @param feedback: The Peer2PeerFeedback as an Object
     * @param document: The selected document
     * @return Send feedback with doc and return true, if the feedback is successfully sended
     */

    Boolean giveFeedback(Peer2PeerFeedback feedback, File document);

    /**
     * show Feedbackhistory
     *
     * @param student
     * @return List of Feedbacks with Docs
     */

    ArrayList<Peer2PeerFeedback> showFeedback(User student);

    /**
     * count Feedback
     *
     * @param student The Student, that have given Feedback
     * @return Number of given Feedback
     */

    int countFeedback(User student);

    /**
     * TODO implement check in DB that everybody has given feedback
     * @param project
     * @return
     */
    Boolean checkFeedbackConstraints(Project project);

    void assigningMissingFeedbackTasks(Project project);
}
