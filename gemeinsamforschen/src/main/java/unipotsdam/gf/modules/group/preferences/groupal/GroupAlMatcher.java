package unipotsdam.gf.modules.group.preferences.groupal;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.groupal.request.ParticipantsHolder;
import unipotsdam.gf.modules.group.preferences.groupal.response.ResponseHolder;
import unipotsdam.gf.modules.project.Project;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class GroupAlMatcher {

    @Inject
    private ProfileDAO profileDAO;

    public Boolean createGroups(Project project, int groupsize) throws JsonProcessingException {
        // get responses
        //profileDAO.get
        final ParticipantsHolder responses = profileDAO.getResponses(project);

        JacksonPojoToJson.writeObject(responses);


        if (responses.getParticipants().size() % groupsize != 0)  {
            return false;
        }

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(GroupAlConfig.GROUPAl_LOCAL_URL + groupsize);
        final ResponseHolder
                post =
                (ResponseHolder) webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(responses, MediaType
                .APPLICATION_XML)).readEntity(ResponseHolder.class);

        System.out.println(post.toString());

        assert post != null;

        // convert to groupalformat

        // send to groupal

        // convert result from groupal

        // persist created groups


        return true;
    }
}
