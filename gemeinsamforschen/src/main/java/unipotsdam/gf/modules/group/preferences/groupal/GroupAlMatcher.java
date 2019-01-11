package unipotsdam.gf.modules.group.preferences.groupal;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.groupal.request.ParticipantsHolder;
import unipotsdam.gf.modules.group.preferences.groupal.response.Groups;
import unipotsdam.gf.modules.group.preferences.groupal.response.Participants;
import unipotsdam.gf.modules.group.preferences.groupal.response.ResponseHolder;
import unipotsdam.gf.modules.project.Management;
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

public class GroupAlMatcher {

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;


    public Boolean createGroups(Project project, int groupsize)
            throws JsonProcessingException, JAXBException, WrongNumberOfParticipantsException {
        // get responses
        //profileDAO.get
        // convert to groupalformat
        final ParticipantsHolder responses = profileDAO.getResponses(project);
        //JacksonPojoToJson.writeObjectAsXML(responses);

        int participantCount = responses.getParticipants().size();
        if (participantCount % groupsize != 0 || participantCount < 6)  {
            throw new WrongNumberOfParticipantsException();
        }

        // send to groupal
        // convert result from groupal
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(GroupAlConfig.GROUPAl_URL + groupsize);
        final ResponseHolder
                groupResults =
                (ResponseHolder) webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(responses, MediaType
                .APPLICATION_XML)).readEntity(ResponseHolder.class);
        assert groupResults != null;

        // persist created groups
        Groups[] groups = groupResults.getGroups();
        for (Groups group : groups) {

            unipotsdam.gf.modules.group.Group group1 = new Group();
            Participants[] participants = group.getParticipants();
            for (Participants participant : participants) {
                //
                User userById = userDAO.getUserById(participant.getiD());
                group1.getMembers().add(userById);
            }
            group1.setProjectName(project.getName());
            groupDAO.persist(group1);
        }
        return true;
    }
}
