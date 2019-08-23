package unipotsdam.gf.modules.group.random;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class RandomGroupAlgorithm implements GroupFormationAlgorithm {

    @Inject
    private UserDAO userDAO;

    @Override
    public List<Group> calculateGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        return calculateGroups(project, 3);
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {
        ArrayList<Group> result = new ArrayList<>();
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        int numberOfUsers = usersByProjectName.size();
        int maxGroupSize = minGroupSize +1;
        int minUserCount = minGroupSize * maxGroupSize - minGroupSize - maxGroupSize +1;
        if (usersByProjectName.size()< minUserCount){
            Group group = new Group();
            group.getMembers().addAll(usersByProjectName);
            result.add(group);
        } else {
            int numberOf3Groups = getNumberOf3Groups(numberOfUsers);
            //int numberOf4Groups = getNumberOf4Groups(numberOfUsers);
            int numberOf3GroupMembers = numberOf3Groups * 3;

            Group group = new Group();
            int i = 1;
            group.setName(i + "");
            for (User user : usersByProjectName) {
                if (numberOf3GroupMembers > 0) {
                    numberOf3GroupMembers--;
                    if (group.getMembers().size() == minGroupSize) {
                        result.add(group);
                        group = new Group();
                        i++;
                        group.setName(i + "");
                    }
                } else {
                    if (group.getMembers().size() == maxGroupSize) {
                        result.add(group);
                        group = new Group();
                        i++;
                        group.setName(i + "");
                    }
                }
                group.addMember(user);
                // set group name 1 more
            }
            result.add(group);
        }
        return result;
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) throws Exception {

    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded(Integer groupSize) {
        return 2;
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
