package unipotsdam.gf.modules.reflection.view;

import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;

@Path("/reflection")
public class GeneralReflectionView {

    @Inject
    private IReflection reflectionService;

    @Inject
    private IExecutionProcess executionProcess;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private UserDAO userDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLearningGoalWithQuestions(@Context HttpServletRequest request, LearningGoalRequest learningGoalRequest) {
        if (Objects.isNull(learningGoalRequest)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("LearningGoalRequest was null").build();
        }
        try {
            String docentEmail = gfContexts.getUserEmail(request);

            User user = userDAO.getUserByEmail(docentEmail);

            if (user.getStudent()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("This should only be called by docents, not from students!").build();
            }

            LearningGoalRequestResult learningGoalRequestResult = executionProcess.saveLearningGoalsAndReflectionQuestions(learningGoalRequest);

            if (Objects.isNull(learningGoalRequestResult)) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while saving learningGoal and reflection questions.").build();
            }
            return Response.ok(learningGoalRequestResult).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while processing the request").build();
        }


    }
}
