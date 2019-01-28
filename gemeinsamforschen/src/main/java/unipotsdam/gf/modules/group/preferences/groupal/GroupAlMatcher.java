package unipotsdam.gf.modules.group.preferences.groupal;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.groupal.request.ParticipantsHolder;
import unipotsdam.gf.modules.group.preferences.groupal.response.Groups;
import unipotsdam.gf.modules.group.preferences.groupal.response.Participants;
import unipotsdam.gf.modules.group.preferences.groupal.response.ResponseHolder;
import unipotsdam.gf.modules.project.Project;
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
import java.util.List;

public class GroupAlMatcher implements GroupFormationAlgorithm {

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private UserDAO userDAO;

    /**
     * create groups for a given project
     *
     * It will read the Profile Data from the users from the db and send it to groupal to be matched
     * @param project
     * @param groupsize
     * @return
     * @throws JsonProcessingException
     * @throws JAXBException
     * @throws WrongNumberOfParticipantsException
     */
    public List<Group> createGroups(Project project, int groupsize)
            throws JsonProcessingException, JAXBException, WrongNumberOfParticipantsException {

        // convert to groupalformat
        ParticipantsHolder responses = profileDAO.getResponses(project);

        responses = adjustUserCount(responses);

        //JacksonPojoToJson.writeObjectAsXML(responses);
        int participantCount = responses.getParticipants().size();
        if (participantCount % groupsize != 0 || participantCount < 6)  {
            throw new WrongNumberOfParticipantsException();
        }

        // send to groupal
        // convert result from groupal
        Client client = ClientBuilder.newClient();
        //WebTarget webTarget = client.target(GroupAlConfig.GROUPAl_LOCAL_URL + groupsize);
        WebTarget webTarget = client.target(GroupAlConfig.GROUPAl_URL + groupsize);
        final ResponseHolder
                groupResults =
                (ResponseHolder) webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(responses, MediaType
                .APPLICATION_XML)).readEntity(ResponseHolder.class);
        assert groupResults != null;

        // persist created groups
        Groups[] groups = groupResults.getGroups();
        List<unipotsdam.gf.modules.group.Group> result = new ArrayList<>();
        for (Groups group : groups) {

            unipotsdam.gf.modules.group.Group group1 = new Group();
            Participants[] participants = group.getParticipants();
            for (Participants participant : participants) {
                User userById = userDAO.getUserById(participant.getiD());
                group1.getMembers().add(userById);
            }
            group1.setProjectName(project.getName());
            //groupDAO.persist(group1);
            result.add(group1);

        }
        return result;
    }

    protected ParticipantsHolder adjustUserCount(ParticipantsHolder responses) {
        return responses;
    }

    /**
     * like createGroups(Project project, int groupsize) with fixed group size 3
     * @param project
     * @return
     * @throws WrongNumberOfParticipantsException
     * @throws JAXBException
     * @throws JsonProcessingException
     */
    @Override
    public List<Group> calculateGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        return createGroups(project,3);
    }

    @Override
    public void addGroupRelevantData(Project project, User user, Object data) throws Exception {
        
    }

    @Override
    public void addGroupRelevantData(Project project, Object data) {

    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 6;
    }
}
