package unipotsdam.gf.modules.peer2peerfeedback;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.states.Constraints;
import unipotsdam.gf.modules.states.ConstraintsMessages;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model.Peer2PeerFeedback;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DummyFeedback implements Feedback {

    /**
     * Utility to create dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();

    private static Boolean missingTaskAssigned = false;
    private Map<StudentIdentifier, Constraints> openTasks;


    public DummyFeedback() {

    }

    @Override
    public Peer2PeerFeedback createPeer2PeerFeedbackmask(
            User feedbackuser, User selectedstudent, File document) {
        return null;
    }

    public Peer2PeerFeedback createPeer2PeerFeedback(Peer2PeerFeedback feedback) {
        return null;
    }

    @Override
    public Boolean giveFeedback(Peer2PeerFeedback feedback, ResearchReport document) {
        return null;
    }

    @Override
    public ArrayList<Peer2PeerFeedback> showFeedback(User student) {
        return null;
    }

    @Override
    public int countFeedback(User student) {
        return 0;
    }

    @Override
    public Map<StudentIdentifier, ConstraintsMessages> checkFeedbackConstraints(Project project) {
        // TODO implement constraints
        NotImplementedLogger.logAssignment(Assignee.KATHARINA, Feedback.class, "check Feedback constraints",
                "checking feedback constraints ");
        Map<StudentIdentifier, ConstraintsMessages> result = new HashMap<>();
        return result;
    }

    @Override
    public void assigningMissingFeedbackTasks(Project project) {
        NotImplementedLogger.logAssignment(Assignee.KATHARINA, Feedback.class, "assigningMissingFeedbackTasks",
                "assigning feedback tasks ");
        missingTaskAssigned = true;
    }

    @Override
    public void assignFeedbackTasks() {

    }

    @Override
    public ResearchReport getFeedbackTask(User student) {
        return factory.manufacturePojo(ResearchReport.class);
    }
}
