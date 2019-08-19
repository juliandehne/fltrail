package unipotsdam.gf.modules.reflection.view;

import com.google.common.base.Strings;
import org.apache.commons.collections4.CollectionUtils;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionWithAnswer;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionsStoreItem;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsToAnswerDAO;
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
    private ReflectionQuestionsToAnswerDAO reflectionQuestionsToAnswerDAO;

    @Inject
    private ReflectionQuestionsStoreDAO questionsStoreDAO;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private IReflection reflectionService;

    @GET
    @Path("projects/{projectName}/bulk")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnansweredReflectionQuestions(@Context HttpServletRequest req,
                                                     @PathParam("projectName") String projectName) {

        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name is null or empty").build();
        }

        List<ReflectionQuestion> reflectionQuestions = getUnansweredReflectionQuestions(req, projectName, false);

        if (Objects.isNull(reflectionQuestions)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("user email was not in context").build();
        }

        return Response.ok(reflectionQuestions).build();
    }

    @GET
    @Path("projects/{projectName}/learninggoal/{learningGoalId}/next")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextUnansweredReflectionQuestion(@Context HttpServletRequest req,
                                                        @PathParam("projectName") String projectName,
                                                        @PathParam("learningGoalId") String learningGoalId) {

        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name is null or empty").build();
        }

        if (Strings.isNullOrEmpty(learningGoalId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("learning goal id is null or empty").build();
        }

        List<ReflectionQuestion> reflectionQuestions = getUnansweredReflectionQuestions(req, projectName, true);

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

        if (Strings.isNullOrEmpty(learningGoal)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("learning goal is null or empty").build();
        }

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

    @GET
    @Path("projects/{projectName}/answered")
    public Response getAllAnsweredReflectionQuestionWithAnswers(@Context HttpServletRequest request, @PathParam("projectName") String projectName) {

        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name is null or empty").build();
        }

        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);

            List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswers =
                    user.getStudent()
                            ? reflectionService.getAnsweredReflectionQuestionsFromUser(project, user)
                            : reflectionService.getAnsweredReflectionQuestions(project);

            if (reflectionQuestionWithAnswers.isEmpty()) {
                Response.status(Response.Status.NOT_FOUND).entity("No answered reflection questions found").build();
            }

            return Response.ok(reflectionQuestionWithAnswers).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("user was not in session").build();
        }
    }

    private List<ReflectionQuestion> getUnansweredReflectionQuestions(HttpServletRequest req, String projectName, boolean onlyFirstEntry) {
        try {
            String userEmail = gfContexts.getUserEmail(req);
            User user = new User(userEmail);
            Project project = new Project(projectName);
            return reflectionQuestionsToAnswerDAO.getUnansweredQuestions(project, user, onlyFirstEntry);
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }
}
