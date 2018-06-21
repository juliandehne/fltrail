package unipotsdam.gf.modules.groupCreation.service;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.interfaces.ICommunication;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@Resource
@Singleton
public class GroupCreationService {

    @Inject
    private ICommunication communicationService;

    @Inject
    private Management management;

    public boolean createExampleProject() {

        User docentUser = getDocentUser();
        Project project = new Project("1", "", true, docentUser.getEmail(), "admin");

        List<Group> groups = createDummyGroups(project.getId());

        if (!management.exists(project)) {
            management.create(project);
        }

        groups.forEach(group -> management.createGroup(group, project.getId()));

        // TODO: read List<Group> of database to get Id for chatRoomName (Should be ProjectName - GroupId)
        // TODO: add projectName as DatabaseEntry
        // TODO: implement sql service injection for, so connection is only done once in app


        List<Group> nonEmptyGroups = groups.stream().filter(group -> group.getMembers().isEmpty())
                .collect(Collectors.toList());
        if (nonEmptyGroups.isEmpty()) {
            return false;
        }


        return true;
    }

    private List<Group> createDummyGroups(String projectId) {
        Group group1 = new Group(new ArrayList<>(), projectId);
        Group group2 = new Group(new ArrayList<>(), projectId);
        Group group3 = new Group(new ArrayList<>(), projectId);
        Group group4 = new Group(new ArrayList<>(), projectId);
        List<Group> groups = Arrays.asList(group1, group2, group3, group4);

        String baseUserName = "Name ";
        String password = "test123";
        String baseEMailFront = "test";
        String baseEMailDomain = "@example.com";
        boolean isStudentValue = true;
        for (int userNumber = 0; userNumber < groups.size() * 5; userNumber++) {
            String userName = baseUserName + (userNumber + 1);
            String email = baseEMailFront + (userNumber + 1) + baseEMailDomain;
            User user = new User(userName, password, email, isStudentValue);
            if (!management.exists(user)) {
                saveUserToDatabase(user);
                int groupPosition = userNumber % groups.size();
                groups.get(groupPosition).addMember(user);
            }
        }
        return groups;
    }

    private User getDocentUser() {
        User docent = new User("Julian", "docent", "docent@docent.com", false);
        if (!management.exists(docent)) {
            saveUserToDatabase(docent);
        } else {
            docent = management.getUserByEmail(docent.getEmail());
        }
        return docent;
    }

    private void saveUserToDatabase(User user) {
        communicationService.registerAndLoginUser(user);
        management.create(user, new UserProfile());
    }
}