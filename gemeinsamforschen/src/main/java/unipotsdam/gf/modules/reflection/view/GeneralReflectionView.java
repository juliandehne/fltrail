package unipotsdam.gf.modules.reflection.view;

import com.google.common.base.Strings;
import com.itextpdf.text.DocumentException;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequest;
import unipotsdam.gf.modules.reflection.model.LearningGoalRequestResult;
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
    private SubmissionController submissionController;

    @Inject
    private IReflection reflectionService;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectLearningGoalWithQuestions(@Context HttpServletRequest request, LearningGoalRequest learningGoalRequest) {
        if (Objects.isNull(learningGoalRequest)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("LearningGoalRequest was null").build();
        }
        try {
            String docentEmail = gfContexts.getUserEmail(request);
            User user = userDAO.getUserByEmail(docentEmail);

            if (user.getStudent()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("This must only be called by docents, not from students!").build();
            }

            LearningGoalRequestResult learningGoalRequestResult = executionProcess.selectLearningGoalAndReflectionQuestions(learningGoalRequest);

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("projects/{projectName}")
    public Response getSelectedLearningGoalsWithQuestions(@PathParam("projectName") String projectName) {
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project name is null or empty").build();
        }

        Project project = new Project(projectName);
        List<LearningGoalRequestResult> learningGoalRequestResultList = reflectionService.getSelectedLearningGoalsAndReflectionQuestions(project);

        return Response.ok(learningGoalRequestResultList).build();
    }

    @POST
    @Path("projects/{projectName}/finish")
    public Response finishLearningGoalWithQuestionSelection(@PathParam("projectName") String projectName) {
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project name is null or empty").build();
        }
        try {
            Project project = new Project(projectName);
            executionProcess.finalizeLearningGoalsAndReflectionQuestionsSelection(project);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while ending learning goal and reflection question choice").build();
        }
    }

    @GET
    @Path("projects/{projectName}/portfolioentries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroupPortfolioEntries(@Context HttpServletRequest request, @PathParam("projectName") String projectName) {
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project name is null or empty").build();
        }

        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);

            return Response.ok(reflectionService.getGroupAndPublicVisiblePortfolioEntriesByUser(user, project)).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();
        }
    }

    @POST
    @Path("/projects/{projectName}/portfolioentries")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectPortfolioEntries(@Context HttpServletRequest request, @PathParam("projectName") String projectName, List<FullSubmission> selectedPortfolioEntries) {
        if (selectedPortfolioEntries == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("selectedPortfolioEntries was null").build();
        }
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name was null or empty").build();
        }

        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);
            executionProcess.selectPortfolioEntries(project, user, selectedPortfolioEntries);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while processing the request").build();
        }
    }

    @GET
    @Path("/projects/{projectName}/portfolioentries/selected")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSelectedGroupPortfolioEntries(@Context HttpServletRequest request, @PathParam("projectName") String projectName) {
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name was null or empty").build();
        }

        Project project = new Project(projectName);
        return Response.ok(submissionController.getSelectedGroupPortfolioEntries(project)).build();
    }

    @POST
    @Path("/projects/{projectName}/save/group/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveGroupPortfolioEntriesPdf(@Context HttpServletRequest request,
                                                 @PathParam("projectName") String projectName, String html,
                                                 @PathParam("groupId") int groupId) {
        if (Strings.isNullOrEmpty(html)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("html to save was null or empty").build();
        }
        if (Strings.isNullOrEmpty(projectName)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("project name was null or empty").build();
        }

        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("groupId is 0.").build();
        }

        try {
            Project project = new Project(projectName);
            executionProcess.saveGroupSubmissionPdf(project, groupId, html);
            return Response.ok().build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context.").build();
        } catch (DocumentException e) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while saving the group submission as pdf").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unknown error happens while saving the document").build();
        }
        return Response.ok().build();
    }


}
