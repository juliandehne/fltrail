package unipotsdam.gf.modules.peer2peerfeedback;

import org.mockito.Mockito;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.io.File;
import java.util.ArrayList;

public class DummyFeedback implements Feedback {

    /**
     * Utility to creaty dummy data for students
     */
    PodamFactory factory = new PodamFactoryImpl();

    private static Boolean missingTaskAssigned = false;


    public DummyFeedback() {

    }

    @Override
    public Peer2PeerFeedback createPeer2PeerFeedbackmask(
            User feedbackuser, User selectedstudent, File document) {
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
    public Boolean checkFeedbackConstraints(Project project) {
        // TODO implement cornstaints
        NotImplementedLogger.logAssignment(Assignee.KATHARINA, Feedback.class, "check Feedback constraints",
                "checking feedback constraints ");
        return missingTaskAssigned;
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
