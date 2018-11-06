package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupfindingImpl implements IGroupFinding {

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

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
        // TODO this algorithm logic should be somewhere different
        int participantsNeeded = 0;
        GroupFormationMechanism selectedGFM = groupDAO.getGroupFormationMechanism(project);
        switch (selectedGFM){
            case UserProfilStrategy: participantsNeeded = 6;
            case LearningGoalStrategy: participantsNeeded = 5;
            default: participantsNeeded = 2;
        }

        return participantsNeeded;
    }

    @Override
    public List<Group> createRandomGroups(Project project) {
        ArrayList<Group> result = new ArrayList<>();
        groupDAO.deleteGroups(project);
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        int numberOfUsers = usersByProjectName.size();
        if (usersByProjectName.size()<6){
            Group group = new Group();
            group.getMembers().addAll(usersByProjectName);
            result.add(group);
        } else {
            int numberOf3Groups = getNumberOf3Groups(numberOfUsers);
            //int numberOf4Groups = getNumberOf4Groups(numberOfUsers);

            Group group = new Group();
            for (User user : usersByProjectName) {
                if (numberOf3Groups > 0) {
                    numberOf3Groups--;
                    // TODO insert formula here for the correct groups
                    if (group.getMembers().size() == 3) {
                        result.add(group);
                        group = new Group();
                    }
                } else {
                    if (group.getMembers().size() == 4) {
                        result.add(group);
                        group = new Group();
                    }
                }
                group.addMember(user);
                // set group name 1 more
            }
            result.add(group);
        }
        return result;
    }
    // (number % 3) + (Math.floor(number/3)-(number%3)) = n für alle Zahlen größer als 5
    public int getNumberOf4Groups(Integer number) {
        return  (number % 3);
    }


    // every number can be divided in factors 4 and 3 as long it is greater then 5
    public int getNumberOf3Groups(Integer number) {
        return (number / 3) - (number % 3);
    }
}
