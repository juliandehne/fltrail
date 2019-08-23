package unipotsdam.gf.modules.group.preferences.groupal;

import unipotsdam.gf.config.GeneralConfig;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.groupal.request.Participants;
import unipotsdam.gf.modules.group.preferences.groupal.request.ParticipantsHolder;
import unipotsdam.gf.modules.group.preferences.groupal.response.Groups;
import unipotsdam.gf.modules.group.preferences.groupal.response.ResponseHolder;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PGroupAlMatcher implements GroupFormationAlgorithm {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    private ProfileDAO profileDAO;

    @Override
    public List<Group> calculateGroups(Project project) {
        Integer minGroupSize;
        try{
            minGroupSize= projectDAO.getGroupSize(project);
        }catch(Exception e){
            minGroupSize= 3;
        }
        if (minGroupSize < 3){
            minGroupSize = 3;
        }
        return calculateGroups(project, minGroupSize);
    }

    @Override
    public List<Group> calculateGroups(Project project, int minGroupSize) {

        // get all users
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());

        // convert to groupalformat
        ParticipantsHolder responses = profileDAO.getResponses(project);
        //JacksonPojoToJson.writeObjectAsXML(responses);

        // calculate user count
        int userCount = usersByProjectName.size();

        // wenn es keine User in dem Projekt gibt, können wir direkt zurück kehren
        if (userCount == 0) {
            return new ArrayList<>();
        }


        /*
         * Der Satz von silvester minUserCount = (ab -a -b) +1
         * minGroupSize = a
         * maxGroupSize = b = a+1
         */
        // if they are less then minUserCount, we cant calculate much
        int maxGroupSize = minGroupSize + 1;
        int minUserCount = minGroupSize * maxGroupSize - minGroupSize - maxGroupSize +1;
        if (userCount < minUserCount) {
            ArrayList<Group> groups = new ArrayList<>();
            Group group = new Group();
            for (User user : usersByProjectName) {
                group.getMembers().add(user);
            }
            group.setName(project.getName());
            groups.add(group);
            return groups;
        }

        int calculateCount = userCount - (userCount % minGroupSize);

        List<User> restUsers = usersByProjectName.subList(calculateCount, userCount);
        responses = adjustUserCount(responses, restUsers);
        List<Group> result = calculateGroupsWithGroupAl(project, responses, minGroupSize);

        // verteile die restlichen
        Iterator<User> iterator = restUsers.iterator();
        for (Group group : result) {
            if (iterator.hasNext()) {
                group.getMembers().add(iterator.next());
            }
        }

        return result;
    }

    private List<Group> calculateGroupsWithGroupAl(Project project, ParticipantsHolder responses, Integer minGroupSize) {
        // send to groupal
        // convert result from groupal
        Client client = ClientBuilder.newClient();


        try {
            JacksonPojoToJson.writeObjectAsXML(responses);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        //WebTarget webTarget = client.target(GroupAlConfig.GROUPAl_LOCAL_URL + groupsize);
        WebTarget webTarget = client.target(GeneralConfig.GROUPAl_URL + minGroupSize);

        // testing
        /*final String groupResults2 = webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(responses, MediaType.APPLICATION_XML)).readEntity(String.class);
        System.err.println(Entity.entity(responses, MediaType.APPLICATION_XML).toString());*/

        final ResponseHolder groupResults = webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(responses, MediaType.APPLICATION_XML)).readEntity(ResponseHolder.class);
        assert groupResults != null;

        // persist created groups
        Groups[] groups = groupResults.getGroups();
        List<Group> result = new ArrayList<>();
        for (Groups group : groups) {

            Group group1 = new Group();
            unipotsdam.gf.modules.group.preferences.groupal.response.Participants[] participants =
                    group.getParticipants();
            for (unipotsdam.gf.modules.group.preferences.groupal.response.Participants participant : participants) {
                User userById = userDAO.getUserById(participant.getiD());
                group1.getMembers().add(userById);
            }
            group1.setProjectName(project.getName());
            //groupDAO.persist(group1);
            result.add(group1);

        }
        return result;
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) {

    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded(Integer groupSize) {
        return groupSize * (groupSize - 1);
    }


    private ParticipantsHolder adjustUserCount(
            ParticipantsHolder responses, List<User> restUsers) {
        List<Integer> restUserIds = restUsers.stream().map(User::getId).collect(Collectors.toList());
        List<Participants> participants = responses.getParticipants();
        List<Participants> adjustedParticpants = new ArrayList<>();
        // make sure the rest users are not included
        for (Participants participant : participants) {
            if (!restUserIds.contains(participant.getId())) {
                adjustedParticpants.add(participant);
            }
        }
        responses.setParticipants(adjustedParticpants);
        return responses;
    }
}
