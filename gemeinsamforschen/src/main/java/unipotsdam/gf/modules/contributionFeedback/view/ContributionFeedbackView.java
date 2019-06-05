package unipotsdam.gf.modules.contributionFeedback.view;

import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;

@Path("/contributionfeedback")
@ManagedBean
public class ContributionFeedbackView {

    @Inject
    private IContributionFeedback contributionFeedbackService;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContributionFeedback(@PathParam("id") String id) {
        ContributionFeedback contributionFeedback = contributionFeedbackService.getContributionFeedback(id);
        if (Objects.isNull(contributionFeedback)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contributionFeedback).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContributionFeedback(@QueryParam("fullSubmissionId") String fullSubmissionId,
                                            @QueryParam("fullSubmissionPartCategory") String fullSubmissionPartCategory,
                                            @QueryParam("groupId") int groupId) {
        ContributionFeedback contributionFeedback = contributionFeedbackService.getContributionFeedback(fullSubmissionId,
                fullSubmissionPartCategory, groupId);
        if (Objects.isNull(contributionFeedback)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(contributionFeedback).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveContributionFeedback(ContributionFeedback contributionFeedback) {
        if (Objects.isNull(contributionFeedback)) {
            Response.status(Response.Status.BAD_REQUEST).entity("contributionFeedback was null").build();
        }
        if (contributionFeedback.getId() != null) {
            Response.status(Response.Status.BAD_REQUEST).entity("id was not null. Please use the put function.").build();
        }
        if (contributionFeedback.getGroupId() == 0) {
            Response.status(Response.Status.BAD_REQUEST).entity("groupId was not defined").build();
        }
        return Response.ok(contributionFeedbackService.saveContributionFeedback(contributionFeedback)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContributionFeedback(@PathParam("id") String id, ContributionFeedback contributionFeedback) {
        contributionFeedback.setId(id);
        contributionFeedbackService.updateContributionFeedback(contributionFeedback);
        return Response.ok(contributionFeedback).build();
    }

    @POST
    @Path("/finalize/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finalizeFeedback(@QueryParam("groupId") int groupId, @QueryParam("projectName") String projectName) {
        Task task = new Task();
        task.setProjectName(projectName);
        task.setTaskName(TaskName.GIVE_FEEDBACK);
        task.setProgress(Progress.FINISHED);
        contributionFeedbackService.endFeedback(task);
        Project project = projectDAO.getProjectByName(projectName);
        dossierCreationProcess.createSeeFeedBackTask(project, groupId);
        dossierCreationProcess.createCloseFeedBackPhaseTask(project);
        return Response.ok().build();
    }

    @GET
    @Path("/feedbackTarget/projectName/{projectName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedBackTarget(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        Group group = new Group();
        group.setId(dossierCreationProcess.getFeedBackTarget(project, user));
        return Response.ok(group).build();
    }
}
