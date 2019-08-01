package unipotsdam.gf.modules.reflection.view;

import com.google.common.base.Strings;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionAnswer;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestionWithAnswer;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/reflection")
public class GeneralReflectionView {

    @Inject
    private IExecutionProcess executionProcess;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Inject
    private SubmissionController submissionController;

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

    @POST
    @Path("/material/chosen/projects/{projectName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response chooseMaterialForAssessment(@Context HttpServletRequest request, @PathParam("projectName") String projectName, String html) {
        if (Strings.isNullOrEmpty(html)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("html was empty or null").build();
        }

        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name was null or empty").build();
        }

        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);
            executionProcess.chooseAssessmentMaterial(project, user, html);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while processing the request").build();
        }
    }

    @GET
    @Path("/material/choose/projects/{projectName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaterialToChooseForAssessment(@Context HttpServletRequest request, @PathParam("projectName") String projectName) {
        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);

            List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswers = new ArrayList<>();
            // if it will be to slow: reImplement on database level
            List<ReflectionQuestion> reflectionQuestions = reflectionQuestionDAO.getReflectionQuestions(project, user);
            reflectionQuestions.forEach(reflectionQuestion -> {
                FullSubmission fullSubmission = submissionController.getFullSubmission(reflectionQuestion.getFullSubmissionId());
                ReflectionQuestionAnswer answer = new ReflectionQuestionAnswer(fullSubmission);
                ReflectionQuestionWithAnswer reflectionQuestionWithAnswer = new ReflectionQuestionWithAnswer(reflectionQuestion, answer);
                reflectionQuestionWithAnswers.add(reflectionQuestionWithAnswer);

            });
            return Response.ok(reflectionQuestionWithAnswers).build();
        } catch (IOException ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();

        }

    }

}
