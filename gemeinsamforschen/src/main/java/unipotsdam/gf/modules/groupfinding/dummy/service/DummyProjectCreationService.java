package unipotsdam.gf.modules.groupfinding.dummy.service;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

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
public class DummyProjectCreationService {


    private ICommunication communicationService;
    private Management management;
    private GroupDAO groupDAO;
    private UserDAO userDAO;

    @Inject
    public DummyProjectCreationService(ICommunication communicationService, Management management, GroupDAO groupDAO, UserDAO userDAO) {
        this.communicationService = communicationService;
        this.management = management;
        this.groupDAO = groupDAO;
        this.userDAO = userDAO;
    }

    public boolean createExampleProject() {

        User docentUser = getDocentUser();
        if (!management.exists(docentUser)) {
            management.create(docentUser, null);
        }
        String[] tags ={"tag1", "tag2", "tag3"};
        Project project = new Project();
        project.setPhase(ProjectPhase.DossierFeedback);
        project.setId("1");
        project.setTags(tags);
        project.setAuthor("author");
        project.setActive(true);
        project.setPassword("1234");
        project.setToken("32trgr");
        project.setAdminPassword("1234");
        if (!management.exists(project)) {
            management.create(project);
        }

        List<Group> groups = createDummyGroups(project.getId());

        List<Group> nonCreatedGroups = groups.stream().filter(group -> !management.exists(group)).collect(Collectors.toList());

        nonCreatedGroups.forEach(group -> management.create(group));

        List<Group> groupsWithId = groupDAO.getGroupsByProjectId(project.getId());
        groupsWithId.forEach(group -> {
            String chatRoomName = String.join(" - ", project.getId(), String.valueOf(group.getId()));
            group.setChatRoomId(communicationService.createChatRoom(chatRoomName, group.getMembers()));
            management.update(group);
        });

        return true;
    }

    List<Group> createDummyGroups(String projectId) {
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

    User getDocentUser() {
        User docent = new User("Julian", "docent", "docent@docent.com", false);
        if (!management.exists(docent)) {
            saveUserToDatabase(docent);
        } else {
            docent = userDAO.getUserByEmail(docent.getEmail());
        }
        return docent;
    }

    private void saveUserToDatabase(User user) {
        communicationService.registerAndLoginUser(user);
        management.create(user, new UserProfile());
    }
}
