package unipotsdam.gf.modules.reflection.view;

import org.apache.commons.collections4.CollectionUtils;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.model.LearningGoalStudentResult;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.LearningGoalStudentResultsDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@ManagedBean
@Path("learninggoal")
public class LearningGoalView {

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

    @Inject
    private LearningGoalStudentResultsDAO learningGoalStudentResultsDAO;

    @Inject
    private IReflection reflectionService;

    @Inject
    private IExecutionProcess executionProcess;

    @Inject
    private GFContexts gfContexts;

    @GET
    @Path("store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLearningGoalsFromStore() {
        List<LearningGoalStoreItem> learningGoals = learningGoalStoreDAO.getAllStoreGoals();

        if (CollectionUtils.isEmpty(learningGoals)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(learningGoals).build();
    }

    @POST
    @Path("result")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLearningGoalStudentResult(@Context HttpServletRequest req, LearningGoalStudentResult learningGoalStudentResult) {
        try {
            if (Objects.isNull(learningGoalStudentResult)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("entity was null").build();
            }
            User user = new User(gfContexts.getUserEmail(req));
            LearningGoalStudentResult newLearningGoal = executionProcess.uploadLearningGoalResult(learningGoalStudentResult, user);
            if (newLearningGoal == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("user email was null or empty").build();
            }
            return Response.ok(newLearningGoal).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The user Email was not in the context").build();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while processing the saving of result").build();
        }
    }
}
