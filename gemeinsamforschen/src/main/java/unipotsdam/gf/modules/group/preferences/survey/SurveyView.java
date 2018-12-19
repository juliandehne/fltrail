package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/survey")
public class SurveyView {
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


}
