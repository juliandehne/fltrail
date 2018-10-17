package unipotsdam.gf.modules.feedback;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.Constraints;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.feedback.Model.Peer2PeerFeedback;
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
    public void assigningMissingFeedbackTasks(Project project) {
        NotImplementedLogger.logAssignment(Assignee.KATHARINA, Feedback.class, "assigningMissingFeedbackTasks",
                "assigning feedback tasks ");
        missingTaskAssigned = true;
    }

    @Override
    public void assignFeedbackTasks(Project project) {

    }


    @Override
    public ResearchReport getFeedbackTask(User student) {
        return factory.manufacturePojo(ResearchReport.class);
    }
}
