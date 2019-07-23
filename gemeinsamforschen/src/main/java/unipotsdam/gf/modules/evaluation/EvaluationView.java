package unipotsdam.gf.modules.evaluation;

import javafx.application.Application;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;

@Path("/evaluation")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EvaluationView {


    @Inject
    EvaluationDAO evaluationDAO;

    @Inject
    GFContexts gfContexts;

    private final EvaluationsQuestions evaluationsQuestions;

    public EvaluationView() {
        this.evaluationsQuestions = new EvaluationsQuestions();
    }

    @GET
    @Path("/sus/data/project/{projectName}")
    @Produces()
    public SurveyData getSUSQuestions(@PathParam("projectName") String projectName) {
        return evaluationsQuestions.getSusSurveyData();
    }

    @POST
    @Path("/sus/data/project/{projectName}/send")
    @Produces()
    public void addEvaluation(@Context HttpServletRequest req, @PathParam("projectName") String projectName, HashMap<String, String> data)
            throws Exception {
        User userFromSession = gfContexts.getUserFromSession(req);
        evaluationDAO.addSUSData(userFromSession, new Project(projectName), data);
    }


}
