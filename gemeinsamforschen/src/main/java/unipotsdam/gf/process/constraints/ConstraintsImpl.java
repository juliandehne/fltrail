package unipotsdam.gf.process.constraints;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.tasks.ProjectStatus;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ConstraintsImpl {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    GroupDAO groupDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    private SubmissionController submissionController;

    public ConstraintsImpl() {

    }

    /**
     * groups can be formed if participantCount > numStudentsNeeded
     */
    public Boolean checkIfGroupsCanBeFormed(Project project) {
        ProjectStatus participantCount = projectDAO.getParticipantCount(project);
        int minNumberOfStudentsNeeded = groupFinding.getMinNumberOfStudentsNeeded(project);
        return participantCount.getParticipants() >= minNumberOfStudentsNeeded;
    }

    /**
     * Feedback can be distributed if numFinalizedDossiers = numParticipants or
     * numFinalizedDossiers = numGroups (if Groupwork is selected)
     *
     * @param project
     * @return
     */
    public boolean checkIfFeedbackCanBeDistributed(Project project) {
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
        int numberOfFinalizedDossiers  = submissionController.getFinalizedDossiersCount(project);
        Boolean result = false;
        switch (groupFormationMechanism) {
            case SingleUser:
                ProjectStatus participantCount = projectDAO.getParticipantCount(project);
                result =  numberOfFinalizedDossiers == participantCount.getParticipants();
                break;
            case LearningGoalStrategy:
            case UserProfilStrategy:
            case Manual:
                int groupCount = groupDAO.getGroupsByProjectName(project.getName()).size();
                result = numberOfFinalizedDossiers == groupCount;
                break;
        }
        return result;
    }

    public List<Group> checkWhichFeedbacksAreMissing(Project project) {
        List<Group> groupsInProject = groupDAO.getGroupsByProjectName(project.getName());
        List<Group> doneGroups = submissionController.getAllGroupsWithFinalizedFeedback(project);
        List<Group> missingFeedbacksFrom = new ArrayList<>();
        for (Group group : groupsInProject) {
            if (!doneGroups.contains(group)) {
                missingFeedbacksFrom.add(group);
            }
        }
        return missingFeedbacksFrom;
    }

}
