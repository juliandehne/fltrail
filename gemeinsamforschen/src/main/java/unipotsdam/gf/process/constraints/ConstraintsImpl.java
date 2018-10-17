package unipotsdam.gf.process.constraints;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.tasks.ParticipantsCount;

import javax.inject.Inject;

public class ConstraintsImpl {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    GroupDAO groupDAO;

    @Inject
    private SubmissionController submissionController;

    /**
     * groups can be formed if participantCount > numStudentsNeeded
     */
    public Boolean checkIfGroupsCanBeFormed(Project project) {
        ParticipantsCount participantCount = projectDAO.getParticipantCount(project);
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
        switch (groupFormationMechanism) {
            case SingleUser:
                ParticipantsCount participantCount = projectDAO.getParticipantCount(project);
                int numberOfFinalizedDossiers = submissionController.getFinalizedDossiersCount(project);
                return numberOfFinalizedDossiers == participantCount.getParticipants();
        }
        return false;
    }
}
