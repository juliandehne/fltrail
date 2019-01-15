package unipotsdam.gf.modules.group;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.preferences.UserPreferenceAlgorithm;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
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
    public GroupfindingImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void selectGroupfindingCriteria(
            GroupfindingCriteria groupfindingCriteria, Project project) {
        //
    }

    @Override
    public GroupFormationMechanism getGFM(Project project){
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
    public List<Group> getGroups(Project project) {
        return groupDAO.getGroupsByProjectName(project.getName());
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {
        // TODO implement for othermechanisms
    }

    public ArrayList<String> getStudentsInSameGroup(StudentIdentifier student) {
        return groupDAO.getStudentsInSameGroupAs(student);
    }

    @Override
    public int getMinNumberOfStudentsNeeded(Project project) {
       return getGroupFormationAlgorithm(project).getMinNumberOfStudentsNeeded();
    }

    @Override
    public void deleteGroups(Project project){
        groupDAO.deleteGroups(project);
    }

    @Override
    public List<Group> createRandomGroups(Project project) {
        return new RandomGroupAlgorithm(userDAO).calculateGroups(project);
    }


    /**
     * after this groups should not be touched by the system
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
        switch (selectedGFM){
            case UserProfilStrategy: return new UserPreferenceAlgorithm();
            case LearningGoalStrategy: return new CompBaseMatcher();
            default: return new RandomGroupAlgorithm(userDAO);
        }
    }

}
