package unipotsdam.gf.modules.peer2peerfeedback;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.Feedback;

import java.io.File;
import java.util.ArrayList;

public class DummyFeedback implements Feedback {
    @Override
    public Peer2PeerFeedback createPeer2PeerFeedbackmask(
            User feedbackuser, User selectedstudent, File document) {
        return null;
    }

    @Override
    public Boolean giveFeedback(Peer2PeerFeedback feedback, File document) {
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
        System.out.println("Checking fake constraints");
        return true;
    }

    @Override
    public void assigningMissingFeedbackTasks(Project project) {
        System.out.println("assigning fake tasks");
    }
}
