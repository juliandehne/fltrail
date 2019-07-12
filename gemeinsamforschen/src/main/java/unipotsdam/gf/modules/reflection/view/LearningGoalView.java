package unipotsdam.gf.modules.reflection.view;

import org.apache.commons.collections4.CollectionUtils;
import unipotsdam.gf.modules.reflection.model.LearningGoalStoreItem;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ManagedBean
@Path("learninggoal")
public class LearningGoalView {

    @Inject
    private LearningGoalStoreDAO learningGoalStoreDAO;

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


}
