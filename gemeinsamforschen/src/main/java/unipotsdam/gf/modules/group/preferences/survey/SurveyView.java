package unipotsdam.gf.modules.group.preferences.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
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
    private SurveyMapper surveyMapper;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private ProfileDAO profileDAO;


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/project/name/{projectContext}")
    public String getProjectName(@PathParam("projectContext") String projectContext) {
        // get project where name like projectContext and is active
        String projectName = projectDAO.getActiveProject(projectContext);

        if (projectName == null) {
            // if result is empty create new project, add all the questions to it and return this
            return surveyMapper.createNewProject(projectContext);
        } else {
            return projectName;
        }
    }

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
            throws IOException {
        surveyMapper.saveData(data, projectName);
    }


}
