package unipotsdam.gf.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;
import unipotsdam.gf.modules.user.User;

import javax.ws.rs.Produces;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public interface IGroupFinding {

    /**
     * Select the groupfinding criteria used
     * @param groupfindingCriteria
     * @param project
     */
    void selectGroupfindingCriteria(
            GroupfindingCriteria groupfindingCriteria, Project project);

    GroupFormationMechanism getGFM(Project project);

    /**
     * Persist the selected manual groups
     * @param groupComposition
     */
    void persistGroups(java.util.List<Group> groupComposition, Project project);

    void persistOriginalGroups(java.util.List<Group> groupComposition, Project project, GroupFormationMechanism groupFormationMechanism);
    /**
     * @param project
     * @return
     */
    java.util.List<Group> getGroups(Project project);

    java.util.List<Group> getGroups(User user, GroupWorkContext context);
    /**
     *
     * @param groupFindingMechanism
     */
    void formGroups(GroupFormationMechanism groupFindingMechanism);

    ArrayList<String> getStudentsInSameGroup(Project project, User user);

    int getMinNumberOfStudentsNeeded(Project project);

    void deleteGroups(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException;

    List<Group> createRandomGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException;

    /**
     * finish the groups in the db
     */
    void finalizeGroups(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException;

    GroupFormationAlgorithm getGroupFormationAlgorithm(Project project);

    GroupFormationMechanism getGroupFormationMechanism(Project project);

}
