package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.project.Project;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/survey")
public class SurveyView {

    @GET
    @Path("/data")
    public SurveyData getSurveyData() {

        // TODO implement
        // get surveyData from db
        return null;
    }

    @GET
    @Path("/projects")
    public java.util.List<Project> getCurrentSurveyProjects() {
            // TODO implement
        return null;
    }

    @GET
    @Path("/status")
    public ProjectStatus getProjectStatus() {

        // TODO implement
        return new ProjectStatus();
    }


}
