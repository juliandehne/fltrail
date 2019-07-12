package unipotsdam.gf.modules.reflection.view;

import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalResult;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/reflection")
public class GeneralReflectionView {

    @Inject
    private IReflection reflectionService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLearningGoalWithQuestions(LearningGoalRequest learningGoalRequest) {

        if (Objects.isNull(learningGoalRequest)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("LearningGoalRequest was null").build();
        }

        LearningGoalResult learningGoalResult = reflectionService.createLearningGoalWithQuestions(learningGoalRequest);

        if (Objects.isNull(learningGoalResult)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while saving learningGoal and reflection questions.").build();
        }
        return Response.ok(learningGoalResult).build();
    }
}
