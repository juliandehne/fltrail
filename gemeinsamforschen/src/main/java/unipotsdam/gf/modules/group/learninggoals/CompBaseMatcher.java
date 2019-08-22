package unipotsdam.gf.modules.group.learninggoals;

import unipotsdam.gf.config.IConfig;
import unipotsdam.gf.exceptions.CompbaseDownException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CompBaseMatcher implements GroupFormationAlgorithm {

    @Inject
    private IConfig iConfig;

    @Override
    public List<Group> calculateGroups(Project project) {
        ArrayList<Group> result = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        String targetURL = iConfig.getCompBaseUrl() + "/api2/groups/" + project.getName();
        try {
            GroupData groupData = client.target(targetURL).request(MediaType.APPLICATION_JSON).get(GroupData.class);
            List<LearningGroup> groups = groupData.getGroups();

            int i = 0;
            for (LearningGroup group : groups) {
                i++;
                Group g = new Group();
                g.setProjectName(project.getName());
                g.setName(i + "");
                List<String> users = group.getUsers();
                for (String user : users) {
                    g.getMembers().add(new User(user));
                }
                result.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
        return result;
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {
        return calculateGroups(project);
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) throws Exception {
        Client client = ClientBuilder.newClient();
        String baseurl = iConfig.getCompBaseUrl();
        String targetUrl = iConfig.getCompBaseUrl() + "/api2/user/" + user.getEmail() + "/projects/" + project
                .getName() + "/preferences";
        Response put = client.target(targetUrl).request(MediaType.TEXT_PLAIN)
                .put(Entity.entity((PreferenceData) data, MediaType.APPLICATION_JSON));
        if (put.getStatus() != 200) {
            client.close();
            throw new CompbaseDownException();
        }
        client.close();
    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 5;
    }

    /**
     * dont touch because is coded against compbase
     *
     * @param projectId      name of project of interest
     * @param userId         that is interested in something about the project
     * @param preferenceData collected preferences
     */
    public void sendPreferenceData(String projectId, String userId, PreferenceData preferenceData)
            throws CompbaseDownException {
        Client client = ClientBuilder.newClient();
        String baseurl = iConfig.getCompBaseUrl();
        String targetUrl =
                iConfig.getCompBaseUrl() + "/api2/user/" + userId + "/projects/" + projectId + "/preferences";
        Response put = client.target(targetUrl).request(MediaType.TEXT_PLAIN)
                .put(Entity.entity(preferenceData, MediaType.APPLICATION_JSON));
        if (put.getStatus() != 200) {
            Object output = put.readEntity(String.class);
            System.out.println(output);
            client.close();
            throw new CompbaseDownException();
        }
        client.close();
    }


}
