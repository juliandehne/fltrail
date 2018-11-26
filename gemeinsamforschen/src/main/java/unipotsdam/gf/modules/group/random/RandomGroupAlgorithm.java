package unipotsdam.gf.modules.group.random;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RandomGroupAlgorithm implements GroupFormationAlgorithm {


    private UserDAO userDAO;

    @Inject
    public RandomGroupAlgorithm(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<Group> calculateGroups(Project project) {
        ArrayList<Group> result = new ArrayList<>();
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
            int i = 1;
            group.setName(i + "");
            for (User user : usersByProjectName) {
                if (numberOf3Groups > 0) {
                    numberOf3Groups--;
                    // TODO insert formula here for the correct groups
                    if (group.getMembers().size() == 3) {
                        result.add(group);
                        group = new Group();
                        i++;
                        group.setName(i + "");
                    }
                } else {
                    if (group.getMembers().size() == 4) {
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
    public int getMinNumberOfStudentsNeeded() {
        return 6;
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
