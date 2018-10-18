package unipotsdam.gf.modules.feedback;

import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.feedback.Model.Peer2PeerFeedback;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.researchreport.ResearchReport;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackImpl implements Feedback {


    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private SubmissionController submissionController;

    @Override
    public void assigningMissingFeedbackTasks(Project project) {

    }

    @Override
    public void assignFeedbackTasks(Project project) {
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);

        switch (groupFormationMechanism) {
            case SingleUser:
                List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
                User firstUser = usersByProjectName.get(0);
                User lastUser = usersByProjectName.get(usersByProjectName.size());
                submissionController.updateFullSubmission(firstUser, lastUser);
                for (int i = 0; i<usersByProjectName.size()-1;i++) {
                    User submissionOwner = usersByProjectName.get(i+1);
                    User feedbackGiver =usersByProjectName.get(i);
                    submissionController.updateFullSubmission(submissionOwner, feedbackGiver);
                }
                break;
            case UserProfilStrategy:
            case Manual:
            case LearningGoalStrategy:
                // TODO implement assigning feedback tasks in case of groups
                // consider https://docs.google.com/document/d/1DLuggw7gxLbpbDblDTWVtYC-EI4Tb42y285mdABLC0Q/edit?ts=5bbb1f20#
        }

    }

    @Override
    public ResearchReport getFeedbackTask(User student) {
        return null;
    }


}
