package unipotsdam.gf.modules.group;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.group.GroupDAO;

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


    private PodamFactory factory = new PodamFactoryImpl();

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

        Project project = factory.manufacturePojo(Project.class);

        User docentUser = getDocentUser();
        if (!management.exists(docentUser)) {
            management.create(docentUser, null);
        }

        if (!management.exists(project)) {
            management.create(project);
        }

        List<Group> groups = createDummyGroups(project.getName());

        List<Group> nonCreatedGroups = groups.stream().filter(group -> !management.exists(group)).collect(Collectors.toList());

        nonCreatedGroups.forEach(group -> management.create(group));

        List<Group> groupsWithId = groupDAO.getGroupsByProjectName(project.getName());
        groupsWithId.forEach(group -> {
            String chatRoomName = String.join(" - ", project.getName(), String.valueOf(group.getId()));
            group.setChatRoomId(communicationService.createChatRoom(chatRoomName, group.getMembers()));
            management.update(group);
        });

        return true;
    }

    public List<Group> createDummyGroups(String projectName) {
        Group group1 = new Group(new ArrayList<>(), projectName);
        Group group2 = new Group(new ArrayList<>(), projectName);
        Group group3 = new Group(new ArrayList<>(), projectName);
        Group group4 = new Group(new ArrayList<>(), projectName);
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

    public User getDocentUser() {
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