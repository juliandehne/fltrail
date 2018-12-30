package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Path("/survey")
public class SurveyView {

    private final static Logger log = LoggerFactory.getLogger(SurveyView.class);

    @Inject
    private SurveyMapper surveyMapper;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private ProfileDAO profileDAO;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/data/project/{projectId}")
    public SurveyData getSurveyData(@PathParam("projectId") String projectId) throws Exception {
        return surveyMapper.getItemsFromDB(GroupWorkContext.DOTA, true, new Project(projectId));
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/projects")
    public java.util.List<Project> getCurrentSurveyProjects() {
        return projectDAO.getSurveyProjects();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/status")
    public ProjectStatus getProjectStatus() {

        // TODO implement
        return new ProjectStatus();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/save")
    public void saveSurvey(HashMap<String, String> data) throws IOException {
        /* ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> o = mapper.readValue(data.getBytes(), typeRef);
        System.out.println("Got " + o);*/
        System.out.println(data.toString());

        surveyMapper.saveData(data);
    }


}
