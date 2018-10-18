package unipotsdam.gf.process.constraints;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.ParticipantsCount;

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
        int numberOfFinalizedDossiers  = submissionController.getFinalizedDossiersCount(project);
        Boolean result = false;
        switch (groupFormationMechanism) {
            case SingleUser:
                ParticipantsCount participantCount = projectDAO.getParticipantCount(project);
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

    /**
     * get how many dossiers are needed
     *
     * @param project
     * @return
     */
    public int dossiersNeeded(Project project) {
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
        Integer result = 0;
        switch (groupFormationMechanism) {
            case SingleUser:
                ParticipantsCount participantCount = projectDAO.getParticipantCount(project);
                result = participantCount.getParticipants();
                break;
            case LearningGoalStrategy:
            case UserProfilStrategy:
            case Manual:
                int groupCount = groupDAO.getGroupsByProjectName(project.getName()).size();
                result = groupCount;
                break;
        }
        return result;
    }


    public List<User> getStrugglersWithSubmission(Project project) {
        ArrayList<User> struggles = new ArrayList<>();
        GroupFormationMechanism groupFormationMechanism = groupDAO.getGroupFormationMechanism(project);
        switch (groupFormationMechanism) {
            case SingleUser:
                List<User> usersInProject = userDAO.getUsersByProjectName(project.getName());
                List<User> usersHavingGivenFeedback = submissionController.getAllUsersWithFeedbackGiven(project);
                for (User user : usersInProject) {
                    if (!usersHavingGivenFeedback.contains(user)) {
                        struggles.add(user);
                    }
                }
                break;
            case LearningGoalStrategy:
            case Manual:
            case UserProfilStrategy:
        }
        return struggles;
    }


}
