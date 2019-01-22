package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class BigGroupMatcher implements GroupFormationAlgorithm {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    GroupDAO groupDAO;

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
    public void addGroupRelevantData(Project project, User user, Object data) {

    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 0;
    }
}
