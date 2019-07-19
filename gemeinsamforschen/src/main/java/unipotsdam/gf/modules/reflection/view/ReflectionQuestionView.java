package unipotsdam.gf.modules.reflection.view;

import org.apache.commons.collections4.CollectionUtils;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.session.GFContexts;

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
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@ManagedBean
@Path("reflectionquestion")
public class ReflectionQuestionView {

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private ReflectionQuestionsStoreDAO questionsStoreDAO;

    @Inject
    private GFContexts gfContexts;

    @GET
    @Path("projects/{projectName}/bulk")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnansweredReflectionQuestions(@Context HttpServletRequest req,
                                                     @PathParam("projectName") String projectName) {

        String learningGoalId = "1";
        List<ReflectionQuestion> reflectionQuestions = getUnansweredReflectionQuestions(req, projectName, learningGoalId, false);

        if (Objects.isNull(reflectionQuestions)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("user email was not in context").build();
        }

        return Response.ok(reflectionQuestions).build();
    }

    @GET
    @Path("projects/{projectName}/next")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextUnansweredReflectionQuestion(@Context HttpServletRequest req, @PathParam("projectName") String projectName) {
        List<ReflectionQuestion> reflectionQuestions = getUnansweredReflectionQuestions(req, projectName, "1", true);

        if (reflectionQuestions == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("user email was not in context").build();
        }

        if (CollectionUtils.isEmpty(reflectionQuestions)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reflectionQuestions.get(0)).build();
    }

    @GET
    @Path("store/learninggoal/{learningGoal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLearningGoalReflectionQuestionFromStore(@PathParam("learningGoal") String learningGoal) {
        List<ReflectionQuestionsStoreItem> questions = questionsStoreDAO.getLearningGoalSpecificQuestions(learningGoal);
        if (CollectionUtils.isEmpty(questions)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(questions).build();
    }

    @GET
    @Path("store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReflectionQuestionFromStore() {
        List<ReflectionQuestionsStoreItem> questions = questionsStoreDAO.getAllReflectionQuestions();
        if (CollectionUtils.isEmpty(questions)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(questions).build();
    }


    private List<ReflectionQuestion> getUnansweredReflectionQuestions(HttpServletRequest req, String projectName, String learningGoalId, boolean onlyFirstEntry) {
        try {
            String userEmail = gfContexts.getUserEmail(req);
            User user = new User(userEmail);
            Project project = new Project(projectName);
            return reflectionQuestionDAO.getUnansweredQuestions(project, user, learningGoalId, onlyFirstEntry);
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }


    }
}
