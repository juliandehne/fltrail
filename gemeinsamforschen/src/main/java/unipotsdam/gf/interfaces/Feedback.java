package unipotsdam.gf.interfaces;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.util.ArrayList;


/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * create Peer2PeerFeedback Object
     *
     * @param feedback:
     * @return Returns the Peer2PeerFeedback Object
     */

    Peer2PeerFeedback createPeer2PeerFeedback (Peer2PeerFeedback feedback);
    //(String id, String reciever, String sender, String text, Category category, String filename);

    /**
     * create Peer2PeerFeedback Object
     *
    * @param feedbackuser:
    * @param selectedstudent:
    * @param document:
     * **/
    Peer2PeerFeedback createPeer2PeerFeedbackmask(User feedbackuser, User selectedstudent, File document);

    /**
     * give Peer2PeerFeedback
     *
     * @param feedback : The Peer2PeerFeedback as an Object
     * @param document : The selected document
     * @return Send feedback with doc and return true, if the feedback is successfully sended
     */

    Boolean giveFeedback(Peer2PeerFeedback feedback, ResearchReport document);

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

    /**
     * TODO implement a routine that assigns missing feedback tasks if someone drops out of a course
     * @param project
     */
    void assigningMissingFeedbackTasks(Project project);

    /**
     * TODO implement: Assigns each student in a project a feedback target
     */
    void assignFeedbackTasks();

    /**
     * TODO implement: Get the research report you have to give feedback to
     * @param student
     * @return
     */
    ResearchReport getFeedbackTask(User student);
}
