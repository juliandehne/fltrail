package unipotsdam.gf.modules.contributionFeedback.view;

import com.google.common.base.Strings;
import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.modules.contributionFeedback.model.ContributionFeedback;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.session.Lock;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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

    @Inject
    private Lock lock;

    @Inject
    private GroupDAO groupDAO;

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
    @Path("/fullSubmissionId/{fullSubmissionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContributionFeedbackForFullSubmission(@PathParam("fullSubmissionId") String fullSubmissionId) {
        if (Strings.isNullOrEmpty(fullSubmissionId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<ContributionFeedback> contributionFeedbacks = contributionFeedbackService.getContributionFeedbacksForFullSubmission(fullSubmissionId);
        if (Objects.isNull(contributionFeedbacks)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contributionFeedbacks).build();
    }

    @GET
    @Path("/id/{id}/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContributionFeedbackForCategoryInSubmission(
            @PathParam("id") String fullSubmissionId,
            @PathParam("category") String category
    ) {
        ContributionFeedback contributionFeedbacks =
                contributionFeedbackService.getContributionFeedback(fullSubmissionId, category);
        if (Objects.isNull(contributionFeedbacks)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contributionFeedbacks).build();
    }

    @POST
    @Path("/fullSubmissionId/{fullSubmissionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveContributionFeedback(ContributionFeedback contributionFeedback, @PathParam("fullSubmissionId") String fullSubmissionId) {
        if (Objects.isNull(contributionFeedback)) {
            Response.status(Response.Status.BAD_REQUEST).entity("contributionFeedback was null").build();
        }
        if (contributionFeedback.getId() != null) {
            Response.status(Response.Status.BAD_REQUEST).entity("id was not null. Please use the put function.").build();
        }
        if (contributionFeedback.getGroupId() == 0) {
            Response.status(Response.Status.BAD_REQUEST).entity("groupId was not defined").build();
        }
        ContributionFeedback contributionFeedback1 = dossierCreationProcess.saveFeedback(contributionFeedback);
        return Response.ok(contributionFeedback1).build();
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
    @Path("/finalize/projects/{projectName}/groups/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response finalizeFeedback(@PathParam("groupId") int groupId, @PathParam("projectName") String projectName) {
        dossierCreationProcess.saveFinalFeedback(groupId, new Project(projectName));
        lock.deleteLockInDB(TaskName.GIVE_FEEDBACK, groupDAO.getGroupByGroupId(groupId));
        return Response.ok().build();
    }


    @GET
    @Path("/feedbackTarget/projectName/{projectName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedBackTarget(@Context HttpServletRequest req, @PathParam("projectName") String projectName)
            throws Exception {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        Group group = new Group();
        group.setId(dossierCreationProcess.getFeedBackTarget(project, user));
        return Response.ok(group).build();
    }
}
