package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class GroupfindingImpl implements IGroupFinding {

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ICommunication iCommunication;

    @Inject
    private RandomGroupAlgorithm randomGroupAlgorithm;

    @Inject
    private GroupFormationFactory groupFormationFactory;


    @Override
    public void selectGroupfindingCriteria(
            GroupfindingCriteria groupfindingCriteria, Project project) {
        //
    }

    @Override
    public GroupFormationMechanism getGFM(Project project) {
        return groupDAO.getGroupFormationMechanism(project);
    }

    @Override
    public void persistGroups(List<Group> groupComposition, Project project) {
        for (Group group : groupComposition) {
            group.setProjectName(project.getName());
            groupDAO.persist(group);
        }
    }

    @Override
    public void persistOriginalGroups(java.util.List<Group> groupComposition, Project project,
                                      GroupFormationMechanism groupFormationMechanism){
        //this solution for groupIds is probably no reason to celebrate for my Boss...
        //since the ID does not match the groupId from table groups
        //but they would not match anyway after groups were manipulated by the docent.
        //so its ok hopefully.
        int groupId=0;
        for (Group group : groupComposition) {
            group.setProjectName(project.getName());
            groupDAO.persistOriginalGroup(group, groupId, groupFormationMechanism);
            groupId++;
        }
    }

    @Override
    public List<Group> getGroups(Project project) {
        return groupDAO.getGroupsByProjectName(project.getName());
    }

    public List<Group> getOriginalGroups(Project project) {
        return groupDAO.getOriginalGroupsByProjectName(project.getName());
    }

    @Override
    public List<Group> getGroups(User user, GroupWorkContext context) {
        return groupDAO.getGroupsByContextUser(user, context);
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {
        // TODO implement for othermechanisms
    }

    public ArrayList<String> getStudentsInSameGroup(Project project, User user) {
        return groupDAO.getStudentsInSameGroupAs(project, user);
    }

    @Override
    public int getMinNumberOfStudentsNeeded(Project project) {
        return getGroupFormationAlgorithm(project).getMinNumberOfStudentsNeeded();
    }

    @Override
    public void deleteGroups(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        groupDAO.deleteGroups(project);
        if (project.getGroupWorkContext().equals(GroupWorkContext.fl)) {
            List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
            for (Group group : groupsByProjectName) {
                iCommunication.deleteChatRoom(group);
            }
        }
    }

    @Override
    public List<Group> createRandomGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        return randomGroupAlgorithm.calculateGroups(project);
    }

    /**
     * after this groups should not be touched by the system
     *
     * @param project
     */
    @Override
    public void finalizeGroups(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        // create group chat rooms
        List<Group> groups = getGroups(project);
        for (Group group : groups) {
            iCommunication.createChatRoom(group, false);
        }
    }

    @Override
    public GroupFormationAlgorithm getGroupFormationAlgorithm(Project project) {
        GroupFormationMechanism selectedGFM = groupDAO.getGroupFormationMechanism(project);
        return groupFormationFactory.instance(selectedGFM);
    }

    public GroupFormationMechanism getGroupFormationMechanism(Project project) {
        return groupDAO.getGroupFormationMechanism(project);
    }
}
