package unipotsdam.gf.modules.group.preferences.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.SurveyProcess;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Path("/survey")
public class SurveyView {

    private final static Logger log = LoggerFactory.getLogger(SurveyView.class);

    @Inject
    private SurveyProcess surveyProcess;


    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private SurveyMapper surveyMapper;



    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/project/name/{projectContext}")
    public Project getProjectName(@PathParam("projectContext") String projectContext) {
        // get project where name like projectContext and is active
        return surveyProcess.getSurveyProjectName(projectContext);
    }



    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/data/project/{projectId}")
    public SurveyData getSurveyData(@PathParam("projectId") String projectId) throws Exception {
        // TODO change context to dynamic
        return surveyMapper.getItemsFromDB(GroupWorkContext.dota, true, new Project(projectId));
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/projects")
    public java.util.List<Project> getCurrentSurveyProjects() {
        List<Project> surveyProjects = projectDAO.getSurveyProjects();
        return surveyProjects;

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
    @Path("/save/projects/{projectName}")
    public void saveSurvey(
            HashMap<String, String> data, @PathParam("projectName") String projectName)
            throws IOException, RocketChatDownException, UserDoesNotExistInRocketChatException {
            surveyProcess.saveSurveyData(new Project(projectName), data);
    }


}
