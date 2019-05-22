package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SingleGroupMatcher implements GroupFormationAlgorithm {

    @Inject
    UserDAO userDAO;

    @Override
    public List<Group> calculateGroups(Project project) {
        ArrayList<Group> groups = new ArrayList<>();
        List<User> users = userDAO.getUsersByProjectName(project.getName());
        for (User user: users) {
            List<User> singleGroup = new ArrayList<>();
            singleGroup.add(user);
            groups.add(new Group(singleGroup, project.getName()));
        }
        return groups;
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {
        return calculateGroups(project);
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) {
        // do nothing
    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {
        // do nothing
    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 2;
    }
}
