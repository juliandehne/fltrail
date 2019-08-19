package unipotsdam.gf.modules.reflection.view;

import com.google.common.base.Strings;
import org.apache.commons.collections4.CollectionUtils;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoal;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.SelectedLearningGoalsDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ManagedBean
@Path("learninggoal")
public class LearningGoalView {

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

    @Inject
    private SelectedLearningGoalsDAO selectedLearningGoalsDAO;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("projects/{projectName}")
    public Response getLearningGoal(@Context HttpServletRequest request, @PathParam("projectName") String projectName) {
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project Name was null or empty").build();
        }
        Project project = new Project(projectName);
        List<LearningGoal> learningGoals = selectedLearningGoalsDAO.getLearningGoals(project);
        if (learningGoals.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No learning goals were found in this project").build();
        }
        return Response.ok(learningGoals).build();
    }
}
