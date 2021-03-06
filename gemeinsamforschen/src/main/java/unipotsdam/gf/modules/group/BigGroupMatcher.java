package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BigGroupMatcher implements GroupFormationAlgorithm {

    @Inject
    UserDAO userDAO;

    @Override
    public List<Group> calculateGroups(Project project) {
        ArrayList<Group> groups = new ArrayList<>();
        List<User> users = userDAO.getUsersByProjectName(project.getName());
        Group group = new Group(users, project.getName());
        groups.add(group);
        return groups;
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {
        return calculateGroups(project);
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) {

    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded(Integer groupSize) {
        return 2;
    }
}
