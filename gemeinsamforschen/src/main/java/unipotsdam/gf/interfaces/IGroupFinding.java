package unipotsdam.gf.interfaces;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupData;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

public interface IGroupFinding {


    GroupFormationMechanism getGFM(Project project);

    /**
     * Persist the selected manual groups
     *
     * @param groupComposition
     */
    void persistGroups(java.util.List<Group> groupComposition, Project project);

    void persistOriginalGroups(
            java.util.List<Group> groupComposition, Project project, GroupFormationMechanism groupFormationMechanism);

    /**
     * @param project
     * @return
     */
    java.util.List<Group> getGroups(Project project);

    java.util.List<Group> getGroups(User user, GroupWorkContext context);

    Integer getMyGroupId(User user, Project project);

    GroupData getStudentsInSameGroup(Project project, User user);

    int getMinNumberOfStudentsNeeded(Project project);

    void deleteGroups(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException;

    GroupFormationAlgorithm getGroupFormationAlgorithm(Project project);

    GroupFormationMechanism getGroupFormationMechanism(Project project);

}
