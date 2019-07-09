package unipotsdam.gf.modules.group.learninggoals;

import unipotsdam.gf.config.CompbaseConfig;
import unipotsdam.gf.exceptions.CompbaseDownException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CompBaseMatcher implements GroupFormationAlgorithm {

    private static final String compbaseURL = CompbaseConfig.COMPBASE_URL;

    @Override
    public List<Group> calculateGroups(Project project) {
        Client client = ClientBuilder.newClient();
        StringBuilder builder = new StringBuilder();
        builder.append(compbaseURL);
        builder.append("/api2/groups/");
        builder.append(project.getName());
        String targetURL = builder.toString();
        GroupData groupData = client.target(targetURL).request(MediaType.APPLICATION_JSON).get(GroupData.class);
        List<LearningGroup> groups = groupData.getGroups();
        ArrayList<Group> result = new ArrayList<>();
        int i = 0;
        for (LearningGroup group : groups) {
            i++;
            Group g = new Group();
            g.setProjectName(project.getName());
            g.setName(i+"");
            List<String> users = group.getUsers();
            for (String user : users) {
                g.getMembers().add(new User(user));
            }
            result.add(g);
        }
        client.close();
        return result;
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {
        return calculateGroups(project);
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) throws Exception {
        Client client = ClientBuilder.newClient();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(compbaseURL);
        stringBuilder.append("/api2/user/");
        stringBuilder.append(user.getEmail());
        stringBuilder.append("/projects/");
        stringBuilder.append(project.getName());
        stringBuilder.append("/preferences");
        String targetUrl = stringBuilder.toString();
        Response put = client.target(targetUrl).request(MediaType.TEXT_PLAIN)
                .put(Entity.entity((PreferenceData)data, MediaType.APPLICATION_JSON));
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
     * @param projectId
     * @param userId
     * @param preferenceData
     */
    public void sendPreferenceData(String projectId, String userId, PreferenceData preferenceData)
            throws CompbaseDownException {
        Client client = ClientBuilder.newClient();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(compbaseURL);
        stringBuilder.append("/api2/user/");
        stringBuilder.append(userId);
        stringBuilder.append("/projects/");
        stringBuilder.append(projectId);
        stringBuilder.append("/preferences");
        String targetUrl = stringBuilder.toString();
        Response put = client.target(targetUrl).request(MediaType.TEXT_PLAIN)
                .put(Entity.entity(preferenceData, MediaType.APPLICATION_JSON));
        if (put.getStatus() != 200) {
            client.close();
            throw new CompbaseDownException();
        }
        client.close();
    }


}
